import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 삼각형 만들기
 *
 * 요구하는 출력
 * - 성냥개비로 만들 수 있는 삼각형의 종류를 출력
 *
 * 입력 변수
 * - N : 성냥개비의 총 개수
 *
 * 문제 유의사항
 * - 합동인 삼각형들은 같은 삼각형으로 취급
 *
 * 체감 난이도 : ★☆☆☆☆
 * - 조합 문제
 * - 삼각형이 되기 위한 조건 (변 a>b>c일 때, |b-c| < a < b+c)
 *
 * @author HeejoPark
 *
 */
public class Main {

    static int N;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int num[] = new int[3];
        combi(0, num, 1, N);
        System.out.println(totalCount);
    }
    static int totalCount;
    public static void combi(int count, int num[], int prev_num, int rest_num){
        if(count==2){
            //나머지 한 변을 구하기
            if(rest_num < num[1]){
                //직전에 구한 변보다 길이가 작으면 안된다. (조합 문제로 풀고 있고, 중복을 제거하기 위해 작은 숫자부터 큰 숫자를 뽑고 있기 때문)
                return;
            }
            num[2] = rest_num;  //나머지 한 변의 길이는 자동 계산
            //삼각형 조건이 되는지 체크 (가장 큰 변의 길이는 작은 두 변의 길이의 차와 합 사이에 있다)
            if(num[2]<num[0]+num[1] && num[1]-num[0] < num[2]){
                totalCount++;   //성립하면 개수 1 증가
            }
            return;
        }
        //삼각형의 한 변의 길이 구하기
        for(int i = prev_num; i<rest_num; i++){
            num[count] = i; //지금 변의 길이를 i로 설정
            combi(count+1, num, i, rest_num-i); //다음 변의 길이 구하러 가기
        }
    }
}