import java.util.Scanner;

/**
 * 요구하는 출력
 * - 소수 화폐를 이용하여 N원을 지불할 수 있는 경우의 수
 * <p>
 * 이 문제는 어떻게 풀까? DP로 풀자
 * - N의 범위인 2원부터 40000원까지를 전부 확인한다.
 * - 확인하는 방식은 2차원으로 진행
 *
 * 회고
 * - DP를 2차원으로 만들었더니 메모리 초과가 발생했다.
 * - 1차원으로 변경하자
 * - 이번에는 시간초과...
 * - 코드 리팩토링(소수 판별 후 패스를 빠르게 처리, 소수 판별시 sqrt 사용)으로 해결했다.
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] DP = new int[N+1];
        for(int currentMoney =2; currentMoney<=N; currentMoney++){
            //currentMoney가 소수인가?
            boolean primeNumber = isPrimeNumber(currentMoney, DP);
            //소수가 아니라면 패스
            if(!primeNumber){
                continue;
            }
            //만약 소수라면
            for(int sumMoney = 2; sumMoney<=N; sumMoney++){
                if(sumMoney-currentMoney>=0) {
                    DP[sumMoney] = (DP[sumMoney] + DP[sumMoney - currentMoney]) % 123456789;   //해당 소수 화폐를 이전 결과들에 추가로 도입한다.
                }
            }
        }

        //결과 출력
        System.out.println(DP[N]);
    }

    public static boolean isPrimeNumber(int num, int[] DP){
        int sqrt = (int) Math.sqrt(num);
        for(int i = 2; i<=sqrt; i++){
            //나누어 떨어지는 존재가 있다면 num은 소수가 아니다
            if(num % i == 0){
                return false;
            }
        }
        //나누어 떨어지지 않았을 때 num은 소수이다.
        DP[num] += 1;   //해당 자리에 1을 체크한다.
        return true;
    }
}