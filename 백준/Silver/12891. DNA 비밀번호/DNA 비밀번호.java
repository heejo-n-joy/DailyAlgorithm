import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * #슬라이딩_윈도우_특집
 *
 * 요구하는 출력 : DNA 문자열에서 조건에 부합하게 만들 수 있는 비밀번호 종류의 수
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
        int S = Integer.parseInt(st.nextToken());   //DND 문자열의 길이
        int P = Integer.parseInt(st.nextToken());   //비밀번호 문자열의 길이
        String DNA = br.readLine();     //문자열 DNA
        st = new StringTokenizer(br.readLine());
        int[] option = new int[4];
        for(int i = 0; i<4; i++){
            option[i] = Integer.parseInt(st.nextToken());   //A,C,G,T의 최소 개수
        }

        //슬라이딩 윈도우 세팅
        int[] sliding_window = new int[4];
        int start_index = 0;
        int end_index = P-1;
        for(int i = start_index; i<=end_index; i++){
            //해당 문자의 개수를 늘린다.
            switch(DNA.charAt(i)){
                case 'A':
                    sliding_window[0]++;
                    break;
                case 'C':
                    sliding_window[1]++;
                    break;
                case 'G':
                    sliding_window[2]++;
                    break;
                case 'T':
                    sliding_window[3]++;
                    break;
            }
        }

        int result = 0;
        //만약 조건에 부합하면
        if(compare(sliding_window, option)){
            result++;   //결과값을 1 늘린다.
        }

        //이제 본격적으로 슬라이딩하며 값을 계속 확인한다.
        for(int i = end_index+1; i<S; i++){
            //시작 지점의 문자 개수는 1 줄이자
            switch(DNA.charAt(i-P)){
                case 'A':
                    sliding_window[0]--;
                    break;
                case 'C':
                    sliding_window[1]--;
                    break;
                case 'G':
                    sliding_window[2]--;
                    break;
                case 'T':
                    sliding_window[3]--;
                    break;
            }
            
            //새로 들어온 끝 지점의 문자 개수는 1 늘리자
            switch(DNA.charAt(i)){
                case 'A':
                    sliding_window[0]++;
                    break;
                case 'C':
                    sliding_window[1]++;
                    break;
                case 'G':
                    sliding_window[2]++;
                    break;
                case 'T':
                    sliding_window[3]++;
                    break;
            }
            //새로 변경된 비밀번호 문자열이 조건에 부합하면
            if(compare(sliding_window, option)){
                result++;   //결과값을 1 늘린다.
            }
        }

        //결과출력
        System.out.println(result);
    }

    public static boolean compare(int[] a, int [] b){
        for(int i = 0 ; i<a.length; i++){
            if(a[i] < b[i]){
                return false;
            }
        }

        return true;
    }
}