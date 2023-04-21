import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 체스판 위의 나이트를 활용하여 원하는 위치까지 걸리는 횟수
 *
 * 풀이 방법 : BFS
 * - 8가지 이동 방식을 가지고, 이동을 진행한다.
 * - 이미 이동한 자리로는 이동하지 않는다.
 */

class Loc{
    int x;
    int y;
    Loc(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bufferedReader.readLine());    //테스트케이스
        for(int test_case = 1; test_case<=T; test_case++) {
            int I = Integer.parseInt(bufferedReader.readLine());    //체스판의 크기
            int[][] map = new int[I][I];
            boolean[][] check = new boolean[I][I];
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            Loc start = new Loc(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            Loc end = new Loc(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));

            //나이트가 이동할 수 있는 8가지 위치
            int[] dr = {-2, -1, 1, 2, 2, 1, -1, -2};
            int[] dc = {1, 2, 2, 1, -1, -2, -2, -1};

            Queue<Loc> queue = new LinkedList<>();
            queue.offer(start);
            check[start.x][start.y] = true;
            while(!queue.isEmpty()){
                Loc current = queue.poll();

                //도착지에 왔다면 종료
                if(current.x == end.x && current.y == end.y){
                    break;
                }

                //8가지 위치 탐방
                for(int k = 0; k<8; k++){
                    Loc next = new Loc(current.x + dr[k], current.y + dc[k]);

                    //해당 위치가 범위를 벗어나면 패스
                    if(next.x < 0 || next.x >= I || next.y < 0 || next.y >= I){
                        continue;
                    }

                    //해당 위치를 이미 탐방했다면 패스
                    if(check[next.x][next.y]){
                        continue;
                    }

                    //그게 아니라면, 탐방을 하자
                    check[next.x][next.y] = true;
                    queue.offer(next);
                    map[next.x][next.y] = map[current.x][current.y] + 1;
                }
            }

            //결과 출력
            System.out.println(map[end.x][end.y]);
        }
    }
}