import java.util.Scanner;

/**
 * 문제 내용
 * - 정수들이 나열되어 있다.
 * <p>
 * 요구하는 출력
 * - 연속된 몇 개의 수를 선택해서 구할 수 있는 가장 큰 합
 * <p>
 * 전략
 * - DP를 이용하여 문제를 푼다.
 * - 현재 수를 포함한 값 vs 현재 수로 시작되는 값을 비교하며 저장하자.
 * - 역대 최대값도 변수로 저장하자
 * - 가능한 최대값 : 1,000 x 100,000 = 100,000,000 (int로도 커버 가능)
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] input = new int[n];    //입력값
        int[] dp = new int[n];    //DP
        for(int i =0; i<n; i++){
            input[i] = sc.nextInt();
        }
        dp[0] = input[0];
        int max = input[0]; //초기 최대값은 첫 수의 값
        //dp로 탐색
        for(int i =1; i<n; i++){
            dp[i] = Math.max(dp[i-1] + input[i], input[i]); //이전 dp에 현재 값을 더한 것과, 현재 값부터 시작하는 것 중 큰 값을 입력
            max = Math.max(dp[i], max); //혹시 역대 최대값이 갱신되었다면 반영하기 
        }

        //결과출력
        System.out.println(max);
    }
}