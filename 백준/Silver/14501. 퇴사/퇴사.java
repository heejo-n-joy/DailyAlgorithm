import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 상담사 그만두기 전에 최대한 많은 상담을 하려고 한다.
 * 
 * 요구하는 출력
 * - 최대 수익을 구하라
 * 
 * 입력 변수
 * - N : 퇴사까지 남은 일수
 * - Ti : 해당 손님에게 쏟아야하는 상담일자
 * - Pi : 해당 손님의 수익
 * 
 * 문제 유의사항
 * - 하루에 한 사람만 상담 가능
 * 
 * 풀어나갈 방향
 * - DP로 접근해보자
 * - 첫 상담일부터 순차적으로 진행(for)
 * - 현재 날짜에서 상담을 진행했을 때의 결과를 바뀐 날짜에 저장한다 (최대값 비교, 이후 날짜가 N+1이내인지 체크 필수)
 * - 이렇게 하면 해당 날짜의 상담일이 진행될 때는, 현재 날짜까지의 최대값이 저장되지 않을까?
 * 
 * 체감 난이도 : ★★☆☆☆
 * - DP스러운 생각을 하는게 쉽지 않았다.
 * 
 * 회고할 내용
 * - 사실 이 문제는 N<=15이기 때문에 앞서 풀었던 Brute Force로도 가능한 간단한 문제였다.
 * - 그러나 N의 범위가 매우 큰 문제라면 무조건 DP로 풀어야 하지 않을까 싶다.
 * - 지금처럼 다양한 시각을 좀 기르는 훈련을 해야겠다.
 * 
 * @author HeejoPark
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // 퇴사까지 남은 일자
		int consulting_table[][] = new int[N+1][2];
		for(int i = 1; i<=N; i++) {			
			StringTokenizer st = new StringTokenizer(br.readLine());
			consulting_table[i][0] = Integer.parseInt(st.nextToken());	//상담 얼마나 걸리는지
			consulting_table[i][1] = Integer.parseInt(st.nextToken());	//상담 얼마나 돈주는지
		}
		int max_consulting_income[] = new int[N+2];		//1~N+1까지의 날짜
		//계산
		for(int i = 1; i<=N; i++) {
			//전일 상담 최대값과 금일 상담 최대값 비교
			if(max_consulting_income[i-1]>max_consulting_income[i]) {
				//전일 상담 최대값이 금일 상담 최대값보다 크면, 어제 기준으로 상담 안하는게 더 이득이므로 반영한다.
				max_consulting_income[i] = max_consulting_income[i-1];
			}
			//현재 상담을 진행해도 퇴사일자까지 가는지?
			if(i + consulting_table[i][0]<=N+1) {
				//안가면 가능하므로 최대값 갱신. 오늘 날짜 최대값+오늘 상담 진행 vs 그 날짜의 현 최대값
				max_consulting_income[i+consulting_table[i][0]] = 
						Math.max(consulting_table[i][1]+max_consulting_income[i], max_consulting_income[i+consulting_table[i][0]]);
			}
			else {				
				//퇴사일자 벗어나면 의미가 없음. 실행안한다.
			}
		}
		int max_income = Math.max(max_consulting_income[N+1], max_consulting_income[N]);	//N+1일 기준 최대값 출력
		System.out.println(max_income);	
	}
}
