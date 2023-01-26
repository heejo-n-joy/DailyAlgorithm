import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 엘리베이터로 장난치는거 아니야. 빌런 호석아.
 * <p>
 * 요구하는 출력
 * - 엘리베이터 LED를 반전시켜 숫자들을 만들 수 있는 경우의 수
 * <p>
 * 입력 변수
 * - N : 엘리베이터의 층 수
 * - K : LED 숫자 자리수
 * - P : 반전시킬 LED 최대 개수
 * - X : 현재 층
 * <p>
 * 문제 유의사항
 * - 숫자는 꼭 완성되어야 한다.
 * <p>
 * 전략
 * - 숫자들마다 켜지고 꺼져야 하는 LED를 배열에 미리 입력해둔다.
 * - 현재 층인 X를 제외한 1부터 N까지의 숫자를 하나씩 보면서 (최대 1,000,000층) 해당 숫자로 반전시킬 경우 필요한 LED 수를 확인한다.
 *
 * <p>
 * 예상 난이도 : ★
 * 체감 난이도 : ★
 * <p>
 * @author HeejoPark
 */

public class Main {

    static boolean numbers[][] = {
            {true, true, true, false, true, true, true},        //0
            {false, false, true, false, false, true, false},    //1
            {true, false, true, true, true, false, true},       //2
            {true, false, true, true, false, true, true},       //3
            {false, true, true, true, false, true, false},      //4
            {true, true, false, true, false ,true, true},       //5
            {true, true, false, true, true, true, true},        //6
            {true, false, true, false, false, true, false},     //7
            {true, true, true, true, true, true, true},         //8
            {true, true, true, true, false, true, true}         //9
    };
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //엘리베이터 층 수. 1부터 N까지
        int K = Integer.parseInt(st.nextToken());   //LED 숫자 자리수. 0인 부분은 0 LED가 들어와야 한다.
        int P = Integer.parseInt(st.nextToken());   //반전시킬 수 있는 최대 LED 개수
        int X = Integer.parseInt(st.nextToken());   //현재 층

        int totalCount = 0;
        //1층부터 N층까지 LED가 감당이 가능한가?
        for(int i =1; i<= N; i++){
            //처음 숫자는 반전시켜야 하기 때문에 패스한다.
            if(i == X){
                continue;
            }
            totalCount += isPossibleToChangeLED(i, K, P, X);
        }

        //결과 출력
        System.out.println(totalCount);
    }

    public static int isPossibleToChangeLED(int number, int K, int P, int X){
        //number와 X를 배열로 나타내보자.
        int[] numArray = new int[K];
        int[] XArray = new int[K];
        for(int i = K-1; i>=0; i--){
            numArray[i] = number % 10;
            number /= 10;
            XArray[i] = X % 10;
            X /= 10;
        }
        //각 배열의 숫자들을 가지고 반전시킬 LED 개수를 세보자
        int sumLED = 0;
        for(int i =0; i<K; i++){
            sumLED += compareNumberLED(i, numArray, XArray);
        }
        //LED 개수가 P보다 크면 0을 리턴, 아니라면 1을 리턴하기
        if(sumLED > P){
            return 0;
        }
        else{
            return 1;
        }
    }

    public static int compareNumberLED(int index, int[] numArray, int[] XArray){
        int sum = 0;
        //LED 숫자에 들어가는 LED 파편 개수
        for(int i =0; i<7; i++){
            //LED 파편이 다르다면
            if(numbers[numArray[index]][i] != numbers[XArray[index]][i]){
                sum++;
            }
        }
        return sum;
    }
}