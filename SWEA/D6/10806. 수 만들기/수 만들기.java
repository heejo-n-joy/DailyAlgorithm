import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * No.26 : 수 만들기
 *
 * 문제 내용
 * - N개의 수를 활용하여 X를 K로 만들어야 한다.
 * - 이 때, X에는 D를 계속해서 더해야 하며, D는 N개의 수 중 원하는 수를 골라 곱하는 식으로 커진다.
 * - X가 K가 되기 위해, 더하는 D의 횟수의 최소값을 출력하자 (D에 N을 곱하는 횟수는 신경쓰지 않는다)
 *
 *  전략
 *  - Priority Queue로 해결을 하자.
 *  -
 *
 * @author HeejoPark
 */

class Node026{
    int count;
    int restK;
    Node026(int count, int restK){
        this.count = count;
        this.restK = restK;
    }
}
public class Solution {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트케이스
        for(int test_case = 1; test_case<=T; test_case++){
            int N = sc.nextInt();
            int[] array = new int[N];
            for(int i =0; i<N; i++){
                array[i] = sc.nextInt();
            }
            int K = sc.nextInt();

            //우선순위큐 - 현재 값들 중 카운터가 낮은 큐를 우선적으로 확인한다.
            PriorityQueue<Node026> queue = new PriorityQueue<>(new Comparator<Node026>() {
                @Override
                public int compare(Node026 o1, Node026 o2) {
                    if (o1.count < o2.count) {
                        return -1;
                    } else if (o1.count > o2.count) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            queue.offer(new Node026(0, K));
            while(!queue.isEmpty()){
                Node026 tempNode = queue.poll();
//                System.out.println("CURRENT QUEUE : (" + tempNode.count + ", " + tempNode.restK + ")");
                if(tempNode.restK == 0){
                    //결과 출력
                    System.out.println("#" + test_case + " " + tempNode.count);
                    break;
                }
                for(int i =0; i<N; i++){
                    queue.offer(new Node026(tempNode.count + (tempNode.restK % array[i]), tempNode.restK / array[i]));
//                    System.out.println("  INSERT QUEUE("+array[i]+") : (" + (tempNode.count + (tempNode.restK % array[i])) + ", " + tempNode.restK / array[i] + ")");
                }
            }
        }
    }
}