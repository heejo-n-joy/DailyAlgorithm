import java.util.HashSet;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 격자판의 임의의 위치에서 시작하는 7자리 수들의 개수를 구하라
 * <p>
 * 이 문제는 어떻게 풀까?
 * - HashSet을 이용하자
 * - HashSet은 중복을 알아서 제거해준다.
 * - 또, HashSet.size()를 통해 중복되지 않은 값이 총 몇 개인지 알 수 있다.
 * - 모든 위치에서 출발해서 7자리 값을 만든 다음, HashSet에 넣자
 *
 * 시간복잡도
 * - 모든 위치 : 4^2
 * - 각 상하좌우로 6번 탐방 : 4^6
 * - 총 시간 : 4^8... 2^16=65536
 *
 * @author HeejoPark
 */
class Solution {
    static final int MAP_SIZE = 4;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int T = sc.nextInt();
        for (int test_case = 1; test_case <= T; test_case++) {
            //인풋값 입력받기
            int[][] map = new int[MAP_SIZE][MAP_SIZE];
            for(int i =0; i<MAP_SIZE; i++){
                for(int j =0; j<MAP_SIZE; j++){
                    map[i][j] = sc.nextInt();
                }
            }
            HashSet<Integer> hashSet = new HashSet();
            for(int i =0; i<MAP_SIZE; i++) {
                for(int j =0; j<MAP_SIZE; j++) {
                    execProgram(1, map[i][j], i, j, map, hashSet);
                }
            }
            //결과 출력
            sb.append("#"+ test_case + " " + hashSet.size() + "\n");
        }
        System.out.println(sb.toString());
    }
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void execProgram(int count, int current_number, int i, int j, int[][] map, HashSet<Integer> hashSet){
        if(count == 7){
            hashSet.add(current_number);
            return;
        }
        for(int direction = 0; direction<4; direction++){
            int nextI = i + dr[direction];
            int nextJ = j + dc[direction];
            //배열을 벗어나면 패스
            if(nextI < 0 || nextI >= MAP_SIZE || nextJ < 0 || nextJ >= MAP_SIZE){
                continue;
            }
            execProgram(count+1, current_number * 10 + map[nextI][nextJ],
                    nextI, nextJ, map, hashSet);
        }
    }
}