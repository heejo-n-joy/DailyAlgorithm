import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * 요구하는 출력 : 두 문자열의 가장 긴 수열의 길이와 값
 *
 * (실패-메모리 초과)풀이 방법 : DP + String
 * - 1. 두 문자열의 가장 긴 수열의 길이를 저장하는 int 타입의 DP와, 수열을 저장하는 String 타입의 DP_str로 나눈다.
 * - 2. 현재 값이 일치하다면 대각선 왼쪽에서 + 현재 문자를, 일치하지 않다면 상하 중 긴 값을 가져오기
 * - 모든 과정에서 현재까지의 최대값을 String 타입으로 저장하려 하는 과정에서 메모리 초과가 발생했다.
 *
 * 풀이 방법 : 역추적
 * - 우선 DP로 최대 길이를 구한다.
 * - 해당 DP를 기준으로 역방향으로 넘어가며, 어떤 문자가 사용되었는지를 체크하고 저장한다.
 * - 이를 출력한다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String A = br.readLine();
        String B = br.readLine();
        int A_len = A.length();
        int B_len = B.length();

        int[][] DP = new int[A_len+1][B_len+1];

        for(int i = 0; i<A_len; i++){
            for(int j =0; j<B_len; j++){
                //만약 두 문자가 일치한다면
                if(A.charAt(i) == B.charAt(j)){
                    //(i,j)에 붙이는게 가장 크다면
                    if(DP[i][j] >= DP[i+1][j]
                            && DP[i][j] >= DP[i][j+1]){
                        DP[i+1][j+1] = DP[i][j] + 1;
                        continue;
                    }
                }
                //좌가 상보다 더 크다면
                if(DP[i+1][j] > DP[i][j+1]){
                    DP[i+1][j+1] = DP[i+1][j];
                }
                else{
                    DP[i+1][j+1] = DP[i][j+1];
                }
            }
        }

        //역추적
        int x = A_len;
        int y = B_len;
        String str = "";
        while(x > 0 && y > 0){
            if(DP[x-1][y] == DP[x][y]){
                x--;
            }
            else if(DP[x][y-1] == DP[x][y]){
                y--;
            }
            else{
                str += A.charAt(x-1);
                x--;
                y--;
            }
        }
        //결과 출력
        StringBuilder sb = new StringBuilder();
        sb.append(DP[A_len][B_len]);
        sb.append("\n");
        for(int i = str.length() - 1; i>=0; i--){
            sb.append(str.charAt(i));
        }
        System.out.println(sb.toString());
    }
}