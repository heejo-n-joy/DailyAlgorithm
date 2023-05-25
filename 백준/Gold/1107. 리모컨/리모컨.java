import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 고장난 리모컨을 가지고 원하는 채널로 이동하는 최소 버튼 횟수
 *
 * 아이디어 : 구현
 * - 채널 0부터 999999까지 모든 경우의 수를 탐색한다.
 *
 */

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());    //원하는 채널
        int M = Integer.parseInt(br.readLine());    //고장난 버튼 수

        boolean[] isBroken = new boolean[10];
        if(M>0) {   //고장난 버튼이 존재한다면
            StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                isBroken[Integer.parseInt(stringTokenizer.nextToken())] = true;
            }
        }

        int result = Math.abs(N - 100); //현재 100번을 보고 있기 때문에 100을 빼고 시작한다.

        //모든 채널 개수 탐색
        for(int channel = 0; channel <= 999999; channel++) {
            String str = String.valueOf(channel);   //해당 채널 숫자를 문자열로 표현
            int len = str.length();

            //채널을 직접 입력하려 하는데, 고장난 버튼이 있어 못누르는지 확인
            boolean isBreak = false;
            for(int i = 0; i < len; i++) {
                //고장난 버튼이 있다면
                if(isBroken[str.charAt(i) - '0']) {
                    isBreak = true;
                    break;  //탐색 종료
                }
            }

            //고장난 버튼이 없었다면
            if(!isBreak) {
                //해당 채널에서 N으로 가는 횟수를 계산하기
                int current = Math.abs(N - channel) + len;
                result = Math.min(current, result);
            }
        }

        //결과출력
        System.out.println(result);
    }
}
