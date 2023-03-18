import java.util.Scanner;

/**
 * 요구하는 출력
 * - 벼락치기를 해야 하는 준석이, 남은 시간 가장 효율적인 투자로 얻을 수 있는 최대 점수
 * <p>
 * 이 문제는 어떻게 풀까?
 * - 일단 DP를 활용해야 한다. (100개의 종류를 조합으로 풀 수 없다)
 * - DP는 1부터 주어진 총 시간(T)까지의 1차원 배열이다.
 * - 모든 과목을 하나씩 확인하며 계산하자. (현재 과목을 s라 할 때)
 * - 시간 i의 기존의 DP[i]값과 DP[i-timeOfS] + scoreOfS 중 최대값을 비교한다.
 * - DP[N]을 출력
 *
 * 시간 복잡도
 * - 계산: O(N) * O(T) = 최대 1,000,000
 *
 * @author HeejoPark
 */

class Subjects{
    int time;
    int score;
    Subjects(int time, int score){
        this.time = time;
        this.score = score;
    }
}
class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N  = sc.nextInt();  //시험 단원 개수
        int T = sc.nextInt();   //총 시간
        int[][] DP = new int[N+1][T+1];    //시간별 가능한 최대 점수
        Subjects[] subjects = new Subjects[N+1];  //과목 정보
        for(int i = 1 ; i<=N; i++){
            subjects[i] = new Subjects(sc.nextInt(), sc.nextInt());
        }
        //계산
        for(int for_subject = 1; for_subject<=N; for_subject++){
            for(int for_time = 0; for_time <= T; for_time++){
                if(for_time - subjects[for_subject].time < 0){
                    DP[for_subject][for_time] = DP[for_subject-1][for_time];
                }
                else {
                    DP[for_subject][for_time] = Math.max(DP[for_subject - 1][for_time],
                            DP[for_subject - 1][for_time - subjects[for_subject].time] + subjects[for_subject].score);
                }
            }
        }

        System.out.println(DP[N][T]);
    }
}