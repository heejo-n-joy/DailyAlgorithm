import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 요구하는 출력 : 입력받은 숫자들 중 절대값이 작은 순서대로 출력하기
 *
 * 풀이 방법 : 우선순위 큐 + 정렬 기준 재설정
 * - 1. 우선순위 큐의 정렬 기준을 절대값이 작은 순으로 정한다.
 *
 * 우선순위 큐의 정렬을 재설정하는 방법 : new Comparator<>(){ @Override public int compare}
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //연산의 개수
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                //절대값 사이즈가 같다면
                if(Math.abs(o1) == Math.abs(o2)){
                    //작은 순서대로 삽입
                    return o1 - o2;
                }
                else{
                    //절대값 사이즈가 작은 순서대로 삽입
                    return Math.abs(o1) - Math.abs(o2);
                }
            }
        });

        //연산 시작
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<N; i++){
            int input = Integer.parseInt(br.readLine());
            switch(input){
                case 0:
                    //출력을 진행하자
                    if(priorityQueue.isEmpty()){
                        sb.append(0+"\n");
                    }
                    else{
                        sb.append(priorityQueue.poll()+"\n");
                    }
                    break;
                default:
                    //입력을 진행하자
                    priorityQueue.offer(input);
                    break;
            }
        }

        //결과 출력
        System.out.println(sb.toString());
    }
}