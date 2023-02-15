import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 지훈이의 카드 게임
 * <p>
 * 요구하는 출력
 * - 카드게임에서 얻을 수 있는 최고 점수
 * <p>
 * 전략
 * - DP로 풀기. DP[왼쪽 카드][오른쪽 카드]
 * - 규칙1) 왼쪽 카드만 버리거나, 양쪽 카드를 버린다.
 * - 규칙2) 오른쪽 카드의 값이 더 작으면, 오른쪽 카드만 버리고 점수를 얻을 수 있다.
 * - 맨 마지막 카드들에서 시작한다.
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //카드의 개수
        int[][] DP = new int[N+1][N+1]; //칸에는 여태까지의 점수가 적혀있다.
        int[] left_cards = new int[N+1];
        int[] right_cards = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int l =N; l>0; l--){
            left_cards[l] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int r =N; r>0; r--){
            right_cards[r] = Integer.parseInt(st.nextToken());
        }
        for(int left_index =1; left_index<=N; left_index++){
            for(int right_index = 1; right_index<=N; right_index++){
                //왼쪽 카드를 추가하거나, 양쪽 카드를 추가한다.
                DP[left_index][right_index] = Math.max(DP[left_index-1][right_index], DP[left_index-1][right_index-1]);
                //오른쪽 카드를 추가한다. (가능하다면)
                if(left_cards[left_index] > right_cards[right_index]){
                    DP[left_index][right_index] = Math.max(DP[left_index][right_index-1] + right_cards[right_index], DP[left_index][right_index]);
                }
            }
        }
        //결과 출력
        System.out.println(DP[N][N]);
    }
}