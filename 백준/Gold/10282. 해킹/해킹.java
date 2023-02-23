import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - yum3이 해킹을 했다! 의존하는 컴퓨터들은 하나 둘 전염되기 시작하는데..
 * <p>
 * 요구하는 출력
 * - 총 감염되는 컴퓨터 수, 마지막 컴퓨터가 감염되는 시간을 출력
 * <p>
 * 전략
 * - 다익스트라를 배웠으니, 이를 활용해서 풀어보자
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        final int MAX_VALUE = 10000001;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());    //test case
        for(int test_case = 1; test_case<=T; test_case++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());   //컴퓨터 개수
            int d = Integer.parseInt(st.nextToken());   //의존성 개수
            int c = Integer.parseInt(st.nextToken()) -1;   //해킹당한 컴퓨터 번호
            List<int[]>[] computers = new List[n];
            for(int i =0; i<n; i++){
                computers[i] = new ArrayList<>();
            }
            for(int i = 0; i<d; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) -1;   //to 컴퓨터
                int b = Integer.parseInt(st.nextToken()) -1;   //from 컴퓨터
                int s = Integer.parseInt(st.nextToken());   //감염 전파에 걸리는 시간
                computers[b].add(new int[]{a, s});
            }
            int[] computers_time = new int[n];
            boolean[] computers_infected = new boolean[n];
            for(int i =0; i<n; i++){
                computers_time[i] = MAX_VALUE; //최대값으로 초기화 (최소값을 구하기 위해)
            }
            computers_time[c] = 0;  //시작 컴퓨터에서는 감염까지 0의 값을 가진다.
            //감염 과정
            for(int j = 0; j<n; j++){
                //computers_time에서 확인 안한 곳 & 가장 소요 시간이 낮은 곳을 공략
                int computer_index = min(computers_infected, computers_time, n);
                computers_infected[computer_index] = true;
                //해당 위치에서 걸리는 다른 위치들을 넣기
                for(int i =0; i<computers[computer_index].size(); i++){
                    int to = computers[computer_index].get(i)[0];
                    int time = computers[computer_index].get(i)[1];
                    //두 수를 비교하고, 더 적은 시간을 반영한다.
                    // 1. 출발 c에서 computer_index, computer_index에서 i까지의 시간
                    int optionA = computers_time[computer_index] + time;
                    // 2. 기존에 저장된 i까지의 최단 시간
                    int optionB = computers_time[to];
                    //더 작은 시간 반영
                    computers_time[to] = Math.min(optionA, optionB);
                }
            }
            //감염 결과 확인
            int result_total_count = 0;
            int result_max_time = 0;
            for(int i =0; i<n; i++){
                if(computers_time[i] != MAX_VALUE){
                    result_total_count++;
                    result_max_time = Math.max(computers_time[i], result_max_time);
                }
            }

            //결과 출력
            System.out.println(result_total_count + " " + result_max_time);
        }
    }

    public static int min(boolean[] computers_infected, int[] computers_time, int n){
        int result_computer_index = -1;
        for(int i =0; i<n; i++){
            if(computers_infected[i]){
                continue;
            }
            if(result_computer_index == -1){
                result_computer_index = i;
            }
            else{
                if(computers_time[i] < computers_time[result_computer_index]){
                    result_computer_index = i;
                }
            }
        }
        return result_computer_index;
    }
}