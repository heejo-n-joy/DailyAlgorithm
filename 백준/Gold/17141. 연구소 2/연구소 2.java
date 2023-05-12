import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 연구실에 바이러스를 뿌려 퍼지는데 걸리는 시간
 *
 * 풀이 방법 : 구현 (큐), 조합
 * - 1. 바이러스의 시작 위치를 선정한다.
 * - 2. 해당 위치에 바이러스를 둔다. (큐에 저장한다.)
 * - 3. 남은 빈 칸의 개수를 저장한다.
 * - 4. 시뮬레이션을 돌려본다.
 * - 모든 경우의 수에 대해 1~4를 테스트한다.
 * - 가장 큰 값을 출력한다.
 */

class Virus{
    int x;
    int y;
    int time;
    Virus(int x, int y, int time){
        this.x = x;
        this.y = y;
        this.time = time;
    }

    Virus(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static final char WALL = 'X';
    static final char EMPTY = ' ';
    static final char VIRUS = 'V';
    static final char POSSIBLE = 'P';

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};


    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());  //맵의 크기
        int M = Integer.parseInt(stringTokenizer.nextToken());  //바이러스의 개수

        Queue<Virus> queue = new LinkedList<>();
        int rest_count = 0;

        Queue<Virus> virusQueue = new LinkedList<>();
        char[][] map = new char[N][N];
        for(int i = 0; i < N; i++){
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            for(int j = 0; j < N; j++){
                switch(Integer.parseInt(stringTokenizer.nextToken())){
                    case 0: //빈 칸이라면
                        map[i][j] = EMPTY;
                        rest_count++;
                        break;
                    case 1: //벽이라면
                        map[i][j] = WALL;
                        break;
                    case 2: //바이러스라면
                        map[i][j] = EMPTY;
                        virusQueue.offer(new Virus(i, j));
                        break;
                }
            }
        }
        Virus[] virus_all = new Virus[virusQueue.size()];
        for(int i = 0; i<virus_all.length; i++){
            virus_all[i] = virusQueue.poll();
        }

        Virus[] virus_selected = new Virus[M];

        total_result = N * N + 1;

        //실행
        exec(0, -1, M, map, N, rest_count + (virus_all.length - M), virus_all.length, virus_all, virus_selected);

        //결과 출력
        if(total_result == N*N + 1){
            total_result = -1;
        }
        System.out.println(total_result);

    }

    static int total_result;
    public static void exec(int index, int prev, int M, char[][] original_map, int N, int rest_count, int virus_count, Virus[] virus_all, Virus[] selected_virus){
        //바이러스를 다 뽑았다면
        if(index == M){
            int result = 0;
            //해당 위치를 큐에 넣는다.
            Queue<Virus> queue = new LinkedList<>();

            //맵의 깊은 복사
            char[][] map = new char[N][N];
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    map[i][j] = original_map[i][j];
                }
            }

            //선택받은 바이러스들 넣기
            for(int i = 0; i<M; i++){
                map[selected_virus[i].x][selected_virus[i].y] = VIRUS;
                queue.offer(selected_virus[i]);
            }

            //바이러스가 다 퍼질 때까지 실행
            while(!queue.isEmpty()){
                //바이러스를 꺼내기
                Virus v = queue.poll();

                result = Math.max(v.time, result);

                //상하좌우로 퍼트리기
                for(int k = 0; k < 4; k++){
                    int nextX = v.x + dr[k];
                    int nextY = v.y + dc[k];

                    //해당 위치가 배열의 범위를 벗어나면 패스
                    if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){
                        continue;
                    }

                    //해당 위치가 빈 공간이 아니라면 패스
                    if(map[nextX][nextY] != EMPTY){
                        continue;
                    }

                    //해당 위치에 바이러스를 퍼트리자
                    map[nextX][nextY] = VIRUS;
                    queue.offer(new Virus(nextX, nextY, v.time + 1));
                    rest_count--;
                }
            }

            //아직 방의 개수가 남았다면
            if(rest_count == 0){
                //값을 갱신한다.
                total_result = Math.min(result, total_result);
            }
            return;
        }

        //바이러스를 뽑자
        for(int i = prev + 1; i < virus_count; i++){
            selected_virus[index] = virus_all[i];
            exec(index+1, i,  M, original_map, N, rest_count, virus_count, virus_all, selected_virus);
        }
    }
}