import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 두 용액을 선택해서 혼합했을 때, 절대값이 가장 적게 나오는 두 용액들
 *
 * 풀이 방법 : 정렬 + 두 포인터
 * - 1. 정렬을 한다.
 * - 2. 양 끝에 포인터(L, R)를 두고 계산을 한다.
 * - 3. 음수가 나온다면 포인터 L을 오른쪽으로 한 칸 이동하고, 양수가 나온다면 포인터 R을 왼쪽으로 한 칸 이동한다. 0이 나온다면 즉시 종료한다.
 * - 4. 계산하면서 최소값이 갱신이 되었는지를 체크하고 반영한다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //전체 용액의 수
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] solutions = new int[N];
        for(int i =0; i<N; i++){
            solutions[i] = Integer.parseInt(st.nextToken());
        }

        //오름차순 정렬
        Arrays.sort(solutions);

        //포인터 생성
        int L_index = 0;
        int R_index = N-1;

        //결과 저장용 변수
        int min_abs_sum = Integer.MAX_VALUE;    //용액의 절대값의 최소값
        int min_L = L_index;    //최소값을 가지는 용액 L
        int min_R = R_index;    //최소값을 가지는 용액 R

        //포인터를 활용한 비교 실행
        while(true){
            //두 포인터가 교차하는 경우 종료한다.
            if(L_index >= R_index){
                break;
            }

            //포인터가 가리키는 용액의 합을 계산하기
            int temp_sum = solutions[L_index] + solutions[R_index];

            //최소값이 갱신되었다면
            if(Math.abs(temp_sum) < min_abs_sum){
                //이를 반영하기
                min_abs_sum = Math.abs(temp_sum);
                min_L = L_index;
                min_R = R_index;
            }

            //합이 음수라면 왼쪽 포인터를 오른쪽으로 1 이동
            if(temp_sum < 0){
                L_index++;
            }
            //합이 양수라면 오른쪽 포인터를 왼쪽으로 1 이동
            else if(temp_sum > 0){
                R_index--;
            }
            //합이 0이라면 즉시 종료
            else{
                break;
            }
        }

        //결과 출력
        System.out.println(solutions[min_L] + " " + solutions[min_R]);
    }
}