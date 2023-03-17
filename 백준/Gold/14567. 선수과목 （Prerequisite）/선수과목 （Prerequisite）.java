import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * 요구하는 출력
 * - 졸업을 하기위해 필요한 최소 학기
 * <p>
 * 이 문제는 어떻게 풀까?
 * - 과목별 수강할 수 있는 학기를 저장하는 배열이 필요하다. (Array)
 * - 과목별 필요한 선수과목을 저장한다. (List. 방향이 역순이 된다는 의미)
 * - 과목들을 과목1부터 N까지 확인하면서 계산한다. (DP)
 * - 1. 해당 과목의 선수과목들을 확인한다.
 * - 2. 그 선수과목을 듣는게 걸리는 최대학기 + 1학기로 저장한다.
 * - 가능한 이유 : 모든 과목의 선수과목은 본인보다 숫자가 작다.
 *
 * 시간 복잡도
 * - 선수과목 관계 저장: O(M)
 * - 계산: O(N) * O(M)
 * - 출력: O(N)
 *
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] array = new int[N+1];
        List<Integer>[] lists = new List[N+1];
        for(int i = 0; i<=N; i++){
            lists[i] = new ArrayList<>();
        }
        //입력받기
        for(int i = 0; i<M; i++){
            int prev = sc.nextInt();
            int next = sc.nextInt();
            lists[next].add(prev);
        }

        //계산
        for(int i = 1; i<=N; i++){
            int max_semester = 0; //선수과목들 중 최대학기
            for(int j = 0; j<lists[i].size(); j++){
                max_semester = Math.max(array[lists[i].get(j)], max_semester);  //선수과목을 하나씩 확인한다.
            }
            array[i] = max_semester + 1;    //최대학기 + 1학기를 더한 값이 현재 과목의 수강 학기
        }

        //결과 출력
        for(int i = 1; i<=N; i++){
            System.out.print(array[i] + " ");
        }
    }
}