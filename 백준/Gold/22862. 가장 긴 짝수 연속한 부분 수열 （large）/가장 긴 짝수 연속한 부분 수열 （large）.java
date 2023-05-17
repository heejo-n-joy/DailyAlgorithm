import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * 요구하는 출력 : 가장 긴 연속한 부분 수열의 길이 (짝수로만 이루어진)
 *
 * 풀이 방법 : 리스트
 * - 1. 홀수의 위치들만 List에 저장해둔다.
 * - 2. 수열의 범위는 시작 짝수부터(홀수index+1) 마지막 짝수까지(홀수index-1)
 * - 3. 수열 사이에 있어야 하는 홀수 개수를 K개 유지해주자.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());   //전체 길이
        int K = Integer.parseInt(st.nextToken());   //수열 내 홀수를 지울 수 있는 횟수

        List<Integer> S = new ArrayList();
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<N; i++){
            int num = Integer.parseInt(st.nextToken());
            if(num % 2 == 1){
                S.add(i);
            }
        }

        //수열
        int current_odd_index = 0;
        int start_index = 0;
        int result = 0;
        if(S.size() > K){   //수열 내 전체 홀수의 개수가 K개보다 많다면
            S.add(N);
            int end_index = S.get(K) - 1;   //초기 위치 잡아주기
            result = end_index - start_index + 1 - K;
            while(true){
                current_odd_index++;    //홀수 인덱스
                if(S.size() > K + current_odd_index){
                    start_index = S.get(current_odd_index-1) + 1;   //시작 인덱스
                    end_index = S.get(K + current_odd_index) - 1;   //끝 인덱스
                    result = Math.max(result, (end_index - start_index + 1 - K));
                }
                else{
                    break;
                }
            }
        }
        //전체 홀수 개수가 K개 이하라면
        else{
            //실제 홀수 개수만큼 빼고 계산은 생략한다.
            result = N - S.size();
        }



        //결과 출력
        System.out.println(result);
    }
}