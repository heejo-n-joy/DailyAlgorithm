import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * No.4 : 암호문3
 *
 * 문제 내용
 * - 암호문을 수정하려고 한다.
 *
 * 요구하는 출력
 * - 결과의 처음 10개 숫자
 *
 * 입력
 * - 원본 암호문의 길이 N
 * - 원본 암호문
 * - 명령어 개수 M
 * - 명령어들 (I, D, A)
 *
 *  전략
 *  - 각 명령어대로 함수들을 구현
 *
 *  난이도(예상/실제) : 2
 *
 *  회고
 *
 * @author HeejoPark
 */

class Node{
    int data;
    Node next;
    public Node(int data){
        this.data = data;
        next = null;
    }
}
public class Solution {
    static Node head;

    public static void main(String args[]) throws Exception
    {
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int T = Integer.parseInt(br.readLine());
        int T = 10;

        for(int test_case = 1; test_case <= T; test_case++)
        {
            head = new Node(-1);
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            Node newNodes = head;
            for(int i =0; i<N; i++){
                insertNode(Integer.parseInt(st.nextToken()), newNodes);
                newNodes = newNodes.next;
            }

            int M = Integer.parseInt(br.readLine());    //명령어의 개수
            st = new StringTokenizer(br.readLine());
            for(int i =0; i<M; i++){
                char cmd = st.nextToken().charAt(0);    //명령 종류
                switch(cmd){
                    case 'I':   //삽입
                        int I_x = Integer.parseInt(st.nextToken());
                        int I_y = Integer.parseInt(st.nextToken());
                        Node insertNode = head;
                        for(int j =0; j<I_x; j++){
                            insertNode = insertNode.next;
                        }
                        Node nextNode = insertNode.next;
                        //숫자들을 하나의 노드들로 연결해서
                        for(int j = 0; j<I_y; j++){
                            Node newNode = new Node(Integer.parseInt(st.nextToken()));
                            insertNode.next = newNode;
                            insertNode = insertNode.next;
                        }
                        insertNode.next = nextNode;
                        break;
                    case 'D':   //삭제
                        int D_x = Integer.parseInt(st.nextToken());
                        int D_y = Integer.parseInt(st.nextToken());
                        //시작 노드의 앞을 찾고
                        Node startNode = head;
                        for(int j =1; j<D_x; j++){
                            startNode = startNode.next;
                        }
                        for(int j = 0; j<D_y; j++){
                            //만약에 지울 노드가 없다면 삭제 끝
                            if(startNode.next == null){
                                break;
                            }
                            else{
                                Node eraseNode = startNode.next;
                                //만약에 지울 노드 다음에 노드가 없다면
                                if(eraseNode.next == null){
                                    startNode.next = null;
                                }
                                else{
                                    startNode.next = eraseNode.next;
                                    eraseNode.next = null;
                                }
                            }
                        }
                        //통으로 도려낸다
                        break;
                    case 'A':   //추가
                        int A_y = Integer.parseInt(st.nextToken());
                        Node pointerNode = head;
                        //포인터노드를 가장 마지막 위치로 이동시킨다.
                        while(pointerNode.next != null){
                            pointerNode = pointerNode.next;
                        }
                        for(int j =0; j<A_y; j++){
                            Node newNode = new Node(Integer.parseInt(st.nextToken()));  //새 노드를 붙인다.
                            pointerNode.next = newNode;
                            pointerNode = pointerNode.next; //포인터노드를 마지막 위치로 이동시킨다.
                        }
                        break;
                }
            }

            System.out.print("#" + test_case + " ");
            Node printNode = head;
            for(int i =0; i<10; i++){
                printNode = printNode.next;
                System.out.print(printNode.data + " ");
            }
            System.out.println();
        }
    }

    public static void insertNode(int data, Node node){
        Node newNode = new Node(data);
        node.next = newNode;
    }

}