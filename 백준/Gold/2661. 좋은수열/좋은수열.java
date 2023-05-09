import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 요구하는 출력 : N자리의 좋은 수열 중 최소값을 출력하라
 *
 * 풀이 방법 : 구현
 * - Array의 index 위치에 1,2,3을 순차적으로 넣어보자.
 * - 1. 해당 숫자를 포함하는 나쁜 수열이 존재하는지 확인한다.
 * - 2. 나쁜 수열이 존재한다면 다음 숫자로 이동한다.
 * - 3. 좋은 수열이 만들어졌다면, 다음 index로 이동한다.
 * - 이렇게 N개의 좋은 수열이 완성되었다면, 실행하던 모든 과정을 중단하고, 결과를 출력한다.
 *
 */

public class Main {

    static boolean isFound;
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //숫자의 길이

        int[] result = new int[N];
        exec(0, N, result);

        //결과 출력
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<N; i++){
            sb.append(result[i]);
        }
        System.out.println(sb.toString());
    }

    public static void exec(int index, int N, int[] array){
        //이미 구한 값이 있다면
        if(isFound){
            //모두 종료하기
            return;
        }

        //좋은 수열을 완성했다면 종료
        if(index == N){
            isFound = true;
            return;
        }

        //그게 아니라면 좋은 수열을 구해보자
        rotate: for(int number = 1; number<=3; number++){
            //이미 좋은 수열을 완성했다면 종료
            if(isFound){
                return;
            }


            //해당 number를 가졌을 때, 좋은 수열이 되는지 체크
            array[index] = number;
            for(int i = 1; i<= (index+1)/2; i++){
                boolean isBadArray = true;
                for(int j = 0; j < i; j++){
                    //값이 일치하지 않는다면
                    if(array[index - j] != array[index - j - i]){
                        isBadArray = false;
                        break;
                    }
                }
                //만약 나쁜 수열임을 찾았다면
                if(isBadArray){
                    //해당 숫자는 사용 못한다.
                    continue rotate;
                }
            }

            //현재까지 좋은 수열이 된다면, 해당 값을 넣고 다음 index로 이동한다.
            exec(index+1, N, array);
        }
    }
}
