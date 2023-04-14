import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 동전들을 가지고 주어진 금액을 만들 수 있는 방법의 수
 *
 * 풀이 방법 : DP
 * - 1. 동전을 하나 선택한다.
 * - 2. 동전으로 만들 수 있는 금액들(1원부터 M원까지)을 하나씩 보며 표에 값을 +1 한다.
 * - 3. 모든 동전으로 1,2번을 반복한다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());   //동전의 가지 수
            int[] coins = new int[N];   //동전의 종류 저장
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i =0 ;i<N; i++){
                coins[i] = Integer.parseInt(st.nextToken());
            }
            int M = Integer.parseInt(br.readLine());   //만들고자 하는 금액

            int[][] DP = new int[N][M+1];
            DP[0][0] = 1;
            for(int coin = 0; coin<N; coin++){
                //동전을 선택한 후, 1원부터 M원까지 계산한다.
                for(int i = 0; i <= M; i++){
                    //1)지금 동전까지 사용해서 만든 현재 금액 - 동전 금액
                    //2)이전 동전까지 사용해서 만든 현재 금액
                    DP[coin][i] += (i>=coins[coin]?DP[coin][i-coins[coin]]:0) + (coin>=1?DP[coin-1][i]:0);
                }
            }

            //결과출력
            System.out.println(DP[N-1][M]);
        }
    }
}