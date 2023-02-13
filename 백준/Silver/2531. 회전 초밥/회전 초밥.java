import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 회전 초밥의 초밥을 먹자
 * <p>
 * 요구하는 출력
 * - 먹을 수 있는 최대 종류 수
 * <p>
 * 전략
 * - 처음부터 하나씩 확인하기
 * - 컨베이어 벨트의 시작점부터 k-1번까지 뒤에 덧붙이기
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //접시의 수
        int d = Integer.parseInt(st.nextToken());   //초밥의 가짓수
        int k = Integer.parseInt(st.nextToken());   //연속해서 먹는 접시의 수
        int c = Integer.parseInt(st.nextToken());   //쿠폰 번호
        int[] conveyor = new int[N+k-1];
        for(int i =0; i<N; i++){
            conveyor[i] = Integer.parseInt(br.readLine());
        }
        for(int i = N; i<N+k-1; i++){
            conveyor[i] = conveyor[i-N];
        }

        int[] check = new int[d+1];
        int currentResult = 0;
        int maxResult = 0;

        //처음 경우의 수를 담는다.
        for(int i =0; i<k; i++){
            if(check[conveyor[i]]==0){
                currentResult++;
            }
            check[conveyor[i]]++;
        }
        maxResult = currentResult;
        //만약 쿠폰 번호에 해당하는 초밥이 없다면
        if(check[c] ==0){
            //최대값은 1 늘어난다.
            maxResult++;
        }
        //
        for(int i =1; i<N; i++){
            int removeSushi = conveyor[i-1];
            int addSushi = conveyor[i-1+k];
            check[removeSushi]--;
            //만약 removeSushi 번호가 없다면, 전체 종류 수가 1 감소 된다.
            if(check[removeSushi]==0){
                currentResult--;
            }
            //만약 addSushi 번호가 없었다면 전체 종류 수가 +1이 된다.
            if(check[addSushi]==0){
                currentResult++;
            }
            check[addSushi]++;
            //만약 쿠폰 번호에 해당하는 초밥이 없다면
            if(check[c] == 0){
                maxResult = Math.max(maxResult, currentResult+1);
            }
            else{
                maxResult = Math.max(maxResult, currentResult);
            }
        }
        //결과 출력
        System.out.println(maxResult);
    }
}