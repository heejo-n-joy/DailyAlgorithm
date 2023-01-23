import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 1,2,3를 이용한 n
 * <p>
 * 요구하는 출력
 * - 1,2,3으로 n을 표현할 경우의 수
 * <p>
 * 입력 변수
 * - T : 테스트케이스
 * - n : 10000 이하의 정수
 * <p>
 * 문제 유의사항
 * - 순서가 다른건 같은걸로 친다
 * <p>
 * 전략
 * - 1,2,3의 조합 문제
 * - 다만 10000을 계산하기 위해선 DP가 필수적이기에..
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 회고
 * - dp는 역시 풀이과정을 생각하는 과정이 오래걸리고 어렵다.
 * @author HeejoPark
 */

public class Main {
    static int[][] array;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        array = new int[10001][4];  //1~10000과, 끝나는수1~3 (조합 문제, 선택 숫자는 점차 증가하게 뽑는다.)
        array[1][1] = 1;
        array[2][1] = 1;
        array[2][2] = 1;
        array[3][1] = 1;
        array[3][2] = 1;
        array[3][3] = 1;
        
        //dp 칸을 먼저 채우자
        for(int i = 4; i<=10000; i++){
            array[i][1] = array[i-1][1];
            array[i][2] = array[i-2][1] + array[i-2][2];
            array[i][3] = array[i-3][1] + array[i-3][2] + array[i-3][3];
        }
        
        for(int test_case = 1; test_case<=T; test_case++) {
            int n = Integer.parseInt(br.readLine());
            //결과 출력
            System.out.println(result(n));
        }
    }
    public static int result(int n){
        //해당 수의 모든 dp를 합친다.
        return array[n][1] + array[n][2] + array[n][3];
    }
}