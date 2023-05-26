import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 연구실에 에어컨 바람이 도달하는 위치의 개수
 *
 * 아이디어 : BFS
 * - 2차원 배열에 에어컨 바람을 상하좌우로 보내며 장소를 저장한다.
 * - 이 때, 방향 변수 direction을 활용하여 현재 에어컨의 바람 방향을 체크한다.
 * - 에어컨 바람이 순환 구조로 되어있을 수 있으니, 장소를 저장할 때, 바람 방향까지 같이 저장하자. (같은 방향의 바람이 들어오면 진행할 수 없게 한다.)
 * 1. 에어컨을 가동한다.
 * 2. 바람을 따라가며 장소를 저장한다.
 */


class Wind{
    int x;
    int y;
    int direction;
    Wind(int x, int y, int direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());   //연구실 세로 길이
        int M = Integer.parseInt(st.nextToken());   //연구실 가로 길이

        Queue<Wind> queue = new LinkedList<>();

        int[][] office = new int[N][M]; //물건을 둔다.
        boolean[][][] wind = new boolean[N][M][4];   //에어컨 바람 체크용. 각각 상하좌우 방향을 의미한다.

        //입력받기
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<M; j++){
                office[i][j] = Integer.parseInt(st.nextToken());
                if(office[i][j] == 9){
                    //4방향의 에어컨 바람을 체크하자
                    for(int d =0; d<4; d++) {
                        queue.offer(new Wind(i, j, d));
                        wind[i][j][d] = true;
                    }
                }
            }
        }

        //순서대로 상하좌우 방향
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        //에어컨을 가동시킨다.
        while(!queue.isEmpty()){
            //바람을 꺼내기
            Wind current_wind = queue.poll();


            //해당 바람의 방향대로 위치를 이동하기
            current_wind.x += dr[current_wind.direction];
            current_wind.y += dc[current_wind.direction];

            //해당 위치가 범위 밖이라면 패스
            if(current_wind.x < 0 || current_wind.x >= N || current_wind.y < 0 || current_wind.y >= M){
                continue;
            }

            //이미 해당 위치에 해당 방향으로 바람이 지나갔다면 패스
            if(wind[current_wind.x][current_wind.y][current_wind.direction]){
                continue;
            }

            //바람을 체크하기
            wind[current_wind.x][current_wind.y][current_wind.direction] = true;

            //해당 위치에 물건이 존재하는지 확인
            switch(office[current_wind.x][current_wind.y]){
                case 1: //물건1 (좌 <-> 우)
                    //바람 방향이 좌,우라면
                    if(current_wind.direction >= 2){
                        current_wind.direction = 5 - current_wind.direction;    //좌,우 서로 바꿔주기
                    }
                    break;
                case 2: //물건2 (상 <-> 하)
                    //바람 방향이 상,하라면
                    if(current_wind.direction < 2){
                        current_wind.direction = 1 - current_wind.direction;    //상,하 서로 바꿔주기
                    }
                    break;
                case 3: //물건3 (상0->우3, 하1->좌2, 좌2->하1, 우3->상0)
                    current_wind.direction = 3 - current_wind.direction;
                    break;
                case 4: //물건4 (상0->좌2, 하1->우3, 좌2->상0, 우3->하1)
                    current_wind.direction += 2;
                    current_wind.direction %= 4;
                    break;
            }

            //다시 큐에 삽입하기
            queue.offer(current_wind);
        }

        //바람이 지나간 자리 개수 확인하기
        int result = 0;
        for(int i =0; i<N; i++){
            for(int j =0; j<M; j++){
                //방향에 상관없이 에어컨 바람이 지나간 곳이라면
                if(wind[i][j][0] || wind[i][j][1] || wind[i][j][2] || wind[i][j][3]){
                    result++;   //1 증가
                }
            }
        }

        //결과 출력
        System.out.println(result);
    }
}
