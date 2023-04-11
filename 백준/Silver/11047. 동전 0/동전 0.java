import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * #그리디알고리즘_특집
 *
 * 요구하는 출력 : 필요한 동전 개수의 최소값
 *
 * 풀이 방법 : 가장 높은 동전들부터 개수를 채운다.
 *
 * 그리디 알고리즘으로 풀이가 가능한 이유?
 * - 동전의 가치가 최소 2배 이상 벌어져 있기 때문에, 큰 가치의 동전을 우선적으로 채워넣는 것이 보장이 되기 때문
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //동전의 종류
        int K = Integer.parseInt(st.nextToken());   //동전으로 만들고자 하는 가치의 합

        int[] coins = new int[N];   //동전의 각 가치
        for(int i =0 ; i<N; i++){
            coins[i] = Integer.parseInt(br.readLine());
        }

        int total_count = 0;
        int rest_value = K;
        //가장 큰 가치의 동전부터 순서대로 개수를 채워본다.
        for(int i = N-1; i>=0; i--){
            int current_count = rest_value / coins[i];  //이번 동전으로 채우는 개수
            total_count += current_count;
            rest_value -= current_count * coins[i];

            if(rest_value == 0){    //남은 가치 합이 0이라면 종료한다.
                break;
            }
        }

        //결과 출력
        System.out.println(total_count);
    }
}