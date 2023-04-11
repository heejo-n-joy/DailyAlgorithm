import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * #그리디알고리즘_특집
 *
 * 요구하는 출력 : +,-로 이루어진 식에 괄호를 쳐서 식의 값을 최소로 만들자
 *
 * 풀이 방법 1. 스택 or String.split(아마도 FM적인 방법?)
 * - 마이너스 부호를 기준으로 괄호를 치면, 최소값으로 만들 수 있다. ex)
 * - ex) a - b + c - d + e + f = > a - (b + c) - (d + e + f)
 *
 * 풀이 방법 2. 그냥 계산..
 * - 사실 일단 -가 나오는 순간, 뒤에 나오는 수와 부호들은 괄호를 이용해서 어떻게든 - 숫자로 만들어낼 수 있다.
 * - ex) a - b + c + d - e => a - (b+c+d) -e
 * - 그러니까 마이너스 부호가 나오기 전까지는 모든 수를 더하고, 부호가 나온 후부터는 나오는 수를 모두 뺀다.
 *
 * 그리디 알고리즘으로 풀이가 가능한 이유?
 * - 현재 선택(숫자와 부호)가 이후 선택(다음 부호와 숫자)에 영향을 주지 않는다.
 * - 마이너스 부호를 기준으로 나뉜 값들을 더하는 것이 최종 해결 방법이 된다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str_split = br.readLine().split("-");  //마이너스 부호를 기준으로 나누기.
        int[] str_split_sum = new int[str_split.length];      //나뉜 값들을 전부 더한 배열
        //나뉜 값들 안에 숫자들을 더한다.
        for(int i = 0; i < str_split.length; i++){
            //+ 부호가 있을 수 있으니 나누기
            String[] split_split = str_split[i].split("\\+");
            int sum = 0;
            for(int j = 0; j<split_split.length; j++){
                sum += Integer.parseInt(split_split[j]);
            }
            str_split_sum[i] = sum; //모두 더한 값을 넣는다.
        }

        //괄호별로 합해진 숫자들을 뺀다.
        int total_sum = str_split_sum[0];
        for(int i = 1; i< str_split_sum.length; i++){
            total_sum -= str_split_sum[i];
        }

        //결과 출력
        System.out.println(total_sum);
    }
}