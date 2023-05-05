import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 과수원에서 K*K의 칸을 수확할 때, 가장 많은 총 이익을 구하기
 *
 * 풀이 방법 : 완전 탐색 (근데 누적 합을 곁들인)
 * 1. 과수원 위치 (x,y)에 값을 (0,0)부터 (x,y)까지 직사각형의 순이익 합을 적어두기
 * 2. 모든 지역에 정사각형을 만들어보기
 *   2-1. 오른쪽 아래 (c,d) 점을 기준으로, 1x1부터 차근차근 정사각형 사이즈를 늘린다.
 *   2-2. 왼쪽 위 (a,b) 점을 잡았다면, S(c,d) - S(a,d) - S(c,b) + S(a,b)로 계산하기
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //과수원의 크기
        int[][] map = new int[N+1][N+1];
        //과수원을 누적 합으로 적어두기
        for(int i = 1; i<=N; i++){
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            for(int j = 1; j<=N; j++){
                map[i][j] = map[i][j-1] + Integer.parseInt(stringTokenizer.nextToken());
            }
            for(int j = 1; j<=N; j++){
                map[i][j] += map[i-1][j];
            }
        }

        //최대값
        int result = -1000;

        //끝 지점을 하나씩 고르고 해당 위치로부터 k칸 이전까지의 k*k 넓이를 구해보기
        for(int i = 0; i<=N; i++){
            for(int j = 0; j<=N; j++){
                //해당 위치로부터 이전 칸을 세보자
                for(int k = 1; k<= Math.min(i, j); k++){
                    result = Math.max(map[i][j] - map[i-k][j] - map[i][j-k] + map[i-k][j-k], result);
                }
            }
        }

        //결과 출력
        System.out.println(result);
    }
}
