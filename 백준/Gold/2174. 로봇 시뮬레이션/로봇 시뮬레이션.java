import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 로봇을 시뮬레이션 돌리자
 * <p>
 * 요구하는 출력
 * - 시뮬레이션 결과 (성공했는지, 실패했으면 왜 실패했는지)
 * <p>
 * 입력 변수
 * - A, B : 땅의 크기
 * - N : 로봇의 개수
 * - M : 명령의 개수
 * <p>
 * 문제 유의사항
 * - x좌표가 반대로 되어있다.
 * - 실패했다면 맨 처음 실패 이유만 출력한다.
 * <p>
 * 전략
 * - 시뮬레이션을 단순히 돌려 해결이 가능해보인다.
 * - 뒤집힌 좌표는 처음부터 뒤집어버려 계산을 편하게 하자
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 회고
 * - 좌표가 좀 특이했는데, 그냥 오른쪽으로 지도 자체를 회전해버렸다. 즉, 북쪽이 위를 향하는 것이 아닌 오른쪽을 향하게.
 * - 그러면 좌표 계산법을 굳이 바꿀 필요가 없어진다.
 * @author HeejoPark
 */

public class Main {
    static int[] dr ={0, 1, 0, -1}; //오른쪽(북), 아래쪽(동), 왼쪽(남), 위쪽(서)
    static int[] dc ={1, 0, -1, 0}; //오른쪽(동), 아래쪽(남), 왼쪽(서), 위쪽(북)

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());   //복도의 크기
        int B = Integer.parseInt(st.nextToken());   //복도의 크기
        int[][] ground = new int[A][B];
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //로봇의 개수
        int M = Integer.parseInt(st.nextToken());   //명령의 개수

        int[][] robots = new int[N][3]; //로봇의 초기위치x, y, 방향
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            robots[i][0] = Integer.parseInt(st.nextToken()) - 1;   //로봇의 초기 위치
            robots[i][1] = Integer.parseInt(st.nextToken()) - 1;   //로봇의 초기 위치
            robots[i][2] = changeDirectionCharToInt(st.nextToken().charAt(0));  //로봇의 초기 방향
            ground[robots[i][0]][robots[i][1]] = (i+1); //지도에 로봇 체크
        }

        int[][] commands = new int[M][3];   //로봇 번호, 명령 종류, 반복 횟수
        for(int i =0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            commands[i][0] = Integer.parseInt(st.nextToken());   //로봇 번호
            commands[i][1] = changeCommandCharToInt(st.nextToken().charAt(0));  //명령 종류
            commands[i][2] = Integer.parseInt(st.nextToken());   //명령 반복 횟수
        }
        execProgram(A, B, N, M, robots, commands, ground);
    }

    public static void execProgram(int A, int B, int N, int M, int[][] robots, int[][] commands, int[][] ground){
        for(int i =0; i<M; i++){
            for(int j =0; j<commands[i][2]; j++) {
                int robotNum = commands[i][0] - 1;
                switch (commands[i][1]) {
                    case 0:
                        //방향을 왼쪽으로 90도 회전
                        robots[robotNum][2] += 3;
                        robots[robotNum][2] %= 4;
                        break;
                    case 1:
                        //방향을 오른쪽으로 90도 회전
                        robots[robotNum][2] += 1;
                        robots[robotNum][2] %= 4;
                        break;
                    case 2:
                        //앞으로 한 칸 움직이기
                        int curX = robots[robotNum][0];
                        int curY = robots[robotNum][1];
                        int nextX = curX + dr[robots[robotNum][2]];
                        int nextY = curY + dc[robots[robotNum][2]];
                        //범위를 벗어난다면
                        if(nextX < 0 || nextX >= A || nextY < 0 || nextY >= B){
                            System.out.println("Robot " + (robotNum+1) + " crashes into the wall");
                            return;
                        }

                        //이미 해당 장소에 다른 로봇이 있다면
                        if(ground[nextX][nextY] > 0){
                            System.out.println("Robot " + (robotNum + 1) + " crashes into robot " + ground[nextX][nextY]);
                            return;
                        }

                        //둘 다 아니라면
                        ground[curX][curY] = 0;
                        ground[nextX][nextY] = robotNum + 1;
                        robots[robotNum][0] = nextX;
                        robots[robotNum][1] = nextY;
                }
            }
        }
        System.out.println("OK");
    }

    public static int changeCommandCharToInt(char command){
        switch(command){
            case 'L':
                return 0;   //L 명령어는 0으로 변환
            case 'R':
                return  1;   //R 명령어는 1로 변환
            case 'F':
                return  2;   //F 명령어는 2로 변환
        }
        return -1;  //에러
    }
    public static int changeDirectionCharToInt(char direction){
        switch(direction){
            case 'E':
                return 1;   //동쪽은 1으로 변환
            case 'S':
                return  2;   //남쪽은 2로 변환
            case 'W':
                return  3;   //서쪽은 3로 변환
            case 'N':
                return  0;   //북쪽은 0으로 변환
        }
        return -1;  //에러
    }
}