import java.util.Scanner;

/**
 * No.37 : 촛불 이벤트
 *
 * 문제 내용
 * - 프로포즈를 위해 촛불을 쌓는다.
 * - 1단에는 K, 2단에는 K+1,...의 과정으로 양초를 쌓는다.
 * - 주어진 N개의 양초를 활용하여 몇 단 크기의 촛불 삼각형을 만들 수 있는가?
 *
 * 전략
 * - 문제에서 주어진 K단에는 (K(K+1))/2의 양초가 필요하다는 공식을 활용한다.
 * - 양초의 개수가 주어지니, 식을 역으로 이용해보자.
 * - K(K+1)은 루트 2N의 값이다.
 * 
 * 상식
 * - long 타입은 10의 18제곱보다 크다
 *
 * @author HeejoPark
 */
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            long N = sc.nextLong();   //공부를 한 날
            long temp_K = (long) Math.floor(Math.sqrt(2 * N));
            if(temp_K * (temp_K + 1) != 2 * N){
                temp_K = -1;
            }
            //결과 출력
            System.out.println("#" + test_case + " " + temp_K);
        }
    }
}