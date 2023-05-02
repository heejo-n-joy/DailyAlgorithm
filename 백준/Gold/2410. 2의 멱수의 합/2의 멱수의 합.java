import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 2의 멱수의 합을 만드는 경우의 수
 *
 * 풀이 방법 : 다이나믹 프로그래밍(DP)
 * - 1. 2의 제곱수로 표현되는 자연수들을 오름차순으로 하나씩 선택한다.
 * - 2. 1부터 N까지 가능한 경우의 수를 갱신한다.
 */
public class Main {
    static final int DIVIDE_NUMBER = 1000000000;
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //만들고자 하는 수 N
        int[] DP = new int[N+1];
        int number = 1;
        DP[0] = 1;
        
        //number가 N보다 커지면 계산을 중단한다.
        while(number <= N){
            for(int i = number; i<=N; i++){
                DP[i] = (DP[i] + DP[i - number]) % DIVIDE_NUMBER;
            }
            number *= 2;

        }


        //결과 출력
        System.out.println(DP[N]);
    }
}