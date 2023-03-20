import java.util.Scanner;

/**
 * 요구하는 출력
 * - 문자열을 입력받은 그대로 출력하세요.
 *
 * 왜 갑자기 브론즈 5 문제를?
 * - 5639번 이진 검색 트리(https://www.acmicpc.net/problem/5639)문제를 풀던 중 입력값이 더이상 없을 경우에 대한 종료 방식이 필요했다.
 * - 기초적인 문제를 풀면서 기능을 학습하고, 다시 문제에 적용하려고 한다.
 *
 * 자바의 EOF (End Of File)
 * - 파일에 더이상 읽을 데이터가 없음을 의미
 * - hasNext()를 사용하면 된다.
 *
 * @author HeejoPark
 */

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
    }
}