import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 문제 내용
 * - 강호가 엘리베이터를 타고 면접을 진행하는 사무실에 가려고 한다.
 * <p>
 * 요구하는 출력
 * - 버튼을 적어도 몇 번을 눌러야 하는지의 최소값
 * <p>
 * 전략
 * - BFS로 층들을 확인하면서 가자
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int F = sc.nextInt();   //고층 건물의 총 층수
        int S = sc.nextInt();   //현재 강호의 위치
        int G = sc.nextInt();   //사무실 층
        int U = sc.nextInt();   //위로 가는 버튼
        int D = sc.nextInt();   //아래로 가는 버튼

        boolean[] check_floor = new boolean[F+1];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{S, 0});
        int result = -1;
        while(!queue.isEmpty()){
            int[] temp = queue.poll();
            int current_floor = temp[0];
            int current_count = temp[1];
            if(current_floor == G){
                result = current_count;
                break;
            }
            //올라갈 수 있는지 확인하고, 가능하다면 올리기
            if((current_floor + U <= F) && (!check_floor[current_floor + U])){
                queue.offer(new int[]{current_floor + U, current_count + 1});
                check_floor[current_floor + U] = true;
            }
            //내려갈 수 있는지 확인하고, 가능하다면 내려가기
            if((current_floor - D > 0) && (!check_floor[current_floor - D])){
                queue.offer(new int[]{current_floor -D , current_count + 1});
                check_floor[current_floor - D] = true;
            }
        }
        //결과 출력
        if(result == -1){
            System.out.println("use the stairs");
        }
        else{
            System.out.println(result);
        }
    }
}