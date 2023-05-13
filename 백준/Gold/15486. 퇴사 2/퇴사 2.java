import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * 요구하는 출력 : 퇴사하기 전 상담을 많이 해서 얻을 수 있는 최대 수익
 *
 * 아이디어 : 상담 시작일을 기준으로 종료일의 값을 갱신하는 DP
 * - 여태껏 비슷한 문제를 만나면 종료일을 기준으로 정렬하고 문제를 접근했다.
 * - 그런데 이번 문제에는 최대 150만개가 있을 수 있기 때문에, 정렬을 하지 않는 방향으로 고려했다.
 * - 상담 종류를 확인하면서 동시에 DP에 반영할 수 있도록 했다.
 *
 * 풀이 방법 : DP
 * - 1. 상담 시작일 순서대로 상담을 확인한다.
 * - 2. 현재 날짜까지의 최대 금액을 찾는다.
 * - 3. 상담 종료일이 N보다 크다면 패스한다.
 * - 4. 해당 상담을 할 때와 안할 때의 가격을 보고 최대값을 DP에 저장한다.
 *
 * 결과 변수 타입
 * - 최대 1,500,000 x 1,000 = 1,500,000,000 (15억)
 * - int를 사용한다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());    //상담의 개수

        int[] DP = new int[N+2];
        int current_max = 0;
        for(int start = 1; start<=N; start++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int end = start + Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());

            current_max = Math.max(current_max, DP[start]); //오늘까지의 현재 최대 수익

            //기한 내에 끝나지 않는 상담이라면 패스
            if(end > N+1){
                continue;
            }

            //DP 계산
            DP[end] = Math.max(DP[end], current_max + price);
        }

        //결과 출력
        int result = Math.max(current_max, DP[N+1]);
        System.out.println(result);
    }
}