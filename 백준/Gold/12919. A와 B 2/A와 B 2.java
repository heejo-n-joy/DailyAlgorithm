import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 수빈이(수빈이라는 이름이 왜이렇게 자주 나오지)의 A와 B만을 이용한 영어 단어 만들기 프로젝트 2
 * <p>
 * 요구하는 출력
 * - 해당 문자열로 바꿀 수 있는지 확인
 * <p>
 * 전략
 * - 어제의 A와 B 문제와 다른 점은, B를 추가하고 문자열을 뒤집는 것이다.
 * - 만약 문자열 맨 앞이 B라면 and 문자열 맨 뒤가 A라면 두 가지 경우로 갈라진다.
 * - DFS로 모든 경우의 수를 찾아보자
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
        String S = br.readLine();   //입력값
        String T = br.readLine();   //결과값
        int s_len = S.length(); //입력값의 길이
        int t_len = T.length(); //결과값의 길이
        isPossible = false;
        //실행
        dfs(S, T, s_len, t_len);
        //결과 출력
        if(isPossible){
            System.out.println(1);
        }
        else{
            System.out.println(0);
        }
    }
    static boolean isPossible;
    public static void dfs(String ori_str, String calc_str, int ori_len, int calc_len){
        //이미 가능한 경우를 찾았다면 종료
        if(isPossible){
            return;
        }
        if(ori_len == calc_len){
            //두 문자열이 같은지 확인하기
            if(ori_str.equals(calc_str)){
                isPossible = true;
            }
            return;
        }
        //만약 문자열 맨 앞이 B라면
        if(calc_str.charAt(0) == 'B'){
            dfs(ori_str, reverse(calc_str, calc_len).substring(0, calc_len-1), ori_len, calc_len-1);
        }
        //만약 문자열 맨 마지막이 A라면
        if(calc_str.charAt(calc_len-1) == 'A'){
            dfs(ori_str, calc_str.substring(0, calc_len-1), ori_len, calc_len-1);
        }
    }
    public static String reverse(String str, int len){
        String result = "";
        //마지막 문자부터 하나씩 뒤집기
        for(int i= len-1; i>=0; i--){
            result += str.charAt(i);
        }
        return result;
    }
}