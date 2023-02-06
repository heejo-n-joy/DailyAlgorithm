import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 피보나치 수열을 빠르게 풀기
 * <p>
 * 요구하는 출력
 * - 피보나치 수열의 결과
 * <p>
 * 전략
 * - DP
 *
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());   //테스트케이스
        int[][] DP = new int[41][2];    //각각 0이 출력되는 횟수, 1이 출력되는 횟수
        DP[1][1] = 1;
        DP[0][0] = 1;
        for(int test_case = 1; test_case <= T; test_case++){
            int N = Integer.parseInt(br.readLine());   //주어진 수
            fib(N, DP);
            System.out.println(DP[N][0] + " " + DP[N][1]);
        }
    }

    public static int[] fib(int num, int[][] DP){
        //하나라도 0이 넘는다면
        if(DP[num][0] > 0 || DP[num][1] > 0){
            return new int[]{DP[num][0], DP[num][1]};
        }
        DP[num][0] = fib(num-1, DP)[0] + fib(num-2, DP)[0];
        DP[num][1] = fib(num-1, DP)[1] + fib(num-2, DP)[1];
        return new int[]{DP[num][0], DP[num][1]};
    }
}