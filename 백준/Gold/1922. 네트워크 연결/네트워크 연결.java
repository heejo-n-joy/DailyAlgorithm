import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * https://www.acmicpc.net/problem/1922
 * 
 * 요구하는 출력 : 모든 컴퓨터를 하나로 연결하는 최소 비용
 *
 * 아이디어 : 최소 스패닝 트리 (MST)
 * 1. 모든 연결 상태를 비용을 기준으로 오름차순 정렬한다.
 * 2. 사이클이 생기지 않게 연결들을 하나씩 선택한다.
 * 3. 선택된 연결 선들의 비용 합 출력
 */

class Line{
    int from;
    int to;
    int cost;
    Line(int from, int to, int cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());   //컴퓨터의 수
        int M = Integer.parseInt(br.readLine());   //연결선의 수

        //컴퓨터에 연결된 컴퓨터들 중 최소 숫자를 저장
        int[] parent = new int[N+1];
        for(int i = 0; i<=N; i++){
            parent[i] = i;
        }

        //연결선들을 저장할 우선순위 큐
        PriorityQueue<Line> pq = new PriorityQueue<>(new Comparator<Line>(){
            @Override
            public int compare(Line o1, Line o2){
                return o1.cost - o2.cost;
            }
        });

        //연결선을 입력받기
        for(int i = 0; i<M; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            //연결 컴퓨터가 서로 다른 경우만 입력을 받자
            if(from != to){
                pq.offer(new Line(Math.min(from,to), Math.max(from,to), cost));
            }
        }

        //연결선들을 하나씩 보자
        int result = 0; //이론상 최대 길이 999 * 10,000 < 10,000,000으로 int 타입 사용
        int count = 0;  //이론상 N-1개만 가져도 연결을 할 수 있어야 한다.
        while(!pq.isEmpty()){
            //연결선 꺼내기
            Line temp = pq.poll();

            //계산
            if(union(parent, temp.from, temp.to)){
                result += temp.cost;
                count++;
            }

            //만약 연결선 개수가 N-1개가 됐다면
            if(count == N-1){
                break;
            }
        }

        //결과 출력
        System.out.println(result);
    }

    public static boolean union(int[] parent, int from, int to){
        int from_result = find(parent, from);
        int to_result = find(parent, to);

        //순환이 발생하므로 불가능
        if(from_result == to_result){
            return false;
        }

        //부모 관계를 갱신해주기
        parent[Math.max(from_result, to_result)] = Math.min(from_result, to_result);
        return true;
    }

    public static int find(int[] parent, int num){
        //만약 연결된 컴퓨터들 중 자기 자신이 가장 작은 번호라면
        if(parent[num] == num){
            return num;
        }
        //그게 아니라면
        return parent[num] = find(parent, parent[num]);
    }
}
