import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
/**
 * No.23 : 힙
 *
 * 문제 내용
 * - 힙 정렬을 진행하자
 *
 * 요구하는 출력
 * - 필요시 최댓값을 출력 후 해당 값 삭제
 *
 *  전략
 *  - PriorityQueue를 활용.
 *  - 단, 순서는 역순으로 진행
 *
 *  난이도(예상/실제) : 2
 *
 * @author HeejoPark
 */
public class Solution {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for(int test_case = 1; test_case<=T; test_case++){
            //힙 정렬 준비
            StringBuilder sb = new StringBuilder();
            int N = scanner.nextInt();  //연산의 수
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
            int count = 0;
            //실행
            for(int exec = 0; exec < N; exec++){
                int selection = scanner.nextInt();
                switch(selection){
                    case 1: //새로운 노드를 추가한다
                        priorityQueue.offer(scanner.nextInt());
                        break;
                    case 2: //루트 노드를 출력한다
                        if(priorityQueue.isEmpty()){
                            sb.append(-1 + " ");
                        }
                        else{
                            sb.append(priorityQueue.poll() + " ");
                        }
                        break;
                    default:
                        //ERROR
                        break;
                }
            }
            //결과 출력
            System.out.println("#" + test_case + " " + sb);
        }
    }
}