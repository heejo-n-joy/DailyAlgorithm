import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 가장 큰 정사각형
 *
 * 아이디어 : DP
 * - 2차원 배열 탐색 (이중 for 문)으로 풀이한다.
 * - 해당 위치의 값이 1일 때: 좌, 상, 좌상의 값들 중 최소값에 +1을 하기
 * - 해당 위치의 값이 0일 때: 0
 * - 이후, 가장 큰 값을 찾아 리턴한다.
 */

public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());   //배열의 세로
        int M = Integer.parseInt(st.nextToken());   //배열의 가로

        //배열 입력받기
        int max = 0;
        int[][] array = new int[N][M];
        for(int i = 0; i<N; i++){
            String str = br.readLine();
            for(int j = 0; j<M; j++){
                array[i][j] = str.charAt(j)-'0';
                max = Math.max(array[i][j], max);
            }
        }

        //계산
        for(int i = 1; i<N; i++){
            for(int j = 1; j<M; j++){
                //배열 값이 1이라면
                if(array[i][j] == 1){
                    //좌, 상, 좌상의 값을 모두 확인
                    array[i][j] += Math.min(Math.min(array[i-1][j], array[i][j-1]), array[i-1][j-1]);
                    max = Math.max(array[i][j], max);
                }
            }
        }

        //결과 출력
        System.out.println(max * max);
    }
}
