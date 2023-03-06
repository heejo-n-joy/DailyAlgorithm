import java.util.Scanner;

/**
 * 요구하는 출력
 * - N개의 파일들이 들어있는 폴더에서 정렬된 파일들 중 앞의 50개의 파일들의 이름을 출력하라
 * <p>
 * 이 문제는 어떻게 풀까?
 * - dfs로 풀어보자
 * - 숫자 N을 받으면 앞에서부터 하나씩 만들어보자.
 *  1. 숫자 x는 0~9까지. 맨 처음에는 1부터 시작한다. 이 숫자가 가능한가?
 *  2-1. 가능한 경우에는 출력을 하면서 숫자를 10배로 늘린다.
 *  2-2. 불가능한 경우에는 무시하고 리턴
 *  3. 이렇게 50개가 채워지면 바로 종료 (50개 전에 dfs가 끝날 때도 있다)
 *- N의 최대값이 1,000,000,000 (10억) 이니 int 사용하자
 * @author HeejoPark
 */
class Solution {
    static StringBuilder sb;
    static int total_count;
    
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for(int test_case = 1; test_case <=T; test_case++){
            int N = sc.nextInt();
            total_count = 0;
            sb = new StringBuilder();
            sb.append("#"+test_case +" ");
            //실행
            for(int i = 1; i<=9; i++) {
                exec_print_file_names(i, N);
            }
            //결과 출력
            System.out.println(sb.toString());
        }
    }
    public static void exec_print_file_names(long current_number, int N){
        //50개를 찾았다면 종료
        if(total_count == 50){
            return;
        }
        //현재 숫자가 N보다 크면 패스
        if (current_number > N) {
            return;
        }

        //출력
        sb.append(current_number +".png ");
        total_count++;

        //다음 숫자로 이동
        for(int i = 0; i<=9; i++){
            exec_print_file_names(current_number * 10 + i, N);
        }
    }
}