import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 판다가 가장 많이 칸을 이동할 수 있는 수
 *
 * 풀이 방법 : DP + 깊이우선탐색
 * - DP에는 현재 위치에서 최대한 많은 칸을 이동할 수 있는 수가 저장되어 있다.
 * - 상하좌우를 기준으로 저장된 DP들 중 최대값 + 1을 저장한다.
 * - 만약 DP값이 갱신이 되어있지 않다면 (-1), 그 위치에서 다시 탐색을 진행하도록 한다.
 *
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //대나무 숲
        int[][] bamboo = new int[N][N]; //대나무 숲의 정보
        int[][] DP = new int[N][N];     //결과값을 저장할 배열
        for(int i = 0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j<N; j++){
                bamboo[i][j] = Integer.parseInt(st.nextToken());
                DP[i][j] = -1;  //기본값은 -1. 이는 탐색이 되지 않았음을 의미한다.
            }
        }

        //실행
        for(int i =0 ;i<N; i++){
            for(int j = 0; j < N; j++){
                function(i, j, N, bamboo, DP);
            }
        }

        //결과 계산
        int max_val = 0;
        for(int i = 0; i<N; i++){
            for(int j = 0; j <N; j++){
                max_val = Math.max(DP[i][j], max_val);
            }
        }

        //결과 출력
        System.out.println(max_val);
    }

    //상하좌우 탐색을 위한 direction 설정
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void function(int x, int y, int N, int[][] bamboo, int[][] DP){
        //이미 해당 지역의 최대 경로가 파악이 됐다면, 탐색을 하지 않는다.
        if(DP[x][y] != -1){
            return;
        }
        
        //경로 파악을 위해 상하좌우 탐색
        for(int k = 0; k<4; k++){
            int nextX = x + dr[k];
            int nextY = y + dc[k];

            //상하좌우가 배열 범위를 벗어났다면 패스
            if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){
                continue;
            }

            //상하좌우의 대나무 양이 현재 위치보다 적거나 같다면 패스
            if(bamboo[x][y] >= bamboo[nextX][nextY]){
                continue;
            }

            //만약 DP가 계산이 안됐다면, 그 지역 DP를 먼저 검색하기
            if(DP[nextX][nextY] == -1){
                function(nextX, nextY, N, bamboo, DP);
            }
            
            //그 지역 DP를 기반으로 최대 경로 갱신하기
            DP[x][y] = Math.max(DP[x][y], DP[nextX][nextY] + 1);
        }
        
        //만약 상하좌우 탐색에서 아무 방향도 가지 못했다면, 현재 위치가 주변에서 가장 양이 많은 곳이니 값을 1로 설정한다.
        if(DP[x][y] == -1){
            DP[x][y] = 1;
        }
    }
}