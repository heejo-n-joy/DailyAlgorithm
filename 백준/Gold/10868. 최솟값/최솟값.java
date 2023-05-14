import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * 요구하는 출력 : 여러 개의 정수들 중 특정 범위에서의 최소값을 찾기
 *
 * 풀이 방법 : 세그먼트 트리
 * - 1. 세그먼트 트리를 먼저 만든다.
 * - 2. 해당하는 부분을 입력받으며 출력한다.
 *
 */

class SegmentTree{
    private int[] tree;    //세그먼트 트리

    //세그먼트 트리 초기화
    SegmentTree(int N){
        //1. 트리를 만들기. N<2^h를 만족하는 최소 h를 구하자
        int h = 1;
        while(Math.pow(2, h) < N){
            h++;
        }
        tree = new int[(int) Math.pow(2, h + 1)];
    }

    //트리 초기 생성
    int init(int[] input_data, int node_index, int start, int end){
        //리프노드라면
        if(start == end){
            //해당 위치에 입력값 저장하고 리턴
            tree[node_index] = input_data[start];
            return tree[node_index];
        }
        //아직 리프노드가 아니라면
        else{
            //미래의 리프노드 값을 리턴받아 최소값을 저장하기
            tree[node_index] = Math.min(init(input_data, node_index * 2, start, (start + end)/2)
                    , init(input_data, node_index * 2 + 1, (start + end) / 2 +1, end));
            return tree[node_index];
        }
    }

    int find(int node_index, int from, int to, int start, int end){
        //만약 범위가 유효 범위 내에 있다면
        if(from <= start && end <= to){
            return tree[node_index];
        }
        else if(end < from || start > to){
            return Integer.MAX_VALUE;
        }
        else{
            return Math.min(find(node_index * 2, from, to, start, (start + end) / 2),
                    find(node_index * 2 + 1, from, to, (start + end) / 2 + 1, end));
        }
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());    //정수의 개수
        int M = Integer.parseInt(st.nextToken());    //질문지의 개수

        SegmentTree segmentTree = new SegmentTree(N);
        int[] input = new int[N+1];
        for(int i = 1; i<=N; i++){
            input[i] = Integer.parseInt(br.readLine());
        }
        segmentTree.init(input, 1, 1, N);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(segmentTree.find(1, a, b, 1, N) + "\n");
        }

        //결과출력
        System.out.println(sb.toString());
    }
}