
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 파일 이름들의 패턴을 찾고 싶다.
 *
 * 요구하는 출력
 * - 일치하는 패턴 형식을 출력하자
 *
 * 입력 변수
 * - N : 파일 이름의 개수
 * - 파일 이름들
 *
 * 문제 유의사항
 * - 파일 이름 길이는 모두 같다
 *
 * 체감 난이도 : ★☆☆☆☆
 *
 * 회고할 내용
 *
 * @author HeejoPark
 *
 */
public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //주어지는 파일 개수

        char[] result_pattern = br.readLine().toCharArray();    //결과용. 첫번째 파일명을 가져온다.

        //나머지 파일 이름들을 하나씩 비교
        for (int i = 0; i < N - 1; i++) {
            char[] fileName = br.readLine().toCharArray();
            for (int j = 0; j < result_pattern.length; j++) {
                //만약 글자가 다른 부분이 있다면 해당 위치 ?로 바꾼다.
                if(result_pattern[j] != fileName[j]) result_pattern[j] = '?';
            }
        }
        //결과 출력
        System.out.println(result_pattern);
    }
}