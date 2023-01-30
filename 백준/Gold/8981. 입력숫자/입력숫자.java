import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - mystery.c로 출력된 숫자들
 * <p>
 * 요구하는 출력
 * - mystery.c를 거치기 전의 입력 숫자들
 * <p>
 * mystery.c의 요약
 * - count 개수만큼 반복 (0부터 ++)
 * - value가 0이 아닌 index를 찾아, 해당 value를 출력
 * - 만약 value가 0이라면 index++를 후 다시 위를 확인
 * - 해당 index의 value는 0으로 바꾸고, index에 value를 더해서 위를 반복
 * <p>
 * 전략
 * - 결과물의 처음부터 하나씩 확인하자. 어차피 맨 처음에는 맨 첫 번째 input을 확인하니.
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //정수 개수
        int[] input = new int[N];
        int[] output = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            output[i] = Integer.parseInt(st.nextToken());
        }

        //실행
        exec(input, output, N);

        //결과 출력
        System.out.println(N);
        for (int i = 0; i < N; i++) {
            System.out.print(input[i] + " ");

        }
    }

    public static void exec(int[] input, int[] output, int N) {
        int form = 0;    //입력 배열을 활보하는 index
        for(int output_index = 0; output_index < N; output_index++){
            int value = output[output_index];
            while(input[form] > 0){
                form = (form + 1) % N;
            }
            input[form] = value;
            form = (form + value) % N;
        }
    }
}