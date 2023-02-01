import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - N개의 줄을 규칙에 맞게 내려가기
 * <p>
 * 요구하는 출력
 * - 맨 밑까지 내려갔을 때 나올 수 있는 최대 점수와 최소 점수
 * <p>
 * 주의사항
 * - 내려갈 때는 자신의 줄이나 옆줄로만 이동할 수 있다.
 *
 * <p>
 * 전략
 * - DP문제.
 * - 최소값을 찾으면서 내려가기, 최대값을 찾으면서 내려가기
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //줄의 수
        int[][] map = new int[N][3];
        for(int i = 0; i<N; i++){
            StringTokenizer st = new StringTokenizer((br.readLine()));
            for(int j =0; j<3; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[][][] calcMap = new int[N][3][2];   //DP 계산을 위해 사용될 배열. [][][0]에는 MAX, [][][1]에는 MIN의 합을 저장한다.

        for(int i =0; i<N; i++){
            for(int j =0; j<3; j++){
                if(i==0){   //맨 첫 줄은 기존 입력값을 그대로 받아온다.
                    calcMap[i][j][0] = map[i][j];
                    calcMap[i][j][1] = map[i][j];
                    continue;
                }
                int tempMax = Integer.MIN_VALUE;    //현재 위치에 넣을 이전 줄의 최대값을 저장할 변수
                int tempMin = Integer.MAX_VALUE;    //현재 위치에 넣을 이전 줄의 최소값을 저장할 변수
                if(j-1>=0){ //이전 줄의 왼쪽을 참고할 수 있다면
                    tempMax = Math.max (calcMap[i-1][j-1][0], tempMax);
                    tempMin = Math.min (calcMap[i-1][j-1][1], tempMin);
                }
                if(j+1<3){  //이전 줄의 오른쪽을 참고할 수 있다면
                    tempMax = Math.max (calcMap[i-1][j+1][0], tempMax);
                    tempMin = Math.min (calcMap[i-1][j+1][1], tempMin);
                } 
                //이전 줄의 현재 위치를 참고
                tempMax = Math.max(calcMap[i-1][j][0], tempMax);
                tempMin = Math.min(calcMap[i-1][j][1], tempMin);
                
                //선택된 이전 줄의 최대값, 최소값을 넣는다.
                calcMap[i][j][0] = map[i][j] + tempMax;
                calcMap[i][j][1] = map[i][j] + tempMin;
            }
        }


        //마지막 줄의 3개에서 가장 큰 값, 가장 작은 값을 선별한다.
        int resultMax = Math.max(calcMap[N-1][0][0], Math.max(calcMap[N-1][1][0], calcMap[N-1][2][0]));
        int resultMin = Math.min(calcMap[N-1][0][1], Math.min(calcMap[N-1][1][1], calcMap[N-1][2][1]));

        //결과 출력
        System.out.println(resultMax + " " + resultMin);
    }

}