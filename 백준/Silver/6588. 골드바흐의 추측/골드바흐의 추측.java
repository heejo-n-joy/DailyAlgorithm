import java.util.Scanner;

/**
 * 요구하는 출력
 * - 주어진 짝수 n 이 홀수 소수의 합으로 나타낼 수 있는지 판별
 *
 * 전략
 * - a + b의 형태로 a와 b를 구분한다.
 * - a <= b가 될 때까지, a에 3부터 홀수를 하나씩 넣는다.
 * - a와 b 모두 소수인지 판별한다.
 *
 * @author HeejoPark
 */
public class Main {
    static final int MAX_N_NUMBER = 1000001;
    public static void main(String[] args) throws Exception {
        short[] primeNumber = new short[MAX_N_NUMBER];
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while(true) {
            int n = sc.nextInt();
            if(n == 0){
                break;
            }
            String result = calc(n, primeNumber);
            sb.append(result + "\n");
        }
        System.out.println(sb.toString());
    }

    public static String calc(int n, short[] primeNumber){
        int a = 3;
        int b = n - a;
        while(a<=b){
            //둘 다 소수라면
            if(isPrimeNumber(a, primeNumber) && isPrimeNumber(b, primeNumber)){
                //형식에 맞춰 리턴
                return n + " = " + a + " + " + b;
            }
            //다음 숫자를 찾으러 가기
            a +=2;
            b -=2;
        }
        //찾지 못했다면 형식에 맞춰 리턴
        return "Goldbach's conjecture is wrong.";
    }

    public static boolean isPrimeNumber(int n, short[] primeNumber){
        if(primeNumber[n] == 1){    //만약 1이라면(소수라면)
            return true;
        }
        else if(primeNumber[n] == -1){  //만약 -1이라면(소수가아니라면)
            return false;
        }
        for(int i = 2; i<=Math.sqrt(n); i++){
            if(n % i == 0){
                primeNumber[n] = -1;    //소수가 아님을 표시
                return false;
            }
        }
        primeNumber[n] = 1; //소수임을 표시
        return true;
    }
}