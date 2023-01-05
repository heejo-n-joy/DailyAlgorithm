import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 마법사 상어가 비바라기를 시전한다.. 효과는 굉장했다!
 * 
 * 요구하는 출력
 * - 비바라기를 M번 실행한 후 전체 물의 양의 합 
 * 
 * 입력 변수
 * - N : 격자의 크기
 * - M : 몇 번 이동하는지
 * - A[r][c] : 격자의 칸별 물의 양
 * - di : 구름 이동 방향
 * - si : 구름 몇 칸 이동하는지
 * 
 * 문제 유의사항
 * - 비구름이 생기는 과정 / 구름이 이동하는 과정에서 격자의 이동 방식이 바뀐다.
 * 
 * 풀어나갈 방향
 * - 시뮬레이션 그대로 순차적으로 작성하면서 진행할 것이다.
 * 
 * 체감 난이도 : ★★★☆☆ 
 * 
 * 회고할 내용
 * - Queue를 이용해 문제를 풀 때, 하나씩 꺼낸 후(poll) 다시 집어넣는(offer) 과정을 잊지 말자 
 * 
 * @author HeejoPark
 *
 */

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//맵의 격자 크기
		int M = Integer.parseInt(st.nextToken());	//비바라기 시전 횟수
		int A[][] = new int[N][N];	//격자
		for(int i =0; i<N; i++) {	//기본 물의 양 격자에 입력
			st = new StringTokenizer(br.readLine());
			for(int j =0; j<N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//비바라기 시전
		Queue<int[]> clouds = new LinkedList<>();
		clouds.offer(new int[] {N-1, 0});
		clouds.offer(new int[] {N-1, 1});
		clouds.offer(new int[] {N-2, 0});
		clouds.offer(new int[] {N-2, 1});
		
		int dr[] = {0, -1, -1, -1, 0, 1, 1, 1};	//구름의 이동 방향
		int dc[] = {-1, -1, 0, 1, 1, 1, 0, -1};	//구름의 이동 방향
		//구름 이동
		for(int i =0; i<M; i++) {
			int cloudCount = clouds.size();
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());	//이번 이동의 방향
			int s = Integer.parseInt(st.nextToken());	//이번 이동의 거리
			for(int j = 0; j<cloudCount; j++) {
				//1. 각 구름을 칸 이동 시킨다.
				int temp[] = clouds.poll();
				temp[0] = ((temp[0] + (dr[d-1] * (s % N))) + N )% N;
				temp[1] = ((temp[1] + (dc[d-1] * (s % N))) + N )% N;
				clouds.offer(temp);
				//2. 각 구름의 위치의 물의 양 1 증가
				A[temp[0]][temp[1]]++;
			}
			//3. 2에서 물이 증가한 칸에 물복사마법을 시전
			for(int j = 0; j<cloudCount; j++) {
				int temp[] = clouds.poll();
				for(int k = 1; k<=7; k=k+2) {
					//인접한 대각선 탐색
					if(temp[0] + dr[k] >= 0 && temp[0] + dr[k]<N
							&& temp[1] + dc[k] >=0 && temp[1] + dc[k]<N) {
						//인접한 대각선에서 바구니에 물이 있다면 그 개수만큼 현재 바구니의 물 증가
						if(A[temp[0] + dr[k]][temp[1] + dc[k]]>0) {
							A[temp[0]][temp[1]]++;
						}
					}
				}
				clouds.offer(temp);
			}
			//4. 저장된 물의 양이 2이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다. 이 때, 구름이 생기는 칸은 2의 구름 위치가 아니어야 한다.
			Queue<int[]> newClouds = new LinkedList<>();
			for(int j = 0; j<N; j++) {
				goToNextBasket : for(int k = 0; k<N; k++) {
					//해당 위치에 물의 양이 2 이상인지 확인
					if(A[j][k]>=2) {
						//해당 위치에 구름이 있었는지 확인
						for(int l = 0; l<cloudCount; l++) {
							int temp[] = clouds.poll();
							clouds.offer(temp);
							//구름이 있었다면 없던 일로 합시다
							if(temp[0]==j && temp[1]==k) {
								continue goToNextBasket;
							}
						}
						//여기까지 오는거면 일치한 구름이 없었다는 뜻
						A[j][k] -= 2;
						newClouds.offer(new int[] {j, k});
					}
				}
			}
			//다시 구름을 이동시키러 가자.
			clouds = newClouds;
		}
		//바구니에 들어있는 물의 양의 합을 구하자
		int totalCount = 0;
		for(int i =0; i<N; i++) {
			for(int j =0; j<N; j++) {
				totalCount += A[i][j];
			}
		}
		System.out.println(totalCount);
		
	}	
}