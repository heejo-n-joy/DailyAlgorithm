import java.util.Stack;

/*
스택을 활용. 맨 뒤에서부터 탐색을 하자
- 현재 스택의 값보다 크거나 같다면, 스택을 하나씩 비우기
- 현재 스택의 값보다 작다면, 해당 위치의 값을 스택의 값으로 하고, 스택에 추가
- 스택이 비어졌다면, 해당 위치의 값을 -1로 하고 스택에 추가
*/

class Solution {
    public int[] solution(int[] numbers) {
        int[] answer = new int[numbers.length];
        Stack<Integer> stack = new Stack<>();
        findNum: for(int i =numbers.length-1; i>=0; i--){
            while(!stack.isEmpty() && numbers[i] >= numbers[stack.peek()]){  //스택의 값보다 크거나 같다면
                stack.pop();    //스택을 하나씩 비우기
            }
            if(stack.isEmpty()){
                //스택이 비어졌다면, 해당 위치의 값을 -1로 하기
                answer[i] = -1;
            }
            else{
                //스택이 남아있다면, 해당 위치의 값을 스택의 값으로 하기
                answer[i] = numbers[stack.peek()];
            }
            //스택에 추가
            stack.push(i);
        }
        return answer;
    }
}