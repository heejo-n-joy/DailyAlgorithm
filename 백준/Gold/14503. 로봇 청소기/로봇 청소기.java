import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 로봇청소기를 이용해서 청소를 하자
 * 
 * 요구하는 출력
 * - 로봇이 청소한 칸의 개수를 출력 
 * 
 * 입력 변수
 * - N : 세로 크기
 * - M : 가로 크기
 * - r,c : 로봇의 좌표
 * - d : 로봇의 방향 (0은 북쪽, 1은 동쪽, 2는 남쪽, 3은 서쪽)
 * - 나머지는 맵의 정보 (0은 빈칸, 1은 벽, 지도의 테두리는 벽-밖으로 나갈 일 없음)
 * 
 * 문제 유의사항
 * - 문제에서의 '그 뱡향으로 회전'에서 그 방향은 어디를 의미하는거지.... 테스트케이스로 풀어보면서 방향을 정해야겠다;
 * - 다시 읽어보니 '현재 방향을 기준으로 왼쪽방향부터 탐색한다'는 문구가 있었다.
 * 풀어나갈 방향
 * - 문제에서 원하는 구현이 구체적이라 (방향만 빼고) 그대로 풀어보면 될 것 같다.
 * - 맵은 하나의 큰 배열을 만들고 진행하면 되겠다.
 * - 로봇의 최대 청소 칸이 아니고, 문제대로 로직을 구현하면 단 하나의 길만 나오게 되니 편하게 작성해도 되겠다. 
 * 
 * 체감 난이도 : ★★☆☆☆ 
 * 
 * 회고할 내용
 * - 방향을 어떻게 회전하는지에 대한 인지가 늦었다. 척하면 척할 수 있도록 꼼꼼하게 읽어보자 
 * 
 * @author HeejoPark
 *
 */

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//맵의 세로
		int M = Integer.parseInt(st.nextToken());	//맵의 가로
		int map[][] = new int[N][M];		//맵 생성
		
		st = new StringTokenizer(br.readLine());
		int r = Integer.parseInt(st.nextToken());	//로봇의 현재 좌표 r
		int c = Integer.parseInt(st.nextToken());	//로봇의 현재 좌표 c
		int d = Integer.parseInt(st.nextToken());	//로봇의 바라보는 방향 d
		
		//맵에 데이터 입력
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int clearSquareCount = 0;
		
		int dr[] = {-1, 0, 1, 0};
		int dc[] = {0, 1, 0, -1};
		
		//로봇청소기 실행
		cleaning : while(true) {
			//현재 위치 청소
			map[r][c] = 2;	//청소한 구역은 2로 표시
			clearSquareCount++;
			
			//왼쪽부터 차례대로 탐색
			int explore_count = 0;
			find : while(explore_count<5) {
				int direction = (d - (explore_count + 1))<0?(d - (explore_count + 1)) + 4:(d - (explore_count + 1));
				//모두 청소가 되어있거나 벽이라면, 
				if(explore_count==4) {
					//후진이 가능할 경우 바라보는 방향을 유지한 채로 한 칸 후진
					if(map[r-dr[d]][c-dc[d]]!=1) {
						r -= dr[d];	//한칸후진
						c -= dc[d];	//한칸후진
						explore_count = 0;
						continue find;
					}
					//후진이 불가능할 경우 작동 중지
					else {
						break cleaning;
					}
				}
				//청소하지 않은 공간 발견되면 해당 방향으로 회전하고 한칸 전진 후 처음으로
				else if(map[r+dr[direction]][c+dc[direction]]==0) {
					d = direction;	//방향 회전
					r += dr[direction];	//한칸전진
					c += dc[direction];	//한칸전진
					continue cleaning;	//처음으로 이동
				}
				//해당 방향에 청소가 불가능하다면
				else {
					explore_count++;	//다음 방향을 탐색
				}
			}
		}
		//결과 출력
		System.out.println(clearSquareCount);
	}
}