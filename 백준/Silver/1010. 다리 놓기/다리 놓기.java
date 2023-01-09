import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 재원 시장님의 다리 건설 프로젝트
 * <p>
 * 요구하는 출력
 * - 다리를 지을 수 있는 경우의 수
 * <p>
 * 입력 변수
 * - T : 테스트 케이스
 * - N : 서쪽의 사이트
 * - M : 동쪽의 사이트
 * <p>
 * 문제 유의사항
 * - 다리는 서로 겹칠 수 없다.
 * - 다리 개수는 서쪽 사이트와 동일한 N개
 * <p>
 * 체감 난이도 : ★☆☆☆☆
 * - 조합으로 풀어야겠다는 생각이 바로 들었다.
 * <p>
 * 회고할 내용
 * -
 *
 * @author HeejoPark
 */
public class Main {
    static int[][] record;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());     //테스트 케이스
        record = new int[30][16];
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());    //서쪽의 사이트
            int M = Integer.parseInt(st.nextToken());    //동쪽의 사이트
            System.out.println(combination(M, N));
        }
    }

    public static int combination(int M, int N){
        //N이 M의 절반보다 크면, N = M-N 처리
        if(N>(M/2)){
            N = M - N;
        }
        //저장된 값이 없다면 새로 값을 입력해주자
        if(record[M][N]==0){
            if(M<= 1 || N < 1){
                //M이 1이하 or N이 1 미만이면 무조건 결과는 1이 된다.
                record[M][N] = 1;
            }
            else {
                //(M)C(N) = (M-1)C(N-1) + (M-1)C(N)
                record[M][N] = combination(M - 1, N - 1) + combination(M - 1, N);
            }
        }
        return record[M][N];
    }
}