import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 도시별 도착하는 최소시간
 *
 * 풀이 방법 : 플로이드-워셜(Floyd-Warshall)
 * - 모든 도시별 경로를 저장하는 2차원 배열 활용
 *
 */
public class Main {

    static final int IMPOSSIBLE_MAX_VALUE = 10000000;

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());    //도시의 개수
        int m = Integer.parseInt(bufferedReader.readLine());    //버스의 개수

        //맵 생성. 초기값을 불가능한 최대 수치로 설정
        int[][] map = new int[n][n];
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                //자기 자신으로 가는 길은 무조건 0
                if(i == j){
                    continue;
                }
                map[i][j] = IMPOSSIBLE_MAX_VALUE;
            }
        }

        //주어진 버스 경로 입력하기
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
            int a = Integer.parseInt(st.nextToken());   //출발 도시
            int b = Integer.parseInt(st.nextToken());   //도착 도시
            int c = Integer.parseInt(st.nextToken());   //비용
            map[a-1][b-1] = Math.min(map[a-1][b-1], c); //경로가 여러 개라면 최소값만 사용하기
        }

        //도시를 하나씩 보면서 탐색하기
        for(int city =0; city<n; city++){
            for(int i = 0; i<n; i++){
                for(int j = 0; j<n; j++){
                    //자기 자신이라면 패스한다.
                    if(i == j){
                        continue;
                    }
                    map[i][j] = Math.min(map[i][j], map[i][city] + map[city][j]);
                }
            }
        }

        //결과 출력
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(map[i][j] == IMPOSSIBLE_MAX_VALUE){
                    sb.append("0 ");
                }
                else {
                    sb.append(map[i][j] + " ");
                }
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }
}