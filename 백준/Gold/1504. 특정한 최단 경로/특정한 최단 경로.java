import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 요구하는 출력 : 주어진 두 정점을 반드시 거치면서 이동하는 최단 경로
 *
 * 풀이 방법 : 다익스트라 (3번)
 * - 출발지 -> 경유지1 -> 경유지2 -> 도착지를 계산한다.
 * - 이 때, 경유지 1과 경유지 2는 순서가 바뀔 수도 있다.
 *
 */

class Edge{
    int to;
    int distance;
    Edge(int to, int distance){
        this.to = to;
        this.distance = distance;
    }
}
public class Main {

    static final int MAX = 10000000;    //이론상 800000 * 3보다 큰 값을 대입
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());    //정점의 개수
        int E = Integer.parseInt(st.nextToken());    //간선의 개수
        List<Edge>[] lists = new List[N+1];
        for(int i =0; i<=N; i++){
            lists[i] = new ArrayList<>();
        }

        //주어진 경로 입력받기
        for(int i = 0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());   //from
            int b = Integer.parseInt(st.nextToken());   //to
            int c = Integer.parseInt(st.nextToken());   //distance
            lists[a].add(new Edge(b, c));   //a에서 b로의 경로 추가
            lists[b].add(new Edge(a, c));   //b에서 a로의 경로 추가
        }

        //경유지 1,2를 입력받기
        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());
        
        //경로 계산1 : 1 -> v1 -> v2 -> N
        //경로 계산2 : 1 -> v2 -> v1 -> N
        int result = Math.min(calcDijkstra(1, v1, v2, N, lists), calcDijkstra(1, v2, v1, N, lists));

        //경로가 불가능한 경우, -1로 값 변경
        if(result == MAX){
            result = -1;
        }

        //결과 출력
        System.out.println(result);
    }

    //다익스트라를 3번 계산하는 함수
    public static int calcDijkstra(int from, int v1, int v2, int N, List<Edge>[] lists){
        int stage1 = dijkstra(from, v1, N, lists);
        int stage2 = dijkstra(v1, v2, N, lists);
        int stage3 = dijkstra(v2, N, N, lists);

        // 세 경로 중 하나라도 도달할 수 없다면
        if(stage1 == MAX || stage2 == MAX || stage3 == MAX){
            return MAX;  //-1을 리턴한다.
        }
        return stage1 + stage2 + stage3;
    }


    //다익스트라 계산을 활용하여 from 부터 to 까지의 최단경로를 계산
    public static int dijkstra(int from, int to, int N, List<Edge>[] lists){
        int[] distances = new int[N+1];     //노드별 최소거리를 저장하는 배열
        boolean[] check = new boolean[N+1]; //노드 방문여부를 확인하는 배열

        for(int i = 0; i<=N; i++){
            distances[i] = MAX; //초기값은 MAX(도달할 수 없는 값)로 설정
        }
        distances[from] = 0;    //자기 자신은 0으로 처리

        //우선순위큐의 우선순위는 거리(distance)에 대한 오름차순이다.
        PriorityQueue<DijkstraNode> priorityQueue = new PriorityQueue<>(new Comparator<DijkstraNode>() {
            @Override
            public int compare(DijkstraNode o1, DijkstraNode o2){
                return o1.distance - o2.distance;
            }
        });

        //출발 지점을 넣고
        priorityQueue.offer(new DijkstraNode(from, 0));

        //다익스트라 시작
        while(!priorityQueue.isEmpty()){
            //노드를 뽑는다.
            DijkstraNode dijkstraNode = priorityQueue.poll();

            //이미 탐색을 한 노드라면 패스
            if(check[dijkstraNode.nodeNum]){
                continue;
            }
            //탐색을 했음을 체크한다.
            check[dijkstraNode.nodeNum] = true;

            //해당 노드와 연결된 곳들의 값을 갱신한다.
            for(Edge list : lists[dijkstraNode.nodeNum]){
                //이미 확인한 노드라면 패스
                if(check[list.to]){
                    continue;
                }
                //현재 노드를 거쳐서 가는게 더 짧다면, 갱신을 하자
                if(distances[list.to] > distances[dijkstraNode.nodeNum] + list.distance){
                    distances[list.to] = distances[dijkstraNode.nodeNum] + list.distance;   //갱신
                    priorityQueue.offer(new DijkstraNode(list.to, distances[list.to]));     //큐에 탐색할 노드 적기
                }
            }
        }

        //최단거리를 리턴
        return distances[to];
    }
}

//다익스트라용 노드
class DijkstraNode{
    int nodeNum;
    int distance;
    DijkstraNode(int nodeNum, int distance){
        this.nodeNum = nodeNum;
        this.distance = distance;
    }
}