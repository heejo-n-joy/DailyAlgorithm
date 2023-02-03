import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 문자열에서 어떤 알파벳을 K개만 가지고 있는 문자열을 추출
 * <p>
 * 요구하는 출력
 * - 문자열 추출의 최대값, 문자열 추출의 최소값
 * <p>
 * 주의사항
 * - 구할 수 없으면 -1을 출력한다.
 *
 * <p>
 * 전략
 * - 2차원 배열로 풀 수 있겠다.
 * - [소문자 알파벳의 수 26][문자열의 시작 위치]
 * - 해당 문자열의 시작 위치에서 특정 소문자가 문제의 조건을 만족하는 문자열 길이를 값으로 저장하자
 * - 마지막에 배열에 저장된 값에서 최소값과 최대값을 찾아 출력하자
 * 
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            String str = br.readLine();
            int K = Integer.parseInt(br.readLine());
            int len = str.length();
            int[][] map = new int[26][len];
            for (int i = 0; i < 26; i++) {   //해당 알파벳으로
                for (int j = 0; j < len; j++) {   //해당 위치에서 시작
                    char alpha = (char) ('a' + i);
                    //해당 알파벳이 해당 위치가 아니라면
                    if (str.charAt(j) != alpha) {
                        continue;   //패스 (최대값, 최소값 모두 해당 알파벳이 해당 위치에서 시작해야 한다)
                    }
                    //해당 알파벳이 해당 위치라면 거기서부터 카운트를 센다
                    int count = 0;
                    int length = 0;
                    for (int k = j; k < len; k++) {
                        if(str.charAt(k) == alpha){
                            count++;
                        }
                        length++;
                        if(count == K){ //문제 조건을 다 채웠다면
                            map[i][j] = length; //배열에 반영
                            break;
                        }
                    }
                }
            }
            //이렇게 해서 가장 긴 길이와 가장 짧은 길이를 확인해보자
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for(int i =0; i<26; i++){
                for(int j =0; j<len; j++){
                    if(map[i][j] == 0){ //0이라면 패스 (배열의 기본값)
                        continue;
                    }
                    if(map[i][j] < min){    //최소값을 갱신할 수 있다면 갱신
                        min = map[i][j];
                    }
                    if(map[i][j] > max){    //최대값을 갱신할 수 있다면 갱신
                        max = map[i][j];
                    }
                }
            }
            //결과 출력
            if(min != Integer.MAX_VALUE && max != Integer.MIN_VALUE){   //최소값, 최대값 모두 갱신됐다면
                System.out.println(min + " " + max);    //문제에서 요구한 최소값 최대값 출력
            }
            else{   //하나라도 갱신이 되지 않았다면
                System.out.println("-1");   //-1 출력
            }
        }
    }
}