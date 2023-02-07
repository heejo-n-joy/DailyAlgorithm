import java.util.Scanner;

/**
 * No.3 : 동아리실 관리하기
 *
 * 문제 내용
 * - N일간의 동아리실 활동
 * - 매일의 활동을 각자 하거나 안하거나 할 수 있다.
 * - 다만, 동아리실 열쇠는 계속해서 다음날로 넘어갈 수 있어야 한다.
 *
 * 요구하는 출력
 * - N일동안 동아리실이 운영될 수 있는 경우의 수를 출력
 *
 * 입력
 * - T : 테스트케이스
 *  - 문자열 : 해당일마다의 책임자
 *
 *  전략
 *  - 15개의 배열을 가지고 이전일, 이후일을 계속해서 갱신하는 방법으로 가보자.
 *  - 이후일의 15개의 칸을 각각 이전일들에서 넘어오는게 가능한지 체크하고 갱신
 *
 *  난이도(예상/실제) : 2.5/2
 *  
 *  회고
 *  - 난이도 자체가 높은 문제가 아니였지만, yesterday와 today 배열을 옮기고 재사용하는 과정에서 today의 값이 계속적으로 변하는 오류가 있었다.
 *  - 이를 해결하기 위해 매일마다 today 배열을 새로 생성하여 진행하도록 했다.
 *
 * @author HeejoPark
 */
public class Solution {
    static final int divideNumber = 1000000007;
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();  //테스트 케이스
        for(int test_case = 1; test_case<=T; test_case++){
            String days = scanner.next();
            int length = days.length();
            int result = calc(days, length);
            System.out.println("#" + test_case + " " + result);
        }
    }

    public static int calc(String days, int length){
        int[] yesterday = new int[16];  //각 비트별 D, C, B, A의 참석 여부를 의미.
        for(int i =0; i<16; i++){
            //시작은 A가 열쇠를 가진 상태기 때문에, A를 반드시 포함해야 한다.
            if((i%2)==1){
                //또한 첫날부터 참석해야 하는 사람이 존재함을 체크
                if((i & (1<< (days.charAt(0)-'A'))) > 0) {
                    yesterday[i] = 1;    //가능한 경우의 수임을 표시
                }
            }
        }
        for(int day =1; day<length; day++){
            int[] today = new int[16];      //각 비트별 D, C, B, A의 참석 여부를 의미.
            int today_person = 1 << (days.charAt(day)-'A');
            //선택하자
            for(int t =0; t<16; t++){
                //일단 오늘의 책임자가 들어가야 한다.
                if(((t & today_person) > 0)) {
                    //책임자가 있고, 어제 값들을 비교하면서 오늘에 키를 넘겨줄 사람이 있는지 확인해보자
                    for (int y = 0; y < 16; y++) {
                        //어제와 오늘 중 공통으로 참석한 사람이 있는지 확인
                        if ((y & t) > 0) {
                            //있다면, 키를 넘겨받을 수 있다.
                            today[t] = (today[t] + yesterday[y]) % divideNumber;
                        }
                    }
                }
            }
            yesterday = today;  //하루가 지나 오늘이 어제가 되었다.
        }
        //모든 경우의 수 계산이 끝났다면, 모든 배열을 합산하여 결과 출력
        int sum = 0;
        for(int i =0; i<16; i++){
            sum = (sum + yesterday[i]) % divideNumber;
        }
        return sum;
    }
}