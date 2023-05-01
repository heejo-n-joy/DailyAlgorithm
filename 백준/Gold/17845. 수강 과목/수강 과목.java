import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 서윤이의 공부 시간의 한계를 초과하지 않으면서 중요도 합이 최대가 되도록 만들기
 *
 * 풀이 방법 : 다이나믹 프로그래밍(DP)
 * - 1. 과목들을 전부 입력받는다.
 * - 2. 0부터 N시간까지 있는 배열에 과목을 하나씩 집어넣어본다.
 * - 3. 현재 과목을 넣는 것과 넣지 않는 것 중에 최대값을 저장한다.
 */
class Subject{
    int time;
    int point;
    
    Subject(int point, int time){
        this.time = time;
        this.point = point;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());  //최대 공부 시간
        int K = Integer.parseInt(stringTokenizer.nextToken());  //과목의 수

        Subject[] subjects = new Subject[K];
        for(int i = 0; i <K; i++){
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            subjects[i] = new Subject(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
        }

        int[][] DP = new int[K][N+1];
        for(int i = 0; i <K; i++){
            for(int j = 0; j<=N; j++){
                //첫 번째 과목은, 조건 비교 없이 해당 과목의 중요도를 입력해둔다.
                if(i == 0){
                    if(j >= subjects[i].time){
                        DP[i][j] = subjects[i].point;
                    }
                }
                //두 번째 과목부터, 제한된 j 시간에 해당 과목 시간이 들어간다면, 값을 비교 후 갱신한다.
                else if(j >= subjects[i].time){
                    //현재 과목을 투입했을 때와 투입하지 않았을 때의 이득을 비교 후 넣는다.
                    DP[i][j] = Math.max(DP[i-1][j - subjects[i].time] + subjects[i].point, DP[i-1][j]);
                }
                //해당 과목 시간을 집어넣지 못하면, 이전 값을 그대로 받는다.
                else{
                    DP[i][j] = DP[i-1][j];
                }
            }
        }

        //결과 출력
        System.out.println(DP[K-1][N]);
    }
}