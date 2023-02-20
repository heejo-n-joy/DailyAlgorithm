import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 가로로 두칸 세로로 N칸이 존재하는 동물원
 * <p>
 * 요구하는 출력
 * - 세로 칸이 결정됨에 따라 사자들을 배치할 수 있는 경우의 수
 * <p>
 * 전략
 * - 우리에 사자를 배치할 수 있는 경우는 전 칸 같은 위치에 사자가 없어야 한다.
 * - 이전 줄에 사자가 없는 경우 / 왼쪽에 사자가 있는 경우 / 오른쪽에 있는 경우로 나누어 DP로 풀자
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    static final int REST = 9901;
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[][] map = new int[N][3];
        map[0][0] = 1;  //맨 첫 줄에 사자가 없는 경우의 수는 1
        map[0][1] = 1;  //맨 첫 줄에 사자가 왼쪽에 있는 경우의 수는 1
        map[0][2] = 1;  //맨 첫 줄에 사자가 오른쪽에 있는 경우의 수는 1
        for(int i = 1; i<N; i++){
            map[i][0] = sum(map[i-1][0], map[i-1][1], map[i-1][2]); //사자가 없는 경우는 이전 줄에 사자가 있거나 없거나 위치에 상관없이 모두 가능하다.
            map[i][1] = sum(map[i-1][0], map[i-1][2]);  //왼쪽에 사자를 넣으려면 이전 줄에 왼쪽에 사자가 있으면 안된다.
            map[i][2] = sum(map[i-1][0], map[i-1][1]);  //오른쪽에 사자를 넣으려면 이전 줄에 오른쪽에 사자가 있으면 안된다.
        }
        System.out.println(sum(map[N-1][0], map[N-1][1], map[N-1][2]));
    }

    public static int sum(int a, int b, int c){
        return (a + b + c) % REST;
    }

    public static int sum(int a, int b){
        return (a + b) % REST;
    }
}