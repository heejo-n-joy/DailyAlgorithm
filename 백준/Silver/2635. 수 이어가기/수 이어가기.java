import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 수를 계속해서 빼면서 이어간다
 * <p>
 * 요구하는 출력
 * - 최대 개수의 수
 * <p>
 * 입력 변수
 * - 첫 번째 수
 * <p>
 * 전략
 * - 재귀형식으로 풀면 될 것 같다.
 * <p>
 * 예상 난이도 : ★
 * 체감 난이도 : ★
 * <p>
 * @author HeejoPark
 */
public class Main {

    static Queue<Integer> resultQueue;
    static int resultNum;
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        resultQueue = new LinkedList<>();
        for(int i = 1; i<=N; i++) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(N);
            queue.offer(i);
            calc(N, i, 2, queue);
        }
        System.out.println(resultNum);
        while(!resultQueue.isEmpty()){
            System.out.print(resultQueue.poll() + " ");
        }
    }

    public static void calc(int prev_prev, int prev, int count, Queue<Integer> queue){
        int newNum = prev_prev - prev;
        //음의 정수가 만들어지면 취소
        if(newNum < 0){
            //만들어진 개수가 이전보다 많다면
            if(count>resultNum){
                resultNum = count;      //개수를 갱신
                resultQueue = queue;    //큐도 갱신
            }
            return;
        }
        queue.offer(newNum);
        calc(prev, newNum, count+1, queue);
    }
}