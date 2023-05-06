import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.*;

/**
 * 요구하는 출력 : 주어진 트리에 선택된 두 노드의 공통 조상 (중 가장 가까운 = 루트 노드와 가장 먼)
 *
 * 풀이 방법 : LCA(Lowest Common Ancestor)
 * 1. 트리를 입력받으며 각 노드 별 부모 노드 번호를 저장해둔다.
 * 2. 완성된 트리를 가지고 모든 노드의 깊이를 저장한다. (루트 노드를 1로 시작)
 * 3. 입력받은 두 노드의 공통 조상 노드를 찾는다.
 *  3-1. 우선, 두 노드의 깊이를 맞춘다.
 *  3-2. 이후 한 칸씩 올라가며 부모 노드가 같은지를 파악한다.
 *
 *  시간 복잡도
 *  O(N-1) + O(N) + O(logN) => O(N)
 */

class Node{
    private int number;
    private int depth;
    private Node parent;
    private List<Node> child;

    Node(int number){
        this.number = number;
        child = new ArrayList<>();
    }

    int getNumber(){
        return number;
    }

    int getDepth(){
        return depth;
    }

    void setDepth(int depth){
        this.depth = depth;
    }

    Node getParent(){
        return parent;
    }

    void setParent(Node parent){
        this.parent = parent;
    }

    List<Node> getChild(){
        return child;
    }

    void setChild(Node child){
        this.child.add(child);
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bufferedReader.readLine());    //테스트 케이스 개수
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(bufferedReader.readLine());    //트리 노드의 개수

            Node[] node = new Node[N+1];
            for(int i = 0; i<=N; i++){
                node[i] = new Node(i);
            }

            //트리를 만들기
            for(int i = 0; i<N-1; i++) {
                StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                int A = Integer.parseInt(stringTokenizer.nextToken());  //부모
                int B = Integer.parseInt(stringTokenizer.nextToken());  //자식
                node[B].setParent(node[A]);
                node[A].setChild(node[B]);
            }

            //가장 루트 노드를 찾기
            Node root = node[N];
            while(root.getParent()!=null){
                root = root.getParent();
            }

            //해당 루트 노드를 기준으로 각 노드들의 깊이를 체크하기
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{root.getNumber(), 1});
            while(!queue.isEmpty()){
                int[] temp = queue.poll();
                node[temp[0]].setDepth(temp[1]);
                for(Node child: node[temp[0]].getChild()){
                    queue.offer(new int[]{child.getNumber(), temp[1] + 1});
                }
            }

            //마지막 두 노드를 받기
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int A = Integer.parseInt(stringTokenizer.nextToken());
            int B = Integer.parseInt(stringTokenizer.nextToken());

            //공통 조상 노드를 찾기
            //1. 높이를 맞추기
            while(node[A].getDepth() < node[B].getDepth()){
                node[B] = node[B].getParent();
            }
            while(node[A].getDepth() > node[B].getDepth()){
                node[A] = node[A].getParent();
            }

            //2. 같은 조상이 나올 때까지 계속 체크하기
            while(!node[A].equals(node[B])){
                node[A] = node[A].getParent();
                node[B] = node[B].getParent();
            }

            //결과 출력
            System.out.println(node[A].getNumber());
        }
    }
}
