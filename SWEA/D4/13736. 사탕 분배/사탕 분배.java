import java.util.Scanner;

/**
 * No.34 : 사탕 분배
 *
 * 문제 내용
 * - 나연이의 사탕 A개와 다현이의 사탕 B개를 분배하려고 한다.
 * - 더 많은 사탕을 가진 사람이 적은 사탕을 가진 사람에게 그 사람이 가진 사탕의 개수만큼 넘겨준다.
 * - K번을 반복한 후, 두 사람이 가진 사탕 중 적은 사탕의 개수를 출력하라
 *
 * 전략
 * - 무슨 K가 10의 9제곱.. 일반 for문으로는 도저히 해결할 수 없다.
 * - 결국 코치님의 도움을 받았다.
 * - X는 2의 K제곱 * X mod S
 * - 이렇게 추리를 한다는게 진짜 신기하다
 * - 아무튼, 2의 K제곱 mod S는 O(logn)을 통해 구할 수 있게 계산
 *
 * @author HeejoPark
 */
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트케이스
        for(int test_case = 1; test_case<=T; test_case++) {
            int A = sc.nextInt();   //나연이의 사탕 개수
            int B = sc.nextInt();   //다현이의 사탕 개수
            long S = A + B;
            long K = sc.nextInt();   //사탕을 나눠주는 횟수

            long calc = pow(K, S);
            long result1 = (A * calc) % S;
            long result2 = S - result1;

            //결과 출력
            System.out.println("#" +test_case + " " + Math.min(result1, result2));
        }
    }

    public static long pow(long K, long S){
        //지수가 0이라면 1 리턴
        if (K == 0) {
            return 1;
        }

        long n = pow(K / 2, S);  //절반을 나눈 값
        long temp = n * n % S;   //이를 다시 합친다

        //K가 짝수였다면 누락된 값이 없었고
        if (K % 2 == 0) {
            return temp;
        }
        //K가 홀수였다면 K/2의 과정에서 2가 누락이 된다.
        else {
            return 2 * temp % S;
        }
    }
}