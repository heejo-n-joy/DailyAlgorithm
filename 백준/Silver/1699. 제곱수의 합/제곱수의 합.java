import java.util.Scanner;

/**
 * 요구하는 출력
 * - 자연수 N을 a2 + b2 + c2 + ... 로 나타낼 수 있을 때, 최소 항의 개수를 구하라
 *
 * 풀이 방법
 * - 다이나믹 프로그래밍을 쓴다.
 * - DP 배열의 기본값을 인덱스 자기 자신의 수로 한다. (최대값으로 지정)
 * - 이후 제곱수를 이용하여 DP를 갱신한다.
 *
 * 시간복잡도
 * - O(logN) : 제곱수를 뽑기
 * - O(N) : DP 배열을 비교 및 갱신하기
 * - 총 O(NlogN)
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] DP = new int[N+1];
        //1. 배열의 기본값들을 자기 자신의 수로 넣는다. (1의 제곱으로만 이루어져 있는 경우가 최대값이기 때문)
        for(int i = 0 ; i<=N; i++){
            DP[i] = i;
        }
        //실행. 제곱수를 순차적으로 DP 배열에 갱신한다.
        for(int i = 2; i<=Math.sqrt(N); i++){
            int val = (int) Math.pow(i, 2); //제곱수 생성
            //DP 배열을 0부터 N까지 확인한다. (val보다 작은 값에서는 갱신이 되지 않으니 val부터 N까지 확인)
            for(int j = val; j<= N; j++){
                DP[j] = Math.min(DP[j], DP[j-val] + 1);
            }

        }

        //결과출력
        System.out.println(DP[N]);
    }
}