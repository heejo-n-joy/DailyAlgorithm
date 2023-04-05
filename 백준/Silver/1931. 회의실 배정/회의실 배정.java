import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 회의실에 최대로 사용할 수 있는 회의의 최대 개수
 *
 * 풀이 방법 : 정렬
 * 1. 회의 시간이 끝나는 순(끝나는 시간이 같으면 시작 시간의 내림차순)으로 오름차순 정렬을 한다.
 * 2. 하나씩 보면서 (시간을 보내면서) 가능하면 넣고, 못하면 패스한다.
 */

class Conference{
    int start_time;
    int end_time;
    Conference(int start_time, int end_time){
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //자연수 N
        Conference[] conferences = new Conference[N];
        for(int i = 0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            conferences[i] = new Conference(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
        }

        //정렬
        Arrays.sort(conferences, (o1, o2) -> {
            if(o1.end_time == o2.end_time){
                //시작 시간을 기준으로 오름차순
                return o1.start_time - o2.start_time;
            }
            else{
                //끝 시간을 기준으로 오름차순
                return o1.end_time - o2.end_time;
            }
        });

        //계산
        int result = 0;
        int current_end_time = -1;
        for(int i = 0; i<N; i++){
            //현재의 끝나는 회의 시간 이후 회의가 시작된다면
            if(conferences[i].start_time >= current_end_time){
                //반영하기
                result++;
                current_end_time = conferences[i].end_time;
            }
        }

        //결과 출력
        System.out.println(result);
    }
}