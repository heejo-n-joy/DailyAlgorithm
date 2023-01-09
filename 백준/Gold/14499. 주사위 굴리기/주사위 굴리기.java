import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 지도 위에서 주사위를 굴린다
 *
 * 요구하는 출력
 * - 이동할 때마다 주사위 윗 면에 있는 수를 출력
 *
 * 입력 변수
 * - N : 보드의 크기 (N * N)
 * - K : 사과의 개수
 * - 사과의 위치 K줄
 * - L : 뱀의 방향 변환 횟수
 * - Xi : 몇초에 방향전환하는지
 * - Ci : 어디로 방향전환 하는지 (L은 왼쪽으로, D는 오른쪽으로 90도)
 *
 * 문제 유의사항
 * - 주사위를 툭툭 치면서 지도를 이동하는 느낌
 * - 주사위가 지도를 벗어나게 하는 명령도 있는데, 이건 무시해야 한다.
 * - 주사위 바닥면에 쓰여 있는 수와 지도의 값은 흡수된다.
 *
 * 체감 난이도 : ★★☆☆☆
 * - 시뮬레이션 문제로 주어진 규칙 그대로 실행하기 때문에 단순해보인다.
 * 
 * 회고할 내용
 * - 주사위가 굴려지면서 배열이 바뀌는 부분을(moveDice) 맛깔나게 바꿔보고 싶긴 하지만.. 딱히 생각이 나지 않는다.
 *
 * @author HeejoPark
 *
 */
public class Main {
    static int[][] map;
    static int[][] dice;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());    //지도의 세로 크기
        int M = Integer.parseInt(st.nextToken());    //지도의 가로 크기
        int x = Integer.parseInt(st.nextToken());    //주사위가 놓인 위치
        int y = Integer.parseInt(st.nextToken());    //주사위가 놓인 위치
        int K = Integer.parseInt(st.nextToken());    //명령의 개수
        map = new int[N][M];
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ;j<M;j++){
                map[i][j] = Integer.parseInt(st.nextToken());   //지도에 주어진 값 입력
            }
        }
        int[] cmd = new int[K];
        st = new StringTokenizer(br.readLine());
        for(int i =0; i<K; i++){
            cmd[i] = Integer.parseInt(st.nextToken());  //명령 받아오기. 동쪽1, 서쪽2, 북쪽3, 남쪽4
        }
        //주사위 계산용 배열 세팅
        dice = new int[4][3];

        //주사위 굴리기 게임 시작
        for(int round = 0; round<K; round++){
            //1. 방향으로 굴리기 전 가능한지 체크
            int nextX = x + dr[cmd[round] - 1];
            int nextY = y + dc[cmd[round] - 1];
            //1-1. 불가능하면 다음 라운드로 continue;
            if(nextX < 0 || nextX >=N || nextY<0 || nextY>=M){
                continue;
            }
            //2. 주사위 이동
            //2-1. x,y좌표 수정
            x = nextX;
            y = nextY;
            //2-2. 주사위 배열 이동
            moveDice(cmd[round]);
            //3. 주사위 윗면 출력
            System.out.println(getDiceTop());
            //4. 주사위 아랫면과 지도 칸 수 비교해서 적용
            compareDiceAndMap(x, y);
        }
    }

    static int dr[] = {0, 0, -1, 1};
    static int dc[] = {1, -1, 0, 0};

    public static void moveDice(int cmd){
        int temp = dice[3][1];
        switch(cmd){
            case 1: //동쪽으로 이동
                dice[3][1] = dice[1][2];
                dice[1][2] = dice[1][1];
                dice[1][1] = dice[1][0];
                dice[1][0] = temp;
                break;
            case 2: //서쪽으로 이동
                dice[3][1] = dice[1][0];
                dice[1][0] = dice[1][1];
                dice[1][1] = dice[1][2];
                dice[1][2] = temp;
                break;
            case 3: //북쪽으로 이동
                dice[3][1] = dice[0][1];
                dice[0][1] = dice[1][1];
                dice[1][1] = dice[2][1];
                dice[2][1] = temp;
                break;
            case 4: //남쪽으로 이동
                dice[3][1] = dice[2][1];
                dice[2][1] = dice[1][1];
                dice[1][1] = dice[0][1];
                dice[0][1] = temp;
                break;
        }
    }

    //주사위 아랫면 출력
    public static int getDiceBottom(){
        return dice[3][1];
    }

    public static void setDiceBottom(int value){
        dice[3][1] = value;
        return;
    }

    //주사위 윗면 출력
    public static int getDiceTop(){
        return dice[1][1];
    }

    public static void compareDiceAndMap(int x, int y){
        if(map[x][y]==0){
            map[x][y] = getDiceBottom();
        }
        else{
            setDiceBottom(map[x][y]);
            map[x][y] = 0;
        }
    }
}