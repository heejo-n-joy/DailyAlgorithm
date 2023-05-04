import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 요구하는 출력 : 회의에 필요한 회의실 수
 *
 * 풀이 방법 : 우선순위 큐
 * 1. 회의를 모두 정렬한다. (시작 시간을 기준으로 오름차순, 이후 종료 시간을 기준으로 오름차순)
 * 2. 회의를 하나씩 회의실에 배정한다. 이 때, 기존 회의실에 배정을 할 수 없다면 회의실을 새로 만들어 넣는다.
 *    회의실은 우선순위로 정렬되며, 가장 먼저 끝난 회의실에 저장하도록 한다.
 */

//회의
class Conference{
    int start;
    int end;
    Conference(int start, int end){
        this.start = start;
        this.end = end;
    }
}

//회의룸
class ConferenceRoom{
    int endTime;
    ConferenceRoom(int endTime){
        this.endTime = endTime;
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //선의 개수

        //회의 목록 받기
        Conference[] conferences = new Conference[N];
        for(int i = 0; i<N; i++){
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            conferences[i] = new Conference(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
        }

        //회의 정렬하기. (시작하는 시간을 기준으로 오름차순, 끝나는 시간을 기준으로 오름차순)
        Arrays.sort(conferences, new Comparator<Conference>(){
            @Override
            public int compare(Conference o1, Conference o2){
                if(o1.start == o2.start){
                    return o1.end - o2.end;
                }
                return o1.start - o2.start;
            }
        });

        //회의룸 정렬하기
        PriorityQueue<ConferenceRoom> priorityQueue = new PriorityQueue<ConferenceRoom>(new Comparator<>(){
            @Override
            public int compare(ConferenceRoom o1, ConferenceRoom o2){
                return o1.endTime - o2.endTime;
            }
        });
        int conferenceRoomCount = 0;

        //회의의 처음부터 하나씩 회의룸에 배치해보자
        for(int i = 0 ; i<N; i++){
            //현재 있는 회의룸들 중 회의가 끝난 회의룸들을 빼기
            while(!priorityQueue.isEmpty() && priorityQueue.peek().endTime <= conferences[i].start) {
                priorityQueue.poll();
            }

            //회의룸을 넣기
            priorityQueue.offer(new ConferenceRoom(conferences[i].end));

            //회의룸 개수를 기억하기
            conferenceRoomCount = Math.max(conferenceRoomCount, priorityQueue.size());
        }

        //결과 출력
        System.out.println(conferenceRoomCount);
    }
}