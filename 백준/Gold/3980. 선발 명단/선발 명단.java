import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 11명의 선수들을 포지션에 배치할 때 능력치 합의 최대값을 출력
 *
 * 아이디어 : 완전 탐색
 * - 능력이 0 이면 해당 위치에 세울 수 없으며, 이를 활용하여 백트레킹을 진행한다.
 * - 1. 포지션을 배치한다.
 * - 2. 해당 포지션의 합을 매긴다.
 * - 3. 가장 큰 수를 가진 값을 출력한다.
 */

public class Main {

    static int result;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());   //테스트 케이스 수

        //실행
        for(int test_case = 1; test_case<=T; test_case++){
            result = 0;

            int[][] scores = new int[11][11];   //각 선수별 포지션의 점수
            for(int i = 0; i<scores.length; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j = 0; j<scores[i].length; j++){
                    scores[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            //숫자를 만들고, 점수를 매기기
            boolean[] check = new boolean[11];
            exec(0, 11, 0, scores, check);

            //결과 출력
            System.out.println(result);
        }
    }

    public static void exec(int count, int N, int current_score, int[][] scores, boolean[] check){
        //선수가 다 모였다면
        if(count == N){
            result = Math.max(current_score, result);
            return;
        }
        //선수를 모으기
        for(int i = 0; i<11; i++){
            //이미 뽑은 선수라면 패스
            if(check[i]){
                continue;
            }
            //만약 선수 능력치가 0이라면 패스
            if(scores[i][count] == 0){
                continue;
            }
            //그게 아니라면 선수로 넣자
            current_score += scores[i][count];
            check[i] = true;
            exec(count+1, N, current_score, scores, check);
            check[i] = false;
            current_score -= scores[i][count];
        }
    }
}
