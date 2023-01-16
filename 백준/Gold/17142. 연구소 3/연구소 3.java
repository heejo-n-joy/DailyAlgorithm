import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 승원이가 바이러스를 유출하려고 한다.. 그러면 안되는데..
 * <p>
 * 요구하는 출력
 * - 바이러스가 연구소 전체에 퍼지기까지 걸리는 시간
 * <p>
 * 입력 변수
 * - N : 연구소의 크기
 * - M : 활성 상태로 바꿀 바이러스 개수
 * - 연구소의 정보들 (0: 빈 칸, 1: 벽, 2: 바이러스를 놓을 수 있는 위치)
 * <p>
 * 전략
 * - 조합문제인 것 같다. M개의 바이러스를 선택하고 활성 상태로 바꾼 후 시뮬레이션을 계속 돌리면 될 것 같다.
 * - 2의 개수가 M이상 10 이하라고 했으니, 조합은 10CM이 되겠다. (300개가 되지 않는다) 2시간상으로도 괜찮을듯!
 * - 조합이 완성될 때마다 시뮬레이션을 돌려서 총 몇초가 걸리는지 재보고, 현재까지 기록과 비교 후 갱신하자
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★☆
 * <p>
 * 회고할 내용
 * - 바이러스를 탐색하는 과정에서 비활성된 바이러스들의 존재를 처리하는게 까다로웠다.
 * - 비활성 상태이긴 하지만 어쨌든 이들도 바이러스이니, 남은 장소를 카운트하는 과정에서는 빼줘야 했다.
 * - 결국 boolean 타입의 이중배열을 이용해서 바이러스 시작 위치와 비활성화 위치를 구분하였다.
 * - 남은 장소 카운트는 따로 int 타입의 변수를 만들어서, 이 값이 0이 되거나 더이상 탐색할 곳이 없다면 탐색이 종료되도록 했다.
 * @author HeejoPark
 */
public class Main {
    static int minimumTime;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //세로선의 개수
        int M = Integer.parseInt(st.nextToken());   //가로선의 개수
        int[][] lab = new int[N][N];
        int virusPlaceCount = 0;    //바이러스를 놓을 수 있는 위치 개수

        //연구소 현재 정보를 입력받기
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<N; j++){
                lab[i][j] = Integer.parseInt(st.nextToken());   //연구소 현재 정보 입력
                if(lab[i][j]==2){
                    virusPlaceCount++;
                }
            }
        }

        int[][] virusPlaces = new int[virusPlaceCount][2];  //바이러스를 놓을 수 있는 위치들의 정보를 저장
        boolean[] selectedVirusPlaces = new boolean[virusPlaceCount];
        virusPlaceCount = 0;
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                if(lab[i][j]==2){
                    virusPlaces[virusPlaceCount][0] = i;
                    virusPlaces[virusPlaceCount][1] = j;
                    virusPlaceCount++;
                }
            }
        }

        //퍼지는 시간의 최소값을 구하기 위해 기본값을 최대값으로 설정한다.
        minimumTime = Integer.MAX_VALUE;
        //계산
        execute(N, M, lab, virusPlaceCount, virusPlaces, selectedVirusPlaces, 0, 0);

        //모든 빈 칸에 바이러스를 퍼뜨릴 수 없는 겨우에는 -1을 출력
        if(minimumTime==Integer.MAX_VALUE){
            minimumTime = -1;
        }
        //결과 출력
        System.out.println(minimumTime);
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void execute(int N, int M, int[][] lab, int virusPlaceCount, int[][] virusPlaces, boolean[] selectedVirusPlaces, int count, int selectedNum){
        //바이러스를 전부 선택했다면, 바이러스를 퍼뜨려보자
        if(count==M){
            int restPlaceCount = 0;
            //시뮬레이션에 사용될 새로운 지도 생성
            int[][] tempLab = new int[N][N];
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    switch(lab[i][j]){
                        case 2: //바이러스가 놓일 수 있는 위치
                            tempLab[i][j] = 0;
                            break;
                        case 0: //아무것도 없는 곳
                            tempLab[i][j] = 0;
                            restPlaceCount++;
                            break;
                        case 1: //벽
                            tempLab[i][j] = -1;
                            break;
                    }
                }
            }
            int lastTime = 0;
            boolean[][] checkTempLab = new boolean[N][N];
            Queue<int[]> activatedVirus = new LinkedList<>();   //활성화된 바이러스 명단
            for(int i = 0 ;i<virusPlaceCount; i++){
                //활성화 상태의 바이러스 명단을 큐에 넣는다.
                if(selectedVirusPlaces[i]){
                    activatedVirus.offer(virusPlaces[i]);
                    checkTempLab[virusPlaces[i][0]][virusPlaces[i][1]] = true;
                }
                else{
                    tempLab[virusPlaces[i][0]][virusPlaces[i][1]] = -2;
                }
            }
            //활성화된 바이러스가 없을 때까지 실행한다.
            ex: while(!activatedVirus.isEmpty()){
                int[] temp = activatedVirus.poll();
                for(int i =0; i<4; i++){
                    int nextX = temp[0] + dr[i];
                    int nextY = temp[1] + dc[i];
                    //이미 모든 장소에 바이러스가 퍼졌다면 종료
                    if(restPlaceCount==0){
                        break ex;
                    }
                    //범위를 벗어나면 패스
                    if(nextX < 0 || nextX >= N || nextY <0 || nextY >= N){
                        continue;
                    }
                    //이미 확인한 구역이라면 패스
                    if(checkTempLab[nextX][nextY]){
                        continue;
                    }
                    //벽이 아니고(not -1), 바이러스가 퍼지지 않은 곳(only 0)일 경우에만 퍼뜨린다.
                    if(tempLab[nextX][nextY] == 0){
                        tempLab[nextX][nextY] = tempLab[temp[0]][temp[1]]+1;
                        lastTime = Math.max(lastTime, tempLab[temp[0]][temp[1]]+1);
                        checkTempLab[nextX][nextY] = true;
                        activatedVirus.offer(new int[]{nextX, nextY});
                        restPlaceCount--;
                    }
                    else if(tempLab[nextX][nextY] == -2){
                        tempLab[nextX][nextY] = tempLab[temp[0]][temp[1]]+1;
                        lastTime = Math.max(lastTime, tempLab[temp[0]][temp[1]]+1);
                        checkTempLab[nextX][nextY] = true;
                        activatedVirus.offer(new int[]{nextX, nextY});
                    }
                }
            }

            //퍼지지 않은 공간이 남아있다면 종료
            if(restPlaceCount != 0){
                return;
            }

            //만약 바이러스 전파에 성공했다면 갱신을 해보자
            minimumTime = Math.min(minimumTime, lastTime);

            return;
        }
        //바이러스 선택하기
        for(int i = selectedNum; i<virusPlaceCount; i++){
            //만약 바이러스가 이미 선택된 지역이라면 패스
            if(selectedVirusPlaces[i]){
                continue;
            }
            //해당 바이러스를 선택 후 다음으로 이동
            selectedVirusPlaces[i] = true;
            execute(N, M, lab, virusPlaceCount, virusPlaces, selectedVirusPlaces, count+1, i+1);
            selectedVirusPlaces[i] = false;
        }
    }
}