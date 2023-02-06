import java.util.Scanner;

/**
 * No.2 : 이진수 표현
 *
 * 문제 내용
 * - 정수 N, M이 주어진다.
 * - M의 이진수 표현의 마지막 N비트가 모두 1로 켜져있는지를 확인한다.
 *
 * 요구하는 출력
 * - 모두 1로 켜져있다면 ON, 아니면 OFF
 *
 * 입력
 * - T : 테스트케이스
 *  - M : 정수
 *  - N : 확인할 끝부분
 *
 *  전략
 *  - M에서 2의 N제곱만큼 나눈 나머지를 확인한다.
 *  - 이 나머지가 2의 N제곱-1이면 true, 아니면 false
 *
 *  난이도(예상/실제) : 1/1
 *
 * @author HeejoPark
 */
public class Solution {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();  //테스트 케이스
        for(int test_case = 1; test_case<=T; test_case++){
            int N = scanner.nextInt();  //첫 번째 수
            int M = scanner.nextInt();  //전체 수

            //끝에서 N의 자리만큼만 남긴 후 해당 이진수 값들이 모두 1인지 확인
            if(M % (Math.pow(2, N)) == Math.pow(2, N) -1){
                System.out.println("#" + test_case + " " + "ON");
            }
            else{
                System.out.println("#" + test_case + " " + "OFF");
            }
        }
    }
}