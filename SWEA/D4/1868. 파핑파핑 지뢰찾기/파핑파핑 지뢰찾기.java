import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * No.14 : 사칙연산 유효성 검사
 *
 * 문제 내용
 * - 지뢰찾기를 만들기
 *
 * 요구하는 출력
 * - 지뢰가 없는 모든 칸에 숫자가 표시되려면 최소 몇 번의 클릭을 해야 하는가?
 *
 * 입력
 * - N : 보드의 크기
 * - 보드 정보
 *
 *  전략
 *  - 우선 0이 확정된 구역부터 클릭을 하자.
 *  - 0 구역 클릭이 모두 끝났다면 나머지는 칸 별로 1번씩 클릭해야 한다.
 *  - 즉, 0을 클릭하는 최소 횟수 + 나머지 칸을 계산한다.
 *
 *  난이도(예상/실제) : 2
 *
 * @author HeejoPark
 */

public class Solution {

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        final int BOMB = -2;
        final int EMPTY = -1;
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());    //보드의 크기
            int[][] map = new int[N][N];
            int rest_count = 0;
            for(int i =0; i<N; i++){
                String str = br.readLine();
                for(int j=0; j<N; j++){
                    char temp = str.charAt(j);
                    switch(temp){
                        case '.':   //빈 칸
                            map[i][j] = EMPTY;
                            rest_count++;
                            break;
                        case '*':   //지뢰가 있는 칸
                            map[i][j] = BOMB;
                            break;
                        default:
                            //ERROR
                            return;
                    }
                }
            }
            //모든 칸을 하나씩 확인하면서, 해당 칸이 0으로 표시될 수 있다면
            boolean[][] check = new boolean[N][N];
            int result = 0;
            for(int i =0; i<N; i++){
                searchSquare: for(int j =0; j<N; j++){
                    //현재 위치가 체크가 됐거나, 지뢰라면 패스
                    if(check[i][j] || map[i][j] == BOMB){
                        continue;
                    }
                    //현재 위치를 기준으로 8방향에 지뢰가 있나 없나 확인한다.
                    for(int dr =-1; dr<=1; dr++){
                        for(int dc = -1; dc<=1; dc++){
                            //자기 자신은 패스
                            if(dc==0 && dr==0){
                                continue;
                            }
                            int nextX = i + dr;
                            int nextY = j + dc;
                            //범위를 벗어나면 패스
                            if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){
                                continue;
                            }
                            //만약 해당 위치에 지뢰가 있다면
                            if(map[nextX][nextY] == BOMB){
                                continue searchSquare;
                            }
                        }
                    }
                    //지뢰가 없다면 큐에 넣어, 주변을 탐색한다.
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{i, j});
                    result++;
                    rest_count--;
                    check[i][j] = true;
                    searchDeepSquare: while(!queue.isEmpty()){
                        int[] temp = queue.poll();
                        //현재 위치를 기준으로 8방향에 지뢰가 있나 없나 확인한다.
                        Queue<int[]> tempQueue = new LinkedList<>();
                        for(int dr =-1; dr<=1; dr++){
                            for(int dc = -1; dc<=1; dc++){
                                //자기 자신은 패스
                                if(dc==0 && dr==0){
                                    continue;
                                }
                                int nextX = temp[0] + dr;
                                int nextY = temp[1] + dc;
                                //범위를 벗어나면 패스
                                if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){
                                    continue;
                                }
                                //만약 해당 위치에 지뢰가 있다면 종료
                                if(map[nextX][nextY] == BOMB){
                                    continue searchDeepSquare;
                                }
                                //해당 위치는 이미 파악이 됐다면 패스
                                if(check[nextX][nextY]){
                                    continue;
                                }
                                //내부 큐에 삽입
                                tempQueue.offer(new int[]{nextX, nextY});
                            }
                        }
                        //8방향 모두 이상이 없다면 확인을 체크하고 내부 큐의 위치들을 외부 큐에 넣는다.
                        while(!tempQueue.isEmpty()){
                            temp = tempQueue.poll();
                            check[temp[0]][temp[1]] = true;
                            queue.offer(temp);
                            rest_count--;
                        }
                    }

                }
            }
            //체크가 되지 않았다면 체크하고 큐에 삽입
            result += rest_count;
//            System.out.println("#" + test_case + " " + result + " , " + rest_count);
            System.out.println("#" + test_case + " " + result);
        }
    }
}