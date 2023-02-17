import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 수빈이(수빈이라는 이름이 왜이렇게 자주 나오지)의 A와 B만을 이용한 영어 단어 만들기 프로젝트
 * <p>
 * 요구하는 출력
 * - 해당 문자열로 바꿀 수 있는지 확인
 * <p>
 * 전략
 * - 완성된 문자열(T)에서 하나씩 제거하며 입력값(S)으로 만들어보자
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
        for(int exec = t_len; exec>s_len; exec--){
            //T의 마지막 문자를 보고 A인지 B인지 확인
            switch(T.charAt(exec-1)){
                case 'A':
                    T = T.substring(0, exec-1);
                    break;
                case 'B':
                    T = T.substring(0, exec-1);
                    T = reverse(T, exec-1);
                    break;
                default:
                    //ERROR
                    break;
            }
        }
        //결과 출력
        if(S.equals(T)){
            System.out.println(1);
        }
        else{
            System.out.println(0);
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