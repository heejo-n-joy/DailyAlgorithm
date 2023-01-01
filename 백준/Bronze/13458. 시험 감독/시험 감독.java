import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 시험장에 시험 감독을 배치하자
 * 
 * 요구하는 출력
 * - 각 시험장별 필요한 감독관 최소 수
 * 
 * 입력 변수
 * - N : 총 시험장 개수
 * - Ai : i번 시험장의 응시자의 수
 * - B : 총감독관의 감시 가능한 응시자 수
 * - C : 부감독관의 감시 가능한 응시자 수
 * 
 * 문제 유의사항
 * - 시험장별 총감독관은 무조건 1명, 부감독관은 여러 명 가능
 * 
 * 풀어나갈 방향
 * - 해당 시험장이 총 감독관으로 커버가 가능한지 확인
 * - 커버가 불가능하다면 부감독관이 몇 명 필요한지 확인 (나머지, 몫 계산으로 확인이 가능할 것으로 예상)
 * 
 * 체감 난이도 : ★☆☆☆☆ -> ★★☆☆☆ 
 * - 문제를 읽으면서 해결방법이 막힘없이 떠올랐다.
 * - 테케에서도 한 번 틀리고, 채점에서도 한 번 틀렸다.
 * 
 * 회고할 내용
 * - 1. 총감독자(B)와 부감독자(C)의 계산 구분 실수
 * - 2. 총 감독관을 세는 과정에서 int 범위를 벗어날 수 있다. (최대 가능한 감독 수 : 1,000,000x1,000,000)
 * - 알고리즘 생각은 잘 했지만, 조금 더 디테일한 부분까지 신경쓰자
 * 
 * 
 * 
 * @author HeejoPark
 *
 */

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());	//시험장 수
		String A_Str[] = br.readLine().split(" ");	//시험장 응시자 수를 배열로 저장
		StringTokenizer st = new StringTokenizer(br.readLine());
		int B = Integer.parseInt(st.nextToken());	//총감독자의 감시 가능 수
		int C = Integer.parseInt(st.nextToken());	//부감독자의 감시 가능 수
		
		long total_supervisor = 0;
		
		for(int testPlaceNum = 1; testPlaceNum <= N; testPlaceNum++) {
			int A = Integer.parseInt(A_Str[testPlaceNum-1]);	//해당 시험장의 응시수 꺼내기
			
			//총감독자의 감시 가능한 인원 수 빼기
			A -= B;
			total_supervisor += 1;
			
			//여전히 인원 수가 남아있다면, 부감독자가 필요
			if(A>0) {
				//부감독자 감시 가능 수가 정확히 나누어떨어진다면
				if(A%C == 0) {
					total_supervisor += A/C;	//몫만큼 더하기 (부감독 수)
				}
				//나머지 값이 존재한다면
				else {
					total_supervisor += (A/C + 1);	//몫 + 1만큼 더하기 (부감독 수)
				}
			}
		}
		
		System.out.println(total_supervisor);

	}

}