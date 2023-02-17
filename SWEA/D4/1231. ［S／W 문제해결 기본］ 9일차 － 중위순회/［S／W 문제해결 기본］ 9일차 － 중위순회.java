import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * No.8 : 중위순회
 *
 * 문제 내용
 * - 주어진 수열을 명령어들에 맞게 편집하자
 *
 * 요구하는 출력
 * - 완성된 수열의 인덱스 L의 데이터 (인덱스 L이 없으면 -1 출력)
 *
 * 입력
 * - T : 테스트케이스
 * - N, M, L : 수열길이, 추가횟수, 출력할인덱스번호
 * - 수열
 * - 인덱스와 숫자 정보들
 *
 *  전략
 *  - Node를 하나 만들어서 구현
 *
 *  난이도(예상/실제) : 2
 *
 *  회고
 *
 * @author HeejoPark
 */
class Tree{
    char data;
    Tree left;
    Tree right;

    public Tree(char data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
public class Solution {
    public static void main(String args[]) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = 10;    //테스트케이스
        for(int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());   //수열의 길이
            Tree[] trees = new Tree[N+1];
            for(int i = 0; i<N+1; i++){
                trees[i] = new Tree('_');
            }
            for(int i = 0; i<N; i++) {
                String[] str = br.readLine().split(" ");
                int num = Integer.parseInt(str[0]);
                char alphabet = str[1].charAt(0);
                trees[num].data = alphabet;
                if(str.length >= 3){
                    int left = Integer.parseInt(str[2]);
                    trees[num].left = trees[left];
                }
                if(str.length == 4){
                    int right = Integer.parseInt(str[3]);
                    trees[num].right = trees[right];
                }
            }
            //결과 출력
            System.out.print("#" + test_case + " ");
            recursive(trees[1]);
            System.out.println();
        }
    }

    public static void recursive(Tree tree){
        if(tree.left != null) {
            recursive(tree.left);
        }
        System.out.print(tree.data);
        if(tree.right != null) {
            recursive(tree.right);
        }
    }
}