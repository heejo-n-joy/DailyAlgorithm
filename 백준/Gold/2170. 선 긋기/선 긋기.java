import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 그려진 선들의 총 길이를 구하기
 *
 * 풀이 방법 : 스위핑 알고리즘 (한쪽 방향부터 시작해서 다른 방향으로 스캔해가며 쓸어간다)
 * 1. 우선 선들을 모두 정렬한다.
 * 2. 현재 긋고 있는 선에 해당 선이 겹친다면 (혹은 이어갈 수 있다면) 계속적으로 더한다.
 * 3. 선이 1도 겹치지 않으면, 현재까지의 길이는 저장하고 새로운 선으로 시작한다.
 */

class Line{
    int x;
    int y;
    Line(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //선의 개수
        PriorityQueue<Line> priorityQueue = new PriorityQueue<>(new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                //x, y에 대한 오름차순 정렬
                if(o1.x == o2.x){
                    return o1.y - o2.y;
                }
                return o1.x - o2.x;
            }
        });
        for(int i = 0; i<N; i++){
            StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
            priorityQueue.offer(new Line(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        Line currentLine = priorityQueue.poll();
        int result = 0;
        while(!priorityQueue.isEmpty()){
            Line temp = priorityQueue.poll();
            //만약에 temp의 x가 기존 선의 y보다 크다면
            if(temp.x > currentLine.y){
                //현재까지의 길이를 더하고 새로운 선으로 만든다.
                result += currentLine.y - currentLine.x;
                currentLine = temp;
                continue;
            }

            //만약 temp의 y가 currentLine의 y보다 크다면
            if(temp.y > currentLine.y){
                //현재 선을 갱신한다.
                currentLine.y = temp.y;
            }
        }
        //현재까지 만들어진 선을 결과에 더한다.
        result += currentLine.y - currentLine.x;

        //결과 출력
        System.out.println(result);
    }
}