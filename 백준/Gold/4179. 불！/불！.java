import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 지훈이가 일하는 미로에서 불이 났다
 * <p>
 * 요구하는 출력
 * - 탈출할 수 있는지, 탈출할 수 있으면 가장 빠른 탈출시간은 몇 분인지
 * <p>
 * 전략
 * - BFS로 풀기
 * - 똑같은 맵이 두 개 존재
 * - 첫 번째 맵에는 불을 지펴본다. (몇 분에 어디 구역에 불이 나는지 확인)
 * - 두 번째 맵에는 지훈이를 대피시킨다. (몇 분에 어디 구역은 가지 못함을 첫 번째 맵으로 체크)
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());   //미로 행의 개수
        int C = Integer.parseInt(st.nextToken());   //미로 열의 개수
        int[][] map_fire = new int[R][C];   //불이 번지는 시간을 확인하는 맵
        int[][] map_jihoon = new int[R][C]; //지훈이가 해당 지역을 통과할 수 있는 시간
        int[] start_jihoon = new int[2];    //지훈이의 시작 위치
        boolean[][] check_fire = new boolean[R][C];  //불 번짐 체크용 배열
        boolean[][] check_jihoon = new boolean[R][C];   //지훈이 이동 체크용 배열
        Queue<int[]> fires = new LinkedList<>();    //불의 확산
        Queue<int[]> jihoons = new LinkedList<>();  //지훈이의 도망
        for(int i =0; i<R; i++){
            String str = br.readLine();
            for(int j =0; j<C; j++){
                switch(str.charAt(j)){
                    case '#':   //벽이라면
                        map_fire[i][j] = -1;
                        map_jihoon[i][j] = -1;
                        break;
                    case '.':   //지나갈 수 있는 공간이라면
                        break;
                    case 'F':   //
                        fires.offer(new int[]{i, j, 0});
                        check_fire[i][j] = true;
                        map_fire[i][j] = 0;
                        break;
                    case 'J':   //지훈이의 시작 위치
                        start_jihoon[0] = i;
                        start_jihoon[1] = j;
                        check_jihoon[i][j] = true;
                        jihoons.offer(new int[]{i, j, 1});
                        break;
                    default:
                        break;
                }
            }
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        //불 시뮬레이션
        while (!fires.isEmpty()){
            int[] temp = fires.poll();
            for(int k =0; k<4; k++){
                int nextR = temp[0] + dr[k];
                int nextC = temp[1] + dc[k];
                //배열 밖으로 벗어나면 패스
                if(nextR < 0 || nextR >= R || nextC < 0 || nextC >= C){
                    continue;
                }
                //해당 위치를 이미 확인해봤다면 패스
                if(check_fire[nextR][nextC]){
                    continue;
                }
                //해당 위치에 이미 불이 있다면 패스
                if(map_fire[nextR][nextC] > 0){
                    continue;
                }
                //해당 위치가 벽이라면 패스
                if(map_fire[nextR][nextC] == -1){
                    continue;
                }
                //해당 위치에 불을 붙인다.
                check_fire[nextR][nextC] = true;
                map_fire[nextR][nextC] = temp[2]+1;
                fires.offer(new int[]{nextR, nextC, temp[2]+1});
            }
        }
        int result = Integer.MAX_VALUE; //결과값

        //지훈 시뮬레이션
        while(!jihoons.isEmpty()){
            int[] temp = jihoons.poll();
            //해당 위치가 탈출 장소라면 종료
            if(temp[0] == 0 || temp[0] == R-1 || temp[1] == 0 || temp[1] == C-1){
                result = Math.min(temp[2], result);
                break;
            }
            for(int k = 0; k<4; k++){
                int nextR = temp[0] + dr[k];
                int nextC = temp[1] + dc[k];
                //배열 밖으로 벗어나면 패스
                if(nextR < 0 || nextR >= R || nextC < 0 || nextC >= C){
                    continue;
                }
                //해당 위치를 이미 확인해봤다면 패스
                if(check_jihoon[nextR][nextC]){
                    continue;
                }
                //해당 위치가 벽이라면 패스
                if(map_jihoon[nextR][nextC] == -1){
                    continue;
                }
                //해당 위치에 해당 시간에 이미 불이 있다면 패스
                if(check_fire[nextR][nextC] && map_fire[nextR][nextC] <= temp[2]){
                    continue;
                }
                //해당 위치로 도망을 간다.
                check_jihoon[nextR][nextC] = true;
                map_jihoon[nextR][nextC] = temp[2]+1;
                jihoons.offer(new int[]{nextR, nextC, temp[2]+1});
            }
        }
        //결과 출력
        if(result == Integer.MAX_VALUE){
            System.out.println("IMPOSSIBLE");
        }
        else{
            System.out.println(result);
        }
    }
}