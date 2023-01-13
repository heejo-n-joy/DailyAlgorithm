import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 상근이는 어떤 숫자카드들을 가지고 있을까?
 * <p>
 * 요구하는 출력
 * - 해당하는 수가 적힌 숫자 카드를 상근이가 가지고 있는지?
 * <p>
 * 입력 변수
 * - N : 상근이가 가지고 있는 숫자 카드의 개수
 * - 숫자 카드들의 목록
 * - M : 확인해보고 싶은 숫자 개수
 * - 숫자들의 목록
 * <p>
 * 전략
 * - 20,000,000개의 boolean타입 배열로 비교하자
 * <p>
 * 체감 난이도 : ★☆☆☆☆
 * <p>
 * 회고할 내용
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //상근이의 숫자 개수
        boolean[] array = new boolean[20000001];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i =0; i<N; i++){
            array[Integer.parseInt(st.nextToken())+10000000] = true;
        }

        int M = Integer.parseInt(br.readLine());   //맞히고 싶은 숫자 개수
        st = new StringTokenizer(br.readLine());
        for(int i =0; i<M; i++){
            if(array[Integer.parseInt(st.nextToken())+10000000]){
                System.out.print("1 ");
            }
            else{
                System.out.print("0 ");
            }
        }
    }
}