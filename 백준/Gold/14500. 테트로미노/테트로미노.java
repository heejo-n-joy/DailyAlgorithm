import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 테트로미노(테트리스의 그것)
 * <p>
 * 요구하는 출력
 * - 테트로미노 1개를 종이에 이리저리 잘 배치해보고 가능한 최대값
 * <p>
 * 입력 변수
 * - N : 종이의 세로 크기
 * - M : 종이의 가로 크기
 * - 종이에 써진 수들
 * <p>
 * 문제 유의사항
 * - 5종류의 테트로미노는 회전이 가능하다
 * <p>
 * 전략
 * - 하나씩 다 대입해볼까..?
 * <p>
 * 체감 난이도 : ★★☆☆☆
 * <p>
 * 회고할 내용
 *
 * @author HeejoPark
 */
public class Main {
    static int paper[][];
    static int N;
    static int M;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   //종이의 세로 크기
        M = Integer.parseInt(st.nextToken());   //종이의 가로 크기
        paper = new int[N][M];
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++){
                paper[i][j] = Integer.parseInt(st.nextToken()); //종이 정보 입력
            }
        }
        int total_max = 0;
        for(int i = 0; i<N; i++) {
            for (int j = 0; j < M; j++) {
                //해당 좌표를 기준으로 계산을 시작한다.
                total_max = Math.max(total_max, calc(i, j));
            }
        }
        System.out.println(total_max);
    }

    static int tetrominos[][][] =
                {{{0, 0}, {0, 1}, {0,2},{0,3}, {2}}, //일직선
                        {{0,0}, {0,1}, {1,0}, {1,1}},   //네모
                        {{0,0}, {1,0}, {2,0}, {2,1}},   //기역자
                        {{0,0}, {1,0}, {1,1}, {2,1}},   //z모양
                        {{0,0}, {0,1}, {0,2}, {1,1}}};   //ㅜ모양
    static int dr[] = {1, 1, -1, -1};
    static int dc[] = {1, -1, 1, -1};
    public static int calc(int x, int y){
        int max = 0;
        int currentValue = 0;
        for(int i =0; i<5; i++){    //테트로미노의 개수
            int nextX;
            int nextY;
            for(int k = 0; k<2; k++) {  //선택한 테트로미노의 대칭 모양까지 포함
                for(int l = 0; l<4; l++) {  //선택한 테트로미노의 회전 모양까지 포함
                    currentValue = 0;
                    for (int j = 0; j < 4; j++) {   //선택된 테트로미노를 좌표를 이용하여 계산
                        nextX = x + dr[l] * tetrominos[i][j][k];
                        nextY = y + dc[l] * tetrominos[i][j][1 - k];
                        if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {   //범위를 벗어나는지 체크
                            break;
                        }
                        currentValue += paper[nextX][nextY];
                    }
                    max = Math.max(max, currentValue);
                }
            }
        }
        return max;
    }
}