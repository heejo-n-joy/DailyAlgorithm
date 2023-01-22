import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 배낭의 무게제한
 * <p>
 * 요구하는 출력
 * - 배낭에 넣을 수 있는 물건들의 가치의 최대값
 * <p>
 * 입력 변수
 * - N : 물품의 수
 * - K : 버틸 수 있는 무게
 * - 물건들의 무게(W)와 가치(V)
 * <p>
 * 문제 유의사항
 * - 무게와 가치는 다르다.
 * <p>
 * 전략
 * - 2차원 배열을 가지고, 해당 배열 내에는 최대 가치를 담아보자.
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 회고
 * - 오랜만에 DP를 풀었다. 옛날에 풀어본 방식이 어렴풋이 기억이 나 풀 수 있었다.
 * - DP는 확실히 어떻게 풀어야할지 보이면 쉽지만, 생각이 안나면 늪에서 빠져나올 수 없는 것 같다..
 * @author HeejoPark
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //물품의 수
        int K = Integer.parseInt(st.nextToken());   //배낭의 무게
        int[][] board = new int[N][K+1];
        int[][] objects = new int[N][2];
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            objects[i][0] = Integer.parseInt(st.nextToken());   //물건의 무게
            objects[i][1] = Integer.parseInt(st.nextToken());   //물건의 가치
        }

        for(int i =0; i<N; i++){
            for(int j =0; j<K+1; j++){
                //첫 번째 물건이라면
                if(i == 0){
                    if(j<objects[i][0]){
                        board[i][j] = 0;
                    }
                    else{
                        board[i][j] = objects[i][1];
                    }
                }
                //두 번째 물건부터는 비교를 들어간다.
                else{
                    if(j-objects[i][0] < 0){
                        board[i][j] = board[i-1][j];
                    }
                    else {
                        board[i][j] = Math.max(board[i - 1][j], board[i-1][j - objects[i][0]] + objects[i][1]);
                    }
                }
            }
        }
        //결과
        System.out.println(board[N-1][K]);
    }
}