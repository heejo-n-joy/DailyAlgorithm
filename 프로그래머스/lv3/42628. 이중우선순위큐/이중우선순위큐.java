import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
        int len = operations.length;
        StringTokenizer st;
        exec : for(int i = 0; i < len; i++){
            st = new StringTokenizer(operations[i]);
            
            char cmd = st.nextToken().charAt(0);
            int num = Integer.parseInt(st.nextToken());
            switch(cmd){
                //숫자 삽입
                case 'I':
                    max.offer(num);
                    min.offer(num);
                    break;
                case 'D':
                    //최대값 삭제하기
                    if(num == 1){
                        if(max.isEmpty()){
                            break;
                        }
                        num = max.poll();
                    }
                    //최소값 삭제하기
                    else{
                        if(min.isEmpty()){
                            break;
                        }
                        num = min.poll();
                    }
                    max.remove(num);
                    min.remove(num);
                    break;
            }
        }
        if(max.isEmpty()){
            return new int[]{0, 0};
        }
        return new int[]{max.peek(), min.peek()};
    }
}