import java.util.Scanner;

/**
 * 요구하는 출력
 * - N개의 영어 단어와 _을 합쳐 만드는 사전 순으로 가장 앞서는 단어
 *
 * 문제 풀이 방식
 * 1. needCount = M - (N개의 단어 길이 합) = '_'을 채워야 하는 개수
 * 2. averageCount = (N-1) / needCount = 단어 사이 들어가야 할 '_'의 평균 개수
 * 3. needCount - averageCount = 앞 부분에 들어가야 할 개수들
 *
 * @author HeejoPark
 */


class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   //단어의 개수
        int M = sc.nextInt();   //만들어야 하는 단어의 길이
        String[] words = new String[N];
        int input_words_total_len = 0;
        int rest_capital_letter_count = 0;  //첫글자가 대문자인 단어 개수
        int rest_small_letter_count = 0;    //첫글자가 소문자인 단어 개수
        for(int i = 0; i<N; i++){
            words[i] = sc.next();
            input_words_total_len += words[i].length();
            //첫단어는 필요없다
            if(i == 0){
                continue;
            }
            //첫글자가 소문자인지 대문자인지를 파악해 해당 변수 개수를 1 증가시킨다.
            if(words[i].charAt(0) >= 'a'){
                rest_small_letter_count++;
            }
            else{
                rest_capital_letter_count++;
            }
        }
        int needCount = M - input_words_total_len;  //필요한 '_'의 개수
        int averageCount = needCount / (N - 1); //needCount는 0이 될 일이 없다. (문제에서 나온 조건)
        int supplementCount = needCount - averageCount * (N - 1);   //보충이 필요한 부분들

//        System.out.println("need: " + needCount + ", word_len: " + input_words_total_len+", average: " + averageCount + ", supple: " + supplementCount);

        //남은'_'를 어디에 추가해야 할지 계산한다.
        //1. 알파벳 소문자는 가장 맨 앞 단어부터 붙인다.
        //2. 알파벳 대문자는 가장 맨 뒤 단어부터 붙인다.
        boolean[] add_here = new boolean[N-1];
        while(supplementCount > 0){
            //소문자 앞에는 이미 다 채웠다면
            if(rest_small_letter_count == 0){
                //대문자 단어들을 대상으로 뒤에서부터 추가하자
                for(int i = N-2; i>=0; i--){
                    if(add_here[i]){
                        continue;
                    }
                    if(words[i+1].charAt(0) <= 'z'){
                        add_here[i] = true;
                        supplementCount--;
                        rest_capital_letter_count--;
                        break;
                    }
                }
            }
            else{
                //소문자 단어들을 대상으로 앞에서부터 추가하자
                for(int i = 0; i<N-1; i++){
                    if(add_here[i]){
                        continue;
                    }
                    //소문자라면
                    if(words[i+1].charAt(0) >= 'a'){
                        add_here[i] = true;
                        supplementCount--;
                        rest_small_letter_count--;
                        break;
                    }
                }
            }
        }

        //하나의 단어로 합치기
        StringBuilder sb = new StringBuilder();
        sb.append(words[0]);    //맨 처음 단어 붙이기
        for(int i = 0; i<N-1; i++){
            //평균 개수만큼 _ 넣기
            for(int j = 0; j<averageCount; j++){
                sb.append('_');
            }
            //만약 추가가 필요하면 추가하기
            if(add_here[i]){
                sb.append('_');
            }
            //그 다음 단어 붙이기
            sb.append(words[i+1]);
        }

        //결과 출력
        System.out.println(sb.toString());
    }
}