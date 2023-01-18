import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 주사위를 굴려 점수를 획득하자
 * <p>
 * 요구하는 출력
 * - 획득하는 점수의 총합
 * <p>
 * 입력 변수
 * - N : 지도의 세로 크기
 * - M : 지도의 가로 크기
 * - K : 이동 횟수
 * - 지도의 점수들
 * <p>
 * 문제 유의사항
 * - 이동 방향에 칸이 없으면 반대로 이동
 * - 주사위가 굴러갈 때 어디가 아랫면으로 가는지, 윗면으로 가는지 잘 체크해야 한다
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 전략
 * - 주사위 이동에 따른 변화를 함수로 구현
 * - 나머지는 문제 요구사항대로 시뮬레이션하면 될 것 같다.
 * <p>
 * 회고할 내용
 * - 큰일이다.. 예제 8번이 해결이 되지 않는다..
 * - 원인을 찾았다. 주사위 방향에 따른 값 변경을 완벽하게 반대로 하고 있었다! 아직 암산으로 생각하고 반영하는게 서툰 것 같다.
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //지도의 세로 크기
        int M = Integer.parseInt(st.nextToken());   //지도의 가로 크기
        int K = Integer.parseInt(st.nextToken());   //주사위 이동 횟수

        //지도 정보를 입력
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //주사위 기본 세팅
        int[][] dice = new int[4][3];
        dice[0][1] = 2;
        dice[1][0] = 4;
        dice[1][1] = 1;
        dice[1][2] = 3;
        dice[2][1] = 5;
        dice[3][1] = 6;

        int score = 0;  //처음 주사위가 위치한 곳에서의 스코어는 인정하지 않는다.
        int direction = 0;  //0~3. 순서대로 동쪽(0), 북쪽(1), 서쪽(2), 남쪽(3)
        int[] dr = {0, -1, 0, 1};
        int[] dc = {1, 0, -1, 0};
        int[] currrentLocation = {0, 0}; //기본 세팅
        //실행
        for (int i = 0; i < K; i++) {
            //1. 주사위가 해당 방향으로 한 칸 굴러간다.
            int nextX = currrentLocation[0] + dr[direction];    //다음 X 위치
            int nextY = currrentLocation[1] + dc[direction];    //다음 Y 위치
            //만약 다음 위치가 지도 범위를 벗어난다면, 다음 위치는 반대가 된다.
            if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {
                direction = (direction + 2) % 4; //방향을 반대로 바꾸고
                nextX = currrentLocation[0] + dr[direction];    //다음 X 위치 재설정
                nextY = currrentLocation[1] + dc[direction];    //다음 Y 위치 재설정
            }
            //주사위 이동
            currrentLocation[0] = nextX;
            currrentLocation[1] = nextY;
            moveDice(dice, direction);  //해당 방향으로 이동에 따른 주사위 변화

            //2. 해당 칸에 대한 점수를 획득한다.
            score += getScore(nextX, nextY, map, N, M, dr, dc);

            //3. 주사위의 아랫면에 있는 정수와 지도의 정수를 비교해 이동방향을 결정한다.
            if (valueOfDiceBottom(dice) > map[currrentLocation[0]][currrentLocation[1]]) {
                //시계방향으로 회전
                direction = ((direction - 1) + 4) % 4;
            } else if (valueOfDiceBottom(dice) < map[currrentLocation[0]][currrentLocation[1]]) {
                //반시계방향으로 회전
                direction = (direction + 1) % 4;
            }
        }

        //결과 출력
        System.out.println(score);
    }

    public static int getScore(int x, int y, int[][] map, int N, int M, int[] dr, int[] dc) {
        int score = map[x][y];
        boolean[][] check = new boolean[N][M];  //이미 탐색한 구간인지 체크
        int count = 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        check[x][y] = true;
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nextX = temp[0] + dr[i];
                int nextY = temp[1] + dc[i];
                //지도 범위를 벗어나면 패스
                if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {
                    continue;
                }
                //만약 이미 탐색한 곳이라면 패스
                if (check[nextX][nextY]) {
                    continue;
                }
                //만약 같은 점수를 가지고 있다면
                if (map[nextX][nextY] == score) {
                    queue.offer(new int[]{nextX, nextY});   //탐색 큐에 추가하고
                    count++;    //카운트를 1 증가한다.
                }
                check[nextX][nextY] = true;     //탐색했다는 표시를 해준다.
            }
        }
        //같은 개수 * 정수인 총 점수 반환
        return count * score;
    }

    public static void moveDice(int[][] dice, int direction) {
        int temp = dice[1][1];
        switch (direction) {
            case 0:
                //주사위는 동쪽으로 이동
                dice[1][1] = dice[1][0];
                dice[1][0] = dice[3][1];
                dice[3][1] = dice[1][2];
                dice[1][2] = temp;
                break;
            case 1:
                //주사위는 북쪽으로 이동
                dice[1][1] = dice[2][1];
                dice[2][1] = dice[3][1];
                dice[3][1] = dice[0][1];
                dice[0][1] = temp;
                break;
            case 2:
                //주사위는 서쪽으로 이동
                dice[1][1] = dice[1][2];
                dice[1][2] = dice[3][1];
                dice[3][1] = dice[1][0];
                dice[1][0] = temp;
                break;
            case 3:
                //주사위는 남쪽으로 이동
                dice[1][1] = dice[0][1];
                dice[0][1] = dice[3][1];
                dice[3][1] = dice[2][1];
                dice[2][1] = temp;
                break;
        }
    }

    public static int valueOfDiceBottom(int[][] dice) {
        return dice[3][1];
    }
}