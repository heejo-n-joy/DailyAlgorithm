import java.util.Stack;

class Solution {
    boolean solution(String s) {
        boolean answer = false;
        Stack<Character> stack = new Stack<>();
        for(int i =0; i<s.length(); i++){
            char this_s = s.charAt(i);
            switch(this_s){
                case '(':
                    stack.push('(');
                    break;
                case ')':
                    if(stack.isEmpty()){
                        return answer;
                    }
                    else{
                        if(stack.pop() == ')'){
                            return answer;
                        }
                    }
                    break;
            }
        }
        if(stack.isEmpty()){
            answer = true;
            return answer;
        }
        else{
            return answer;
        }
    }
}