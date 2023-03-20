import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 주어진 이진 트리의 전위 순회, 중위 순회, 후위 순회 결과
 *
 * <p>
 * 문제에서 필요한 이진 트리의 순회 종류
 * - 전위 순회 : 루트를 먼저 체크하고, 왼쪽 체크, 오른쪽 체크 (preorder)  - 루왼오
 * - 중위 순회 : 왼쪽을 먼저 체크하고, 루트 체크, 오른쪽 체크 (inorder)   - 왼루오
 * - 후위 순회 : 왼쪽을 먼저 체크하고, 오른쪽 체크, 루트 체크 (postorder) - 왼오루
 *
 * 문제를 푸는 방법
 * - 트리를 만든다.
 * - 각 순회별 함수를 만들어서 출력한다.
 *
 * @author HeejoPark
 */

class Tree{
    char alphabet;
    Tree left;
    Tree right;
    Tree(){

    }
    Tree(char alphabet){
        this.alphabet = alphabet;
    }
}
class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   //트리의 노드 개수
        Tree[] tree = new Tree[26];   //A부터 Z까지의 트리를 의미한다.
        for(int i = 0; i<26; i++){
            tree[i] = new Tree((char) (i + 'A'));
        }
        for(int i = 0; i<N; i++){
            Tree tempTree = tree[sc.next().charAt(0) - 'A'];
            //해당 tempTree의 왼쪽
            char c = sc.next().charAt(0);
            if(c != '.'){
                tempTree.left = tree[c - 'A'];
            }
            c = sc.next().charAt(0);
            if(c != '.'){
                tempTree.right = tree[c - 'A'];
            }
        }

        //3가지 트리 실행
        StringBuilder sb = new StringBuilder();
        preorder(tree[0], sb);
        sb.append("\n");
        inorder(tree[0], sb);
        sb.append("\n");
        postorder(tree[0], sb);

        //결과 출력
        System.out.println(sb);
    }

    public static void preorder(Tree tree, StringBuilder sb){
        sb.append(tree.alphabet);
        if(tree.left != null) {
            preorder(tree.left, sb);
        }
        if(tree.right != null) {
            preorder(tree.right, sb);
        }
    }

    public static void inorder(Tree tree, StringBuilder sb){
        if(tree.left != null){
            inorder(tree.left, sb);
        }
        sb.append(tree.alphabet);
        if(tree.right != null){
            inorder(tree.right, sb);
        }
    }

    public static void postorder(Tree tree, StringBuilder sb){
        if(tree.left != null){
            postorder(tree.left, sb);
        }
        if(tree.right != null){
            postorder(tree.right, sb);
        }
        sb.append(tree.alphabet);
    }
}