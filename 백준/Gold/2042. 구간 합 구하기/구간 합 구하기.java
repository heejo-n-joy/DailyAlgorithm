import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 아주 큰 N개의 수가 있고, 중간 중간 숫자가 바뀐다.
 * <p>
 * 요구하는 출력
 * - 구간합
 * <p>
 * 전략
 * - 어제 학습한 Segment Tree를 활용해서 풀자
 * - 아직 미숙하지만 계속 연습해서 실전에서도 잘 활용할 수 있도록 연습하자
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class SegmentTree{
    long[] tree;

    SegmentTree(int n){
        double treeHeight = Math.ceil(Math.log(n) / Math.log(2)) + 1;
        long treeNodeCount = Math.round(Math.pow(2, treeHeight));
        tree = new long[Math.toIntExact(treeNodeCount) + 1];
    }
    long init(long[] array, int node, int start, int end){
        if(start == end){
            tree[node] = array[start];
            return tree[node];
        }
        else{
            return tree[node] = init(array, node * 2, start, (start + end) / 2)
                + init(array, node*2 + 1 , (start + end) / 2 + 1, end);
        }
    }

    void update(int node, int start, int end, int index, long diff){
        if(index < start || index > end){
            return;
        }
        tree[node] += diff;
        if(start != end){
            update(node * 2, start, (start + end ) / 2, index, diff);
            update(node * 2 + 1 , (start + end) / 2 + 1, end, index, diff);
        }
    }

    long find(int node, int start, int end, int left, int right){
        if(start > right || end < left){
            return 0;
        }
        if(left <= start && right >= end){
            return tree[node];
        }
        else{
            return find(node * 2, start, (start + end ) / 2, left, right)
                    + find(node * 2 + 1 , (start + end ) / 2 + 1, end, left, right);
        }
    }
}
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //수의 개수
        int M = Integer.parseInt(st.nextToken());   //변경 횟수
        int K = Integer.parseInt(st.nextToken());   //구간 합 구하는 횟수
        long[] array = new long [N+1];
        for(int i =1; i<=N; i++){
            array[i] = Long.parseLong(br.readLine());
        }
//        System.out.println("INPUT");
//        for(int i =0; i<N; i++){
//            System.out.print(array[i+1] +" ");
//        }
//        System.out.println();
        SegmentTree segmentTree = new SegmentTree(N);
        segmentTree.init(array, 1, 1, N);
//        System.out.println("INIT");
//        for(int i =0; i<segmentTree.tree.length; i++){
//            System.out.print(segmentTree.tree[i] + " ");
//        }
//        System.out.println();
        for(int i = 0; i< M + K; i++){
            st = new StringTokenizer(br.readLine());
            int option = Integer.parseInt(st.nextToken());
            switch(option){
                case 1:
                    //수 바꾸기
                    int index = Integer.parseInt(st.nextToken());
                    long changeValue = Long.parseLong(st.nextToken());
//                    System.out.println("CHANGE! : " + array[index] + "(" + index + ") => " + changeValue);
                    long diff = changeValue - array[index];
                    array[index] = changeValue;
                    segmentTree.update(1, 1, N, index, diff);
                    break;
                case 2:
                    //합 구하기
                    int from = Integer.parseInt(st.nextToken());
                    int to = Integer.parseInt(st.nextToken());
//                    System.out.println("SUM! : FROM " + from + " TO " + to);
                    System.out.println(segmentTree.find(1, 1, N, from, to));
                    break;
                default:
                    //ERROR
                    break;
            }
        }
    }
}