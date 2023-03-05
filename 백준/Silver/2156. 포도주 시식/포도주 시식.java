import java.util.Scanner;

/**
 * 요구하는 출력
 * - 효주가 포도주 시식회에서 마실 수 있는 포도주의 최대 양
 *
 * 전략(DP)
 * - 포도주를 3일 연속 먹을 수 없으니, 어제와 오늘의 상태를 저장하는 DP 배열을 활용하자
 * - [0]은 이거 안마신다. = 이전 [0],[1],[2]중 최대값
 * - [1]은 전꺼 마셨다. = 이전 [0] + 이번 포도주
 * - [2]은 두개 연속 마셨다. = 이전 [1] + 이번 포도주
 * - 마지막 결과에서 가장 큰 값을 리턴
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   //시식회에 있는 포도주 개수
        int[] wine = new int[n];    //포도주 정보
        for(int i = 0; i<n; i++){
            wine[i] = sc.nextInt(); //포도주 정보를 입력받기
        }
        int[][] dp = new int[n][3];
        dp[0][0] = 0;
        dp[0][1] = wine[0];
        dp[0][2] = wine[0];
        for(int i =1; i<n; i++){
            dp[i][0] = Math.max(Math.max(dp[i-1][0], dp[i-1][1]), dp[i-1][2]);
            dp[i][1] = dp[i-1][0] + wine[i];
            dp[i][2] = dp[i-1][1] + wine[i];
        }
        //결과 출력
        System.out.println(Math.max(Math.max(dp[n-1][0], dp[n-1][1]), dp[n-1][2]));
    }
}