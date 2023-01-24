import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 철수의 토마토 농장
 * <p>
 * 요구하는 출력
 * - 토마토가 다 익을 수 있는 최소 일수
 * <p>
 * 입력 변수
 * - M,N,H : 상자의 가로, 세로, 높이
 * - 상자의 정보들 (1은 익은 토마토, 0은 익지 않은 토마토, -1은 빈 공간)
 * <p>
 * 문제 유의사항
 * - 토마토가 스스로 익는 경우는 없고, 다 익지 못하면 -1을 출력
 * <p>
 * 전략
 * - M이 가로를, N이 세로를 의미하기 때문에, map[N][M]으로 바꾸자
 * - Queue를 활용해서 익은 토마토들을 계속해서 담아두고 계산을 돌리자
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 회고
 *
 * @author HeejoPark
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());   //토마토 상자의 가로 크기
        int N = Integer.parseInt(st.nextToken());   //토마토 상자의 세로 크기
        int H = Integer.parseInt(st.nextToken());   //토마토 상자의 높이
        int[][][] box = new int[H][N][M];   //토마토 상자
        int greenTomatoCount = 0;
        for (int h = 0; h < H; h++) {   //상자에 대한 정보 입력받기
            for (int n = 0; n < N; n++) {
                st = new StringTokenizer(br.readLine());
                for (int m = 0; m < M; m++) {
                    box[h][n][m] = Integer.parseInt(st.nextToken());
                    if(box[h][n][m] == 0){
                        greenTomatoCount++; //익지 않은 토마토 개수
                    }
                }
            }
        }

        //실행하기 전, Queue에 익은 토마토면서 주변에 안익은 토마토가 있는 토마토 위치들을 넣기
        Queue<int[]> tomatoes = new LinkedList<>();
        for(int h = 0; h < H; h++){
            for(int n = 0; n < N; n++){
                for(int m = 0; m < M; m++){
                    //만약 해당 토마토가 익었다면
                    if(box[h][n][m] == 1){
                        tomatoes.offer(new int []{h, n, m, 0});    //큐에 넣는다. 마지막은 시간
                    }
                }
            }
        }

        int totalTime = 0;
        //실행
        while(!tomatoes.isEmpty()){
            int[] temp = tomatoes.poll();   //토마토를 꺼내서
            int h = temp[0];
            int n = temp[1];
            int m = temp[2];
            int t = temp[3];
            totalTime = Math.max(totalTime, t);
            //주변의 토마토들을 물든다.
            for(int k = 0; k<6; k++){
                int nextH = h + dh[k];
                int nextN = n + dr[k];
                int nextM = m + dc[k];
                //해당 위치가 상자 범위를 벗어나면 그냥 패스
                if(nextH < 0 || nextH >= H || nextN < 0 || nextN >= N || nextM < 0 || nextM >= M){
                    continue;
                }
                //해당 위치의 토마토가 안익었다면 토마토를 익히고 익힌 명단에 추가
                if(box[nextH][nextN][nextM] == 0){
                    box[nextH][nextN][nextM] = 1;
                    tomatoes.offer(new int[]{nextH, nextN, nextM, t + 1});
                    greenTomatoCount--;
                }
            }
        }

        //만약 모든 토마토를 바꾸지 못했다면
        if(greenTomatoCount > 0){
            totalTime = -1;
        }

        //결과 출력
        System.out.println(totalTime);
    }

    static int[] dr = {-1, 1, 0, 0, 0, 0};
    static int[] dc = {0, 0, -1, 1, 0, 0};
    static int[] dh = {0, 0, 0, 0, -1, 1};
}