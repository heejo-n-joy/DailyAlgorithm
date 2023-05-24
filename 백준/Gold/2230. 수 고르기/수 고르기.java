import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 수열에서 두 수를 고를 때, 차이가 M 이상이면서 제일 작은 경우를 구하기
 *
 * 아이디어 : 정렬 + 두 포인터
 * 1. 우선 수열을 오름차순으로 정렬한다.
 * 2. 두 포인터(left, right)를 활용하여 차례대로 값을 비교한다.
 * 2-1. 포인터의 값의 차이가 M 미만이라면, right를 1 증가한다.
 * 2-2. 포인터의 값의 차이가 M 이상이라면, 결과값을 갱신할지 비교한 후 left를 증가한다.
 * 3. 포인터가 수열 범위를 벗어나면, 종료한다.
 *
 *
 * - 결과값이 가질 수 있는 최소값은 M이다. 해당 케이스를 찾았다면 사실 계산을 더이상 하지 않아도 된다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());   //수열의 개수
        int M = Integer.parseInt(st.nextToken());   //유지해야 하는 최소값

        int[] array = new int[N];
        for(int i = 0; i<N; i++){
            array[i] = Integer.parseInt(br.readLine());
        }

        //1. 수열을 오름차순으로 정렬
        Arrays.sort(array);

        long result = 2000000000;    //이론상 가능한 최대 차이

        //값 비교에 사용될 두 포인터
        int left = 0;
        int right = 1;

        //2. 값 비교
        while(right < N){
            //left, right가 역전됐다면
            if(left >= right){
                right++;    //right를 1증가한다.
                continue;
            }

            //만약 차이가 M보다 작다면 패스하기
            if((long) array[right]-array[left] < M){
                right++;    //right를 1 증가한다.
            }
            //차이가 M 이상이라면 결과 반영하기
            else{
                result = Math.min((long) array[right] - array[left], result);   //값을 비교해보고 갱신하기
                left++; //left 1 증가
            }
        }

        //결과 출력
        System.out.println(result);
    }

}