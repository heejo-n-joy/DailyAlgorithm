import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 컨베이어 벨트 위의 로봇을 최대한 보내보자
 * 
 * 요구하는 출력
 * - 시스템이 종료되었을 때 몇 번째 단계였는지 출력 
 * 
 * 입력 변수
 * - N : 컨베이어벨트의 윗면 칸 개수
 * - K : 시스템이 종료되기 위한 조건인 내구도가 0인 칸의 개수.
 * - A1~A2N : 컨베이어벨트 칸 (N+1부터 2N까지는 뒷면)
 * 
 * 문제 유의사항
 * - 컨베이어 벨트라 계속해서 값이 순환
 * - 로봇도 자체적으로 움직일 수 있음 (다음 칸의 내구도 확인 필수)
 * 
 * 풀어나갈 방향
 * - 컨베이어 벨트를 배열로 처리
 * - 다만, 매번 배열 값을 바꾸는건 비효율적일 수 있으므로 원형 배열을 배치하고 포인터로 올리는 위치와 내리는 위치를 기억한다. 
 * 
 * 체감 난이도 : ★★☆☆☆ -> ★★★☆☆ 
 * - 글이 길지만, 명확한 순서가 나와있어 오히려 구현을 생각하기에는 편했다.
 * 
 * 
 * 회고할 내용
 * - 제대로 설계를 하는 과정에서 시간이 많이 소모가 됐다. 
 * - 포인터를 이용해 간접적으로 위치를 파악했기 때문에, 배열의 범위 내에서 이동하는 과정에 신경을 써야 했다.
 * - 로봇 이동처리, 내구성 처리 등을 함수로 만들어봤는데 내구성 처리는 두 번 나오는 로직이라 한 번으로 간편하게 만들 수 있었다.
 * 
 * @author HeejoPark
 *
 */

public class Main {
	static int conveyor[][];
	static int notDurableCount;
	static int N;
	static int pointerEnd;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());	//컨베이어 벨트의 칸 개수 (윗면)
		int K = Integer.parseInt(st.nextToken());	//종료를 위한 조건. 내구도가 0인 칸의 개수
		conveyor = new int[N*2][2];	//컨베이어벨트 내구성이 보관되는 칸. [0]은 내구성, [1]은 로봇 유무
		int pointerStart = 0;	//컨베이어의 로봇 올리는 위치
		pointerEnd = N-1;	//컨베이어의 로봇 내리는 위치
		notDurableCount = 0;	//내구도가 0인 칸의 개수
		int currentStage = 0;	//현재 단계
		String[] squares = br.readLine().split(" ");
		for(int i =0; i<squares.length; i++) {
			conveyor[i][0] = Integer.parseInt(squares[i]);
		}
		
		calc : while(notDurableCount<K) {
			//1.수행 단계 증가
			currentStage += 1;
			//2.벨트가 로봇과 함께 한칸씩 이동
			if(pointerStart==0) {
				pointerStart += 2*N;
			}
			pointerStart -= 1;
			if(pointerEnd==0) {
				pointerEnd += 2*N;
			}
			pointerEnd -= 1;
			//3.맨 오른쪽 로봇부터 이동
			for(int count = pointerEnd<pointerStart?pointerEnd+2*N:pointerEnd; count>=pointerStart; count--) {
				//처음 시작 칸이 내리는 위치
				if(count%(2*N)==pointerEnd) {
					//로봇을 칸에서 내린다.
					deleteRobot(count);
				}
				//마지막 칸이 올리는 위치
				else if(count==pointerStart) {
					//내구도가 0이 아니라면 로봇을 칸에 올린다.
					if(conveyor[count][0] > 0) {
						conveyor[count][1] = 1;	//로봇을 칸에 올리고
						decreaseAndCheckDurability(count);	//내구성 감소
						if(notDurableCount>=K) {
							break calc;
						}
					}
				}
				//중간 칸들의 과정
				else {
					//현재 로봇이 있는지
					if(conveyor[count%(2*N)][1]==1) {
						//다음 로봇 위치가 비어있고 내구성도 1이상이라면
						if((conveyor[(count+1)%(2*N)][1]==0)
								&&(conveyor[(count+1)%(2*N)][0]>0)) {
							//로봇을 해당 자리로 이동시키고 내구성을 감소시킨다.
							moveRobot(count);
							decreaseAndCheckDurability(count+1);
							if(notDurableCount>=K) {
								break calc;
							}
						}
					}
				}
				
			}
		}
		//결과출력
		System.out.println(currentStage);
	}
	public static void decreaseAndCheckDurability(int num) {
		conveyor[num%(2*N)][0]--;	//내구성 1 감소
		if(conveyor[num%(2*N)][0] == 0) {
			//내구성이 0이 됐다면
			notDurableCount++;
		}
	}
	
	public static void moveRobot(int num) {
		conveyor[num%(2*N)][1] = 0;
		conveyor[(num+1)%(2*N)][1] = 1;
		if((num+1)%(2*N)==pointerEnd) {
			deleteRobot(num+1);
		}
	}
	
	public static void deleteRobot(int num) {
		conveyor[num%(2*N)][1] = 0;
	}
}