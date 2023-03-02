import java.util.Scanner;

/**
 * 요구하는 출력
 * - 가장 큰 값을 가지는 경로
 *
 * 전략
 * - DP를 활용한다.
 * - 현재 층의 현재 위치까지 올 수 있는 최대값을 계산하면서 내려가기
 * - 맨 마지막 층에서 가장 큰 값을 가지는 곳을 리턴
 * - 삼각형의 크기는 500, 범위는 9999이니 int 타입으로 계산해도 괜찮다
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] dp = new int[n+1][n+1];
        int[][] triangle = new int[n+1][n+1];
        for(int i =1; i<=n; i++){
            for(int j =1; j<=i; j++){
                triangle[i][j] = sc.nextInt();
            }
        }
        //계산
        for(int i =1; i<=n; i++){
            for(int j =1; j<=i; j++){
                dp[i][j] += Math.max(dp[i-1][j-1], dp[i-1][j]) + triangle[i][j];
            }
        }
        //마지막 줄에서 가장 큰 값을 찾자
        int max_route = 0;
        for(int i =1; i<=n; i++){
            max_route = Math.max(max_route, dp[n][i]);
        }
        //결과 출력
        System.out.println(max_route);
    }
}