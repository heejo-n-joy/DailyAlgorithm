import java.util.*;

/**
 * 요구하는 출력
 * - 해빈이가 짜장면을 만드는 최소 요리 횟수
 * <p>
 * 이 문제는 어떻게 풀까? DP
 * - 웍을 1개, 혹은 2개로 만들 수 있는 짜장면 양을 미리 계산해둔다.
 * - 해당 웍을(1개 혹은 2개를) 사용할 때 횟수를 1 증가시킨다.
 * - 만들어야 하는 짜장면의 양을 1부터 N까지 차례로 증가시키며 최소값을 갱신한다.
 *
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] woks_input = new int[M + 1];  //woks의 입력
        boolean[] woks = new boolean[N + 1];  //woks의 정보
        int[] DP = new int[N + 1];    //1부터 N까지의 웤을 활용한 수

        for (int i = 0; i <= N; i++) {
            DP[i] = 10001;
        }

        for (int i = 0; i < M; i++) {
            woks_input[i] = sc.nextInt();
        }

        //woks를 두개 묶어서 하나로 쳐보자
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0 ; i<M; i++){
            for(int j = 0; j<=M; j++){
                if(i == j){
                    continue;
                }
                queue.offer(woks_input[i] + woks_input[j]);
            }
        }
        while(!queue.isEmpty()){
            int wok = queue.poll();
            if(wok <= N) {
                woks[wok] = true;
                DP[wok] = 1;
            }
        }

        //짜장면을 하나씩 만들어보자
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                if (woks[j]) {
                    if (i - j > 0) {
                        DP[i] = Math.min(DP[i], DP[i - j] + 1);
                    }
                }
            }
        }

        //결과 출력
        if(DP[N] == 10001){
            System.out.println(-1);
        }
        else {
            System.out.println(DP[N]);
        }
    }
}