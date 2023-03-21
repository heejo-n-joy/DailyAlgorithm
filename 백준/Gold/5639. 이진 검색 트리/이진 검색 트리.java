import java.util.*;

/**
 * 요구하는 출력
 * - 중위 순회(루트-왼쪽-오른쪽)으로 주어진 이진 검색 트리를 후위 순회(왼쪽-오른쪽-루트)로 출력하기
 *
 * <p>
 * 이진 검색 트리의 특징
 * - 루트 값을 기준으로 왼쪽 노드는 값이 작아야 하고, 오른쪽 노드는 값이 커야 한다.
 *
 * 문제를 푸는 방법
 * - 스택을 활용하자!
 * - 1 하나씩 스택에 넣는다.
 * - 2 현재 스택의 peek보다 큰 값이 나타나면, 이는 오른쪽 노드가 나타난 것이다.
 * - 3 해당 값보다 작은 스택들을 모두 출력하고 (후위 순회 1. 왼쪽)
 * - 4 해당 값 또한 출력한다. (후위 순회 2. 오른쪽)
 * - 이런식으로 계산이 끝났는데, 스택 값이 남아있다면 차례대로 출력한다. (후위 순회 3. 루트)
 *
 * 재밌는 점
 * - 보통 입력값의 개수나, 끝나는 지점을 말해주는데.. 이 문제는 그런게 없네?
 * - 자바의 End Of File에 대해서 알게 되었다.
 * - hasNextLine()을 통해 돌다리 두드려보듯이 건널 수 있다.
 *
 * 회고
 * - 스택만으로 문제를 풀려고 하니까 여러 문제가 발생했는데..
 * - 그리고 시간초과 발생..
 * - 결국 정통적인 방식으로 트리를 생성해서 풀어보자
 * @author HeejoPark
 */

class Tree{
    int data;
    Tree left;
    Tree right;
    Tree parent;
    Tree(){
        left = null;
        right = null;
        parent = null;
    }
    Tree(int data){
        this.data = data;
        left = null;
        right = null;
        parent = null;
    }
}

class Main {
    static StringBuilder sb;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        sb = new StringBuilder();
        Tree[] trees = new Tree[10000];
        int tree_index = 0;
        //입력값으로 트리를 만들기
        Tree head = new Tree(-1);
        Tree searchTree = head;
        while(sc.hasNextLine()){
            int num = Integer.parseInt(sc.nextLine());
            trees[tree_index] = new Tree(num);  //노드를 생성하고
            //해당 노드가 어디에 붙어야 하는지를 파악하기
            if(tree_index == 0){
                head = trees[tree_index];
                searchTree = head;
            }
            else{
                //지금 트리가 searchTree의 왼쪽에 가야할지, 오른쪽에 가야할지, 부모로 이동해야 할지 판단하기
                while(true) {
                    //searchTree의 왼쪽에 가야한다.
                    if (trees[tree_index].data < searchTree.data) {
                        searchTree.left = trees[tree_index];
                        trees[tree_index].parent = searchTree;
//                        System.out.println("[1]" + searchTree.data + "의 왼쪽 노드에 " + trees[tree_index].data + " 삽입");
                        searchTree = searchTree.left;
                        break;
                    }
                    else{
                        //searchTree의 부모가 있는지 확인한다.
                        if(searchTree.parent == null) {
                            //부모가 없다면, 현재 searchTree의 오른쪽 값이 있는지 확인한다.
                            while(searchTree.right != null){
                                //오른쪽 값이 없을 때까지 searchTree가 쭉 내려간다.
                                searchTree = searchTree.right;
                            }
                            searchTree.right = trees[tree_index];
                            trees[tree_index].parent = searchTree;
//                            System.out.println("[2]" + searchTree.data + "의 오른쪽 노드에 " + trees[tree_index].data + " 삽입");
                            searchTree = searchTree.right;
                            break;
                        }
                        else {
                            //부모가 있다면, searchTree의 부모의 값을 확인한다.
                            //부모의 값보다 현재 트리 값이 크다면, searchTree의 오른쪽 값이 된다.
                            if(searchTree.parent.data > trees[tree_index].data){
                                while(searchTree.right != null){
                                    //오른쪽 값이 없을 때까지 searchTree가 쭉 내려간다.
                                    searchTree = searchTree.right;
                                }
                                searchTree.right = trees[tree_index];
                                trees[tree_index].parent = searchTree;
//                                System.out.println("[3]" + searchTree.data + "의 오른쪽 노드에 " + trees[tree_index].data + " 삽입");
                                searchTree = searchTree.right;
                                break;
                            }
                            else{
                                searchTree = searchTree.parent;
                            }
                        }
                    }
                }
            }
            tree_index++;
        }

        //후위 순회 실행
        post_order(trees[0]);

        //결과 출력
        System.out.println(sb.toString());
    }
    public static void post_order(Tree tree){
        if(tree.left != null){
            post_order(tree.left);
        }
        if(tree.right != null){
            post_order(tree.right);
        }
        sb.append(tree.data + "\n");
    }
}

//스택을 활용했던 풀이 (시간초과 엔딩)
//class Main {
//    static StringBuilder sb;
//    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(System.in);
//        sb = new StringBuilder();
//        Stack<Integer> stack = new Stack();
//        Deque<Integer> deque = new LinkedList<>();
//        while(sc.hasNextLine()){
//            int num = Integer.parseInt(sc.nextLine());
//
//            //스택이 비어있다면, 추가를 한다.
//            if(stack.isEmpty()){
//                stack.push(num);
//                continue;
//            }
//
//            //스택 피크보다 작은 값이라면, 추가를 한다.
//            if(stack.peek() > num){
//                stack.push(num);
//                continue;
//            }
//
//            //스택이 비어있지 않고 입력받은 숫자가 현재 피크보다 크다면, 오른쪽 노드가 나타난 것이다.
//            while(true){
//                //스택이 비었다면, 이는 num이 가장 오른쪽 노드임을 의미한다.
//                if(stack.isEmpty()){
//                    break;
//                }
//                //스택 피크가 num보다 작다면, num은 더 위로 올라가봐야 한다.
//                if(stack.peek() < num){
//                    //그런데 deque의 최근 값보다 stack의 peek값이 더 크면, 이는 오른쪽 값들을 갖는 트리가 아니다.
//                    //즉 트리가 왼쪽으로 꺾였는데, 후위순회는 왼-오-노드 순으로 출력하기 때문에, 여기까지의 기록들을 출력해줘야 한다.
//                    if(!deque.isEmpty() && deque.peekLast() < stack.peek()){
//                        //따라서 현재까지의 deque는 모두 출력한다.
//                        while(!deque.isEmpty()){
//                            Pop(deque.pollLast());
//                        }
//                    }
//                    deque.push(stack.pop());
//                }
//                //스택피크가 num보다 크다면, num의 위치는 여기까지이다.
//                else{
//                    //정복 성공.
//                    stack.push(deque.pop());    //현재 스택피크 아래 노드를 다시 스택에 추가해준다.
//                    break;
//                }
//            }
//            //deque의 모든 내용은 다시 stack으로 이동한다.
//            while (!deque.isEmpty()) {
//                stack.push(deque.pop());
//            }
//            //스택에 num을 추가한다.
//            stack.push(num);
//        }
//        //스택에 남아있는 값을 모두 출력한다
//        while(!stack.isEmpty()){
//            Pop(stack.pop());
//        }
//
//        //결과 출력
//        System.out.println(sb.toString());
//    }
//
//    public static void Pop(int num){
//        sb.append(num + "\n");
//    }
//}