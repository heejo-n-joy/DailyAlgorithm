import java.util.Scanner;

public class Main {
    static int DP[];
    static int array[];
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int N = scan.nextInt();
        DP = new int[N + 1];
        array = new int[N + 1];

        for(int i = 1; i <= N; i++) {
            array[i] = scan.nextInt();
            DP[i] = -1; //초기화
        }

        DP[0] = array[0];
        DP[1] = array[1];

        if(N >= 2) {
            DP[2] = array[1] + array[2];
        }
        //실행
        int result = calc(N);
        
        //결과 출력
        System.out.println(result);

    }

    static int calc(int N) {
        // DP에 값이 들어가있지 않다면
        if(DP[N] == -1) {
            //최대값을 찾고 현재 계단의 점수를 더한다
            DP[N] = Math.max(calc(N - 2), calc(N - 3) + array[N - 1]) + array[N];
        }

        //값이 들어있다면, 해당 값 출력
        return DP[N];
    }
}