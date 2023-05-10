import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 요구하는 출력 : N명의 아이들이 임시로 줄을 서 있을 때, 번호 순서대로 배치하기 위해 옮겨지는 아이의 최소 수
 *
 * 풀이 방법 : DP
 * - 아이들을 최소의 수로 옮기기 위해선, 현재 임시 줄에서 순서대로 선 아이들이 최대 몇 명인지를 알아야 한다.
 * - 각 DP에는 현재 값을 오름차순에 포함한 최대 값을 가지고 있다.
 * - 즉, DP[i]를 알아본다면 DP[0]부터 DP[i-1]까지 전부 확인을 해본다.
 *  - 이 때 조건은 data[i]값이 더 커야 한다는 것이다. (그래야 오름차순이 진행되기 때문)
 * - 계산이 모두 끝나면, DP의 값들 중 최대값을 찾아 N에서 빼 출력한다.
 * 
 * 시간복잡도 : O(N^2)
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //숫자의 길이

        int[] data = new int[N];
        int[] DP = new int[N];
        for(int i = 0 ; i<N; i++){
            data[i] = Integer.parseInt(bufferedReader.readLine());
            DP[i] = 1;  //DP의 기본값은 1이다. (오름차순이 자기 자신에서 출발)
        }
        
        //DP 계산
        for(int i = 1; i<N; i++){
            for(int j = 0; j<i; j++){
                if(data[i] > data[j]) {
                    DP[i] = Math.max(DP[i], DP[j] + 1);
                }
            }
        }
        
        //최대값 찾기
        int max = 0;
        for(int i = 0;  i<N; i++){
            max = Math.max(DP[i], max);
        }
        
        //결과 출력
        System.out.println(N - max);
    }
}
