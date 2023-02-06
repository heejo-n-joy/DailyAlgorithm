import java.util.Scanner;

/**
 * No.1 : 새로운 불면증 치료법
 *
 * 문제 내용
 * - 불면증인 민석이의 N의 배수 양을 세기
 * - 0~9의 숫자를 모두 보기 위해선 몇 배의 양을 세야할까?
 *
 * 요구하는 출력
 * - 0~9의 숫자를 모두 봤을 때의 양을 센시점
 *
 * 입력
 * - T : 테스트케이스
 *  - N : 시작되는 수
 *
 *  전략
 *  - 0~9를 의미하는 boolean타입 배열[10]을 만든다.
 *  - 양을 세면서 해당 숫자가 들어오면 그 배열을 true로 변환한다.
 *  - 모든 배열이 true가 되면 양 세기를 종료한다.
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

            boolean[] numbers = new boolean[10];

            int multiple_num = 0;

            //양을 세기
            while(notFindAllNumbers(numbers, 10)){
                multiple_num++; //다음 배수의 양으로 넘어간다.
                int temp_num = N * multiple_num;    //양을 배수한다.
                //모든 자리의 숫자를 확인해서, 새로 반영해준다.
                while(temp_num>0){
                    numbers[temp_num % 10] = true;  //해당 숫자를 true로 변경
                    temp_num /= 10;
                }
            }

            //결과 출력
            System.out.println("#" + test_case + " " + (N * multiple_num));
        }

    }

    public static boolean notFindAllNumbers(boolean[] numbers, int length){
        for(int i =0; i<length; i++){
            //아직 확인하지 못한 숫자가 있으면 즉시 true 반환
            if(!numbers[i]){
                return true;
            }
        }
        //모든 숫자를 찾았으니 false 리턴
        return false;
    }
}