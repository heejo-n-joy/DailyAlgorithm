import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 집합에 포함되지 않는 숫자 3개를 골라 |N-xyz|의 최솟값을 구하자
 *
 * 풀이 방법 : 완전 탐색 (3중 for문)
 * 1. 집합 S에 없는 숫자를 순서대로 3개 선택한다.
 * 2. |N-xyz|를 계산한다.
 * 3. 해당 값이 직전에 계산한 값보다 크면 1번의 y, z를 다시 선택하는 과정으로 돌아간다.
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //자연수 N
        int M = Integer.parseInt(st.nextToken());   //집합 S의 크기
        HashSet<Integer> S = new HashSet<>();       //집합 S
        if(M > 0){
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<M; i++){
                S.add(Integer.parseInt(st.nextToken()));
            }
        }

        int min_value = Integer.MAX_VALUE;  //최솟값을 저장하는 변수.
        for(int x = 1; x<=1001; x++){
            //x가 집합 S에 있다면 다음 x를 뽑자
            if(S.contains(x)){
                continue;
            }
            for(int y = x; y<=1001; y++){
                //y가 집합 S에 있다면 다음 y를 뽑자
                if(S.contains(y)){
                    continue;
                }
                for(int z = y; z <= 1001; z++){
                    //z가 집합 S에 있다면 다음 z를 뽑자
                    if(S.contains(z)){
                        continue;
                    }

                    min_value = Math.min(min_value, Math.abs(N - (x * y * z)));

                    //xyz가 N 이상이 되면, 이제부터는 값이 커지므로, z의 남은 값들은 모두 패스한다.
                    if(x * y * z >= N){
                        break;
                    }
                }
            }
        }

        //결과 출력
        System.out.println(min_value);
    }
}