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
 * - CCTV 별 맵을 따로 만들어서 방향을 정해 시야를 체크한 후, 마지막에 모든 cctv의 맵들을 한꺼번에 합쳐 사각지대를 확인했다.
 * <p>
 * 회고할 내용
 * - 어떻게 해야 할지 상상은 되지만, 변수명이나 사용 방식 등에서 애를 먹었다.
 * - 앞으로 명확한 변수명의 사용과 사용 방식을 가독성이 좋게끔 훈련하는 모습이 필요해보인다.
 *
 * @author HeejoPark
 */
public class Main {
    static int N;
    static int M;
    static int[][][] office;
    static int[][] cctv_information;    //cctv의 정보
    static int cctv_count;  //cctv의 개수
    static int minimum_blind_spot;  //사각지대가 최소일 때의 개수
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
        minimum_blind_spot = N*M;   //사각지대 최대 개수는 사무실 전체로 두고 시작.
        //모든 경우의 수 탐색
        findMinimumSpots(0);

        //결과 출력
        System.out.println(minimum_blind_spot);
    }

    static int[] dr = {1, 0, -1, 0};    //상하좌우
    static int[] dc = {0, 1, 0, -1};    //상하좌우
    static int[][] cctv_direction = {{4, 1, 0, 0, 0}, {2, 1, 0, 1, 0}, {4, 1, 1, 0, 0}, {4, 1, 1, 1, 0}, {1, 1, 1, 1, 1}};  //CCTV 방향을 나타내는 배열. 첫 숫자는 CCTV가 회전할 때 나올 수 있는 경우의 수를 뜻한다.
    static final int NEW_AREA = 7;  //CCTV로 볼 수 있는 사무실 내 칸을 NEW_AREA로 규정. 7은 임의의 값
    public static void findMinimumSpots(int count){
        //cctv의 모든 방향을 설정했다면 사각지대 개수를 세보자
        if(count == cctv_count){
            int current_blind_spot = 0;
            //모든 칸을 돌면서 해당 칸을 볼 수 있는 cctv가 있었는지 체크하기
            for(int i =0 ; i<N; i++){
                for(int j =0; j<M; j++){
                    //cctv를 회전시키기도 전에 이미 cctv가 있거나 벽이 있었다면
                    if(office[i][j][0]>0){
                        office[i][j][count] = office[i][j][0];  //사각지대가 아님을 체크
                    }
                    //cctv를 하나씩 돌리면서 해당 칸을 확인하기
                    for(int k = 1; k<=cctv_count; k++){
                        if(office[i][j][k]>0){  //CCTV가 해당 칸의 시야를 획득했다면
                            office[i][j][count] = office[i][j][k];  //사각지대가 아님을 체크
                        }
                    }
                    //모든 cctv를 확인했음에도 시야가 없다면 사각지대다.
                    if(office[i][j][count]==0){
                        current_blind_spot++;   //현재 세팅에서의 사무실 사각지대의 개수 1 증가
                    }
                }
            }
            //기존 결과값과 비교 후 최소 개수 반영
            minimum_blind_spot = Math.min(minimum_blind_spot, current_blind_spot);
            return;
        }

        int current_cctv = cctv_information[count][0];  //현재 cctv의 종류

        //cctv종류마다 달랐던 경우의 수만큼 회전시켜 시야 개수 체크해보기
        for(int i =1 ; i<=cctv_direction[current_cctv-1][0]; i++){
            //상하좌우를 하나씩 보기
            for(int j =0; j<4; j++) {
                //만약 해당 방향으로 나아갈 필요가 없는 경우의 수라면 패스. 다음 방향으로 찾기
                if(cctv_direction[current_cctv-1][(j+i)>=5?(j+i-4):j+i]==0){
                    continue;
                }
                int nextX = cctv_information[count][1];
                int nextY = cctv_information[count][2];
                //해당 방향으로 끝까지 가보기
                while (true) {
                    nextX += dr[j] * cctv_direction[current_cctv-1][(j+i)>=5?(j+i-4):j+i];  //상하좌우 * 경우의 수에 따른 x 위치
                    nextY += dc[j] * cctv_direction[current_cctv-1][(j+i)>=5?(j+i-4):j+i];  //상하좌우 * 경우의 수에 따른 y 위치

                    //사무실 범위를 벗어나는 경우에 탐색 중단
                    if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {
                        break;
                    }
                    //벽을 만나는 경우에도 탐색 중단
                    if (office[nextX][nextY][0] == 6) {
                        break;
                    }

                    //탐색이 됐다면, 해당 cctv 전용 지도에 시야 체크
                    office[nextX][nextY][count + 1] = NEW_AREA;
                }
            }

            //이제 다음 cctv의 방향을 정하러 가자
            findMinimumSpots(count+1);

            //현재 cctv의 시야 체크 현황을 리셋하기
            for(int j = 0; j<N; j++){
                for(int k = 0; k<M; k++){
                    office[j][k][count+1] = 0;
                }
            }
        }
    }
}