import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 배추를 유기농으로 키우기 위해 필요한 배추흰지렁이의 최소 마리 수
 *
 * 풀이 방법 : BFS
 * - 배추를 입력받고, BFS를 활용하여 구역들의 수를 센다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case<=T; test_case++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());   //배추밭의 가로 길이
            int N = Integer.parseInt(st.nextToken());   //배추밭의 세로 길이
            int K = Integer.parseInt(st.nextToken());   //배추의 개수

            boolean[][] cabbage_field_cabbage = new boolean[N][M];  //배추밭의 배추 여부
            boolean[][] cabbage_field_check = new boolean[N][M];    //배추밭의 방문 여부
            for(int i = 0 ; i<K; i++){
                st = new StringTokenizer(br.readLine());
                int cabbage_m = Integer.parseInt(st.nextToken());
                int cabbage_n = Integer.parseInt(st.nextToken());
                cabbage_field_cabbage[cabbage_n][cabbage_m] = true; //배추 입력
            }

            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            int worm_count = 0;    //애벌레 개수
            //배추밭 탐색
            for(int i = 0; i<N; i++){
                for(int j = 0; j<M; j++){
                    //밭에 배추가 없다면 패스
                    if(!cabbage_field_cabbage[i][j]){
                        continue;
                    }

                    //이미 확인한 배추라면 패스
                    if(cabbage_field_check[i][j]){
                        continue;
                    }

                    //해당 구역의 배추들을 확인하자
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{i, j});
                    cabbage_field_check[i][j] = true;
                    while(!queue.isEmpty()){
                        int[] temp_cabbage = queue.poll();
                        //해당 배추를 기준으로 상하좌우를 탐색하자
                        for(int k = 0; k<4; k++){
                            int nextI = temp_cabbage[0] + dr[k];
                            int nextJ = temp_cabbage[1] + dc[k];
                            //배추밭을 넘어가면 패스
                            if(nextI < 0 || nextI >= N || nextJ < 0 || nextJ >= M){
                                continue;
                            }
                            //해당 배추밭에 배추가 없다면 패스
                            if(!cabbage_field_cabbage[nextI][nextJ]){
                                continue;
                            }
                            //해당 배추밭을 이미 확인했다면 패스
                            if(cabbage_field_check[nextI][nextJ]){
                                continue;
                            }
                            //해당 배추를 큐에 넣는다.
                            queue.offer(new int[]{nextI, nextJ});
                            cabbage_field_check[nextI][nextJ] = true;
                        }
                    }

                    //해당 구역의 배추 확인을 끝냈으면 지렁이 마리 수를 1 증가한다.
                    worm_count++;
                }
            }

            //결과 출력
            System.out.println(worm_count);
        }
    }
}