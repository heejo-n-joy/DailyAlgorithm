import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 두 수의 최대공약수와 최소공배수를 활용해 두 수를 찾기
 *
 * 요구하는 출력
 * - 최대공약수와 최소공배수를 만족하는 두 수
 *
 * 주의사항
 * - 쌍이 여러개라면 두 수의 합이 최소인 수를 출력하기
 * - 수는 작은 수부터 출력
 * - 성립이 안되는 경우에는 어떻게 해야하지..?
 *
 * 전략
 * - 최대공약수를 A, 최소공배수를 B라고 한다면 두 수를 A * x, A * y라 할 때 최소공배수는 A * x * y가 된다.
 * - 그렇다면 B에서 A를 나누면 나오는 수는 x * y가 된다.
 * - xy가 될 수 있는 x와 y들을 찾고, 최소값을 비교해 결과를 출력하자.
 *
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());    //최대공약수
        int B = Integer.parseInt(st.nextToken());    //최소공배수
        int xy = B / A;
        int x = 0;
        int y = 0;
        int sum = Integer.MAX_VALUE;
        findNum : for(int i = 1; i<=Math.sqrt(xy); i++){
            if(xy % i == 0){   //만약 xy를 i로 나눴을 때 나머지가 없다면, 이는 나누어 떨어짐을 의미
                int tempX = i;
                int tempY = xy / i;
                //만약 tempX와 tempY가 서로소가 아니라면 패스
                for(int j = 2; j<=tempX; j++){
                    if(tempX % j == 0 && tempY % j == 0){
                        continue findNum;
                    }
                }
                //두 수의 합이 최소값인 경우 갱신
                if(tempX + tempY < sum){
                    x = tempX;
                    y = tempY;
                }
            }
        }
        x *= A;
        y *= A;
        System.out.println(x + " " + y);
    }
}