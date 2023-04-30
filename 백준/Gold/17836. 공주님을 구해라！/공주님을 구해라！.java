import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 공주를 구할 수 있는 최소 시간 (구할 수 없다면 fail)
 *
 * 풀이 방법 : 너비 우선 탐색(BFS)
 * - 1. 입력받은 맵을 너비 우선 탐색으로 공주까지의 최단 거리를 구한다.
 * - 2. 용사가 그람을 잡을 수 있는지 확인하고 ,가능하다면 용사-그람까지의 직선 거리 + 그람-공주까지의 직선 거리를 구한다.
 * - 3. 둘 중 최소값이 T를 넘으면 fail, 아니라면 최소값을 출력한다.
 */

class Loc{
    int x;
    int y;
    Loc(int x, int y){
        this.x = x;
        this.y = y;
    }
}

class TimeLoc extends Loc{
    int time;

    TimeLoc(int x, int y, int time) {
        super(x, y);
        this.time = time;
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());  //성의 크기
        int M = Integer.parseInt(stringTokenizer.nextToken());  //성의 크기
        int T = Integer.parseInt(stringTokenizer.nextToken());  //제한 시간

        int[][] castle = new int[N][M];
        Loc sword = new Loc(-1, -1);
        for(int i = 0 ; i<N; i++){
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            for(int j = 0; j<M; j++){
                castle[i][j] = Integer.parseInt(stringTokenizer.nextToken());
                //만약 그람을 발견한다면 해당 위치를 저장하기
                if(castle[i][j] == 2){
                    sword.x = i;
                    sword.y = j;
                }
            }
        }

        //1. BFS로 최단 거리 구하기
        int bfs_result = bfs(0, 0, N-1, M-1, castle, N, M);

        //2. 그람을 구할 수 있는지 확인해보자.
        int gram_result = bfs(0, 0, sword.x, sword.y, castle, N, M);
        gram_result += (N - 1 - sword.x) + (M - 1 - sword.y);

        //3. 둘 중 최소값을 찾는다.
        int result = Math.min(bfs_result, gram_result);

        //결과 출력
        if(result == N * M + 1 || result > T){
            System.out.println("Fail");
        }
        else{
            System.out.println(result);
        }
    }
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static int bfs(int startX, int startY, int endX, int endY, int[][] map, int N, int M){

        boolean[][] check = new boolean[N][M];
        Queue<TimeLoc> queue = new LinkedList<>();
        queue.offer(new TimeLoc(startX, startY, 0));
        check[startX][startY] = true;
        int bfs_result = N * M + 1;
        while(!queue.isEmpty()){
            TimeLoc temp = queue.poll();

            //만약 도착했다면 종료
            if(temp.x == endX && temp.y == endY){
                bfs_result = Math.min(bfs_result, temp.time);
                break;
            }

            for(int k = 0; k<4; k++){
                int nextX = temp.x + dr[k];
                int nextY = temp.y + dc[k];

                //만약 배열의 범위를 벗어나면 패스
                if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= M){
                    continue;
                }

                //만약 해당 위치가 벽이라면 패스
                if(map[nextX][nextY] == 1){
                    continue;
                }

                //이미 확인한 곳이라면 패스
                if(check[nextX][nextY]){
                    continue;
                }

                //그게 아니라면 큐에 새로 추가한다.
                queue.offer(new TimeLoc(nextX, nextY, temp.time + 1));
                check[nextX][nextY] = true;
            }
        }

        return bfs_result;
    }
}