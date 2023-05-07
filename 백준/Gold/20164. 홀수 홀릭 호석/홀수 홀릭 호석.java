import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 요구하는 출력 : 숫자를 나누고 합하는 과정에서 홀수의 개수가 최대 몇개인지 확인하기
 *
 * 풀이 방법 : 재귀
 * - 현재 숫자의 홀수 개수를 세기
 * - 숫자의 자리수가 세자리 이상이면 3개로, 두자리면 2개로 나누기
 *
 */

public class Main {
    static int max;
    static int min;
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //숫자 (최대 9자리이기 때문에 int 사용)

        max = 0;
        min = N;

        //실행
        exec(N, 0);


        //결과 출력
        System.out.println(min + " " + max);
    }

    public static void exec(int num, int count){
        //현재 숫자의 길이 체크하기
        int len = 0;
        while(num >= Math.pow(10, len)){
            len++;
        }

        //현재 홀수 개수를 세기
        int calc = 0;
        int temp = num;
        while(temp > 0){
            if((temp % 10) % 2 == 1){
                calc++;
            }
            temp /= 10;
        }

        //숫자를 나누기
        if(len >= 3){
            for(int i = 1; i<len; i++){
                for(int j = i + 1; j<len; j++){
                    //숫자 3개를 더하기
                    int A = (int) (num / Math.pow(10, len - i));
                    int B = (int) ((num % Math.pow(10, len - i)) / Math.pow(10, len - j));
                    int C = (int) (num % Math.pow(10, len - j));

                    int newNum = A + B + C;
                    exec(newNum, count + calc);
                }
            }
        }
        else if(len == 2){
            int newNum = num / 10 + num % 10;
            exec(newNum, count + calc);
        }
        else{
            //최대값, 최소값을 갱신하기
            max = Math.max(count + calc, max);
            min = Math.min(count + calc, min);
        }
    }
}
