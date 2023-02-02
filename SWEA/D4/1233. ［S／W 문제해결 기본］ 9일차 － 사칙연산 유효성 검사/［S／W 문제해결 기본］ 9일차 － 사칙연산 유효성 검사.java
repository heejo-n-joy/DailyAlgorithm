import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * No.9 : 사칙연산 유효성 검사
 *
 * 문제 내용
 * - 주어진 이진 트리가 사칙연산이 유효한지 확인하려 한다.
 *
 * 요구하는 출력
 * - 사칙연산이 유효하다면 1, 그렇지 않다면 0
 *
 * 입력
 * - N : 정점의 총 수
 * - 정점 정보
 *
 *  전략
 *  - 사칙연산의 형태가 유효한지를 확인하기 때문에 0으로 나누어지는 경우는 고려하지 않는다.
 *  - 사칙연산을 진행하기 위해선 숫자들 사이에 연산자가 들어가는 형태가 필요.
 *  - 즉, 모든 노드는 자식 노드가 2개 혹은 0개를 가져야 하며
 *  - 2개를 가진 노드에는 연산자, 0개를 가진 노드에는 피연산자(숫자)가 들어가야 한다.
 *  - 입력을 받으면서 동시에 조건을 확인하고 결과를 내자.
 *
 *  난이도(예상/실제) : 2
 *
 *  회고
 *
 * @author HeejoPark
 */

public class Solution {

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int T = Integer.parseInt(br.readLine());
        int T = 10;

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());
            boolean valid = true;
            for(int i = 0; i<N; i++){
                String[] strings = br.readLine().split(" ");
                //이미 유효하지 않은 테스트 케이스라면 (다음 테스트케이스를 위해 입력은 받지만) 계산은 넘긴다.
                if(!valid){
                    continue;
                }
                //노드의 데이터가 숫자라면
                if(strings[1].charAt(0) >= '0' && strings[1].charAt(0) <= '9'){
                    //자식 노드가 없어야 한다. 있다면 invalid
                    if(strings.length > 2){
                        valid = false;
                    }
                }
                //노드의 데이터가 연산자라면
                else{
                    //자식 노드가 2개 있어야 한다. 아니라면 invalid
                    if(strings.length < 4){
                        valid = false;
                    }
                }
            }
            int result = 0;
            if(valid){
                result = 1;
            }
            //결과출력
            System.out.println("#" + test_case + " " + result);
        }
    }
}