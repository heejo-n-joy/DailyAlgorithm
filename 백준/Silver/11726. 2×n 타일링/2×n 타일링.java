import java.util.*;

/**
 * 요구하는 출력
 * - 2x1, 1x2의 타일들로 만들 수 있는 2xn의 타일 배치 경우의 수
 * <p>
 * 이 문제는 어떻게 풀까?
 * - DP를 활용한다.
 * - 1. 2칸 짧은 배치에서 가로로 된 배치를 하는 것
 * - 2. 1칸 짧은 배치에서 세로로 된 배치를 하는 것
 *
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   //채우고 싶은 타일 배치의 가로 길이
        int[] dp = new int[n+1];    //1부터 n까지
        dp[1] = 1;  //초기 입력값
        if(n>=2) {
            dp[2] = 2;  //초기 입력값
        }
        for(int i = 3; i<=n; i++){
            dp[i] = (dp[i-1] + dp[i-2]) % 10007;
        }
        //결과 출력
        System.out.println(dp[n]);
    }
}