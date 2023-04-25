import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 시간이 지난 후 특정 위치의 바이러스 종류
 *
 * 풀이 방법 : 구현
 * - 작은 번호의 바이러스부터 증식하기
 * - 증식된 바이러스는 맵에 적고, 새로운 큐에 작성
 * - 1초가 지나면, 새로운 큐을 가지고 다시 작은 번호의 바이러스부터 증식하기
 *
 * 팁
 * - 증식을 한 바이러스는 더이상 증식할 수 없으니 큐에 넣지 않는다.
 *
 */
class Virus{
    int x;
    int y;
    int no;
    Virus(int x, int y, int no){
        this.x = x;
        this.y = y;
        this.no = no;
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(st.nextToken());   //시험관의 크기
        int K = Integer.parseInt(st.nextToken());   //바이러스 종류

        int[][] map = new int[N][N];
        PriorityQueue<Virus> priorityQueue = createPriorityQueue();
        for(int i = 0 ; i<N; i++) {
            st = new StringTokenizer(bufferedReader.readLine());
            for(int j =0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] > 0){
                    priorityQueue.offer(new Virus(i, j, map[i][j]));
                }
            }
        }


        st = new StringTokenizer(bufferedReader.readLine());
        int S = Integer.parseInt(st.nextToken());   //끝나는 시간
        int X = Integer.parseInt(st.nextToken());   //찾고자 하는 위치
        int Y = Integer.parseInt(st.nextToken());   //찾고자 하는 위치


        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        //실행
        for(int time = 1; time<=S; time++){
            PriorityQueue<Virus> priorityQueueNext = createPriorityQueue();

            //바이러스 전파
            while(!priorityQueue.isEmpty()){
                Virus temp = priorityQueue.poll();
                //상하좌우 탐색
                for(int k = 0; k<4; k++){
                    int nextX = temp.x + dr[k];
                    int nextY = temp.y + dc[k];

                    //범위를 벗어났다면 패스
                    if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){
                        continue;
                    }

                    //이미 다른 바이러스가 있으면 패스
                    if(map[nextX][nextY] > 0){
                        continue;
                    }

                    //바이러스를 심자
                    priorityQueueNext.offer(new Virus(nextX, nextY, temp.no));
                    map[nextX][nextY] = temp.no;
                }
            }

            //새로 전파된 바이러스들을 다음 탐색할 큐에 넣는다.
            priorityQueue = priorityQueueNext;
        }

        //결과 출력
        System.out.println(map[X-1][Y-1]);
    }

    //맞춤형 정렬이 적용된 우선순위를 생성하는 메소드
    public static PriorityQueue<Virus> createPriorityQueue(){
        PriorityQueue<Virus> priorityQueue = new PriorityQueue<>(new Comparator<Virus>() {
            @Override
            public int compare(Virus o1, Virus o2) {
                return o1.no - o2.no;
            }
        });
        return priorityQueue;
    }
}