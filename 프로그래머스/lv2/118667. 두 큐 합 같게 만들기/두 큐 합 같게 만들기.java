/*
* 일단 두 큐의 합 / 2를 해서 어떻게 수를 맞춰야할지 보고
* n/2의 수에 비해 얼마나 남고 부족한지를 체크
* 만약 queue1 > queue2 상태라면 queue1에서 하나를 pop, queue2에 넣는다.
* 다시 얼마나 남고 부족한지 체크를 반복
* 큐의 길이만큼 실행이 됐는데 실행이 안끝났다면, 중단하고 -1 출력
*/

import java.util.Queue;
import java.util.LinkedList;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        Queue<Integer> realQueue1 = new LinkedList<>();
        Queue<Integer> realQueue2 = new LinkedList<>();
        long sum1 = 0;
        long sum2 = 0;
        for(int i =0; i<queue1.length; i++){
            realQueue1.offer(queue1[i]);
            sum1 += queue1[i];
            realQueue2.offer(queue2[i]);
            sum2 += queue2[i];
        }
        long wannaResult = (sum1 + sum2) / 2;
        int length = queue1.length * 4; //다른 큐에 갔다가(length) + 오는 경우(length) * 큐는 두개(2)
        int answer = 0; //카운트
        while(true){
            //만약 더이상의 기회가 없다면
            if(answer > length){
                answer = -1;    //실패
                break;
            }
            //계산
            if(sum1 > sum2){    //queue1이 더 합이 크다면
                int temp = realQueue1.poll();
                realQueue2.offer(temp); //queue2에 넣어준다
                sum1 -= temp;
                sum2 += temp;
            }
            else if(sum1 < sum2){   //반대로 queue2의 합이 더 크다면
                int temp = realQueue2.poll();
                realQueue1.offer(temp); //queue1에 넣어준다
                sum2 -= temp;
                sum1 += temp;
            }
            else{
                //두 합이 같다면
                break;
            }
            //카운트 1 증가
            answer++;
        }
        return answer;
    }
}