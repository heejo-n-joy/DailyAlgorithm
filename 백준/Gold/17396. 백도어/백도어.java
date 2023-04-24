import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 요구하는 출력 : 적 네서스까지 들키지 않고 갈 수 있는 최단 시간
 *
 * 풀이 방법 : 다익스트라(Dijkstra)
 * - 분기별로 갈 수 있는 최단 거리를 저장하는 배열을 활용한다.
 * - 이때 중간 분기점들에서 시야가 확보된 분기점들은 이동할 수 없으므로 제외한다.
 * - 1. 분기별 이동 가능한 상태인지 체크 (시야에 걸리는지 안걸리는지)
 * - 2. 이동 가능한 분기들만을 대상으로 경로 입력을 받고, 다익스트라 실행
 */
class Move{
    int destination;
    long time;
    Move(int destination, long time){
        this.destination = destination;
        this.time = time;
    }
}

public class Main {
    static final long IMPOSSIBLE_MAX_TIME = 10000000000L + 1;   //도달할 수 없는 숫자

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(st.nextToken());   //분기점의 수
        int M = Integer.parseInt(st.nextToken());   //길의 수

        long[] time = new long[N];            //0번 분기에서 출발하여 걸리는 분기별 이동 소요 시간
        Arrays.fill(time, IMPOSSIBLE_MAX_TIME);
        time[0] = 0;    //자기 자신으로는 0

        boolean[] check = new boolean[N];   //분기점 이동 가능 여부 및 확인 여부

        List<Move>[] roads = new List[N];    //분기별 이동 가능한 경로 및 소요 시간
        for(int i = 0; i<N; i++){
            roads[i] = new ArrayList<>();
        }

        //시야 확인
        st = new StringTokenizer(bufferedReader.readLine());
        for(int i = 0; i<N; i++){
            if(Integer.parseInt(st.nextToken()) == 1) {
                check[i] = true;
            }
        }
        check[N-1] = false; //시야 체크 시 넥서스는 제외하자

        //경로 탐색
        for(int i = 0 ; i<M; i++){
            st = new StringTokenizer(bufferedReader.readLine());
            int a = Integer.parseInt(st.nextToken());   //분기 a
            int b = Integer.parseInt(st.nextToken());   //분기 b
            int t = Integer.parseInt(st.nextToken());   //소요 시간
            //만약 a나 b 중에 이동할 수 없는 분기점이 있다면 패스한다.
            if(check[a] || check[b]){
                continue;
            }
            //그게 아니라면, 이동 가능한 위치와 시간을 양방향으로 표시한다.
            roads[a].add(new Move(b, t));
            roads[b].add(new Move(a, t));
        }

        //다익스트라 실행
        PriorityQueue<Move> priorityQueue = new PriorityQueue<>(new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                if(o1.time > o2.time){
                    return 1;
                }
                else if(o1.time == o2.time){
                    return 0;
                }
                else{
                    return -1;
                }
            }
        });
        priorityQueue.offer(new Move(0, 0));
        while(!priorityQueue.isEmpty()){
            Move temp = priorityQueue.poll();

            //이미 확인을 마친 곳이라면 패스한다.
            if(check[temp.destination]){
                continue;
            }
            check[temp.destination] = true;

            //temp로 부터 갈 수 있는 모든 이동 거리를 체크한다.
            for(Move list : roads[temp.destination]){
                //이미 확인을 마친 곳이라면 패스한다.
                if(check[list.destination]){
                    continue;
                }

                //이동 거리에 갱신이 필요하다면 갱신을 한다.
                if(time[list.destination] > time[temp.destination] + list.time){
                    time[list.destination] = time[temp.destination] + list.time;
                    priorityQueue.offer(new Move(list.destination, time[list.destination]));
                }
            }
        }

        //결과 출력
        long result = -1;
        if(time[N-1] < IMPOSSIBLE_MAX_TIME){
            result = time[N-1];
        }
        System.out.println(result);
    }
}