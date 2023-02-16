import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 수빈이와 동생의 숨바꼭질
 * <p>
 * 요구하는 출력
 * - 동생에게 도달하는 가장 빠른 시간
 * <p>
 * 전략
 * - 수빈이가 도달할 수 있는 모든 표를 다 채우면서, 동생의 위치에 도착하면 계산을 중단
 * - 표의 사이즈는 동생과 수빈 중 최대값보다 큰 2의 제곱수 (중에서 제일 작은 수)
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
        int N = Integer.parseInt(st.nextToken());   //수빈의 위치
        int K = Integer.parseInt(st.nextToken());   //동생의 위치
        int size = getArraySize(Math.max(N, K));    //표의 사이즈 측정
        //배열 생성
        int[] map_calc = new int[size+1];             //최단 시간을 나타내는 배열
        boolean[] map_check = new boolean[size+1];    //최단 시간이 입력되었는지 체크

        //실행
        int result = exec(map_calc, map_check, N, K, size);

        //결과 출력
        System.out.println(result);
    }

    public static int exec(int[] map_calc, boolean[] map_check, int N, int K, int size){
        if(N > K){
            return N-K;
        }
        Queue<int[]> queue = new LinkedList<>();
        map_check[N] = true;
        queue.offer(new int[]{N, 0});   //위치와 걸리는 시간 체크
        while(!queue.isEmpty()){
            int[] temp = queue.poll();
            int location = temp[0];
            int count = temp[1];
            if(location == K){
                return count;
            }
            //2의 배수들을 모두 넣자
            for(int i = location * 2; i<=size; i*=2){
                if(map_check[i]){
                    break;
                }
                //해당 위치가 탐방이 되지 않은 곳이라면
                queue.offer(new int[]{i, count});   //큐에 추가
                map_check[i] = true;
            }
            //1을 빼자
            if(location > 0 && !map_check[location-1]){
                queue.offer(new int[]{location-1, count+1});
                map_check[location-1] = true;
            }
            //1을 더하자
            if(location < size && !map_check[location+1]){
                queue.offer(new int[]{location+1, count+1});
                map_check[location+1] = true;
            }
        }
        //탐색을 다 했는데 찾지 못했다면 ERROR
        return -1;
    }

    public static int getArraySize(int num){
        int result = 1;
        while(result < num){
            result *= 2;
        }
        return result;
    }
}