import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * #이분탐색연습
 *
 * 요구하는 출력 : 가장 인접한 두 공유기 사이의 거리를 최대로 할 수 있는 거리
 *
 * 풀이 방법 : 이분탐색
 * - 공유기 사이의 거리를 가지고 이분 탐색을 진행한다.
 * - 1. 임의의 거리를 정한다.
 * - 2. 해당 거리 이상의 거리를 유지하며 공유기를 설치한다.
 * - 3. 공유기 개수가 C개 이상이 된다면 거리를 늘리고, C개 미만이 된다면 거리를 줄인다.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(st.nextToken());   //집의 개수
        int C = Integer.parseInt(st.nextToken());   //공유기 개수
        int[] house = new int[N];
        for(int i = 0; i<N; i++){
            house[i] = Integer.parseInt(bufferedReader.readLine());
        }

        //집을 일단 정렬한다.
        Arrays.sort(house);

        //이분 탐색을 진행하자
        int result = execBinarySearch(1, (1 + house[N-1] - house[0]) / 2, house[N-1] - house[0], N, C, house);

        //결과 출력
        System.out.println(result);
    }

    public static int execBinarySearch(int start, int current, int end, int N, int C, int[] house){
        int result = 0;
        while(true){
            //만약 start가 end보다 크다면 종료
            if(start > end){
                break;
            }

            //계산을 진행
            int count = 1;
            int prev_wifi_house_index = 0;
            for(int i = 1; i<N; i++){
                //거리가 충족되면
                if(house[i] - house[prev_wifi_house_index] >= current){
                    //공유기를 달자
                    prev_wifi_house_index = i;
                    count++;
                }
            }

            //개수가 C보다 작으면
            if(count < C){
                //end를 반토막 내자
                end = current - 1;
                current = (start + end) / 2;
            }
            //개수가 C 이상이라면
            else{
                result = Math.max(current, result);
                start = current + 1;
                current = (start + end) / 2;
            }
        }
        //최대 거리 리턴
        return result;
    }
}