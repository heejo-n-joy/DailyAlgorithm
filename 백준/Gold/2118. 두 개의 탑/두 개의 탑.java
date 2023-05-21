import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 요구하는 출력 : 원형으로 이루어진 탑들에서 두 탑의 거리의 최대값
 *
 * 아이디어 : 두 포인터
 * - 원형의 총 길이를 저장하고 있는다.
 * 1. 탑을 일렬로 정렬한 후에, start와 end포인트를 처음부터 시작한다.
 * 2. 해당 start를 기준으로 작은 거리의 최대값이 되는 end 포인트를 구한다. (총 거리의 절반 이상이 될 때까지)
 * 3. 그 다음, start를 한 칸 이동한다.
 * 4. 2,3번을 반복한다.
 * 5. 역대 최대값을 저장한다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] tower = new int[N-1];
        int total_len = 0;
        for(int i = 0; i<N; i++){
            if(i<N-1){
                tower[i] = Integer.parseInt(br.readLine());
                total_len += tower[i];
            }
            else{
                total_len += Integer.parseInt(br.readLine());
            }
        }

        int max = 0;
        int start = 0;
        int end = 0;

        int current_len = 0;

        while(end <= N-1 && start <= N-2){
            //알맞는 end 자리를 찾기
            if((current_len < total_len - current_len) && (end <= N-2)){
                current_len += tower[end];
                end++;
            }
            //알맞는 start 자리를 찾기
            else {
                current_len -= tower[start];
                start++;
            }
            max = Math.max(max, Math.min(current_len, total_len - current_len));
        }

        System.out.println(max);
    }

}