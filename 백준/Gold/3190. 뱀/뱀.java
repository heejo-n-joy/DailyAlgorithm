import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 뱀이 맵을 기어다니며 사과를 먹고 성장하는 게임 시뮬레이션
 *
 * 요구하는 출력
 * - 게임이 몇 초에 끝나는지 계산
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
 * - 뱀의 방향 변환 정보 이전에 게임이 끝날 수 있다.
 * - 이동시 뱀의 길이가 1 늘어났다가 다시 줄어든다.
 *
 * 체감 난이도 : ★★☆☆☆
 * - 시뮬레이션 문제로 주어진 규칙 그대로 실행하기 때문에 단순해보인다.
 * - Deque를 이용하여 뱀의 몸의 위치를 저장하자.
 *
 * @author HeejoPark
 *
 */
public class Main {
    static int dr[] = {0, 1, 0, -1};    //오른쪽을 기준으로 시계방향 순서
    static int dc[] = {1, 0, -1, 0};    //오른쪽을 기준으로 시계방향 순서
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //보드의 크기
        int K = Integer.parseInt(br.readLine());    //사과의 개수
        boolean map[][] = new boolean[N][N];
        for(int i =0; i<K; i++){    //사과의 정보를 맵에 입력
            String apple[] = br.readLine().split(" ");
            map[Integer.parseInt(apple[0])-1][Integer.parseInt(apple[1])-1] = true;
        }
        int L = Integer.parseInt(br.readLine());    //뱀의 방향 회전 정보 개수
        int direction_data_map[][] = new int[L][2];    //방향 회전 정보가 담긴 배열
        for(int i =0; i<L; i++){
            String direction_information[] = br.readLine().split(" ");
            direction_data_map[i][0] = Integer.parseInt(direction_information[0]);
            direction_data_map[i][1] = direction_information[1].equals("D")?1:0;   //L이면 왼쪽(0), D면 오른쪽(1)
        }
        //뱀의 위치가 놓일 deque
        Deque<int[]> snake = new LinkedList<>();
        snake.offerLast(new int[]{0, 0});
        //게임 시작
        int sec = 0;        //게임 경과 시간(초단위)
        int direction = 0;  //초기 방향
        int direction_data_index = 0;   //방향 회전 정보의 인덱스
        game : while(true){
            //0. 시간 증가
            sec++;
            //1. 현재 머리 위치를 불러온다. 그리고 방향을 기준으로 다음 칸으로 이동되는지 체크
            int currentHead[] = snake.peekFirst();
            int nextHead[] = {currentHead[0] + dr[direction], currentHead[1] + dc[direction]};  //다음 칸
            // 1-1. 혹시 장애물이 있다면(배열 밖의 범위) 게임 종료
            if(nextHead[0] < 0 || nextHead[0] >= N || nextHead[1] < 0 || nextHead[1] >= N){
                break game;
            }
            //1-2. 혹시 뱀의 몸이랑 부딪힌다면 게임 종료
            for(int i =0; i<snake.size(); i++){
                int temp[] = snake.pollFirst();
                if(temp[0] == nextHead[0] && temp[1] == nextHead[1]){
                    break game;
                }
                snake.offerLast(temp);
            }
            //2. 다음 칸으로 이동
            snake.offerFirst(nextHead);
            //3-1. 혹시 이동한 위치에 사과가 놓여져있다면 해당 사과는 사라지고 몸의 길이는 늘어난다.
            if(map[nextHead[0]][nextHead[1]]){
                map[nextHead[0]][nextHead[1]] = false;
            }
            //3-2. 사과가 없다면 뱀의 꼬리 위치는 사라진다.
            else{
                snake.pollLast();
            }
            //4. 혹시 방향 회전이 필요한 시기인지 체크한다.
            if(direction_data_index<L) {
                if (direction_data_map[direction_data_index][0] == sec) {
                    switch (direction_data_map[direction_data_index][1]) {
                        case 1:
                            //오른쪽
                            direction = (direction + 1) % 4;
                            break;
                        case 0:
                            //왼쪽
                            direction = ((direction - 1) + 4) % 4;
                            break;
                    }
                    direction_data_index++;
                }
            }
        }
        System.out.println(sec);
    }
}