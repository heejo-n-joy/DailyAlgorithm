import java.util.Scanner;
import java.util.Stack;

/**
 * 요구하는 출력
 * - 현재 주어진 수열이 stack으로 표현이 가능한지
 *
 * 전략
 * - 직접 stack을 다뤄보면서 하자
 * - push를 하면서 peek 값이 해당 수열의 해당 위치라면 pop하기
 * - 중간에 안맞는 부분이 있다면 (가령 stack이 비어버린다거나) 실행을 종료하고 NO를 출력하기
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        //실행
        String result = calcStack(n, sc);
        //결과 출력
        System.out.println(result);
    }

    public static String calcStack(int n, Scanner sc){
        int num = 1;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        String error = "NO";
        Stack<Integer> stack = new Stack();
        int inputNum = -1;
        while(true){
            if(count == n){
                return sb.toString();
            }
            //스택이 비어있다면 푸시를 하고
            if(stack.isEmpty()){
                stackPush(stack, num++, sb);
                continue;
            }
            //inputNum이 -1이라면
            if(inputNum == -1){
                //새 값을 입력받는다.
                inputNum = sc.nextInt();
            }
            //스택이 비어있지 않고, 현재 값보다 스택 피크가 크다면
            if(stack.peek() > inputNum){
                //오류
                return error;
            }
            else if(stack.peek() < inputNum){
                if(num > n){
                    return error;
                }
                stackPush(stack, num++, sb);
            }
            //정확한 값을 찾는다면
            else{
                stackPop(stack, sb);
                inputNum = -1;
                count++;
            }
        }
    }

    public static void stackPush(Stack stack, int num, StringBuilder sb){
        stack.push(num);
        sb.append("+\n");
    }

    public static void stackPop(Stack stack, StringBuilder sb){
        stack.pop();
        sb.append("-\n");
    }
}