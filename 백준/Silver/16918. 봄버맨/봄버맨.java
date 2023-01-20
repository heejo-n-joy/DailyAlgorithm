import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 봄버맨의 폭탄 폭탄
 * <p>
 * 요구하는 출력
 * - N초가 흐른 후의 격자판 상태
 * <p>
 * 입력 변수
 * - R : 보드의 세로 크기
 * - C : 보드의 가로 크기
 * - N : 시간
 * - 보드의 정보
 * <p>
 * 문제 유의사항
 * - 폭탄은 3초 뒤에 터진다.
 * - 폭탄이 터질 때는 상하좌우 칸이 모두 초기화된다.
 * <p>
 * 전략
 * - 시간의 흐름에 따라 실행을 다르게 진행하면 간단하게 풀 수 있을 것 같다.
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 회고
 * - O와 0을 구분하기 어려운 백준 홈페이지..
 *
 * @author HeejoPark
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());   //보드의 세로 크기
        int C = Integer.parseInt(st.nextToken());   //보드의 가로 크기
        int N = Integer.parseInt(st.nextToken());   //시간
        char[][] board = new char[R][C];
        char nowBomb = 'A';
        char nextBomb = 'B';
        char vacant = '.';
        for (int i = 0; i < R; i++) {
            String str = br.readLine();
            for (int j = 0; j < C; j++) {
                board[i][j] = str.charAt(j);
                if(board[i][j] == 'O'){
                    board[i][j] = nowBomb;  //처음 터질 폭탄
                }
            }
        }
        //처음 1초에는 아무 일도 일어나지 않기 때문
        for(int time = 2; time<=N; time++){
            //폭탄이 설치되어있지 않은 모든 칸에 폭탄을 설치한다
            if(time%2 == 0){
                for(int i =0 ;i<R; i++){
                    for(int j =0; j<C; j++){
                        if(board[i][j]==vacant){
                            board[i][j] = nextBomb;
                        }
                    }
                }
            }
            //폭탄
            else{
                boolean[][] bombArea = new boolean[R][C];
                for(int i =0; i<R; i++){
                    for(int j = 0; j<C; j++){
                        if(board[i][j] == nowBomb){
                            bombArea[i][j] = true;
                            for(int k = 0; k<4; k++){
                                int nearI = i + dr[k];
                                int nearJ = j + dc[k];

                                //만약 해당 지역이 보드 범위를 벗어나면 패스
                                if(nearI < 0 || nearI >= R || nearJ < 0 || nearJ >=C){
                                    continue;
                                }

                                //폭탄 주변 지역은 터질 곳임을 체크
                                bombArea[nearI][nearJ] = true;
                            }
                        }
                    }
                }

                //모든 지역 탐색이 끝나면 해당 부분들을 다 초기화한다.
                for(int i =0; i<R; i++){
                    for(int j =0; j<C; j++){
                        if(bombArea[i][j]){
                            board[i][j] = vacant;
                        }
                    }
                }

                //폭탄을 터뜨렸으니, 다음 폭탄은 지금 폭탄으로 변경해주고, 지금 폭탄은 다음 폭탄으로 변경한다.
                char temp = nowBomb;
                nowBomb = nextBomb;
                nextBomb = temp;
            }
        }

        //결과 출력
        for(int i =0; i<R; i++){
            for(int j =0; j<C; j++){
                if(board[i][j] == vacant){
                    System.out.print(vacant);
                }
                else{
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
}