import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 애벌레들이 성장한 후 마지막 저녁 애벌레들의 크기
 *
 * 풀이 방법 : 시뮬레이션
 * - 1. 애벌레가 들어있는 벌집과 똑같은 사이즈의 배열을 만든다.
 * - 2. 해당 배열에 각 애벌레가 얼마만큼 성장하는지 작성한다.
 * - 3. 구한 값만큼 벌집의 애벌레에 반영한다.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        StringBuilder sb = new StringBuilder();

        int M = Integer.parseInt(stringTokenizer.nextToken());    //벌집의 크기
        int N = Integer.parseInt(stringTokenizer.nextToken());    //날짜 수

        int[][] original_map = new int[M][M];   //벌집
        for(int i = 0; i<M; i++){
            for(int j = 0; j<M; j++){
                original_map[i][j] = 1;
            }
        }

        for(int day =0; day<N; day++){
            int[][] temp_map = new int[M][M];   //계산용 맵

            //제일 왼쪽 열과 제일 왼쪽 행의 애벌레들의 성장 에너지를 반영한다.
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int[] increase_count = new int[3];  //주어진 0,1,2 에너지의 개수를 담는다.
            for(int i = 0; i<3; i++){
                increase_count[i] = Integer.parseInt(stringTokenizer.nextToken());
            }
            int increase_index = 0;
            for(int i = 0; i < M * 2 -1; i++){
                //혹시 현재 크기의 에너지 개수가 0개라면
                while(increase_count[increase_index] == 0){
                    //다음 크기의 에너지로 넘어간다.
                    increase_index++;
                }
                //(0,0)으로 왔다면, 이제는 행으로(우측으로) 이동한다.
                if(i >= M){
                    temp_map[0][i-M + 1] = increase_index;
                }
                //(0,0)에 오지 않았다면, 열로(위로) 이동한다.
                else {
                    temp_map[M-1-i][0] = increase_index;
                }
                //에너지 개수를 1 감소한다.
                increase_count[increase_index]--;
            }

            //나머지 애벌레들의 성장 에너지를 계산한다.
            for(int i = 1; i<M; i++){
                for(int j = 1; j<M; j++){
                    temp_map[i][j] = calcMax(temp_map[i-1][j-1], temp_map[i][j-1], temp_map[i-1][j]);
                }
            }

            //모든 애벌레들의 성장 에너지를 기존에서 더한다.
            for(int i = 0; i<M; i++){
                for(int j = 0; j<M; j++){
                    original_map[i][j] += temp_map[i][j];
                }
            }
        }

        //결과 출력
        for(int i =0; i<M; i++){
            for(int j = 0; j<M; j++){
                sb.append(original_map[i][j] + " ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    public static int calcMax(int a, int b, int c){
        return Math.max(a, Math.max(b, c));
    }
}