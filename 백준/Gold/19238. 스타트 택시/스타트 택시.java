import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 스타트 택시는 손님을 도착지로 데려다주면서 연료가 충전된다.
 * <p>
 * 요구하는 출력
 * - 남은 연료의 양, 중간에 못하면 -1 출력
 * <p>
 * 입력 변수
 * - N : 지도의 세로 크기
 * - M : 승객 수
 * - fuel : 초기 연료 양
 * - N줄에 걸친 지도 정보 (0은 빈칸, 1은 벽)
 * - x, y : 시작 위치
 * - M줄에 걸친 승객 출발지와 목적지
 * <p>
 * 문제 유의사항
 * - 승객은 한꺼번에 태울 수 없다.
 * - 연료는 한 칸에 1만큼 소모
 * - 승객을 데려다주면, 소모한 양의 2배만큼 충전
 * - 목적지에 도착했을 때 연료가 바닥나는 것은 성공으로 간주
 * - 모든 손니믈 이동시킬 수 없는 경우에도 -1 출력
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★★
 * <p>
 * 전략
 * - 복잡해 보이지만, bfs를 활용하여 풀 수 있을 것이라 예상
 * - bfs로 최단거리 승객을 찾는다.
 * - 해당 승객을 태우러 가고, 연료를 소모한다.
 * - 해당 승객을 도착지에 데려다주고 연료르 소모한다.
 * - 이를 반복
 * <p>
 * 회고할 내용
 * - 승객끼리 겹칠 수는 없지만, 승객과 목적지, 목적지끼리는 서로 겹칠 수 있다는 사실을 간과했다. 이를 위해 승객 정보가 저장된 배열을 따로 만들어 목적지를 체크했다.
 * - (자잘한 실수들을 제외하고) 구현 자체에 어려운 부분이 있지는 않았으나 코드 자체가 길어 시간이 오래 걸렸다.
 * @author HeejoPark
 */
public class Main {

    static final int NUM_FOR_PASSENGER = 1000;
    static final int NUM_FOR_DESTINATION = 2000;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //지도의 크기
        int M = Integer.parseInt(st.nextToken());   //승객 수
        int fuel = Integer.parseInt(st.nextToken());   //초기 연료

        //지도 정보를 입력
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken())-1;
        int y = Integer.parseInt(st.nextToken())-1;
        int[][] passengersDestinationInfor = new int[M+1][2];  //승객별 목적지 정보
        //승객 정보를 입력
        for(int i = 1 ;i<=M; i++){
            st = new StringTokenizer(br.readLine());
            map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = NUM_FOR_PASSENGER + i; //해당 승객의 출발지
            passengersDestinationInfor[i][0] = Integer.parseInt(st.nextToken())-1;
            passengersDestinationInfor[i][1] = Integer.parseInt(st.nextToken())-1;
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        int passengers = M; //남은 승객 수 표시
        //실행
        while_letsGoTaxi : while(passengers>0){
            //1. 현재 위치에서 최단거리의 다음 승객 위치를 찾기
            Queue<int[]> findPassengers = new LinkedList<>();   //다음 승객을 찾는데 사용
            Queue<int[]> nextPassengersPossible = new LinkedList<>();   //다음 승객을 저장하는데 사용
            int distanceFromTaxiToPassenger = Integer.MAX_VALUE;
            //현재 택시의 위치에 승객이 있다면
            if(isThereAnyPassenger(x, y, map, N)){
                //그 승객만 태운다.
                nextPassengersPossible.offer(new int[]{x, y});
                distanceFromTaxiToPassenger = 0;
            }
            //현재 택시의 위치에 승객이 없다면
            else {
                //승객 탐색을 시작한다.
                boolean[][] checkMap = new boolean[N][N];
                findPassengers.offer(new int[]{x, y, 0});
                checkMap[x][y] = true;
                while_findPassengers:
                while (!findPassengers.isEmpty()) {
                    int[] temp = findPassengers.poll();
                    int distance = temp[2];
                    //현재 승객까지의 거리가 기존 승객들의 거리보다 크다면 패스
                    if (distance >= distanceFromTaxiToPassenger) {
                        continue;
                    }
                    //현재 위치에서 상하좌우로 탐색을 해본다.
                    for (int i = 0; i < 4; i++) {
                        int nextX = temp[0] + dr[i];
                        int nextY = temp[1] + dc[i];

                        //해당 위치가 지도 범위를 벗어난다면 패스
                        if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= N) {
                            continue;
                        }

                        //해당 위치를 이미 탐색했다면 패스
                        if (checkMap[nextX][nextY]) {
                            continue;
                        }

                        //해당 위치가 벽이라면 패스
                        if (map[nextX][nextY] == 1) {
                            continue;
                        }

                        //해당 위치에 승객이 있다면
                        if (isThereAnyPassenger(nextX, nextY, map, N)) {
                            distanceFromTaxiToPassenger = distance + 1; //승객과 택시의 거리 갱신해주기
                            nextPassengersPossible.offer(new int[]{nextX, nextY});  //승객 후보에 넣기
                            checkMap[nextX][nextY] = true;
                        }
                        //해당 위치에 승객이 없다면, 다시 상하좌우 탐색을 하기
                        else {
                            findPassengers.offer(new int[]{nextX, nextY, distance + 1});
                            checkMap[nextX][nextY] = true;
                        }
                    }
                }
            }
            //다음 승객이 없다면 종료
            if(nextPassengersPossible.isEmpty()){
                break while_letsGoTaxi;
            }
            //승객이 여러명이라면
            if(nextPassengersPossible.size() > 1){
                //행 번호가 가장 작은 승객을, 그래도 여러명이라면 열 번호가 가장 작은 승객을 선택
                int length = nextPassengersPossible.size();
                int[] minimumPassenger = new int[2];
                minimumPassenger = nextPassengersPossible.poll();
                for(int i =1; i<length; i++){
                    int[] temp = nextPassengersPossible.poll();
                    //행이 작다면 갱신
                    if(temp[0] < minimumPassenger[0]){
                        minimumPassenger = temp;
                    }
                    //만약 행이 같다면
                    else if (temp[0] == minimumPassenger[0]){
                        //열이 작다면 갱신
                        if (temp[1] < minimumPassenger[1]){
                            minimumPassenger = temp;
                        }
                    }
                }
                //가장 적합한 승객만 큐에 남긴다.
                nextPassengersPossible.offer(minimumPassenger);
            }
            
            int[] nextPassengerFixed = nextPassengersPossible.poll();
            int passengerNum = map[nextPassengerFixed[0]][nextPassengerFixed[1]] - NUM_FOR_PASSENGER;
            //2. 연료를 소비하고 출발
            x = nextPassengerFixed[0];
            y = nextPassengerFixed[1];
            fuel -= distanceFromTaxiToPassenger;

            //도착지까지의 거리 찾기
            Queue<int[]> findDestination = new LinkedList<>();
            boolean[][] checkMap = new boolean[N][N];
            int distanceFromPassengerToDestination = Integer.MAX_VALUE;
            int[] destination = passengersDestinationInfor[passengerNum];
            findDestination.offer(new int[]{x, y, 0});
            checkMap[x][y] = true;
            while_findDestination : while(!findDestination.isEmpty()){
                int[] temp = findDestination.poll();
                int distance = temp[2];
                for(int i =0; i<4; i++){
                    int nextX = temp[0] + dr[i];
                    int nextY = temp[1] + dc[i];
                    //해당 위치가 지도 범위를 벗어난다면 패스
                    if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= N) {
                        continue;
                    }

                    //해당 위치를 이미 탐색했다면 패스
                    if (checkMap[nextX][nextY]) {
                        continue;
                    }

                    //해당 위치가 벽이라면 패스
                    if (map[nextX][nextY] == 1) {
                        continue;
                    }

                    //해당 위치에 목적지가 있다면
                    if ((nextX == destination[0])&&(nextY == destination[1])){
                        distanceFromPassengerToDestination = distance + 1;
                        break while_findDestination;
                    }
                    //해당 위치에 승객이 없다면, 다시 상하좌우 탐색을 하기
                    else {
                        findDestination.offer(new int[]{nextX, nextY, distance+1});
                        checkMap[nextX][nextY] = true;
                    }
                }
            }

            //도착지로 이동
            x = destination[0];
            y = destination[1];
            fuel -= distanceFromPassengerToDestination;
            map[nextPassengerFixed[0]][nextPassengerFixed[1]] = 0;  //승객을 무사히 태웠으니, 승객의 위치는 0이 된다.
            passengers--;   //남은 승객 수가 1 감소

            //3. 남은 연료 확인
            if(fuel < 0) {
                //마이너스라면 종료
                break while_letsGoTaxi;
            }
            //연료 재충전
            fuel += (distanceFromPassengerToDestination * 2);
        }
        
        //승객이 아직 남아있거나 연료가 마이너스라면
        if(passengers > 0 || fuel < 0){
            //-1 출력
            System.out.println("-1");
        }
        //모든 승객들을 목적지에 데려다줬다면
        else{
            //남은 연료 출력
            System.out.println(fuel);
        }
    }
    
    public static boolean isThereAnyPassenger(int x, int y, int[][] map, int N){
        return map[x][y] > NUM_FOR_PASSENGER && map[x][y] < NUM_FOR_DESTINATION;
    }
}