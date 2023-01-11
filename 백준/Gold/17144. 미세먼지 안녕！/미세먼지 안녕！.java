import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 미세먼지는 확산하고, 공기청정기는 작동한다.
 * <p>
 * 요구하는 출력
 * - T초 후 방 안에 남아있는 미세먼지의 양을 출력하라
 * <p>
 * 입력 변수
 * - R,C : 방의 크기
 * - T : 지나야 하는 시간
 * - 방 상태 (미세먼지 양)
 * <p>
 * 문제 유의사항
 * - 공기청정기가 있는 곳으로는 미세먼지가 확산되지 않는다.
 * - 공기청정기를 거친 미세먼지는 모두 정화
 * <p>
 * 전략
 * - 방과 똑같은 사이즈의 새 배열에서 확산 결과값들을 저장하고, 공기청정기를 돌리고 원래 배열에 넣어보자
 * <p>
 * 체감 난이도 : ★★☆☆☆
 * <p>
 * 회고할 내용
 * 1. 공기청정기 정보 입력 구현 X
 * - 공기청정기 정보가 들어간 배열이 있는데, 처음 입력값 스캔할 때 정보 입력을 하지 않았다.
 * - 맵을 복사할 때 공기청정기 정보를 입력하는 과정을 빼먹었다.
 * 2. 칸 위치 탐색 도중 오류
 * - 칸이 없는지 탐색하는 과정에서 없는 칸을 만났을 때는 다시 변수 값을 이전으로 되돌려야 한다.
 * 3. 공기청정기 윗부분과 아래부분의 회전 경계 부분
 * - 공기청정기 정화 함수를 하나로 둘 다 구현하려다 생긴 실수
 * - 공기청정기 윗부분과 아랫부분은 (특히 행이) 나눠져서 계산되어야 한다.
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());   //방의 크기
        int C = Integer.parseInt(st.nextToken());   //방의 크기
        int T = Integer.parseInt(st.nextToken());   //시간
        int[][] room = new int[R][C];
        int[][] airCleaner = new int[2][2];     //공기청정기의 위, 아래
        int airCleanerCount = 0;                //공기청정기 개수 체크
        for(int i =0; i<R; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<C; j++){
                room[i][j] = Integer.parseInt(st.nextToken());
                if(room[i][j] == -1){
                    //공기청정기를 발견할 경우 해당 정보는 따로 추가 저장
                    airCleaner[airCleanerCount][0] = i;
                    airCleaner[airCleanerCount][1] = j;
                    airCleanerCount++;
                }
            }
        }

        //프로그램 실행
        for(int time = 0; time<T; time++){
            int[][] roomWithNewDust = new int[R][C];    //미세먼지 확산을 저장하는 배열 생성
            //미세먼지 확산
            for(int i =0; i<R; i++){
                for(int j = 0; j<C; j++){
                    //해당 방에 미세먼지가 있다면
                    if(room[i][j]>0){
                        int validDirectionCount = 0;
                        //상하좌우에 흩뿌리기
                        for(int k = 0; k<4; k++){
                            //하나의 방향을 정하고
                            int nextR = i + dr[k];
                            int nextC = j + dc[k];
                            //해당 방향이 유효한 곳이라면
                            if(nextR >= 0 && nextR < R && nextC >= 0 && nextC <C){
                                //해당 방향이 공기청정기가 아니라면
                                if(room[nextR][nextC] != -1){
                                    //확산하기
                                    roomWithNewDust[nextR][nextC] += room[i][j] / 5;
                                    validDirectionCount++;
                                }
                            }
                        }
                        //흩뿌리기 작업이 끝났다면, 원래 자리에서의 미세먼지 양을 줄인다.
                        roomWithNewDust[i][j] += room[i][j] - (room[i][j] / 5 * validDirectionCount);
                    }
                }
            }
            //모든 작업이 끝나면 변경된 미세먼지 상태 반영
            room = roomWithNewDust;
            //공기청정기 자리 재지정
            for(int i =0 ; i<airCleanerCount; i++){
                room[airCleaner[i][0]][airCleaner[i][1]] = -1;
            }
            //공기청정기 작동
            executeAirCleaner(room, airCleaner[0], true, R, C);     //공기청정기 윗부분 실행
            executeAirCleaner(room, airCleaner[1], false, R, C);    //공기청정기 아랫부분 실행
        }
        //남은 미세먼지의 양을 계산한다
        int totalDustCount = 0;
        for(int i =0; i<R; i++){
            for(int j = 0; j<C; j++){
                if(room[i][j]>0) {  //공기청정기 -1은 무시해야 하기 때문
                    totalDustCount += room[i][j];
                }
            }
        }

        //결과 출력
        System.out.println(totalDustCount);
    }

    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    public static void executeAirCleaner(int[][] room, int[] airCleanerLocation, boolean isTopLocation, int R, int C){
        //공기청정기 위치 파악
        int r = airCleanerLocation[0];
        int c = airCleanerLocation[1];
        //방향 선택
        int direction = -1;
        if(isTopLocation){  //공기청정기 윗부분이라면
            direction = 1; //시계 반대방향을 위해 -1값 부여
        }
        int prevR = r;
        int prevC = c;
        //공기청정기 윗 부분이라면 시계방향으로 추적, 아랫 부분이라면 시계반대방향으로 추적
        for(int i = 0; i<4; i++){
            while(true){
                prevR = prevR + dr[i] * (int) Math.pow(direction, i+1);
                prevC = prevC + dc[i] * (int) Math.pow(direction, i+1);
                if(prevR < (isTopLocation?0:airCleanerLocation[0]) || prevR >= (isTopLocation?airCleanerLocation[0]+1:R) || prevC <0 || prevC >=C){
                    //방향 전환이 필요
                    prevR = prevR - dr[i] * (int) Math.pow(direction, i+1); //원위치
                    prevC = prevC - dc[i] * (int) Math.pow(direction, i+1); //원위치
                    break;
                }
                //만약 이동하기 전 위치가 공기청정기라면, 이동되는 값은 0이 된다. (공기 정화)
                if(room[prevR][prevC] == -1){
                    room[r][c] = 0;
                }
                //이동하기 전 위치도 공기청정기가 아니고, 이동할 위치도 공기청정기도 아니라면 미세먼지를 이동시킨다.
                else if(room[r][c] != -1){
                    room[r][c] = room[prevR][prevC];
                }
                //한 칸 옮겨서 계속 정화
                r = prevR;
                c = prevC;
            }
        }
    }
}