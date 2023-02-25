import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 문제 내용
 * - 1, 2, 3을 합하여 숫자를 표현하기
 * <p>
 * 요구하는 출력
 * - 나타낼 수 있는 경우의 수
 * <p>
 * 전략
 * - 순서가 다른 것 또한 경우의 수에 포함된다.
 * - DP로 문제를 풀자
 * - d[n-3]에 +3 숫자를 넣은 경우, d[n-2]에 +2 숫자를 넣은 경우, d[n-1]에 +1 숫자를 넣은 경우
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트 케이스
        for(int test_case = 1; test_case <= T; test_case++){
            int N = sc.nextInt();
            int[] DP = new int[N+1];
            DP[0] = 1;
            for(int j = 1; j<=N; j++){
                for(int k = 1; k<=3; k++){
                    if(j-k>=0){
                        DP[j] += DP[j-k];
                    }
                }
            }
            //결과 출력
            System.out.println(DP[N]);
        }
    }
}