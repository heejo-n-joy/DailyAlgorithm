import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - n가지 동전들을 활용해서 k원을 만들자
 * <p>
 * 요구하는 출력
 * - k원을 만들 수 있는 동전의 최소 개수
 * <p>
 * 주의사항
 * - 가치가 같은 동전이 여러 번 주어질 수 있다.
 * - 순서만 다른 구성은 같은 것으로 취급한다.
 *
 * <p>
 * 전략
 * - DP문제.
 * - DP 배열의 값을 동전 개수로 표시하기
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
        StringTokenizer st = new StringTokenizer((br.readLine()));
        int n = Integer.parseInt(st.nextToken());    //동전의 종류
        int k = Integer.parseInt(st.nextToken());    //원하는 결과
        int[][] dp = new int[n][k+1];   //dp 배열
        for(int i = 0; i<n; i++){
            for(int j =0; j<k+1; j++){
                dp[i][j] = k+1;   //최소값을 찾기 위해 기본값을 최대값으로 설정
            }
        }
        int[] coins = new int[n];
        for(int i = 0; i<n; i++){
            coins[i] = Integer.parseInt(br.readLine());
            if(coins[i] < k+1) {
                dp[i][coins[i]] = 1;
            }
        }

        for(int i =0; i<n; i++){
            for(int j =0; j<k+1; j++){
                if(i-1>=0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);    //이전 동전들까지의 결과와 비교
                }
                if(j-coins[i]>=0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i]] + 1); //현재 동전을 추가한다.
                }
            }
        }

        //결과 출력
        int result = dp[n-1][k];
        if(result == k+1){
            result = -1;    //불가능한 경우
        }
        System.out.println(result);
    }
}