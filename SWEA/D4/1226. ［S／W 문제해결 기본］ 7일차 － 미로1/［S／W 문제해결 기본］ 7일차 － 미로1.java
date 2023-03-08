import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 미로의 출발지에서 도착지까지 도달찰 수 있는지
 * <p>
 * 이 문제는 어떻게 풀까?
 * - BFS를 이용한다.
 * 1. 입력값을 받을 때 출발지와 도착지 정보를 미리 저장하기
 * 2. 출발지에서 시작해 상하좌우 중 갈 수 있는 길이 있으면 가기
 * 3. 도착지에 도착하면 true, 모든 길을 찾아봤지만 도착하지 못했다면 false
 *
 * @author HeejoPark
 */

class Loc{
    int x;
    int y;
    Loc(){
    }
    Loc(int x, int y){
        this.x = x;
        this.y = y;
    }
    void setLoc(int x, int y){
        this.x = x;
        this.y = y;
    }
}
class Solution {
    static final int MAZE_SIZE = 16;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        for (int test_case = 1; test_case <=10; test_case++) {
            int T = sc.nextInt();
            int[][] map = new int[MAZE_SIZE][MAZE_SIZE];
            Loc start = new Loc();
            for(int i = 0; i<MAZE_SIZE; i++) {
                String str = sc.next();
                for(int j =0; j<MAZE_SIZE; j++){
                    map[i][j] = str.charAt(j) - '0';
                    //만약 출발지라면
                    if(map[i][j] == 2){
                        start.setLoc(i, j);
                    }
                }
            }
            sb.append("#"+test_case+" ");
            if(isPossibleMaze(map, start)){
                sb.append(1);
            }
            else{
                sb.append(0);
            }
            sb.append("\n");
        }
        //결과 출력
        System.out.println(sb.toString());
    }

    public static boolean isPossibleMaze(int[][] map, Loc start){
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        Queue<Loc> queue = new LinkedList<>();
        boolean[][] checkMap = new boolean[MAZE_SIZE][MAZE_SIZE];
        queue.offer(start);
        checkMap[start.x][start.y] = true;
        while(!queue.isEmpty()){
            Loc temp = queue.poll();
            //도착지라면
            if(map[temp.x][temp.y] == 3){
                return true;
            }
            //상하좌우 탐색
            for(int k = 0; k<4; k++){
                int nextX = temp.x + dr[k];
                int nextY = temp.y + dc[k];
                //혹시 위치가 벗어나면 패스
                if(nextX < 0 || nextX >= MAZE_SIZE || nextY < 0 || nextY >= MAZE_SIZE){
                    continue;
                }
                //벽이라면 패스
                if(map[nextX][nextY] == 1){
                    continue;
                }
                //이미 탐험을 했다면 패스
                if(checkMap[nextX][nextY]){
                    continue;
                }
                //둘 다 아니라면 큐에 넣자
                queue.offer(new Loc(nextX, nextY));
                checkMap[nextX][nextY] = true;
            }
        }
        return false;
    }
}