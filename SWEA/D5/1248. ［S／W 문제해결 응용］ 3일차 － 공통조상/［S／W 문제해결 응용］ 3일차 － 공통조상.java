import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * No.11 : 공통조상
 *
 * 문제 내용
 * - 이진 트리에서 두 노드를 선택하고, 그 노드들의 공통 조상을 찾으려 한다.
 *
 * 요구하는 출력
 * - 공통 조상의 번호와 공통 조상을 루트로 한 서브 트리의 크기
 *
 * 입력
 * - T : 테스트케이스
 * - V, E, A, B : 정점의 개수, 간선의 개수, 두 개의 정점 번호
 * - E개의 간선. 순서는 부모 자식 순.
 *
 *  전략
 *  - 1. 두 노드의 공통 조상을 찾기
 *  - 2. 찾은 공통 조상을 기준으로 서브 트리 크기 재기
 *  - 공통 조상을 찾기 위해, 각 노드들의 부모 노드를 표시하는 일차원 배열을 만들자.
 *
 *  난이도(예상/실제) : 2
 *
 *  회고
 *
 * @author HeejoPark
 */

class Tree011{
    int data;
    Tree011 left;
    Tree011 right;

    public Tree011(int data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
public class Solution {

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(st.nextToken());   //정점의 개수
            int E = Integer.parseInt(st.nextToken());   //간선의 개수
            int A = Integer.parseInt(st.nextToken());   //정점 1
            int B = Integer.parseInt(st.nextToken());   //정점 2
            Tree011[] trees = new Tree011[V + 1];   //트리 생성
            int[] parentOfTrees = new int[V + 1];   //트리의 부모가 저장된 배열
            for (int i = 0; i < V + 1; i++) {
                trees[i] = new Tree011(i);    //tree 초기화
            }
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < E; i++){
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                parentOfTrees[child] = parent;
                if(trees[parent].left == null){ //부모 노드의 왼쪽이 비어있다면
                    //왼쪽에 넣기
                    trees[parent].left = trees[child];
                }
                else if(trees[parent].right == null){   //부모 노드의 오른쪽이 비어있다면
                    //오른쪽에 넣기
                    trees[parent].right = trees[child];
                }
            }
            //계산1. 공통 조상 노드 찾기
            boolean[] isParentOfNodeA = new boolean[V+1];
            int pointerNode = A;
            while(true){    //A의 부모들을 전부 true로 체크한다.
                if(pointerNode==0){
                    return;
                }
                isParentOfNodeA[pointerNode] = true;
                if(pointerNode == 1){ //트리의 루트 노드에 도달했으면 종료
                    break;
                }
                pointerNode = parentOfTrees[pointerNode];
            }


            //B의 부모들을 찾으면서, A의 부모들 중 겹치는 부분이 있는지 확인하기
            pointerNode = B;
            while(true){
                if(pointerNode==0) {
                    return;
                }
                //만약 현재 pointerNode가 A의 조상이라면
                if(isParentOfNodeA[pointerNode]){
                    //여기가 공통 조상 노드라고 할 수 있다.
                    break;
                }
                //아니라면, B의 부모로 이동
                pointerNode = parentOfTrees[pointerNode];
            }

            //계산2. 트리의 크기 체크하기
            int size = countTree(trees[pointerNode]);

            //결과 출력
            System.out.println("#" + test_case + " " + pointerNode + " " + size);

        }
    }

    public static int countTree(Tree011 tree){
        if(tree.left != null && tree.right != null){    //트리의 자식노드가 2개 존재한다면
            return 1 + countTree(tree.left) + countTree(tree.right);
        }
        else if (tree.left != null && tree.right == null){  //트리의 자식노드가 1개 존재한다면
            return 1 + countTree(tree.left);
        }
        else{   //트리의 자식노드가 없다면
            return 1;
        }
    }
}