import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 센서들을 커버하는 집중국들을 설치할 때, 집중국 구역별 거리의 합의 최솟값
 *
 * 아이디어 : 그리디 + 정렬
 * - 센서들을 K개 구역으로 나누는 것이기 때문에, 센서 간 거리가 제일 큰 곳들을 기준으로 나눠야 한다. (오름차순 정렬)
 * 1. 센서간 거리를 내림차순 정렬한다.
 * 2. K-1개 선택을 한다.
 * 3. 나눠진 구역들의 합을 구한다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //센서의 개수
        int K = Integer.parseInt(br.readLine());    //집중국의 개수

        //센서 입력받기
        int[] sensors = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i<N; i++){
            sensors[i] = Integer.parseInt(st.nextToken());
        }

        //센서 정렬하기
        Arrays.sort(sensors);

        //센서들을 기준으로 거리 차이 구하기
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<>(){
            @Override
            public int compare(int[] o1, int[] o2){
                return o2[0] - o1[0];
            }
        });
        for(int i = 0; i<N-1; i++){
            priorityQueue.offer(new int[]{sensors[i+1] - sensors[i], i});   //다음 센서와의 거리
        }

        //해당 거리 차이에서 K-1개만큼 집중국 구하기
        PriorityQueue<Integer> priorityQueue1 = new PriorityQueue<>();
        for(int i = 0; i<K-1; i++){
            if(priorityQueue.isEmpty()){
                break;
            }
            priorityQueue1.offer(priorityQueue.poll()[1]);
        }

        //나눠진 구역별 거리를 합한다.
        int result = 0;
        int start_index = 0;
        int end_index;
        while(!priorityQueue1.isEmpty()){
            end_index = priorityQueue1.poll();
            result += sensors[end_index] - sensors[start_index];
            start_index = end_index + 1;
        }

        //마지막 구역 더해주기
        end_index = N-1;
        result += sensors[end_index] - sensors[start_index];

        //결과 출력
        System.out.println(result);

    }

}