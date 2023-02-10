import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 영어단어를 숫자로 암호화한다.
 * <p>
 * 요구하는 출력
 * - 숫자가 주어졌을 때 만들 수 있는 문자의 개수
 * <p>
 * 전략
 * - DP를 활용해보자.
 * - 숫자 개수만큼 가로에 배분. 현 위치까지 만들 수 있는 알파벳 개수
 * - DP[i] = (DP[i-1] + 1자리알파벳) + (DP[i-2] + 2자리알파벳)
 *
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    static int[] DP;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        int len = str.length();
        DP = new int[len+1];
        DP[0] = 1;
        exec(str, len);
        //결과출력
        System.out.println(DP[len]);
    }

    public static void exec(String str, int len){
        for(int i = 1; i<=len; i++){
            int currentNum = (str.charAt(i-1) - '0');
            int strIndex = i-1;
            if(currentNum == 0){
                //만약 이전 숫자가 없다면 실패
                if(strIndex == 0){
                    fail(len);
                    return;
                }
                //만약 이전 숫자가 0이라면 실패
                if(str.charAt(strIndex-1) == '0'){
                    fail(len);
                    return;
                }
                //만약 이전 숫자가 2보다 크다면 실패
                if(str.charAt(strIndex-1) - '0' > 2){
                    fail(len);
                    return;
                }
                DP[i] = DP[i-2];
            }
            else{
                //이전 숫자가 없으면
                if(strIndex == 0) {
                    DP[i] = 1;
                    continue;
                }
                //이전 숫자가 0이라면
                if(str.charAt(strIndex-1) == '0'){
                    DP[i] += DP[i-1];
                }
                //이전 두 숫자의 값을 확인
                else {
                    int num = (str.charAt(strIndex - 1) - '0') * 10 + (str.charAt(strIndex) - '0');
                    if (num <= 26) {
                        DP[i] += DP[i - 2];
                    }
                    DP[i] += DP[i - 1];
                }
                DP[i] %= 1000000;
            }
        }
    }
    public static void fail(int len){
        DP[len] = 0;
        return;
    }
}