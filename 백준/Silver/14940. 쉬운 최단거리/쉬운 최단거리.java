import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 지도와 모든 목표지점까지의 최단거리
 * <p>
 * 요구하는 출력
 * - 지도와 목표지점까지의 거리들
 * <p>
 * 전략
 * - BFS
 * - check[][]로 해당 위치를 방문했는지를 파악
 * - result[][]에 해당 위치까지의 거리를 저장
 * <p>
 * 주의사항
 * - 원래 갈 수 없는 땅의 위치에는 0을 출력
 * - 이론적으로 갈 수는 있지만, 실제로는 못 가는 땅에는 -1 출력
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Coordinate{
    int x;
    int y;
    int distance;
    Coordinate(int x, int y, int distance){
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
}
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());   //세로
        int m = Integer.parseInt(st.nextToken());   //가로
        int[][] map = new int[n][m];
        boolean[][] check = new boolean[n][m];
        int[][] result = new int[n][m];
        Coordinate start = new Coordinate(-1, -1, -1);
        for(int i = 0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<m; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 2){
                    start.x = i;
                    start.y = j;
                    start.distance = 0;
                }
            }
        }
        //만약 start(값 2)가 없다면
        if(start.x == -1){
            //ERROR
            return;
        }
        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(new Coordinate(start.x, start.y, start.distance));
        check[start.x][start.y] = true;
        result[start.x][start.y] = start.distance;
        while(!queue.isEmpty()){
            Coordinate coordinate = queue.poll();
            for(int k = 0; k<4; k++){
                int nextX = coordinate.x + dr[k];
                int nextY = coordinate.y + dc[k];
                int nextDistance = coordinate.distance + 1;
                //지도의 범위를 벗어나는건 패스
                if(nextX < 0 || nextX >= n || nextY < 0 || nextY >= m){
                    continue;
                }

                //이미 확인을 한 공간이라면 패스
                if(check[nextX][nextY]){
                    continue;
                }

                //만약에 벽이라면 패스
                if(map[nextX][nextY] == 0){
                    continue;
                }

                //모든 조건을 통과한다면 큐에 넣는다.
                check[nextX][nextY] = true;
                result[nextX][nextY] = nextDistance;
                queue.offer(new Coordinate(nextX, nextY, nextDistance));
            }
        }

        //후보정 - 원래 갈 수 없는 땅인가? 아니면 그냥 못갔던건가?
        for(int i =0; i<n; i++){
            for(int j =0; j<m; j++){
                if(result[i][j] == 0){
                    //출발지라면 패스
                    if(i == start.x && j == start.y){
                        continue;
                    }
                    //원래 벽이라면 패스
                    if(map[i][j] == 0){
                        continue;
                    }
                    //그게 아니라면 -1
                    result[i][j] = -1;
                }
            }
        }

        //결과 출력
        for(int i =0; i<n; i++){
            for(int j =0; j<m; j++){
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
}