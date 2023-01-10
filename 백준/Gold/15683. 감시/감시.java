import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 사무실에 CCTV를 이용하여 사각지대를 줄여보려고 한다.
 * <p>
 * 요구하는 출력
 * - 주어진 CCTV를 이용하여 최소가 될 수 있는 사각지대의 수
 * <p>
 * 입력 변수
 * - N : 사무실의 세로 크기
 * - M : 사무실의 가로 크기
 * - 사무실의 칸 별 정보
 * <p>
 * 문제 유의사항
 * - CCTV끼리는 통과가 가능하다
 * - 두 사람이 동시에 이기는 경우도 없고, 한 사람이 여러개로 이기는 경우도 없다.
 * <p>
 * 체감 난이도 : ★★☆☆☆
 * <p>
 * 전략
 * - Brute Force. 설치된 CCTV들을 기준으로 모든 경우의 수를 활용해보자
 * <p>
 * 회고할 내용
 *
 * @author HeejoPark
 */
public class Main {
    static int N;
    static int M;
    static int[][][] office;
    static int[][] cctv_information;
    static int cctv_count;
    static int minimum_blind_spot;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   //사무실의 세로 크기
        M = Integer.parseInt(st.nextToken());   //사무실의 가로 크기
        office = new int[N][M][9];
        cctv_count = 0;
        cctv_information = new int[8][3];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++){
                office[i][j][0] = Integer.parseInt(st.nextToken());    //사무실 정보 입력
                //해당 위치에 CCTV가 있다면
                if(office[i][j][0]>0 && office[i][j][0]<6){
                    cctv_information[cctv_count][0] = office[i][j][0];  //CCTV 종류 1.한방향, 2.양방향, 3.직각방향, 4.ㅜ방향, 5.모든방향
                    cctv_information[cctv_count][1] = i;    //CCTV의 세로 위치
                    cctv_information[cctv_count][2] = j;    //CCTV의 가로 위치
                    cctv_count++;
                }
            }
        }
        minimum_blind_spot = N*M;
        //모든 경우의 수 탐색
        findMinimumSpots(0);
        System.out.println(minimum_blind_spot);
    }

    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] cctv_direction = {{4, 1, 0, 0, 0}, {2, 1, 0, 1, 0}, {4, 1, 1, 0, 0}, {4, 1, 1, 1, 0}, {1, 1, 1, 1, 1}};
    static final int NEW_AREA = 7;
    public static void findMinimumSpots(int count){
        if(count == cctv_count){
//            System.out.println("CALC!");
            int current_blind_spot = 0;
            //계산
            for(int i =0 ; i<N; i++){
                for(int j =0; j<M; j++){
                    if(office[i][j][0]>0){  //이미 시야가 있거나 장애물이 있거나 한다면
                        office[i][j][count] = office[i][j][0];
                    }
                    for(int k = 1; k<=cctv_count; k++){
                        if(office[i][j][k]>0){  //해당 CCTV가 시야를 획득했다면
                            office[i][j][count] = office[i][j][k];  //획득했다는 표시
                        }
                    }
                    if(office[i][j][count]==0){ //그럼에도 시야가 없다면 사각지대다.
                        current_blind_spot++;
                    }
                }
            }

//            for(int i =0; i<N; i++){
//                for(int j =0; j<M; j++){
//                    System.out.print(office[i][j][1] + " ");
//                }
//                System.out.println();
//            }
            //비교
            minimum_blind_spot = Math.min(minimum_blind_spot, current_blind_spot);
            return;
        }
        int current_cctv = cctv_information[count][0];
        for(int i =1 ; i<=cctv_direction[current_cctv-1][0]; i++){
//            System.out.println("DIRECTION!");
            for(int j =0; j<4; j++) {
                int nextX = cctv_information[count][1];
                int nextY = cctv_information[count][2];
                while (true) {
                    if(cctv_direction[current_cctv-1][(j+i)>=5?(j+i-4):j+i]==0){
                        break;
                    }
                    nextX += dr[j] * cctv_direction[current_cctv-1][(j+i)>=5?(j+i-4):j+i];
                    nextY += dc[j] * cctv_direction[current_cctv-1][(j+i)>=5?(j+i-4):j+i];

                    if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {
                        break;
                    }
                    if (office[nextX][nextY][0] == 6) {
                        break;
                    }
//                    System.out.println("X & Y : " +nextX +", " + nextY);
                    office[nextX][nextY][count + 1] = NEW_AREA;
                }
            }

            findMinimumSpots(count+1);

            for(int j = 0; j<N; j++){
                for(int k = 0; k<M; k++){
                    office[j][k][count+1] = 0;
                }
            }
        }
    }
}