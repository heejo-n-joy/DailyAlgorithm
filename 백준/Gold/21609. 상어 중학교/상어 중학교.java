import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 상어 중학교 코딩 동아리가 만든 블록 게임
 * <p>
 * 요구하는 출력
 * - 블록게임을 통해 얻은 점수의 합
 * <p>
 * 입력 변수
 * - N : 격자의 크기
 * - M : 색상의 개수
 * - 격자의 블록 정보 (자연수: 일반색상, 0:무지개, -1:검은블록)
 * <p>
 * 문제 유의사항
 * - 격자의 중력은 검은블록이나 격자를 넘을 수 없다.
 * - 남은 그룹이 존재하지 않으면 게임이 종료된다.
 * - 그룹의 조건은 일반블록은 1가지 색상만, 무지개는 상관없다.
 * <p>
 * 전략
 * - bfs 방식으로 색깔별 그룹을 만들어보고 가장 큰 그룹을 선택하자
 * - 다만... 오래 거릴 것 같은 기분..
 * <p>
 * 예상 난이도 : ★★★
 * 체감 난이도 : ★★★
 * <p>
 * 회고
 * - 역시 생각 이상으로 시간이 오래 걸렸다.
 * - 코드의 규모가 커질수록 자잘한 변수명을 헷갈리는 경우가 많아지고, 디버깅 시간도 규모가 클수록 아주 오래 걸린다...
 *
 * @author HeejoPark
 */

class GroupInformationClass{
    Queue<int[]> group;
    int rainbowBrickCount;
    int[] standardLoc = new int[2];

    GroupInformationClass(int N){
        group = new LinkedList<>();
        rainbowBrickCount = 0;
        standardLoc[0] = N-1;
        standardLoc[1] = N-1;
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //맵의 크기
        int M = Integer.parseInt(st.nextToken());   //일반 블록의 색상 종류
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        score = 0;
        letsGame(map, N, M);
        //결과 출력
        System.out.println(score);
    }

    static int score;

    static boolean printForDebug = false;
    public static void letsGame(int[][] map, int N, int M) {
        while(true) {
            //가장 큰 그룹을 선정
            GroupInformationClass bestGroup = selectBestGroup(map, N, M);

            //만약 큰 그룹이 선정이 되지 않았다면
            if(bestGroup.group.isEmpty()){
                //자동 게임을 종료한다
                break;
            }
            //블록을 제거하고 점수를 올린다.
            deleteGroupAndGetScore(map, bestGroup);
            printMap(printForDebug,"점수 반영", map, N);
            //맵에 중력을 작용한다
            gravityMap(map, N);
            printMap(printForDebug,"중력 작용", map, N);
            //맵을 반시계방향으로 90도 돌린다.
            map = turnMap(map, N);
            printMap(printForDebug,"반시계 회전", map, N);
            //다시 맵에 중력을 작용한다.
            gravityMap(map, N);
            printMap(printForDebug,"중력 재 작용", map, N);

        }
    }

    public static void printMap(boolean print, String str, int[][] map, int N){
        if(!print){
            return;
        }
        System.out.println("------" + str + "------");
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                if(map[i][j] == -2){
                    System.out.print("_\t");
                }
                else {
                    System.out.print(map[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static GroupInformationClass selectBestGroup(int[][] map, int N, int M){
        boolean[][][] checkMap = new boolean[M][N][N];  //체크배열은 색깔별로 둔다.
        GroupInformationClass bestGroup = new GroupInformationClass(N);
        //그룹 탐색 시작
        for(int i =0; i<N; i++){
            for(int j = 0; j<N; j++){
                //무지개 블록이라면 탐색을 하지 않고 패스한다. (시작점으로 탐색하지 않는다는 의미)
                //마찬가지로 검은 블록(벽)은 탐색을 하지 않고 패스한다.
                //비어있는 공간도 패스한다.
                if(map[i][j]==0 || map[i][j]==-1 || map[i][j] == -2){
                    continue;
                }

                //해당 위치가 해당 색깔에서 이미 파악이 된 곳이라면 패스
                if(checkMap[map[i][j]-1][i][j]){
                    continue;
                }

                //해당 위치를 기반으로 탐색 시작
                GroupInformationClass tempGroup = new GroupInformationClass(N);
                Queue<int[]> tempGroupForCalc = new LinkedList<>();
                tempGroup.group.offer(new int[]{i, j});
                tempGroup.standardLoc[0] = i;
                tempGroup.standardLoc[1] = j;
                tempGroupForCalc.offer(new int[]{i, j});
                checkMap[map[i][j]-1][i][j] = true;
                while(!tempGroupForCalc.isEmpty()){

                    int[] temp = tempGroupForCalc.poll();
                    for(int k = 0; k<4; k++) {  //상하좌우를 탐색한다.
                        int nextX = temp[0] + dr[k];
                        int nextY = temp[1] + dc[k];

                        //맵의 범위를 벗어나면 패스
                        if(nextX <0 || nextX >= N || nextY <0 || nextY >=N){
                            continue;
                        }

                        //이미 탐색한 지역이라면 패스
                        if(checkMap[map[i][j]-1][nextX][nextY]){
                            continue;
                        }

                        //무지개 블록이나 내 색깔과 일치하는 블록이라면, 그룹에 포함
                        if(map[nextX][nextY] == 0 || map[nextX][nextY] == map[i][j]){
                            //만약 무지개 블록이라면
                            if(map[nextX][nextY] == 0){
                                tempGroup.rainbowBrickCount++;    //무지개 벽돌 개수 1 증가
                            }
                            //만약 일반 블록이라면
                            else{
                                //기준 블록이 갱신되는지 확인
                                if(nextX < tempGroup.standardLoc[0]){
                                    tempGroup.standardLoc[0] = nextX;
                                    tempGroup.standardLoc[1] = nextY;
                                }
                                else if(nextX == tempGroup.standardLoc[0]){
                                    if(nextY < tempGroup.standardLoc[1]){
                                        tempGroup.standardLoc[1] = nextY;
                                    }
                                }
                            }
                            tempGroup.group.offer(new int[]{nextX, nextY});
                            tempGroupForCalc.offer(new int[]{nextX, nextY});
                            checkMap[map[i][j]-1][nextX][nextY] = true;
                        }
                    }
                }
                //그룹 조건인 내부 개수가 2보다 부족하다면 패스
                if(tempGroup.group.size() < 2){
                    continue;
                }
                //지금 만들어낸 그룹이 기존의 최고 그룹보다 더 큰지 비교
                if(tempGroup.group.size() < bestGroup.group.size()){
                    //작다면 패스
                    continue;
                }
                //만약 두 그룹의 사이즈가 같다면
                else if(tempGroup.group.size() == bestGroup.group.size()){
                    // 무지개 블록 수가 어디가 더 많은지 비교
                    if(tempGroup.rainbowBrickCount < bestGroup.rainbowBrickCount){
                        //작다면 패스
                        continue;
                    }
                    //만약 두 그룹의 무지개블록 개수가 같다면
                    else if(tempGroup.rainbowBrickCount == bestGroup.rainbowBrickCount){
                        //기준 블록의 행의 위치가 어디가 더 높은지 비교
                        if(tempGroup.standardLoc[0] < bestGroup.standardLoc[0]){
                            //작다면 패스
                            continue;
                        }
                        //만약 두 그룹의 기준 블록 행의 위치가 같다면
                        else if(tempGroup.standardLoc[0] == bestGroup.standardLoc[0]){
                            //기준 블록의 열의 위치가 어디가 더 큰지 비교
                            if(tempGroup.standardLoc[1] < bestGroup.standardLoc[1]){
                                //작다면 패스
                                continue;
                            }
                        }
                    }
                }
                //모두 통과한다면 갱신할 가치가 있다.
                bestGroup = tempGroup;
            }
        }

        //최고의 그룹 선정
        return bestGroup;
    }

    public static void deleteGroupAndGetScore(int[][] map, GroupInformationClass bestGroup){
        score += Math.pow(bestGroup.group.size(), 2);   //해당 그룹의 점수 획득
        //해당 그룹의 블록 각각을 빼서 빈 공간(-2)로 만들기
        while(!bestGroup.group.isEmpty()){
            int[] temp = bestGroup.group.poll();
            map[temp[0]][temp[1]] = -2;
        }
    }

    public static void gravityMap(int[][] map, int N) {
        for (int j = 0; j < N; j++) {
            int tempIndex = N-1;  //임시저장된 떨어질 블록의 위치
            boolean tempCheck = false;  //임시저장된 블록이 있는지 체크
            //맨 아래 칸부터 확인
            for (int i = N - 1; i >= 0; i--) {
                switch(map[i][j]){
                    case -1:    //검은 블록을 만난다면
                        tempCheck = false;  //지금까지 저장된 떨어질 위치는 무용지물이 되었다.
                        break;
                    case -2:    //비어있다면
                        //만약 떨어질 자리가 이미 있다면
                        if(tempCheck){
                            //그냥 패스
                            continue;
                        }
                        //떨어질 자리가 아직 없다면
                        else{
                            tempIndex = i;  //현재 위치를 저장
                            tempCheck = true;   //떨어질 자리가 생겼음을 표시
                        }
                        break;
                    default:    //무지개 + 일반 블록들을 만난다면
                        //만약 떨어질 위치가 있다면
                        if(tempCheck){
                            map[tempIndex][j] = map[i][j];  //해당 위치로 점프
                            map[i][j] = -2;     //현재 위치는 -2로 변경
                            tempIndex--;      //떨어질 위치 현재 자리로 변경
                        }
                        break;
                }
            }
        }
    }

    public static int[][] turnMap(int[][] map, int N) {
        int[][] newMap = new int[N][N]; //회전 후의 맵 정보가 저장될 배열
        //반시계방향 회전
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newMap[N - 1 - j][i] = map[i][j];
            }
        }
        //회전한 맵으로 저장
        return newMap;
    }
}