import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * #슬라이딩_윈도우_특집
 *
 * 요구하는 출력 : 벽화에서 추출한 문자열에서 알파벳 순서에 상관없이 글자가 같은 형태의 개수
 *
 * 풀이 방법 : 슬라이딩 윈도우 + 원형
 * - 1. 슬라이딩 윈도우를 세팅한다.
 * - 2. 시작 위치와 끝 위치를 동시에 한 칸씩 이동하며, 슬라이딩 윈도우 내 문자열의 값을 변경한다.
 * - 3. 값이 문제 조건에 만족하는지 확인하고, 2번으로 다시 간다.
 * - 4. 원형으로 이루어져있기 때문에, 처음 위치로 돌아올 때까지 계산을 한다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());   //마을의 집 개수
            int M = Integer.parseInt(st.nextToken());   //도둑이 돈을 훔칠 연속된 집의 개수
            int K = Integer.parseInt(st.nextToken());   //장범 장치가 작동하는 돈의 최소값

            //집의 재산들을 저장
            int[] houses = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<N; i++){
                houses[i] = Integer.parseInt(st.nextToken());
            }

            //결과값을 저장할 변수
            int result = 0;

            //슬라이딩 윈도우 세팅
            int thief_money = 0;
            int start_index = 0;
            int end_index = M - 1;
            for (int i = start_index; i <= end_index; i++) {
                //해당 집의 재산을 더한다.
                thief_money += houses[i];
            }

            //만약 조건에 부합하면
            if (compare(thief_money, K)) {
                result++;   //결과값을 1 늘린다.
            }

            //만약 전체 사이즈와 알아볼 집 사이즈가 같다면 이미 계산했으니, 사이즈가 다른 경우에만 계산을 이어간다.
            if(N!=M) {
                //이제 본격적으로 슬라이딩하며 값을 계속 확인한다.
                for (int i = end_index + 1; i != M - 1; i = (i + 1) % N) {
                    //시작 지점의 재산은 빼자
                    thief_money -= houses[(i - M + N) % N];

                    //새로 들어온 끝 지점의 문자 개수는 1 늘리자
                    thief_money += houses[i];

                    //새로 변경된 비밀번호 문자열이 조건에 부합하면
                    if (compare(thief_money, K)) {
                        result++;   //결과값을 1 늘린다.
                    }
                }
            }

            //결과출력
            System.out.println(result);
        }
    }

    public static boolean compare(int thief, int siren){
        //도둑의 재산이 K 이상이라면 사이렌이 울려 실패
        if(thief >= siren){
            return false;
        }
        return true;
    }
}