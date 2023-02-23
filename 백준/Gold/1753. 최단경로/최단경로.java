import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        final int MAX_VALUE = 300000;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());   //정점 개수
        int E = Integer.parseInt(st.nextToken());   //간선 개수
        List<int[]>[] nodes = new List[V];
        for (int i = 0; i < V; i++) {
            nodes[i] = new ArrayList<>();
        }
        int K = Integer.parseInt(br.readLine()) - 1;   //시작 정점 번호
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;   //from
            int v = Integer.parseInt(st.nextToken()) - 1;   //to
            int w = Integer.parseInt(st.nextToken());   //가중치 w
            nodes[u].add(new int[]{v, w});
        }
        int[] nodes_time = new int[V];
        boolean[] nodes_checked = new boolean[V];
        for (int i = 0; i < V; i++) {
            nodes_time[i] = MAX_VALUE; //최대값으로 초기화 (최소값을 구하기 위해)
        }
        nodes_time[K] = 0;
        //감염 과정
        for (int j = 0; j < V; j++) {
            //nodes_time에서 확인 안한 곳 & 가장 소요 시간이 낮은 곳을 공략
            int node_index = min(nodes_checked, nodes_time, V);
            nodes_checked[node_index] = true;
            //해당 위치에서 걸리는 다른 위치들을 넣기
            for (int i = 0; i < nodes[node_index].size(); i++) {
                int to = nodes[node_index].get(i)[0];
                int time = nodes[node_index].get(i)[1];
                //두 수를 비교하고, 더 적은 시간을 반영한다.
                // 1. 출발 K에서 node_index, node_index에서 i까지의 시간
                int optionA = nodes_time[node_index] + time;
                // 2. 기존에 저장된 i까지의 최단 시간
                int optionB = nodes_time[to];
                //더 작은 시간 반영
                nodes_time[to] = Math.min(optionA, optionB);
            }
        }
        //감염 결과 확인
        for (int i = 0; i < V; i++) {
            if (nodes_time[i] == MAX_VALUE) {
                System.out.println("INF");
            }
            else{
                System.out.println(nodes_time[i]);
            }
        }
    }

    public static int min(boolean[] nodes_checked, int[] nodes_time, int n) {
        int result_node_index = -1;
        for (int i = 0; i < n; i++) {
            if (nodes_checked[i]) {
                continue;
            }
            if (result_node_index == -1) {
                result_node_index = i;
            } else {
                if (nodes_time[i] < nodes_time[result_node_index]) {
                    result_node_index = i;
                }
            }
        }
        return result_node_index;
    }
}