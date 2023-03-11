import java.util.*;

/**
 * 요구하는 출력
 * - 별 찍기
 * <p>
 * 이 문제는 어떻게 풀까?
 * - 재귀를 활용
 *
 * @author HeejoPark
 */

class Main {
    static final char STAR = '*';
    static final char SPACE = ' ';
    static StringBuilder sb;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        sb = new StringBuilder();
        int n = sc.nextInt();   //채우고 싶은 타일 배치의 가로 길이
        for(int i =0; i<n; i++){
            func(n, STAR, i);
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    public static void func(int print_char_count, char print_char, int row_index){
        //출력할 글자가 1개라면
        if(print_char_count == 1){
            sb.append(print_char);
            return;
        }
        //출력1. 기존 문자
        func(print_char_count/3, print_char, row_index % (print_char_count / 3));
        //출력2. 중간을 비워야 한다면
        if(row_index / (print_char_count / 3) == 1) {
            func(print_char_count / 3, SPACE,  row_index % (print_char_count / 3));
        }
        else{
            func(print_char_count / 3, print_char, row_index % (print_char_count / 3));
        }
        //출력3. 기존 문자
        func(print_char_count/3, print_char, row_index % (print_char_count / 3));
    }
}