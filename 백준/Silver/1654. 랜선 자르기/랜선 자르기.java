import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : K개의 랜선을 잘라 N개 이상의 랜선을 만들 때, 만들 수 있는 최대길이
 *
 * 풀이 방법 : 이분탐색
 * - 완성될 cm의 랜선을 기준으로 생각한다.
 * - 범위는 1부터 K개읠 랜선들 중 가장 긴 랜선까지 할당한다.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
        int K = Integer.parseInt(st.nextToken());   //주어진 개수
        int N = Integer.parseInt(st.nextToken());   //만들고자 하는 개수
        long[] lan_cable = new long[K];
        long max_len_in_K = 0;
        for(int k = 0; k<K; k++){
            lan_cable[k] = Integer.parseInt(bufferedReader.readLine());
            max_len_in_K = Math.max(max_len_in_K, lan_cable[k]);
        }

        //실행(이분 탐색)
        long result = findMaxLanCableLen(1, (1 + max_len_in_K) / 2, max_len_in_K, lan_cable, K, N);

        //결과 출력
        System.out.println(result);
    }

    public static long findMaxLanCableLen(long start, long current_len, long end, long[] lan_cable, int K, int N){
        long result = 0;
        while(true) {
            //만약 start가 end보다 크다면, 더이상 유효한 식이 나오지 않는다.
            if (start > end) {
                return result;
            }
            //만들 수 있는 케이블 개수를 세보자
            long count = 0;
            for (int i = 0; i < K; i++) {
                count += lan_cable[i] / current_len;
            }
            //만약 원하는 개수를 만들었다면
            if (count >= N) {
                result = Math.max(result, current_len);
                start = current_len + 1;
            }
            //원하는 개수를 만들지 못했다면
            else {
                //이전 값으로 넘어가자
                end = current_len - 1;
            }
            current_len = (start + end) / 2;
        }
    }
}