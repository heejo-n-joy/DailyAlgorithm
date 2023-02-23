import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 수빈이와 동생의 숨바꼭질 첫번째 이야기
 * <p>
 * 요구하는 출력
 * - 동생을 찾을 수 있는 가장 빠른 시간
 * <p>
 * 전략
 * - BFS를 이용해서 풀어보자
 * - 이미 도착한 위치면 패스
 * - 최대 범위는 동생의 위치보다 큰 가장 작은 2의 제곱 위치
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //수빈이의 위치
        int K = Integer.parseInt(st.nextToken());   //동생의 위치
        int result = Exec(N, K);
        //결과 출력
        System.out.println(result);
    }
    public static int Exec(int N, int K){
        if(N >= K){
            return N-K;
        }
        int maxRange = findMinimumPowerOfTwo(K);    //동생의 위치보다는 큰 2의 제곱 중 가작 작은 수
        int[] map = new int[maxRange+1];    //1부터 maxRange까지
        for(int i = 0; i<=maxRange; i++){
            map[i] = -1;
        }
        Queue<Integer> queue = new LinkedList<>();
        map[N] = 0; //수빈의 시작 위치까지 걸리는 시간 0
        queue.offer(N); //큐에 삽입
        while(!queue.isEmpty()){
            int tempLoc = queue.poll(); //현재 위치
            //현 위치가 동생의 위치라면 종료
            if(tempLoc == K){
                return map[tempLoc];
            }
            //+1 위치가 가능한지 체크
            if(tempLoc+1 <= maxRange && map[tempLoc+1] == -1){
                map[tempLoc+1] = map[tempLoc] + 1;
                queue.offer(tempLoc+1);
            }
            //-1 위치가 가능한지 체크
            if(tempLoc-1 > 0 && map[tempLoc-1] == -1){
                map[tempLoc-1] = map[tempLoc] + 1;
                queue.offer(tempLoc-1);
            }
            //*2 위치가 가능한지 체크
            if(tempLoc*2 <= maxRange && map[tempLoc*2] == -1){
                map[tempLoc*2] = map[tempLoc] + 1;
                queue.offer(tempLoc * 2);
            }
        }
        //ERROR
        return -1;
    }
    public static int findMinimumPowerOfTwo(int num){
        int i = 1;
        while(true){
            if(num <= i){
                return i;
            }
            i *= 2;
        }
    }
}