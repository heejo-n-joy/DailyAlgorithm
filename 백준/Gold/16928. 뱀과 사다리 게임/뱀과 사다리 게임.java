import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 뱀과 사다리 게임을 가장 빨리 끝내는 횟수 (주사위를 조작해서라도..)
 *
 * 풀이 방법
 * - 주사위를 임의로 조작하며 보드판에서 이동을 한다. (Queue)
 * - 이동한 자리는 체크를 하며, 다른 방법으로 해당 칸이 오면 계산을 무시한다. (어차피 최소 횟수로 움직여야 하기 때문이다)
 * - 이동할 때마다 주사위를 굴린 횟수를 기억한다.
 * - 100번 칸에 가장 먼저 도착하는 경우가 최소 횟수로 게임에 도착하는 경우이다.
 *
 * 시간복잡도
 * - 최대 100번을 확인하니 O(n)?
 *
 * 디테일한 차이
 * - 문제에선는 1번부터 100번까지이지만, 계산의 편의성을 위해 0번부터 99번으로 변경했다.
 * 
 */

class GameRound{
    int current_location;
    int current_dice_count;
    GameRound(int current_location, int current_dice_count){
        this.current_dice_count = current_dice_count;
        this.current_location = current_location;
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   //사다리의 수
        int M = sc.nextInt();   //뱀의 수
        int[][] map = new int[10][10];    //사실 이 문제는 1차원 배열로 풀어도 되지만, 문제에서 원하는 대로 2차원 배열로 풀자.
        boolean[][] check_map = new boolean[10][10];    //해당 위치에 도착했던 적이 있는지를 체크
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                map[i][j] = 10 * i + j; //기본적으로 자기 자신의 번호를 가진다.
            }
        }

        //사다리와 뱀을 맵에 배치한다.
        for(int i = 0; i<N + M; i++){
            int from = sc.nextInt() - 1 ;
            int to = sc.nextInt() - 1 ;
            map[from / 10][from % 10] = to;
        }

        Queue<GameRound> queue = new LinkedList<>();
        queue.offer(new GameRound(0, 0));
        int result = 0;

        //실행
        while(!queue.isEmpty()){
            GameRound temp = queue.poll();

            //목적지에 도착했다면
            if(temp.current_location == 99){
                //결과를 저장하고 종료
                result = temp.current_dice_count;
                break;
            }

            //1부터 6까지 주사위를 더해 다음으로 넘어간다.
            for(int dice = 1; dice <=6; dice++){
                int next_location = temp.current_location + dice;
                //값이 칸을 넘어가면 패스
                if(next_location >= 100){
                    continue;
                }

                //뱀이나 사다리가 있으면 해당 포털을 타고 이돟한다.
                int next_arrive_location = map[next_location / 10][next_location % 10];


                //이미 확인을 한 곳이라면 패스
                if(check_map[next_arrive_location / 10][next_arrive_location % 10]){
                    continue;
                }

                //큐에 해당 위치를 새로 추가한다.
                check_map[next_arrive_location / 10][next_arrive_location % 10] = true;
                queue.offer(new GameRound(next_arrive_location, temp.current_dice_count + 1));
            }

        }

        //결과 출력
        System.out.println(result);
    }
}