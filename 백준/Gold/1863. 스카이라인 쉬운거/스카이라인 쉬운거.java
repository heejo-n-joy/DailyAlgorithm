import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 스카이라인 윤곽을 바탕으로 추측할 수 있는 건물의 최수 개수
 *
 * 풀이 방법 : 스택
 * - 1. 위치와 스카이라인 높이가 들어올 때
 * - 1-0. 만약 높이가 0이라면, 현재 스택을 모두 뺀다(pop).
 * - 1-1. 스택에 아무것도 없거나 최근값(peek)보다 높으면 스택에 추가(push)하고
 * - 1-2. 최근값(peek)보다 낮으면 스택에서 빼고(pop), 다시 1번으로 돌아간다.
 * - 1-3. 만약 최근값(peek)과 같으면, 스택에 넣지 않고 다음으로 넘어간다. 높이가 같으면 같은 건물이라 할 수 있기 때문)
 * - 이 과정에서 pop을 한 횟수만큼 건물이 존재한다.
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());   //스카이라인의 변경 횟수
        Stack<Integer> stack = new Stack<>();
        int minimum_count = 0;
        int[] skylines = new int[n];
        for(int i = 0; i<n; i++) {
            String[] str = br.readLine().split(" ");    //x좌표는 계산 상 필요없어서 str[1]만을 사용한다.
            skylines[i] = Integer.parseInt(str[1]);
        }

        for(int i = 0; i<n; i++) {
            //만약 높이가 0이라면
            if(skylines[i] == 0){
                //존재하는 모든 스택에서 값을 뺀다.
                while(!stack.isEmpty()){
                    stack.pop();
                    minimum_count++;
                }
                continue;
            }
            //스택이 비어있다면
            if(stack.isEmpty()){
                stack.push(skylines[i]);
            }
            //스택에 값이 존재한다면
            else{
                //스택 값이 더 높다면
                if(stack.peek() > skylines[i]){
                    stack.pop();
                    minimum_count++;
                    i--;
                }
                //스택 값이 더 낮다면
                else if(stack.peek() < skylines[i]){
                    stack.push(skylines[i]);
                }
                //값이 서로 같은 경우는 패스한다.
            }
        }
        //남은 스카이라인마다 건물 하나씩 잡고 일괄적으로 더한다.
        minimum_count += stack.size();

        //결과 출력
        System.out.println(minimum_count);
    }
}