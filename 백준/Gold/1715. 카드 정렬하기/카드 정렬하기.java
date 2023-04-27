import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/**
 * 요구하는 출력 : 카드들을 합치는데 필요한 최소 비교 횟수
 *
 * 풀이 방법 : 우선순위 큐 & 그리디
 * - 카드를 합칠 때는 두 묶음만 비교 후 합칠 수 있다.
 * - 따라서 먼저 카드가 합쳐질 수록, 그 묶음은 계속해서 비교 횟수에 포함이 된다.
 * - 그렇기 때문에 먼저 비교하는 카드는 카드 수가 적어야, 전체 비교 횟수가 작다.
 * 1. 카드를 모두 오름차순으로 정렬
 * 2. 카드를 하나씩 빼서 비교 후 합치기
 * 3. 합친 카드는 다시 큐에 넣기
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //카드의 개수
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();   //기본 설정이 오름차순
        for(int i = 0; i<N; i++){
            priorityQueue.offer(Integer.parseInt(bufferedReader.readLine()));
        }

        //개수가 많아질 것 같으니 long을 쓰자
        long card_count = 0;

        //카드 더미가 2개 이상일 때만 실행
        while(priorityQueue.size() > 1){
            int temp_card_A = priorityQueue.poll(); //가장 작은 더미 뽑기
            int temp_card_B = priorityQueue.poll(); //그 다음으로 작은 더미 뽑기
            int sum_card = temp_card_A + temp_card_B;   //두 더미 합치지
            card_count += sum_card; //합칠 때 비교한 횟수 반영
            priorityQueue.offer(sum_card);   //합친 더미 다시 우선순위에 넣기
        }

        //결과 출력
        System.out.println(card_count);
    }
}