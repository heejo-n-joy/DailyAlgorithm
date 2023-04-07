import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 이다솜이 벌 수 있는 가장 큰 돈
 *
 * 풀이 방법 : 구현
 * - 자를 나무의 길이를 정한다 (길이 범위는 1부터 가장 큰 나무 길이까지)
 * - 모든 나무들을 길이에 맞춰 자르고, 벌 수 있는 돈을 계산한다.
 * - 이 중 최대값을 구한다.
 *
 * 시간복잡도
 * - 나무의 길이 정하기 : O(N)
 * - 나무들을 자르기 : O(N)
 * - 총 O(N^2)
 *
 * 이론상 최대값
 * - 가격W(10000)* 나무(10000) * 개수N(50) - 50만
 * - 합산에 사용되는 변수는 long 타입을 쓰자
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //나무의 개수
        int C = Integer.parseInt(st.nextToken());   //나무를 자를 때의 가격
        int W = Integer.parseInt(st.nextToken());   //나무 길이 1당 가격
        int[] trees = new int[N];
        int max_tree_len = 0;
        for(int i = 0; i<N; i++){
            trees[i] = Integer.parseInt(br.readLine());
            max_tree_len = Math.max(max_tree_len, trees[i]);
        }

        //실행 (나무위 길이는 1부터 최대 나무 길이까지)
        long max_money = 0;
        for(int cut_tree_len = 1; cut_tree_len <=max_tree_len; cut_tree_len++){
            long current_money = 0;
            for(int index = 0; index < N; index++){

                int possible_tree_count = (trees[index]) / cut_tree_len;    //만들어지는 나무 개수
                int cut_tree_count = (trees[index]-1) / cut_tree_len;   //자르는 횟수

                //나무를 자르고 팔았을 때 양수인지 체크
                int money = (possible_tree_count * cut_tree_len * W) - (C * cut_tree_count);

                //해당 나무를 자르고 파는게 이득이라면 돈을 반영한다.
                if(money > 0){
                    current_money += money;
                }
            }
            max_money = Math.max(max_money, current_money);
        }

        //결과 출력
        System.out.println(max_money);
    }
}