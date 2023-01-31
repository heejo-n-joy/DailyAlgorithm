import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 동전으로 가치 만들기
 * <p>
 * 요구하는 출력
 * - 해당 가치를 만들 수 있는 경우의 수
 * <p>
 * 주의사항
 * - 동전은 몇 개라도 사용할 수 있다
 * - 순서만 바뀐 경우의 수는 같은 경우로 취급한다
 * <p>
 * 전략
 * - DP문제.
 * - 가치 1을 만들 수 있는 경우의 수, 가치 2를 만들 수 있는 경우의 수, ..., 가치 k를 만들 수 있는 경우의 수 느낌으로 가자
 * - 동전의 종류는 n가지니까 [n][k] 배열을 활용하자
 * - 같은 종류가 순서만 바뀌면 안되니까 배열 윗줄들부터 천천히 내려오자
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());    //동전의 종류
        int k = Integer.parseInt(st.nextToken());    //원하는 가치
        int[] coins = new int[n];   //동전의 정보
        int[][] dp = new int[n][k+1];
        for(int i =0; i<n; i++){
            coins[i] = Integer.parseInt(br.readLine());
            if(coins[i] <= k) {
                dp[i][coins[i]] = 1;
            }
        }

        for(int i =0; i<n; i++){
            for(int j = 1; j<=k; j++){
                //지금 뽑은 코인을 사용하지 않는다: 기존에 쌓아온 경우의 수를 불러온다.
                if(i>0){
                    dp[i][j] += dp[i-1][j];
                }
                //지금 뽑은 코인을 사용한다.
                if(j-coins[i]>0){
                    dp[i][j] += dp[i][j-coins[i]];
                }
            }
        }

        //결과 출력
        System.out.println(dp[n-1][k]);

    }
}