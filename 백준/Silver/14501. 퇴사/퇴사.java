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
 * - 상담 테이블을 전역변수(배열)로 설정
 * - 이 상담을 할 것인가? 안할 것인가?로 구분하며 상담 시뮬레이션 진행
 * - 이전 시뮬레이션의 수익보다 클 경우 갱신
 * - 결과값 출력
 * 
 * 체감 난이도 : ★★☆☆☆
 * - 순간 고민을 하게 했다.
 * 
 * 회고할 내용
 * 
 * @author Heejo Park
 *
 */
public class Main {
	static int consulting_table[][];
	static int max_income;
	static int N;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 퇴사까지 남은 일자
		consulting_table = new int[N+1][2];
		for(int i = 1; i<=N; i++) {			
			StringTokenizer st = new StringTokenizer(br.readLine());
			consulting_table[i][0] = Integer.parseInt(st.nextToken());
			consulting_table[i][1] = Integer.parseInt(st.nextToken());
		}
		max_income = 0;
		execute(0, 1);
		System.out.println(max_income);
		
	}
	public static void execute(int current_income, int current_day) {
		//퇴사일을 지난다면 해당 시뮬레이션은 탈락
		if(current_day>N+1) {
			return;
		}
		//퇴사일이라면 
		else if(current_day==N+1) {
			//최대값 비교
			if(current_income > max_income) {
				max_income = current_income;
			}
			return;
		}
		
		//선택1. 현재 날짜의 신규 상담을 받는다.
		execute(current_income+consulting_table[current_day][1], current_day+consulting_table[current_day][0]);
		//선택2. 현재 날짜의 신규 상담을 받지 않는다.
		execute(current_income, current_day+1);
	}
}
