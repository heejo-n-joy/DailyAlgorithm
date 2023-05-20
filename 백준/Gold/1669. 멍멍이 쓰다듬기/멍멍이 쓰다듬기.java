import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 요구하는 출력 : 원숭이의 키를 멍멍이만큼 늘리는 최소일수
 *
 * 아이디어 : 수학
 * - 1에서 시작해서 1로 끝나야 한다. 즉, 최대 치 n을 찍고 내려오는 방식
 * - 1 + 2 + ... + n + ... + 1이 되기 때문에 n(n+1)/2 + n(n+1)/2 - n의 값을 가지게 된다. (n은 한 번만 찍는다고 가정)
 * - 그리고 정체된 구간이 있을 수 있는데, n으로 나눠서 며칠이 필요한지 체크하자.
 *
 * 풀이과정
 * 1. n^2 <= 키의 차이를 성립하는 n의 최대값을 구한다. (2n-1일)
 * 2. 이후 남은 키 차이가 존재한다면 키 차이 <= n*x를 만족하는 최소 x를 구한다. (x일)
 * 3. 정답은 2n-1 + x일이 된다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(st.nextToken());   //원숭이
        int Y = Integer.parseInt(st.nextToken());   //멍멍이

        //실행
        int result = calc(X, Y);

        //결과 출력
        System.out.println(result);
    }

    public static int calc(int X, int Y){
        int diff = Y - X;   //둘의 키 차이

        //키가 동일하다면 0을 리턴한다.
        if(diff == 0){
            return 0;
        }

        //1. n^2 - n<=diff를 만족하는 최대 n을 찾는다.
        int n = 0;
        while(true){
            if((long) n * n <= diff){
                n++;
            }
            else{
                n--;
                break;
            }
        }

        //2. 남은 diff에서 diff<=n*x를 만족하는 최소 x를 구한다.
        diff -= n * n;

        int x = 0;
        while(true){
            if(diff <= n * x){
                break;
            }
            else{
                x++;
            }
        }

        //3. 결과 출력
        return 2 * n -1 + x;
    }
}