import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력
 * - 배열을 Z모양으로 재귀적으로 탐색할 때, 해당 위치를 몇 번째로 도착하는지
 *
 * 풀이 방법
 * - 1. 배열을 4등분을 한 후 현재 위치가 좌상, 우상, 좌하, 우하 중 어디에 있는지 확인한다.
 * - 2. 확인하면, 그 전까지의 값들을 일괄적으로 더한 후, 다시 쪼갠 배열 범위에서 1을 반복한다.
 * - 3. 만약 배열이 정확한 위치에 도착했다면, 실행을 종료한다.
 *
 * 시간복잡도
 * - 방문 위치를 구할 때 계속해서 1/4씩 범위를 나누며 계산하기 때문에 O(logN)이라 할 수 있다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());       //배열의 크기(2의 지수)
        int r = Integer.parseInt(st.nextToken());       //찾아야하는 행
        int c = Integer.parseInt(st.nextToken());       //찾아야하는 열
        int realN = (int) Math.pow(2, N);               //지수를 계산한 실제 배열의 크기
        //계산
        int result = exec(0, realN-1, r,0, realN-1, c);
        //결과 출력
        System.out.println(result);
    }

    public static int exec(int startR, int endR, int wannaR, int startC, int endC, int wannaC){
        //칸을 4개로 나눠야 하는데 현재 범위가 총 4칸보다 작다면 더이상 계산이 어렵다.
        if(startR >= endR || startC >= endC){
            return 0;
        }
        int size = (endR - startR + 1) * (endC - startC  + 1) / 4;  //배열을 4등분했을 때의 각 사이즈
        int loc = 0;    //현재 칸이 어디에 있는지를 파악 (0부터 3까지 순서대로 좌상, 우상, 좌하, 우하
        int nextStartR = startR;    //다음으로 실행할 r의 시작범위
        int nextEndR = endR;        //다음으로 실행할 r의 끝범위
        int nextStartC = startC;    //다음으로 실행할 c의 시작범위
        int nextEndC = endC;        //다음으로 실행할 c의 끝범위
        //배열을 4등분했을 때, 현재 위치가 어디에 있는지 확인한다.
        if(((startR + endR) / 2) < wannaR){
            //하에 위치한다.
            loc +=2;
            nextStartR = (startR + endR) / 2 + 1;
        }
        else{
            //상에 위치한다.
            nextEndR = (startR + endR) / 2;
        }
        if(((startC + endC) / 2) < wannaC){
            //우에 위치한다.
            loc += 1;
            nextStartC = (startC + endC) / 2 + 1;
        }
        else{
            //좌에 위치한다.
            nextEndC = (startC + endC) / 2;
        }
        //범위를 좁히고 다시 계산
        return loc * size + exec(nextStartR, nextEndR, wannaR, nextStartC, nextEndC, wannaC);
    }
}