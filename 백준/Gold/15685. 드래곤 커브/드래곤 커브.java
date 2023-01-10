import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 드래곤 커브를 만들어보자
 * <p>
 * 요구하는 출력
 * - 드래곤 커브가 완성됐을 떄, 네 꼭지점이 드래곤 커브에 모두 들어간 정사각형의 개수를 구하라
 * <p>
 * 입력 변수
 * - N : 드래곤 커브의 개수
 * - x, y : 드래곤 커브의 시작점
 * - d : 시작 방향 (0: 오른쪽, 1: 위쪽, 2: 왼쪽, 3:아래쪽)
 * - g : 세대
 * <p>
 * 문제 유의사항
 * - 드래곤 커브는 서로 겹칠 수 있다.
 * <p>
 * 체감 난이도 : ★★☆☆☆
 * <p>
 * 전략
 * - 드래곤 커브를 만들어주는 함수를 짜면 편하게 될 것 같다.
 * - 드래곤 커브가 만들어질 때마다 queue를 이용해서 방향들만 저장하자.
 * - 하다보니 드래곤 커브를 추가하는 과정에는 역순이 필요해서 stack도 함께 사용하기로 했다.
 * <p>
 * 회고할 내용
 * - x와 y가 위치가 바뀌었다. x가 가로, y가 세로르 의미했기 때문에, map[y][x]로 계산했어야 했다.
 *
 * @author HeejoPark
 */
public class Main {
    static final int MAP_SIZE = 101;
    public static void main(String[] args) throws Exception {
        boolean map[][] = new boolean[MAP_SIZE][MAP_SIZE];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //드래곤 커브의 개수
        for(int i = 0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());   //가로
            int y = Integer.parseInt(st.nextToken());   //세로
            int d = Integer.parseInt(st.nextToken());   //방향
            int g = Integer.parseInt(st.nextToken());   //세대
            dragonCurve(x,y,d,g, map);
        }
        //결과 구하기
        int totalCount = 0;
        for(int i =0; i<MAP_SIZE-1; i++){
            for(int j = 0; j<MAP_SIZE-1; j++){
                if(map[i][j] && map[i][j+1] && map[i+1][j] && map[i+1][j+1]){
                    totalCount++;
                }
            }
        }
        System.out.println(totalCount);
    }
    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {1, 0, -1, 0};
    public static void dragonCurve(int x, int y, int d, int g, boolean map[][]){
        Queue<Integer> queue = new LinkedList<>();  //여태까지의 모든 방향 정보가 담겨있다.
        queue.offer(d); //0세대
        map[y][x] = true;   //처음 구간 true처리
        x += dc[d];
        y += dr[d];
        map[y][x] = true;   //처음 구간 true처리
        for(int i =1; i<=g; i++) {
            int dragonCurveLength = queue.size();
            Stack<Integer> stack = new Stack<>();   //이번에 추가로 만들어야하는 드래곤 커브의 정보
            for(int j =0; j<dragonCurveLength; j++){
                int temp = queue.poll();
                stack.push((temp+1)%4); //시계방향으로 90도 회전하고 역순으로 진행되기 때문에 방향을 바꿔서 넣어준다.
                queue.offer(temp);  //큐에는 그대로 둔다.
            }
            //x,y에서 방향으로 시작
            for(int j =0; j<dragonCurveLength; j++){
                d = stack.pop();    //방향을 하나씩 빼온다.
                x += dc[d];
                y += dr[d];
                map[y][x] = true;   //하나씩 드래곤 커브 구간을 체크해준다.
                queue.offer(d);     //방향을 다시 전체 보관 queue에 넣는다.
            }
        }
    }
}