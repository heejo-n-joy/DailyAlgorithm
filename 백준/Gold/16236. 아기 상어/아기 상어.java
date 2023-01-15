import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 아기 상어의 물고기 식사
 * <p>
 * 요구하는 출력
 * - 엄마 상어에게 도움을 요청하기 전까지 몇 초 동안 물고기를 잡아먹을 수 있는지?
 * <p>
 * 입력 변수
 * - N : 공간의 크기
 * - 공간의 상태 (0: 빈칸, 1~6: 물고기 크기, 9: 아기상어위치)
 * <p>
 * 전략
 * - 가장 짧은 거리의 물고기들을 찾아봐야한다. => bfs로 탐색하기
 * - 아기상어보다 크기가 큰 물고기 칸은 지나갈 수 없다.
 * - 아기상어보다 크기가 작은 물고기만 먹을 수 있다.
 * <p>
 * 예상 난이도 : ★★★
 * 체감 난이도 : ★★☆
 * <p>
 * 회고할 내용
 *- 자신의 크기와 같은 수의 물고기를 먹을 때만 사이즈가 커진다는 것을 놓쳤다.
 *
 * @author HeejoPark
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //공간의 크기
        int[] babyShark = new int[2];       //아기상어 위치
        int sizeOfBabyShark = 2;
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 9){ //아기상어의 위치는 따로 저장한다.
                    babyShark[0] = i;
                    babyShark[1] = j;
                    map[i][j] = 0;
                }
            }
        }

        int[] dr = {0, 0, -1, 1};
        int[] dc = {1, -1, 0, 0};

        int totalTime = 0;
        int eatenFishes = 0;
        adventureOfBabyShark : while(true){
            Queue<int[]> possiblePlace = new LinkedList<>();
            possiblePlace.offer(new int[]{babyShark[0], babyShark[1], 0});
            boolean[][] mapChecked = new boolean[N][N];
            mapChecked[babyShark[0]][babyShark[1]] = true;

            //잡아먹을 수 있는 물고기가 있는지 탐색
            int distance = Integer.MAX_VALUE;
            int selectedFishX = N;
            int selectedFishY = N;
            exploreUntilFindFish : while(!possiblePlace.isEmpty()){
                int temp[] = possiblePlace.poll();
                //만약 꺼낸 위치가 이미 찾은 곳의 거리보다 멀다면 패스
                if(temp[2] > distance){
                    continue exploreUntilFindFish;
                }
                //꺼낸 위치에 물고기가 있고, 아기상어보다 작다면
                if(map[temp[0]][temp[1]] > 0 && map[temp[0]][temp[1]] < sizeOfBabyShark){
                    if((selectedFishX > temp[0]) || ((selectedFishX == temp[0]) && (selectedFishY > temp[1]))){
                        selectedFishX = temp[0];
                        selectedFishY = temp[1];
                    }
                    distance = temp[2]; //거리를 조절해준다.
                }
                else {
                    //상하좌우 탐색
                    for(int i =0; i<4; i++){
                        int nearX = temp[0] + dr[i];
                        int nearY = temp[1] + dc[i];
                        //공간의 범위를 벗어나면 패스
                        if(nearX < 0 || nearX >= N || nearY < 0 || nearY >= N){
                            continue;
                        }
                        //이미 확인한 곳이라면 패스
                        if(mapChecked[nearX][nearY]){
                            continue;
                        }
                        //아기상어보다 큰 물고기라면 지나갈 수 없으니 패스
                        if(map[nearX][nearY] > sizeOfBabyShark){
                            continue;
                        }
                        //가능한 곳이면
                        mapChecked[nearX][nearY] = true;    //해당 지역 탐색했음을 체크
                        possiblePlace.offer(new int[]{nearX, nearY, temp[2]+1});    //탐색할 장소에 넣기
                    }
                }
            }
            //만약 선택된 물고기가 없다면 엄마 상어에게 도움을 요청해야 한다.
            if(selectedFishX == N && selectedFishY == N){
                break adventureOfBabyShark;
            }
            //선택된 물고기를 먹으러 간다.
            babyShark[0] = selectedFishX;
            babyShark[1] = selectedFishY;
            totalTime += distance;  //시간 증가
            eatenFishes++;  //먹은 물고기 수 1 증가
            //만약 자신의 크기와 먹은 물고기 수가 같다면
            if(sizeOfBabyShark==eatenFishes){
                sizeOfBabyShark++;  //사이즈 1 증가
                eatenFishes = 0;    //먹은 물고기 수 초기화
            }
            map[selectedFishX][selectedFishY] = 0;  //먹은 물고기의 자리는 0이 된다.
        }

        //결과출력
        System.out.println(totalTime);
    }

}