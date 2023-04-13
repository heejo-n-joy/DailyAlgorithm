import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * #슬라이딩_윈도우_특집
 *
 * 요구하는 출력 : 벽화에서 추출한 문자열에서 알파벳 순서에 상관없이 글자가 같은 형태의 개수
 *
 * 풀이 방법 : 슬라이딩 윈도우
 * - 1. 슬라이딩 윈도우를 세팅한다.
 * - 2. 시작 위치와 끝 위치를 동시에 한 칸씩 이동하며, 슬라이딩 윈도우 내 문자열의 값을 변경한다.
 * - 3. 값이 문제 조건에 만족하는지 확인하고, 2번으로 다시 간다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int g = Integer.parseInt(st.nextToken());   //단어 W의 문자열의 길이
        int S_len = Integer.parseInt(st.nextToken());   //벽화 문자열의 길이
        String W = br.readLine();     //단어
        String S = br.readLine();     //벽화
        int[] w_alphabets = new int[58];

        //결과값을 저장할 변수
        int result = 0;

        //비교할 단어 W의 정보 세팅
        int[] sliding_window = new int[58];
        for(int i =0; i<g; i++){
            sliding_window[W.charAt(i) - 'A']++;
        }
        
        //슬라이딩 윈도우 세팅
        int start_index = 0;
        int end_index = g-1;
        for(int i = start_index; i<=end_index; i++){
            //해당 문자의 개수를 늘린다.
            w_alphabets[S.charAt(i) - 'A']++;
        }

        //만약 조건에 부합하면
        if(compare(sliding_window, w_alphabets)){
            result++;   //결과값을 1 늘린다.
        }
        
        //이제 본격적으로 슬라이딩하며 값을 계속 확인한다.
        for(int i = end_index+1; i<S_len; i++){
            //시작 지점의 문자 개수는 1 줄이자
            w_alphabets[S.charAt(i-g) - 'A']--;

            //새로 들어온 끝 지점의 문자 개수는 1 늘리자
            w_alphabets[S.charAt(i) - 'A']++;

            //새로 변경된 비밀번호 문자열이 조건에 부합하면
            if(compare(sliding_window, w_alphabets)){
                result++;   //결과값을 1 늘린다.
            }
        }

        //결과출력
        System.out.println(result);
    }

    public static boolean compare(int[] a, int [] b){
        for(int i = 0 ; i<a.length; i++){
            if(a[i] != b[i]){
                return false;
            }
        }
        return true;
    }
}