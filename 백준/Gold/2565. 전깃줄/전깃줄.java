import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 전깃줄이 겹치지 않기 위해 잘라야 하는 전선의 최소 개수
 *
 * 아이디어 : DP
 * - 전깃줄을 하나씩 세팅하며 겹치지 않고 최대로 세팅할 수 있는 개수를 구하고, 전체 개수에서 빼주자
 * 1. 전깃줄들을 정렬한다.
 * 2. 차례대로 선택하며, 이전 전깃줄 개수들 중 최대값으로 갱신한다.
 * 3. 최대 값을 구한 후, 전체 개수에서 이를 빼준다.
 */

class Line{
    int start;
    int end;
    Line(int start, int end){
        this.start = start;
        this.end = end;
    }
}
public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());   //전깃줄 수

        Line[] lines = new Line[N];

        //전깃줄 입력받기
        for(int i = 0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            lines[i] = new Line(start, end);
        }

        //전깃줄을 정렬하기
        Arrays.sort(lines, new Comparator<Line>(){
            @Override
            public int compare(Line o1, Line o2){
                if(o1.start == o2.start){
                    return o1.end - o2.end;
                }
                return o1.start - o2.start;
            }
        });

        //겹치지 않게 전깃줄을 쌓아보기
        int[] DP = new int[N];
        for(int i = 0; i<N; i++){
            DP[i] = 1;

            //해당 전깃줄 기준 이전 전깃줄들 확인해보기
            for(int j = 0; j<i; j++){
                //전선이 겹치지 않는다면
                if(lines[j].end < lines[i].end){
                    DP[i] = Math.max(DP[j] + 1, DP[i]); //갱신이 필요하면 갱신하기
                }
            }
        }

        //최대 설치 가능 전깃줄 개수 구하기
        int max = 0;
        for(int i =0; i<N; i++){
            max = Math.max(DP[i], max);
        }

        //결과 출력
        System.out.println(N - max);
    }
}
