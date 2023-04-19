import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 쿼리를 수행하며 해당하는 수열 출력
 *
 * 풀이 방법 : 세그먼트 트리(Segment Tree)
 *
 */

class Node{
    int min_val;
    int min_index;
}
class SegmentTree{

    Node[] trees;
    SegmentTree(int N){
        //N개 이상의 리프 노드를 가지는 Node 배열 사이즈
        int size = (int) Math.pow(2, Math.ceil(Math.log(N) / Math.log(2)) + 1);

        trees = new Node[size];
        for(int i = 0; i<size; i++){
            trees[i] = new Node();
        }
    }

    Node init(String[] arr, int node, int start, int end){
        //리프 노드에 도달했다면
        if(start == end){
            trees[node].min_val = Integer.parseInt(arr[start-1]);
            trees[node].min_index = start;
        }
        else{
            Node tempA = init(arr, node * 2, start, (start + end) / 2);
            Node tempB = init(arr, node * 2 + 1, (start + end) / 2 + 1, end);

            if(tempA.min_val > tempB.min_val){
                trees[node] = tempB;
            }
            else{
                trees[node] = tempA;
            }
        }
        return trees[node];
    }

    //최소값을 출력
    int print(){
        // 루트 노드에 저장되어있는 값을 출력
        return trees[1].min_index;
    }

    //값을 변경 및 최소값 업데이트
    Node update(int node, int start, int end, int index, int value){
        //만약 인덱스가 범위를 벗어나면, 계산 없이 값을 출력
        if(index < start || index > end){
            return trees[node];
        }
        //인덱스가 범위 안에 있고 정확한 위치라면, 값을 갱신
        if(start == index && end == index){
            trees[node].min_val = value;
            return trees[node];
        }
        //인덱스가 범위 안에 있지만 정확한 위치는 아니라면
        Node tempA = update(node * 2, start, (start + end) / 2, index, value);
        Node tempB = update(node * 2 + 1, (start + end) / 2 + 1, end, index, value);
        if(tempA.min_val > tempB.min_val){
            trees[node] = tempB;
        }
        else{
            trees[node] = tempA;
        }
        return trees[node];
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(bufferedReader.readLine());    //수열의 크기

        //수열을 입력받고 세그먼트 트리 초기화
        String[] array = bufferedReader.readLine().split(" ");
        SegmentTree segmentTree = new SegmentTree(N);
        segmentTree.init(array, 1, 1, N);

        int M = Integer.parseInt(bufferedReader.readLine());    //쿼리의 크기

        //쿼리 실행
        for(int i = 0 ; i<M; i++){
            String[] query = bufferedReader.readLine().split(" ");
            switch(query[0]){
                case "1":
                    int index = Integer.parseInt(query[1]); //바꿀 위치
                    int v = Integer.parseInt(query[2]);     //바꿀 값
                    segmentTree.update(1, 1, N, index, v);
                    break;
                case "2":
                    sb.append(segmentTree.print() + "\n");
                    break;
                default:
                    //ERROR
                    System.out.println("ERROR : UNDEFINED QUERY NUMBER!");
                    break;
            }
        }

        //결과 출력
        System.out.println(sb.toString());
    }
}