import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;

/**
 * 문제: 멕시노스들의 전선을 배치하기
 * 출력: 최대한 많은 코어에 전원을 연결한 후 전선 길이의 합 (여러 방법이 있다면 최소값으로)
 *
 * @author HeejoPark
 */
class Solution
{
    static int max_connected_core_count;
    static int min_connected_lines_sum;
    public static void main(String args[]) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();

        for(int test_case = 1; test_case <= T; test_case++)
        {
            max_connected_core_count = Integer.MIN_VALUE;
            min_connected_lines_sum = Integer.MAX_VALUE;
            List<int[]> cores = new ArrayList<>();
            int N = sc.nextInt();
            int[][] map = new int[N][N];
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    map[i][j] = sc.nextInt();
                    if(map[i][j]==1){   //코어를 발견하면
                        cores.add(new int[]{i, j}); //코어 정보를 넣기
                    }
                }
            }
            calc(cores, map, N, cores.size(), 0, 0, 0);
            System.out.println("#" + test_case + " " + min_connected_lines_sum);
        }
    }
    public static void calc(List<int[]> cores, int[][] map, int N, int cores_count, int current_count, int possible_count, int possible_lines_sum){
        //모든 코어를 확인했다면
        if(current_count == cores_count){
            //가능한 코어수가 갱신되거나, 코어수가 같은데 전선 길이가 기존보다 더 작다면
            if((possible_count > max_connected_core_count)
            ||((possible_count == max_connected_core_count) && (possible_lines_sum < min_connected_lines_sum))){
                //갱신
                max_connected_core_count = possible_count;
                min_connected_lines_sum = possible_lines_sum;
            }
            return;
        }
        //현재 코어를 살펴보기
        int x = cores.get(current_count)[0];
        int y = cores.get(current_count)[1];
        //현재 코어가 테두리에 있다면 전선 배치 없이 다음 코어로 직행
        if(x == 0 || x == N-1 || y == 0 || y == N-1){
            calc(cores, map, N, cores_count, current_count+1, possible_count+1, possible_lines_sum);
        }
        else{
            //상하좌우를 탐색
            for(int k = 0; k <4; k++) {
                List<int[]> tempList = new ArrayList<>();
                int nextX = x;
                int nextY = y;
                boolean valid = false;
                //선택한 코어의 해당 방향으로 끝까지 확인하기
                while(true){
                    nextX += dr[k];
                    nextY += dc[k];
                    //해당 위치가 유효한지 확인
                    if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){
                        break;
                    }
                    //해당 위치에 다른 전선이나 코어가 있는지 확인
                    if(map[nextX][nextY] > 0){
                        //있다면, 해당 방향으로는 낼 수 없으니 break
                        valid = false;
                        break;
                    }
                    else {
                        //없다면, 해당 방향으로 낼 수 있으니 계속한다.
                        valid = true;
                        tempList.add(new int[]{nextX, nextY});
                    }
                }
                //만약 해당 방향으로 전선을 낼 수 있다면
                if(valid){
                    for(int i =0; i<tempList.size(); i++){
                        map[tempList.get(i)[0]][tempList.get(i)[1]] = 2;
                    }
                    calc(cores, map, N, cores_count, current_count+1, possible_count+1, possible_lines_sum+tempList.size());
                    for(int i =0; i<tempList.size(); i++){
                        map[tempList.get(i)[0]][tempList.get(i)[1]] = 0;
                    }
                }
            }
            //어떤 방향으로도 낼 수 없을 때의 값도 계산해야 한다.
            calc(cores, map, N, cores_count, current_count+1, possible_count, possible_lines_sum);
        }
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
}