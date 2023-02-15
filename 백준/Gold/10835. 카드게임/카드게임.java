import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
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
        int[] left_cards = new int[N];
        int[] right_cards = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i =N-1; i>=0; i--){
            left_cards[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int i =N-1; i>=0; i--){
            right_cards[i] = Integer.parseInt(st.nextToken());
        }
        for(int left_index =0; left_index<=N; left_index++){
            for(int right_index = 0; right_index<=N; right_index++){
                //왼쪽 카드를 버렸던 것이다.
                if(left_index>0) {
                    DP[left_index][right_index] = Math.max(DP[left_index][right_index], DP[left_index - 1][right_index]);
                }
                //양쪽 카드를 버렸던 것이다.
                if(left_index>0 && right_index > 0){
                    DP[left_index][right_index] = Math.max(DP[left_index][right_index], DP[left_index - 1][right_index-1]);
                }
                //오른쪽 카드를 버리고 점수를 얻었던 것이다.
                if(right_index>0 && left_index<N && (left_cards[left_index] > right_cards[right_index-1])){
                    DP[left_index][right_index] = Math.max(DP[left_index][right_index], DP[left_index][right_index-1]+right_cards[right_index-1]);
                }
            }
        }
        //결과 출력
        System.out.println(DP[N][N]);
    }
}