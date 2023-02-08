import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * (예전에 시도했지만 풀지 못했던) 문제 내용
 * - 스도쿠를 완성하기
 * <p>
 * 요구하는 출력
 * - 완성된 스도쿠
 * <p>
 * 전략
 * - 비어있는 자리들을 따로 저장
 * - 하나씩 칸을 확인하면서, 가능한 경우의 수를 확인하고 하나씩 넣어본다.
 * - 만약 오류가 생긴다면, 해당 계산을 중단한다.
 *
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    static final int N = 9;
    static boolean alreadyFinished;
    public static void main(String[] args) throws Exception {
        alreadyFinished = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] map = new int[N][N];
        List<int[]> emptySquares = new ArrayList<>();
        for(int i = 0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j =0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 0){
                    emptySquares.add(new int[]{i, j});
                }
            }
        }
        int len = emptySquares.size();
        calc(emptySquares, map, len, 0);
    }
    public static void calc(List<int[]> emptySquares, int[][] map, int len, int current_count){
        //이미 구해진 정답이 있으면 종료
        if(alreadyFinished){
            return;
        }
        //완성을 했다면 출력하고 종료
        if(current_count == len){
            print(map);
            alreadyFinished = true;
            return;
        }
        int x = emptySquares.get(current_count)[0];
        int y = emptySquares.get(current_count)[1];
        for(int num = 1; num<=9; num++){
            //이미 구해진 정답이 있으면 종료
            if(alreadyFinished){
                return;
            }
            //수평에서 숫자가 중복된다면 패스
            if(impossibleHorizontal(x, y, num, map)){
                continue;
            }
            //수직에서 숫자가 중복된다면 패스
            if(impossibleVertical(x,y,num,map)){
                continue;
            }
            //작은네모칸에서 숫자가 중복된다면 패스
            if(impossibleSquare(x,y,num,map)){
                continue;
            }
            //전부 통과를 하면 해당 위치에 숫자를 할당하고 다음으로
            map[x][y] = num;
            calc(emptySquares, map, len, current_count+1);
            map[x][y] = 0;
        }
    }

    public static boolean impossibleHorizontal(int x, int y, int num, int[][] map){
        for(int i = 0; i< 9; i++){
            if(i == y){
                continue;
            }
            if(map[x][i] == num){
                return true;
            }
        }
        return false;
    }

    public static boolean impossibleVertical(int x, int y, int num, int[][] map){
        for(int i = 0; i< 9; i++){
            if(i == x){
                continue;
            }
            if(map[i][y] == num){
                return true;
            }
        }
        return false;
    }

    public static boolean impossibleSquare(int x, int y, int num, int[][] map){
        int startX = x - (x % 3);
        int startY = y - (y % 3);
        for(int i = startX; i< startX + 3; i++){
            for(int j = startY; j <startY + 3; j++){
                if(i == x && j == y){
                    continue;
                }
                if(map[i][j] == num){
                    return true;
                }
            }
        }
        return false;
    }

    public static void print(int[][] map){
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}