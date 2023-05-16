import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * 요구하는 출력 : 장애물을 피하며 아이템을 모두 먹고 목적지에 도착할 경우의 수
 *
 * 풀이 방법 : DP
 * - 1. 시작지점과 도착지점의 범위를 계속 한정짓는다.
 * - 2. 해당하는 부분에 DP를 돌려 가능한 경우의 수를 찾는다.
 * - 3. 관련 정보를 저장하면서, 경우의 수들을 지속적으로 곱한다.
 *
 * 다만 문제에서 왼쪽 아래가 1,1이고 오른쪽 위가 N,M이라는 점을 유의하자
 * (문제를 풀 때는 왼쪽 위에서 오른쪽 아래로 내려가는 걸로 한다)
 */

class Node{
    int x;
    int y;
    Node(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());    //맵
        int M = Integer.parseInt(st.nextToken());    //맵
        int A = Integer.parseInt(st.nextToken());    //아이템 개수
        int B = Integer.parseInt(st.nextToken());    //장애물 개수

        int[][] DP = new int[N+1][M+1];

        //들려야 할 지점들을 오름차순으로 정렬
        PriorityQueue<Node> loc = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.x == o2.x){
                    return o1.y - o2.y;
                }
                return o1.x - o2.x;
            }
        });

        //시작 위치, 끝 위치도 넣기
        loc.offer(new Node(1, 1));
        loc.offer(new Node(N, M));

        //아이템을 목록에 넣기
        for(int i = 0; i<A; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            loc.offer(new Node(x, y));
        }

        //장애물을 맵에 배치하기 (-1로)
        for(int i = 0; i<B; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            DP[x][y] = -1;
        }

        DP[1][1] = 1;

        //시작과 도착 위치를 꺼내면서 계산하기
        while(true){
            Node start = loc.poll();

            //만약 도착지점에 왔다면 종료
            if(start.x == N && start.y == M){
                break;
            }

            //도착 지점을 설정
            Node end = loc.peek();

            //해당 위치부터 도착 위치까지 직사각형의 배열 내에서 이동 경로 경우의 수 찾기
            for(int x = start.x; x <= end.x; x++){
                for(int y = start.y; y <= end.y; y++){
                    if(DP[x][y] == -1){ //장애물 위치는 패스
                        continue;
                    }
                    if((x > start.x) && DP[x-1][y] != -1){
                        DP[x][y] += DP[x-1][y];
                    }
                    if ((y > start.y) && DP[x][y-1] != -1) {
                        DP[x][y] += DP[x][y-1];
                    }
                }
            }
        }

        //결과 출력
        System.out.println(DP[N][M]);
    }
}