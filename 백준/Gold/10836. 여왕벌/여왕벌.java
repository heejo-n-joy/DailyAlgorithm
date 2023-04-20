import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * 요구하는 출력 : 애벌레들이 성장한 후 마지막 저녁 애벌레들의 크기
 *
 * 잘못된 풀이 방법 : 시뮬레이션(시간초과)
 * - 1. 애벌레가 들어있는 벌집과 똑같은 사이즈의 배열을 만든다.
 * - 2. 해당 배열에 각 애벌레가 얼마만큼 성장하는지 작성한다.
 * - 3. 구한 값만큼 벌집의 애벌레에 반영한다.
 * => O(M*M*N)이라는 말도안되는 시간초과가 발생
 *
 * 보완
 * - 벌집도 만들 필요 없다.
 * - 맨 왼쪽 열을 제외하고, 나머지 모든 값은 맨 위의 행의 증가 에너지를 그대로 받기 때문이다.
 * - 맨 왼쪽 열의 에너지를 저장하는 1차원 배열, 맨 위의 행의 에너지를 저장하는 1차원 배열을 만들어, 그 곳에만 값을 갱신한다.
 * - 출력할 때만 값을 더해주면 O(N*M)이라는 시간 복잡도로 간단하게 구할 수 있다.
 *
 * 보완2
 * - 더 줄여야 한다..
 * - 에너지 0인 경우에는 과감하게 패스해버리자
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        StringBuilder sb = new StringBuilder();

        int M = Integer.parseInt(stringTokenizer.nextToken());    //벌집의 크기
        int N = Integer.parseInt(stringTokenizer.nextToken());    //날짜 수

        int[] temp_map = new int[2*M -1];
        for(int day =0; day<N; day++){

            //제일 왼쪽 열과 제일 왼쪽 행의 애벌레들의 성장 에너지를 반영한다.
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int index = Integer.parseInt(stringTokenizer.nextToken());   //값이 0만큼 패스한다.
            for(int energy = 1; energy<=2; energy++){
                int rest_energy = Integer.parseInt(stringTokenizer.nextToken());    //에너지 1과 2의 개수
                while(rest_energy > 0){
                    temp_map[index++] += energy;
                    rest_energy--;
                }
            }
        }

        //결과 출력
        for(int i =0; i<M; i++){
            for(int j = 0; j<M; j++){
                //열
                if(j == 0){
                    sb.append((1 + temp_map[M-1 - i]) + " ");
                }
                //행
                else{
                    sb.append((1 + temp_map[M - 1 + j]) + " ");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
