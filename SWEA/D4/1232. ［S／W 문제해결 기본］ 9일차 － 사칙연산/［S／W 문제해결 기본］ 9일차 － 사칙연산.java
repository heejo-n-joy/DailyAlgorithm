import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * No.10 : 사칙연산
 *
 * 문제 내용
 * - 트리를 이용하여 사칙연산을 진행한다.
 *
 * 요구하는 출력
 * - 사칙연산 결과
 *
 * 입력
 * - N : 정점의 개수
 * - 정점 정보들
 *
 *  전략
 *  - recursive를 이용하여 계산하기
 *  - 계산은 왼-오-중 순서로
 *
 *  난이도(예상/실제) : 2
 *
 *  회고
 *
 * @author HeejoPark
 */

class Tree010{
    String data;
    Tree010 left;
    Tree010 right;

    public Tree010(String data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
public class Solution {

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int T = Integer.parseInt(br.readLine());
        int T = 10;

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());
            Tree010[] trees = new Tree010[N+1]; //트리 생성
            for(int i =0; i<N+1; i++){
                trees[i] = new Tree010(" ");    //tree 초기화
            }
            //입력값 받으면서 트리 초기세팅하기
            for(int i =0; i<N; i++){
                String[] str = br.readLine().split(" ");
                int num = Integer.parseInt(str[0]); //노드의 번호
                switch(str[1]){
                    case "+":   //더하기인 경우
                    case "-":   //빼기인 경우
                    case "*":   //곱하기인 경우
                    case "/":   //나누기인 경우
                        int leftNum = Integer.parseInt(str[2]); //왼쪽 노드 연결
                        int rightNum = Integer.parseInt(str[3]);    //오른쪽 노드 연결
                        trees[num].data = str[1];
                        trees[num].left = trees[leftNum];
                        trees[num].right = trees[rightNum];
                        break;
                    default:    //숫자인 경우
                        trees[num].data = str[1];
                        break;
                }
            }

            //계산하기
            int result = (int) (Math.floor(calc(trees[1])));
            //결과 출력
            System.out.println("#" + test_case + " " + (result));
        }
    }

    public static double calc(Tree010 tree){
        switch(tree.data){
            case "+":
                return calc(tree.left) + calc(tree.right);
            case "-":
                return calc(tree.left) - calc(tree.right);
            case "*":
                return calc(tree.left) * calc(tree.right);
            case "/":
                return calc(tree.left) / calc(tree.right);
            default:
                return Integer.parseInt(tree.data);
        }
    }
}