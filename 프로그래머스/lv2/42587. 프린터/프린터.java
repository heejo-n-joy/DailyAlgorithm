/*
기본적으로 queue를 이용하는 문제
priorities와 똑같은 크기의 boolean타입 배열을 생성

0. queue에 모든 인쇄 요청을 순서대로 넣는다. 이 때, 중요도가 아닌 index 번호를 넣는다.
1. 현재 남은 종이들에서 가장 높은 중요도를 가진 값을 찾는다.
2. 실행
3. 만약 내가 원하던 문서가 출력되었으면, 몇 번째 출력이었는지 결과 출력

실행
1. 대기목록 맨 앞을 확인
2-1. 가장 높은 중요도 값이라면 인쇄. boolean타입에 체크. 다시 max값 찾기. 출력된 순서 카운트
2-2. 높은 중요도 아니라면 그대로 queue 맨 끝에 다시 삽입
*/

import java.util.Queue;
import java.util.LinkedList;

class Solution {
    public int solution(int[] priorities, int location) {
        Queue<Integer> queue = new LinkedList<>();
        for(int i =0; i<priorities.length; i++){
            queue.offer(i); //0. queue에 모든 인쇄 요청을 순서대로 넣는다. 이 때, 중요도가 아닌 index 번호를 넣는다.
        }
        boolean[] isPrinted = new boolean[priorities.length];   //priorities와 똑같은 크기의 boolean타입 배열을 생성
        int maxValue = max(priorities, isPrinted);
        int count = 0;
        while(!queue.isEmpty()){
            int temp = queue.poll();    //1. 대기목록 맨 앞을 확인
            
            //2-1. 가장 높은 중요도 값이라면 인쇄. boolean타입에 체크. 다시 max값 찾기. 출력된 순서 카운트
            if(priorities[temp] == maxValue){
                //인쇄.
                isPrinted[temp] = true;
                maxValue = max(priorities, isPrinted);
                count++;
                
                if(location == temp){   //인쇄한 문서가 확인하고 싶었던 문서라면
                    return count;   //몇 번째 인쇄인지 출력하고 종료
                }
            }
            //2-2. 높은 중요도 아니라면 그대로 queue 맨 끝에 다시 삽입
            else{
                queue.offer(temp);
            }
        }
        
        int answer = -1;
        return answer;
    }
    
    public int max(int[] priorities, boolean[] isPrinted){
        int value = -1;
        for(int i =0; i<priorities.length; i++){
            if(!isPrinted[i]){  //아직 인쇄가 되지 않은 문서들에 한해
                value = Math.max(value, priorities[i]); //최대값을 비교하고 갱신한다.
            }
        }
        return value;
    }
}