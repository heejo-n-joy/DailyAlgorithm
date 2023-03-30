import java.util.Scanner;

/**
 * 요구하는 출력
 * - 특정한 문자열과 범위, 알파벳이 주어지면 총 몇 번 등장하는지 출력하기
 *
 * 풀이 방법
 * - 입력을 받으면서, 0번째부터 각 위치까지의 알파벳 등장 횟수를 저장해둔다.
 * - 범위 (l,r)이 주어지면 (0, r)까지의 a 개수에서 (0, l-1)까지의 a의 개수를 뺀다.
 *
 * 시간복잡도
 * - 문자별 등장 횟수 저장 : 200000 x 26
 * - 계산 : 2000 x 2
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String str = sc.next(); //주어진 문자열
        int len = str.length(); //주어진 문자열의 길이
        int[][] alphabets = new int[len][26];
        for(int i = 0; i<len; i++){
            alphabets[i][str.charAt(i)-'a'] = 1;    //시작 알파벳
            if(i > 0) {
                //이전 j의 정보들을 그대로 가져온다.
                for (int j = 0; j < 26; j++) {
                    alphabets[i][j] += alphabets[i-1][j];
                }
            }
        }

        //질문 별 답을 한다.
        int q = sc.nextInt();
        for(int i = 0; i<q; i++){
            char a = sc.next().charAt(0);
            int l = sc.nextInt();
            int r = sc.nextInt();

            //처음부터 시작하는게 아니라면
            if(l > 0){
                //l - 1까지는 빼줘야 한다.
                sb.append(alphabets[r][a - 'a'] - alphabets[l - 1][a - 'a']);
            }
            //처음부터 시작하는거라면 뺄 필요가 없다.
            else{
                sb.append(alphabets[r][a - 'a']);
            }
            sb.append('\n');
        }
        //결과 출력
        System.out.println(sb.toString());
    }
}