import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 문제 내용
 * - 숫자들 사이에 부호를 넣어서 0 만들기
 * <p>
 * 요구하는 출력
 * - 0이 되는 경우들을 그대로 출력하기
 * <p>
 * 전략
 * - 숫자의 개수는 최대 9개이므로, 부호들을 고르는 경우의 수는 3^8개. 완전 탐색이 가능하다.
 * - 부호에 따라 빼기, 더하기, 혹은 숫자 합치기가 되기 때문에, 오른쪽에서 왼쪽 방향으로 탐색을 진행
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        char[] operators = {'+', '-', ' '};
        Arrays.sort(operators);
        for(int test_case = 1; test_case<=T; test_case++){
            int N = Integer.parseInt(br.readLine());
            //계산 및 출력
            int[] operator = new int[N-1];
            calc(N-1, 0, N, operator, operators);
            System.out.println();
        }
    }

    public static void calc(int total_count, int current_count, int N, int[] operator, char[] operators){
        if(total_count == current_count){
            //계산
            int result = 0;
            int index = N-2;
            int num = 0;
            int count = 0;
            while(true){
                num += Math.pow(10, count) * (index + 2);
                if(index == -1){
                    result += num;
                    break;
                }
                switch(operators[operator[index]]){
                    case '+':
                        result += num;
                        num = 0;
                        count = 0;
                        break;
                    case '-':
                        result -= num;
                        num = 0;
                        count = 0;
                        break;
                    case ' ':
                        count++;
                        break;
                }
                index--;
            }
            if(result == 0){
                for(int i =0; i<N-1; i++){
                    System.out.print((i+1));
                    System.out.print(operators[operator[i]]);
                }
                System.out.println(N);
            }
            return;
        }
        for(int i =0; i<3; i++){
            operator[current_count] = i;
            calc(total_count, current_count+1, N, operator, operators);
        }
    }
}