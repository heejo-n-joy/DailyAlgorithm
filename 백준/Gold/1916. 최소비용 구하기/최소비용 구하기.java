import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * #다익스트라_다시복습
 *
 * 요구하는 출력 : A번째 도시에서 B번째 도시에 가는데 드는 최소비용
 *
 * 풀이 방법 : 다익스트라(Dijkstra)
 * - 도시별 연결되어있는 리스트를 저장
 * - A 도시를 시작으로, 주변에 이동 가능한 도시들의 값 계산
 * - 가장 짧은 거리에 있는 도시를 선택 후(도시 Q), 주변 도시들에 대해 기존의 이동 가능한 경로와 선택도시(Q)를 거쳐서 이동하는 경로 중 짧은 거리 갱신
 * - 모든 도시를 탐색 후 결과 출력
 *
 * 디버깅 : 시간초과 발생
 * - 다익스트라 방문체크 과정에서 발생하는 문제 아닐까..
 * - priority Queue의 과정에서, 방문체크를 했으면 패스를 하는 과정을 뺐다.
 */

class City{
    int cityNum;    //도시 번호
    int busCost;    //버스 비용

    City(int cityNum, int busCost){
        this.cityNum = cityNum;
        this.busCost = busCost;
    }
}
public class Main {

    static final int IMPOSSIBLE_MAX_VAL = 200000000;    //도달할 수 없는 값
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //도시의 개수
        int M = Integer.parseInt(bufferedReader.readLine());    //버스의 개수

        //도시별 연결되어있는 도시들
        List<City>[] cities = new List[N+1];
        for(int i = 0; i<=N; i++){
            cities[i] = new ArrayList<>();
        }

        //연결된 도시와 버스 비용 추가
        for(int i = 0; i<M; i++){
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int fromCity = Integer.parseInt(stringTokenizer.nextToken());
            int toCity = Integer.parseInt(stringTokenizer.nextToken());
            int busCost = Integer.parseInt(stringTokenizer.nextToken());
            cities[fromCity].add(new City(toCity, busCost));
        }

        //시작 위치와 도착 위치 입력받기
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int A = Integer.parseInt(stringTokenizer.nextToken());  //시작 위치
        int B = Integer.parseInt(stringTokenizer.nextToken());  //도착 위치

        //다익스트라 실행
        int result = Dijkstra(A, B, N, cities);

        //결과 출력 (출발점에서 도착점을 갈 수 있는 경우에만 입력으로 주어지니, 예외 처리는 하지 않는다)
        System.out.println(result);
    }

    public static int Dijkstra(int A, int B, int N, List<City>[] cities){
        //다익스트라 계산에 필요한 최단거리 저장 배열
        int[] costs = new int[N+1];
        Arrays.fill(costs, IMPOSSIBLE_MAX_VAL);

        boolean[] check = new boolean[N+1]; //방문 여부를 체크하는 배열

        //우선순위 큐. 정렬 기준은 cost 오름차순
        PriorityQueue<City> priorityQueue = new PriorityQueue<>(new Comparator<>(){
            @Override
            public int compare(City o1, City o2){
                return o1.busCost - o2.busCost;
            }
        });

        //초기값 (시작 위치) 설정
        priorityQueue.offer(new City(A, 0));
        costs[A] = 0;

        //다익스트라 진행
        while(!priorityQueue.isEmpty()){
            City temp = priorityQueue.poll();

            //이미 방문했던 곳이라면 패스
            if(check[temp.cityNum]){
                continue;
            }

            //방문 체크하기
            check[temp.cityNum] = true;

            for(City list : cities[temp.cityNum]){
                //만약 이미 방문했던 곳이라면 패스
                if(check[list.cityNum]){
                    continue;
                }

                //최단거리가 갱신이 된다면
                if(costs[list.cityNum] > costs[temp.cityNum] + list.busCost){
                    //갱신하기
                    costs[list.cityNum] = costs[temp.cityNum] + list.busCost;
                    priorityQueue.offer(new City(list.cityNum, costs[list.cityNum]));
                }
            }
        }

        //B까지의 최단거리 리턴
        return costs[B];
    }
}