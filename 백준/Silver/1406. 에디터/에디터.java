import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 요구하는 출력
 * - 커서를 활용하여 편집된 문자열
 *
 * 전략1
 * - LinkedList를 활용한다
 * - 다만 커서가 오른쪽, 왼쪽 모두 이동하기 때문에 Double Linked List를 구현한다.
 * - 시간초과
 *
 * 전략2
 * - Stack을 활용한다.
 * - 두 개의 스택을 생성, 커서를 기준으로 왼쪽에 있는 문자열, 커서를 기준으로 오른쪽에 있는 문자열로 생각하자
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String original_str = br.readLine();
        Stack<Character> left_stack = new Stack<Character>();
        Stack<Character> right_stack = new Stack<Character>();
        int N = original_str.length();
        for(int i = 0; i<N; i++){
            left_stack.push(original_str.charAt(i));
        }
        int M = Integer.parseInt(br.readLine());
        for(int i = 0; i<M; i++){
            String[] input_str = br.readLine().split(" ");
            switch(input_str[0]){
                case "L":
                    if(!left_stack.empty()){
                        right_stack.push(left_stack.pop());
                    }
                    break;
                case "D":
                    if(!right_stack.empty()){
                        left_stack.push(right_stack.pop());
                    }
                    break;
                case "B":
                    if(!left_stack.empty()){
                        left_stack.pop();
                    }
                    break;
                case "P":
                    left_stack.push(input_str[1].charAt(0));
                    break;
                default:
                    System.out.println("ERROR");
                    break;
            }
        }
        //출력을 위한 스택 정렬
        while(!left_stack.empty()){
            right_stack.push(left_stack.pop());
        }
        StringBuilder sb = new StringBuilder();
        //결과 출력
        while(!right_stack.empty()){
            sb.append(right_stack.pop());
        }
        System.out.println(sb);
    }
}