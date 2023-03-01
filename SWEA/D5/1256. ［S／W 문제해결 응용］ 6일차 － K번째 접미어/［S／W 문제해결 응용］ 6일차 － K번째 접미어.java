import java.util.*;

/**
 * No.41 : K번째 접미어
 *
 * 문제 내용
 * - 문자열의 접미어들을 사전순으로 나열했을 때, K번째 접미어를 출력하라
 *
 * 전략
 * - trie를 활용하여 접근해보자
 * - 모든 접미어들을 집어넣고, 사전순으로 찾아가자
 *
 * @author HeejoPark
 */

class Trie041{
    char alphabet;
    boolean isWordEnd;
    Map<Character, Trie041> children = new HashMap<Character, Trie041>();

    Trie041(char alphabet){
        this.alphabet = alphabet;
    }

    Trie041(){
    }

}
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            Trie041 head = new Trie041();
            K = sc.nextInt();   //찾아야하는 K번째 문자열
            String words = sc.next();   //주어진 문자열
            int len = words.length();
            if(K > len){
                result("none", test_case);
                continue;
            }
            for(int i = 0; i<len; i++){
                Trie041 indexTrie = head;
                for(int j = i; j<len; j++){
                    char alphabet = words.charAt(j);
                    //indexTrie가 chatAt(i) 알파벳의 노드를 가지고 있는지 체크
                    if(indexTrie.children.containsKey(alphabet)){
                        //있으면 그곳으로 이동
                    }
                    else {
                        //없으면 노드를 새로 만들기
                        Trie041 newTrie = new Trie041(alphabet);
                        indexTrie.children.put(alphabet, newTrie);
                    }
                    indexTrie = indexTrie.children.get(alphabet);
                }
                //해당 위치는 단어의 끝임을 체크
                indexTrie.isWordEnd = true;
            }
            //K번째 단어를 찾기
            results = new char[len];
            index = 0;
            dfs(head, 0, test_case);
        }
    }

    static char[] results;
    static int index;
    static int K;

    public static void dfs(Trie041 trie041, int depth, int test_case){
        if(index >= K){
            return;
        }
        //isWordEnd먼저 확인
        if(trie041.isWordEnd){
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
            if(trie041.children.containsKey(i)){
                results[depth] = i;
                dfs((trie041.children.get(i)), depth+1, test_case);
                results[depth] = '_';
            }
        }
    }

    //결과 출력
    public static void result(String str, int test_case){
        System.out.println("#" + test_case + " " + str);
    }
}