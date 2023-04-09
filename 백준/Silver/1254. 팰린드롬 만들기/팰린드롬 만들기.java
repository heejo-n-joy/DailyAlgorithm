import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * 요구하는 출력 : 주어진 문자열에서 문자들을 이어붙여 만들 수 있는 가장 짧은 팰린드롬 길이
 *
 * 풀이 방법 : 완전탐색 (홀수 팰린드롬과 짝수 팰린드롬 중 작은 값)
 * - 문자열의 길이를 구한다. 홀수라면 중간부터, 짝수라면 중간(오른쪽)부터 시작 위치를 잡는다.
 * - 해당 위치로부터 +n, -n 위치의 문자들이 같은지를 확인한다.
 * - 둘 중에 한 곳이라도 범위를 벗어난다면, 이는 팰린드롬의 중간을 시작 위치로 잡는 것이 가능하다는 의미이다.
 * - 문자가 중간에 다르다면, 시작 위치를 +1하고 다시 계산을 한다.
 *
 */

public class Main {

    static final String ODD = "odd";
    static final String EVEN = "even";

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine();   //문자열
        int len = S.length();       //문자열의 길이

        //실행
        int result = Math.min(exec(S, len, ODD), exec(S, len, EVEN));   //문자열을 가지고 만들 수 있는 홀수 팰린드롬과 짝수 팰린드롬 중 작은 값을 갖는다.

        //결과 출력
        System.out.println(result);
    }

    public static int exec(String S, int len, String exec_case){
        int right_index;    //오른쪽 방향으로 탐색할 인덱스
        int left_index;     //왼쪽 방향으로 탐색할 인덱스

        stage : for(int i = len / 2 ; i <=len; i++){
            switch(exec_case){
                case ODD:   //홀수 팰린드롬을 찾는다면
                    right_index = i + 1;
                    left_index = i - 1;
                    break;
                case EVEN:  //짝수 팰린드롬을 찾는다면
                    if(len % 2 == 0){
                        right_index = i;
                        left_index = i - 1;
                    }
                    else{
                        right_index = i + 1;
                        left_index = i;
                    }
                    break;
                default:
                    //ERROR
                    System.out.println("ERROR : CANNOT FIND EXEC_CASE");
                    return -1;
            }

            //좌우 탐색을 시작한다.
            while(right_index < len && left_index >= 0){
                //대칭이 안된다면
                if(S.charAt(right_index) != S.charAt(left_index)){
                    //다음 시작 위치로 넘어간다.
                    continue stage;
                }
                //대칭이라면 계속 비교를 진행한다..
                right_index++;
                left_index--;
            }

            //범위를 벗어날 때까지 이상이 없었다면 결과를 저장하고 종료
            switch (exec_case){
                case ODD:   //홀수 팰린드롬이라면
                    return i * 2 + 1;
                default:    //짝수 팰린드롬이라면
                    if(len % 2 == 0){
                        return i * 2;
                    }
                    return (i + 1) * 2;
            }
        }
        //ERROR
        System.out.println("ERROR : UNEXPECTED LOGIC IS EXECUTED");
        return -2;
    }
}