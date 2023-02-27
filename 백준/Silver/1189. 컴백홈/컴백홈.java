import java.util.Scanner;

/**
 * 문제 내용
 * - 한수가 캠프를 마치고 집에 들어가려고 한다.
 * <p>
 * 요구하는 출력
 * - 집에 돌아가는 길ㄹ이 K인 경우의 수
 * <p>
 * 전략
 * - T는 지나갈 수 없다.
 * - 지도의 크기는 최대 5x5이니, DFS로 문제를 풀어보자
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */

public class Main {
    static int totalCount;
    static int R;
    static int C;
    static int K;
    static char[][] map;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();   //지도의 행
        C = sc.nextInt();   //지도의 열
        K = sc.nextInt();   //필요한 거리
        map = new char[R][C];  //지도
        totalCount = 0;
        for (int i = 0; i < R; i++) {
            String str = sc.next();
            for (int j = 0; j < C; j++) {
                map[i][j] = str.charAt(j);
            }
        }
        //실행
        map[R-1][0] = 'O';
        Calc(1, R-1, 0);
        //결과 출력
        System.out.println(totalCount);
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void Calc(int currentCount, int currentR, int currentC){
        //도착지에 도착을 한다면
        if(currentR == 0 && currentC == C-1){
            if(currentCount == K){  //거리가 되는지 보고 카운트 증가하기
                totalCount++;
            }
            return;
        }
        //상하좌우 탐색을 하자
        for(int k =0; k<4; k++){
            int nextR = currentR + dr[k];   //다음 위치의 행
            int nextC = currentC + dc[k];   //다음 위치의 열
            //지도 범위를 벗어나면 패스
            if(nextR < 0 || nextR >= R || nextC < 0 || nextC >= C){
                continue;
            }
            //만약 해당 위치에 장애물(T)이 있다면 패스
            if(map[nextR][nextC] == 'T'){
                continue;
            }
            //만약 이미 방문한 곳이라면 패스
            if(map[nextR][nextC] == 'O'){
                continue;
            }
            //그렇다면 방문을 하자
            map[nextR][nextC] = 'O';    //방문 체크
            Calc(currentCount+1, nextR, nextC);
            map[nextR][nextC] = '.';    //다시 원위치
        }
    }
}