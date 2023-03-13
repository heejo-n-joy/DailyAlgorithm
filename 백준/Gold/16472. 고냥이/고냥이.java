import java.util.Scanner;

/**
 * 요구하는 출력
 * - 귀여운 고양이의 말을 얼마까지 이해할 수 있을까? (최대 길이)
 * <p>
 * 이 문제는 어떻게 풀까? 투 포인터(start, end)를 활용하자
 * - N의 범위인 2원부터 40000원까지를 전부 확인한다.
 * - 확인하는 방식은 2차원으로 진행
 *
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String str = sc.next();

        //계산
        int result = calc(N, str);

        //결과 출력
        System.out.println(result);
    }

    public static int calc(int N, String str){
        int str_len = str.length();
        int[] alphabet_array = new int[26];  //알파벳 별 길이에 포함된 개수
        int current_alphabet_kind = 0;      //현재 알파벳 종류
        int current_length = 0;             //현재 해석 언어 길이
        int max_length = 0;                 //최대 해석 언어 길이
        int start_index = 0;
        for(int current_index =0; current_index<str_len; current_index++){

            //현재 인덱스의 알파벳이 이미 사용중인 알파벳인지 확인
            if(alphabet_array[str.charAt(current_index) - 'a'] > 0){
                //만약 그렇다면
            }
            else{
                //새로 추가해야 하는 상황이라면, 알파벳 종류를 초과하지는 않았는지 확인
                if(current_alphabet_kind < N){
                    current_alphabet_kind++;
                }
                else{
                    //기존의 알파벳을 하나 버리고, 새로 추가해야 한다.
                    while(true) {
                        //해당 알파벳이 0이 됐다면
                        current_length--;
                        alphabet_array[str.charAt(start_index) - 'a']--;
                        if (alphabet_array[str.charAt(start_index++) - 'a'] == 0) {
                            break;
                        }
                    }
                }
            }
            alphabet_array[str.charAt(current_index) - 'a']++;
            current_length++;
            max_length = Math.max(current_length, max_length);

        }
        return max_length;
    }
}