import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 선생님을 피해 탈출하려는 학생들
 * <p>
 * 요구하는 출력
 * - 장애물을 통해 감시를 피할 수 있는가?
 * <p>
 * 입력 변수
 * - N : 복도의 크기
 * - 복도의 정보
 * <p>
 * 문제 유의사항
 * - 장애물은 꼭 3개를 넣어야 한다.
 * <p>
 * 전략
 * - 장애물을 둘 3개의 공간을 선택한 후 시뮬레이션을 돌려보는 식으로 진행하자
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */

public class Main {
    static boolean result;
    static char[][] map;
    static int N;
    static int[][] vacantPlaces;
    static int vacantPlaceCount;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   //복도의 크기
        map = new char[N][N];
        vacantPlaceCount = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = st.nextToken().charAt(0);
                if (map[i][j] == 'X') {
                    vacantPlaceCount++;
                }
            }
        }

        vacantPlaces = new int[vacantPlaceCount][2];
        vacantPlaceCount = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 'X') {
                    vacantPlaces[vacantPlaceCount][0] = i;
                    vacantPlaces[vacantPlaceCount][1] = j;
                    vacantPlaceCount++;
                }
            }
        }
        check = new boolean[vacantPlaceCount];
        result = false;

        calc(0, 0);

        if(result){
            System.out.println("YES");
        }
        else{
            System.out.println("NO");
        }
    }
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static boolean[] check;
    public static void calc(int count, int prevNum){
        if(result == true){ //이미 가능한 경우를 찾았다면 이후의 모든 계산 생략
            return;
        }
        if(count == 3){
            char[][] tempMap = new char[N][N];
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    tempMap[i][j] = map[i][j];
                }
            }
            //계산
            for(int i =0; i<vacantPlaceCount; i++){
                if(check[i]){
                    tempMap[vacantPlaces[i][0]][vacantPlaces[i][1]] = 'O';
                }
            }
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    if(tempMap[i][j] == 'T'){
                        //상하좌우로 탐색
                        for(int k = 0; k<4; k++){
                            int nextI = i;
                            int nextJ = j;
                            while(true) {
                                nextI += dr[k];
                                nextJ += dc[k];

                                //만약 복도 범위를 벗어나면 무사히 통과, 종료
                                if(nextI < 0 || nextI>= N || nextJ <0 || nextJ >= N){
                                    break;
                                }

                                //만약 해당 위치에 장애물이 있다면 무사히 통과, 종료
                                if(tempMap[nextI][nextJ] == 'O'){
                                    break;
                                }

                                //만약 해당 위치에 학생이 있다면 바로 return
                                if(tempMap[nextI][nextJ] == 'S'){
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            //여기까지 왔다면 모든 장애물을 통과한 것이다.
            result = true;
            return;
        }
        for(int i =prevNum; i<vacantPlaceCount; i++){
            check[i] = true;
            calc(count+1, i+1);
            check[i] = false;
        }
    }
}