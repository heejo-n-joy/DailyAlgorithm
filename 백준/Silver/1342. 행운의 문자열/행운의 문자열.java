import java.util.Scanner;

/**
 * 요구하는 출력
 * - 문자열을 재배치해서 '행운의 문자열(인접한 모든 문자들이 서로 다른 문자열)'이 되는 경우의 수
 *
 * 풀이 방법
 * - 문자들을 알파벳 배열에 개수를 저장한다.
 * - 남은 알파벳들을 하나씩 돌아가며, 남은 카운트 개수가 존재하는 알파벳 & 이전에 선택된 문자와 중복되지 않은 알파벳을 선택한다.
 * - 문자열이 완성되면 최종 개수에 1을 증가한다.
 *
 * 시간복잡도
 * - O(n!) (단 n은 최대 10)
 */

class Alphabet{
    char alphabet;
    int count;
    Alphabet(char alphabet, int count){
        this.alphabet = alphabet;
        this.count = count;
    }
}
public class Main {
    static int result;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] input_str = sc.nextLine().toCharArray();   //문자를 입력받는다.
        int len = input_str.length; //입력받은 문자열의 길이

        //문자열에 사용된 알파벳 별 사용 개수
        int[] alphabets = new int[26];
        for(int i = 0; i<len; i++){
            alphabets[input_str[i] - 'a']++;
        }

        //문자열에 사용된 알파벳의 종류
        int alphabets_count = 0;
        for(int i = 0; i<alphabets.length; i++){
            if(alphabets[i] > 0){
                alphabets_count++;
            }
        }

        //사용된 알파벳과 사용 개수만을 정리한 배열 (실행에 직접적으로 사용된다)
        int[][] used_alphabets = new int[alphabets_count][2];   //[0]은 알파벳, [1]은 사용된 개수를 의미
        int current_index = 0;
        for(int i = 0; i<alphabets.length; i++){
            if(alphabets[i] > 0){
                used_alphabets[current_index][0] = i;
                used_alphabets[current_index][1] = alphabets[i];
                current_index++;
            }
        }

        int[] current_str = new int[len];
        result = 0;

        //실행
        permutation(0, len, current_str, used_alphabets);

        //결과 출력
        System.out.println(result);
    }

    public static void permutation(int count, int len, int[] current_str, int[][] used_alphabets){
        if(count == len){
            result++;
            return;
        }

        //알파벳 종류를 선택
        for(int i = 0; i < used_alphabets.length; i++){
            //해당 알파벳의 잔여 개수가 0이라면 패스
            if(used_alphabets[i][1] == 0){
                continue;
            }

            //만약 처음으로 추가하는 과정이 아니고
            if(count > 0){
                //직전 문자와 지금 넣으려는 문자가 같은 문자라면 패스
                if(current_str[count-1] == used_alphabets[i][0]){
                    continue;
                }
            }

            //추가하기
            current_str[count] = used_alphabets[i][0];
            used_alphabets[i][1]--;
            permutation(count+1, len, current_str, used_alphabets);
            used_alphabets[i][1]++;
        }
    }
}