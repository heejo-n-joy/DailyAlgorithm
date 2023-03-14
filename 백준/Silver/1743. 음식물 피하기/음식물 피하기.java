import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 선생님이 피해야 할 갖아 큰 음식물 덩어리
 * <p>
 * 이 문제는 어떻게 풀까? BFS
 * - 가장 큰 덩어리를 찾자
 *
 * @author HeejoPark
 */

class Loc{
    int r;
    int c;
    Loc(int r, int c){
        this.r = r;
        this.c = c;
    }
}
class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        boolean[][] map = new boolean[N][M];
        boolean[][] check = new boolean[N][M];
        for(int i =0; i<K; i++){
            int r = sc.nextInt();
            int c = sc.nextInt();
            map[r-1][c-1] = true;
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int result = 0;
        for(int i =0; i<N; i++){
            for(int j =0; j<M; j++){
                //음식물 쓰레기가 없으면 패스
                if(map[i][j] == false){
                    continue;
                }
                //이미 확인한 곳이면 패스
                if(check[i][j]){
                    continue;
                }
                //확인 안한 음식물 쓰레기 덩어리를 발견. 조사 시작
                Queue<Loc> queue = new LinkedList<>();
                queue.offer(new Loc(i, j));
                check[i][j] = true;
                int current_count = 1;  //현재 탐색의 결과
                while(!queue.isEmpty()){
                    Loc loc = queue.poll();
                    //상하좌우 탐색
                    for(int k =0; k<4; k++){
                        Loc nextLoc = new Loc(loc.r + dr[k], loc.c + dc[k]);
                        //배열 범위를 벗어나면 패스
                        if(nextLoc.r < 0 || nextLoc.r >= N || nextLoc.c < 0 || nextLoc.c >= M){
                            continue;
                        }
                        //이미 확인을 한 곳이라면 패스
                        if(check[nextLoc.r][nextLoc.c]){
                            continue;
                        }

                        //음식물 쓰레기가 없다면 패스
                        if(map[nextLoc.r][nextLoc.c] == false){
                            continue;
                        }

                        //그게 아니라면 큐에 포함
                        queue.offer(nextLoc);
                        check[nextLoc.r][nextLoc.c] = true;
                        current_count++;
                    }
                }
                //해당 덩어리가 최대값이라면 기존 값을 갱신
                result = Math.max(result, current_count);
            }
        }

        //결과 출력
        System.out.println(result);
    }
}