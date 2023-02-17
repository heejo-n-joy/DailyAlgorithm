import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * No.25 : 중간값 구하기
 *
 * 문제 내용
 * - 심심해하던 홍준이에게 경근이가 중간값 문제를 낸다.
 *
 * 요구하는 출력
 * - 데이터를 2개씩 추가적으로 적는데, 이 때마다 데이터들의 중간값을 구한다.
 *
 * 힌트
 * - 1. N개의 중간값들을 더하는 과정에서 32bit 정수 자료형의 최대 범위를 벗어날 수 있음에 주의합니다.
 * - 따라서 중간값을 더할 때마다, 20171109로 나눈 나머지를 변수에 기록하는 것이 좋습니다.
 * - 2. 최대 힙과 최소 힙을 이용하여 문제를 해결할 수 있습니다.
 *
 *  전략
 *  - Priority Queue로 해결을 하자.
 *  - 힌트 2번에 따라, 최대힙(중간값보다 작은 숫자들의 모임), 최소힙(중간값보다 큰 숫자들의 모임)을 만든다.
 *
 * @author HeejoPark
 */
public class Solution {
    public static void main(String args[]){
        final int REST = 20171109;
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for(int test_case = 1; test_case<= T; test_case++) {
            PriorityQueue<Integer> max_heap = new PriorityQueue<>(Collections.reverseOrder());  //중간값보다 작은 숫자들
            PriorityQueue<Integer> min_heap = new PriorityQueue<>();    //중간값보다 큰 숫자들
            int N = scanner.nextInt();
            int center_number = scanner.nextInt();
            int sum = 0;
            for(int i =0; i<N; i++){
                int X = scanner.nextInt();
                int Y = scanner.nextInt();
                //둘 다 중간값보다 작다면
                if(X < center_number && Y < center_number) {
                    //둘 다 max_heap에 넣고
                    //중간값은 min_heap에 넣고
                    //max_heap에서 가장 큰 값을 빼서 중간값으로 이동
                    center_number = renewal(max_heap, min_heap, X, Y, center_number);
                }
                //둘 다 중간값보다 크다면
                else if(X > center_number && Y > center_number) {
                    //둘 다 min_heap에 넣고
                    //중간값은 max_heap에 넣고
                    //min_heap에서 가장 작은 값을 빼서 중간값으로 이동
                    center_number = renewal(min_heap, max_heap, X, Y, center_number);
                }
                //반반이라면
                else {
                    //작은 수는 max_heap에 넣고
                    max_heap.offer(Math.min(X, Y));
                    //큰 수는 min_heap에 넣기
                    min_heap.offer(Math.max(X, Y));
                }
                sum = (sum + center_number) % REST;
            }
            //결과 출력
            System.out.println("#" + test_case + " " + sum);
        }
    }

    public static int renewal(PriorityQueue<Integer> queue1, PriorityQueue<Integer> queue2, int num1, int num2, int center_num){
        //두 숫자 모두 queue1에 넣고
        queue1.offer(num1);
        queue1.offer(num2);
        //중간 숫자는 queue2에 넣고
        queue2.offer(center_num);
        //queue1에서 가장 큰 값을 빼서 리턴
        return queue1.poll();
    }
}