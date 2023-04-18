import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 주어진 집합 S의 문자열들 중 접두사에 해당하는 개수
 *
 * 풀이 방법 : 트라이(Trie)
 * - 집합 S의 문자열들을 트라이 방식으로 트리를 형성
 * - M개의 문자열들을 트리에 집어넣어보고 가능한 상황인지를 체크
 *
 */

class Node{
    Map<Character, Node> child;
    Node(){
        child = new HashMap<>();
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());    //집합 S에 포함되는 문자열 개수
        int M = Integer.parseInt(stringTokenizer.nextToken());    //검사해야 하는 문자열 개수

        Node head = new Node(); //Trie의 head

        //집합 S를 바탕으로 트리를 만들자
        for(int i = 0; i<N; i++){
            Node index = head;
            String str = bufferedReader.readLine();
            //문자열의 처음부터 끝까지 트리에 넣는다.
            for(int j = 0; j<str.length(); j++){
                //해당 알파벳을 가진 노드가 없다면
                if(!index.child.containsKey(str.charAt(j))){
                    Node newNode = new Node();  //노드를 새로 만들고
                    index.child.put(str.charAt(j), newNode);    //자녀 노드 명단에 추가한다.
                }

                index = index.child.get(str.charAt(j)); //인덱스를 해당 위치로 이동
            }
        }

        //결과 변수
        int result = 0;

        //M개의 문자열이 접두사가 되는지 확인하기
        exec : for(int i = 0; i<M; i++){
            Node index = head;
            String str = bufferedReader.readLine();
            //문자열의 처음부터 끝까지 트리에 넣는다.
            for(int j = 0; j<str.length(); j++){
                //해당 알파벳을 가진 노드가 없다면
                if(!index.child.containsKey(str.charAt(j))){
                    //더이상 알아볼 필요가 없으므로, 다음 문자열을 탐방하러 간다.
                    continue exec;
                }
                index = index.child.get(str.charAt(j)); //인덱스를 해당 위치로 이동
            }
            //만약 문자열의 끝까지 트리에서 확인했다면, 이 문자열은 접두사 역할이 된다.
            result++;
        }

        //결과 출력
        System.out.println(result);
    }

}