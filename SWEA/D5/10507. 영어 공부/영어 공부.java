import java.util.Scanner;

/**
 * No.36 : 영어 공부
 *
 * 문제 내용
 * - 수림이가 영어 공부를 한다.
 * - 연속 공부 기간 중 최대를 만들고 싶어한다.
 * - 이 때, 해킹의 기회가 있어서 p개의 날짜동안은 안 한 날을 한 날로 체크할 수 있다.
 *
 * 전략
 * - 포인터를 이용했다.
 * - 시작 위치와 끝 위치를 확인한다.
 * - 계속해서 끝 위치는 오른쪽으로 이동하고, 해킹의 기회가 있으면 해킹을 한다.
 * - 더 오른쪽으로 가고 싶은데 기회가 없으면, 시작 위치를 한 칸 이동하고 해킹 기회를 만든다.
 * - 역대 최대일을 기억했다가 출력한다.
 *
 * 회고
 * - 코드상에 문제는 없는거같은데, 계속해서 2개의 테케가 틀렸다.
 * - 인터넷상의 다른 풀이법들을 보니 max_days의 기본값을 해킹 기회 + 1로 설정했다.
 * - 생각해보니 단 하루만 문제를 푸는 경우도 존재했다.. (n의 값이 1 이상이니, 최소 한 번은 문제를 풀었다)
 * @author HeejoPark
 */
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int maximum_date = 1000001;
        int T = sc.nextInt();   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            int n = sc.nextInt();   //공부를 한 날
            int p = sc.nextInt();   //해킹을 할 수 있는 기회
            boolean[] date = new boolean[maximum_date];
            int ending_date = 0;    //공부가 끝난 마지막 날
            for (int i = 0; i < n; i++) {
                int temp_date = sc.nextInt();
                date[temp_date] = true;
                if (i == n - 1) {
                    ending_date = temp_date;
                }
            }
            //실제 계산
            int start = 1;
            int max_days = p + 1;
            int cur_days = 0;
            int rest_hacking_count = p;
            for (int end = 1; end < maximum_date; end++) {
                //현재 위치로 end를 이동하고자 한다
                //현재 end가 공부를 실제로 했던 날이라면
                if (date[end]) {
                    //다음으로 넘어간다
                    //물론, max와 cur은 갱신을 해준다.
                    cur_days++;
                }
                //현재 end가 공부를 안했다면
                else {
                    //해킹 기회가 있다면
                    if (rest_hacking_count > 0) {
                        //해킹을 -1하고 다음으로 넘어간다.
                        rest_hacking_count--;
                        cur_days++;
                    }
                    //해킹 기회가 없다면
                    else {
                        //해킹을 쓴 start 위치를 찾는다.
                        while (date[start]) {
                            start++;
                        }
                        //start를 1 증가시키고, 해킹을 한다.
                        start++;
                        cur_days = end - start + 1;
                    }
                }
                max_days = Math.max(max_days, cur_days);
            }
            System.out.println("#" + test_case + " " + max_days);
        }
    }
}