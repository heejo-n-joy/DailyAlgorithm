import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * #그리디알고리즘_특집
 *
 * 요구하는 출력 : 신입 사원들을 뽑을 수 있는 최대 인원
 *
 * 풀이 방법 : 정렬 + 탐색
 * - 서류 등수를 높은 등수 순(실질적으로 오름차순)으로 정렬한다.
 * - 서류 1등을 기준으로, 순차적으로 규칙을 만족하는 인원들을 뽑는다. 뽑을 때마다 기준은 뽑은 사람으로 바꾼다.
 *
 * 그리디 알고리즘으로 풀이가 가능한 이유?
 * - 동석차가 존재하지 않아, 서류 심사 1등이 정확하게 정해져 있다.
 * - 이 사람은 면접 등수가 어떠한들 합격을 할 수 밖에 없다.
 * - 이처럼 등수의 기준이 잡혀져 있으니, 이 기준을 만족하는 사람들을 뽑으면 된다.
 *
 */

class Applicant{
    int paper;
    int interview;
    Applicant(int paper, int interview){
        this.paper = paper;
        this.interview = interview;
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++){
            int N = Integer.parseInt(br.readLine());
            Applicant[] applicants = new Applicant[N];
            for(int i = 0; i<N; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                applicants[i] = new Applicant(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            //서류 등수를 기준으로 오름차순 정렬
            Arrays.sort(applicants, (o1, o2) ->
                    o1.paper - o2.paper
                    );

            //인터뷰 등수를 확인
            int min_interview = applicants[0].interview;
            int count = 1;
            for(int i = 1; i<N; i++){
                //인터뷰 등수가 기준보다 더 높다면
                if(applicants[i].interview < min_interview){
                    count++;    //합격
                    min_interview = Math.min(applicants[i].interview, min_interview);    //기준을 갱신한다.
                }
            }

            //결과 출력
            System.out.println(count);
        }
    }
}