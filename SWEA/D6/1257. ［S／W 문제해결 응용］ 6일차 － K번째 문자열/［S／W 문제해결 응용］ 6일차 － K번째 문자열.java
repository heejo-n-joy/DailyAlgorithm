import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * No.42 : K번째 문자열
 *
 * 문제 내용
 * - 41번과 거의 비슷한 문제. 다른 점이 있다면, 접미어가 아닌 문자열의 모든 연속된 부분집합들 중 K번째 순서를 구하는 것이다.
 * - 이 때, 중복은 제거된다.
 *
 * 전략
 * - trie를 활용하여 접근해보자
 * - 기존의 접미어에서 연속된 부분집합들로 변경하고, 사전 순서대로 확인해봤지만 K번째가 존재하지 않는다면 none을 출력한다.
 *
 * @author HeejoPark
 */

class Trie042{
    char alphabet;
    boolean isWordEnd;
    Map<Character, Trie042> children = new HashMap<Character, Trie042>();

    Trie042(char alphabet){
        this.alphabet = alphabet;
    }

    Trie042(){
    }

}
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            Trie042 head = new Trie042();
            K = sc.nextInt();   //찾아야하는 K번째 문자열
            String words = sc.next();   //주어진 문자열
            int len = words.length();
            for(int i = 0; i<len; i++){
                for(int k =i; k<len; k++) {
                    Trie042 indexTrie = head;
                    for (int j = i; j <= k; j++) {
                        char alphabet = words.charAt(j);
                        //indexTrie가 chatAt(i) 알파벳의 노드를 가지고 있는지 체크
                        if (indexTrie.children.containsKey(alphabet)) {
                            //있으면 그곳으로 이동
                        } else {
                            //없으면 노드를 새로 만들기
                            Trie042 newTrie = new Trie042(alphabet);
                            indexTrie.children.put(alphabet, newTrie);
                        }
                        indexTrie = indexTrie.children.get(alphabet);
                    }
                    //해당 위치는 단어의 끝임을 체크
                    indexTrie.isWordEnd = true;
                }
            }
            //K번째 단어를 찾기
            results = new char[len];
            index = 0;
            dfs(head, 0, test_case);
            if(index < K){
                result("none", test_case);
            }
        }
    }

    static char[] results;
    static int index;
    static int K;

    public static void dfs(Trie042 trie042, int depth, int test_case){
        if(index >= K){
            return;
        }
        //isWordEnd먼저 확인
        if(trie042.isWordEnd){
            index++;
            if(index == K){
                String result = "";
                for(int i = 0; i<depth; i++){
                    result += results[i];
                }
                result(result, test_case);
                return;
            }
        }
        //하나씩 확인
        for(char i = 'a'; i<='z'; i++){
            if(trie042.children.containsKey(i)){
                results[depth] = i;
                dfs((trie042.children.get(i)), depth+1, test_case);
                results[depth] = '_';
            }
        }
    }

    //결과 출력
    public static void result(String str, int test_case){
        System.out.println("#" + test_case + " " + str);
    }
}