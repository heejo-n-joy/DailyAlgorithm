import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 연결된 집의 모임을 단지로 정의하고 번호를 붙인다.
 * <p>
 * 요구하는 출력
 * - 단지의 수를 출력하고, 각 단지별 집의 수를 오름차순으로 정렬하여 출력
 * <p>
 * 입력 변수
 * - N : 지도의 크기
 * - 지도 정보들
 * <p>
 * 전략
 * - bfs로 단지의 개수와 단지별 집의 수를 저장하자
 * <p>
 * 예상 난이도 : ★
 * 체감 난이도 : ★
 * <p>
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());
        int[][] map = new int[N][N];    //지도 정보
        for(int i =0; i<N; i++){
            String line = bufferedReader.readLine();
            for(int j = 0; j<N; j++){
                map[i][j] = line.charAt(j) - '0';   //지도 정보 입력
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        PriorityQueue<Integer> totalQueue = new PriorityQueue<>();
        boolean[][] check = new boolean[N][N];  //이미 탐색한 구역인지 체크
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                //해당 단지 내 집의 개수
                int count = 0;
                //이미 탐색한 지역이라면 패스
                if(check[i][j]){
                    continue;
                }
                //해당 위치에 집이 없다면 패스
                if(map[i][j]==0){
                    continue;
                }
                //탐색을 들어간다.
                count = 1;
                Queue<int[]> queue = new LinkedList<>();
                queue.offer(new int[]{i, j});
                check[i][j] = true;

                while(!queue.isEmpty()){
                    int[] temp = queue.poll();
                    for(int k = 0; k<4; k++){
                        int nextI = temp[0] + dr[k];
                        int nextJ = temp[1] + dc[k];
                        //만약 범위를 벗어나면 패스
                        if(nextI < 0 || nextI >= N || nextJ < 0 || nextJ >=N){
                            continue;
                        }
                        //만약 해당 위치를 이미 탐색했다면 패스
                        if(check[nextI][nextJ]){
                            continue;
                        }
                        //해당 위치에 집이 없다면 패스
                        if(map[nextI][nextJ]==0){
                            continue;
                        }
                        //모두 통과한다면 큐에 새롭게 넣자
                        queue.offer(new int[]{nextI, nextJ});
                        count += 1;
                        check[nextI][nextJ] = true;
                    }
                }
                //탐색 결과 1이상일 경우
                if(count>=1){
                    //해당 정보를 종합 큐에 저장한다.
                    totalQueue.offer(count);
                }
            }
        }

        //모든 구역의 탐색이 끝났다면, 개수와 정보를 출력하고 종료한다.
        System.out.println(totalQueue.size());
        while(!totalQueue.isEmpty()){
            System.out.println(totalQueue.poll());
        }
    }
}