import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 오목의 승패여부 판별하기
 * <p>
 * 요구하는 출력
 * - 누가 이겼는지, 이긴 바둑줄의 가장 왼쪽 위에 있는 바둑알 좌표가 어디인지 함께 출력
 * <p>
 * 입력 변수
 * - 바둑알이 놓여진 정보들을 찾기
 * <p>
 * 문제 유의사항
 * - 6줄은 승리로 인정 안한다.
 * - 두 사람이 동시에 이기는 경우도 없고, 한 사람이 여러개로 이기는 경우도 없다.
 * <p>
 * 체감 난이도 : ★★☆☆☆
 * <p>
 * 전략
 * - 오목 탐색 방향을 위에서 아래로 정하고 이를 가로, 세로, 대각선좌하향, 대각선우하향으로 탐색한다.
 * - 해당 돌을 어떤 방향으로 탐색했었는지를 파악해서 중복 탐색을 제외한다.
 * - 해당 돌을 해당 방향으로 끝까지 탐색하고 딱 5개가 됐다면 즉시 모든 탐색을 종료한다.
 * <p>
 * 회고할 내용
 * - 문제 출력 조건을 제대로 읽지 않았다. 무조건 가장 왼쪽에 있는 돌이 기준이며, 특별히 세로 정답일 때만 가장 위쪽의 돌을 출력한다.
 * - nextX와 nextY가 들어가야 하는 자리에 x, y를 입력.. 바보
 * @author HeejoPark
 */
public class Main {
    static int[][][] checkboard;

    static final int size = 19;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        checkboard = new int[size][size][5];
        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                checkboard[i][j][0] = Integer.parseInt(st.nextToken());
            }
        }
        game();
    }

    static boolean findIt;
    public static void game(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                if(checkboard[i][j][0]>0) {
                    //4가지 방향 전부 탐색
                    for(int k =1; k<=4; k++){
                        //해당 오목알이 해당 방향으로 탐색했던 이력이 없다면 확인해보기
                        if(checkboard[i][j][k]==0){
                            findIt = check(k, i, j, 1, checkboard[i][j][0]);
                        }
                        if(findIt){
                            return;
                        }
                    }
                }
            }
        }
        if(!findIt){
            //아직 승부가 나지 않았다.
            System.out.println(0);
        }
    }

    static int dr[] = {0, 1, 1, 1};
    static int dc[] = {1, 0, -1, 1};
    //오목이 됐는지 계산하는 함수. 1은 가로, 2는 세로, 3는 대각선좌하향, 4는 대각선우하향
    public static boolean check(int version, int x, int y, int count, int color){
        int nextX = x;
        int nextY = y;
        while(true) {
            //바둑판에 확인했다는 체크
            checkboard[nextX][nextY][version] = 1;
            //다음 방향 탐색
            nextX = nextX + dr[version - 1];
            nextY = nextY + dc[version - 1];
            //범위를 벗어나지는 않는지?
            if(nextX<0 || nextX>=size || nextY <0 || nextY>=size){
                break;
            }
            //다음 값이 현재 바둑알과 다르다면
            if(checkboard[nextX][nextY][0]!=color){
                break;
            }
            count++;
        }
        //최종 카운트가 몇개인지 확인하기
        if(count==5){
            System.out.println(color);
            if(version == 3){
                x = nextX - dr[version - 1];
                y = nextY - dc[version - 1];
            }
            System.out.println((x + 1) + " " + (y + 1));
            return true;
        }
        else{
            return false;
        }
    }

}