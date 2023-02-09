import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * (예전에 시도했지만 풀지 못했던) 문제 내용
 * - 최대 정사각형을 찾기
 * <p>
 * 요구하는 출력
 * - 완성될 수 있는 최대 정사각형의 한 변의 길이
 * <p>
 * 전략
 * - DP를 활용해보자.
 * - 정사각형을 만드는 것이 중요하기 때문에, 해당 위치까지의 최대 정사각형을 DP로 기록하자.
 * - 이후 [i-1][j], [i][j-1], [i-1][j-1]를 확인해서 각 위치까지의 최대 정사각형들을 파악하고
 * - 이 중 가장 작은 개수 +1을 [i][j]에 반영하기
 *
 * <p>
 * 예상 난이도 : ★ (이걸 왜 못풀었지)
 * 체감 난이도 : ★★ 역시 DP는 생각이 떠올라야 한다.
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            //두 값이 모두 0이라면 프로그램을 종료
            if(N == 0 && M == 0){
                break;
            }
            int[][] map = new int[N+1][M+1];
            for(int i =1; i<=N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j =1; j<=M; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int[][] DP = new int[N+1][M+1];
            //계산
            for(int i =1; i<=N; i++){
                for(int j =1; j<=M; j++){
                    if(map[i][j] == 1){ //해당 위치가 1이라면 (이전 값에서 나아갈 가능성이 있다)
                        DP[i][j] = min(DP[i-1][j], DP[i][j-1], DP[i-1][j-1]) + 1;   //직전의 세 구역에서 최소값을 기준으로 +1을 한다.
                    }
                    else if(map[i][j] == 0){ //해당 위치가 0이라면 바로 초기화
                        DP[i][j] = 0;
                    }
                }
            }
            //최대값 찾기
            int max_len = 0;
            for(int i =1; i<=N; i++){
                for(int j =1; j<=M; j++){
                    max_len = Math.max(max_len, DP[i][j]);
                }
            }
            //결과 출력
            System.out.println(max_len);
        }
    }

    public static int min(int a, int b, int c){
        return Math.min(a,Math.min(b,c));
    }
}