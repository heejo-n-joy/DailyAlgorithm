import java.util.*;

/**
 * 문제 내용
 * - 극장에서 눈치보며 자리 바꾸기
 * <p>
 * 요구하는 출력
 * - 좌석에 앉을 수 있는 경우의 수
 * <p>
 * 전략
 * - VIP 자리는 옮길 수 없으니, 이를 기준으로 끊어서 계산하자
 * - DP를 활용하여 계산 기록을 남기자.
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();  //좌석 수
        int M = scanner.nextInt();  //VIP 수
        int[][] DP = new int[N+1][2]; //[0]은 바뀐 경우, [1]은 안 바뀐 경우
        DP[1][1] = 1;   //초기값 설정
        List<Integer> seat_set = new ArrayList<>();
        int prev_vip_seat_num = 0;
        for(int i =0; i<=M; i++){
            int cur_vip_seat_num;
            if(i ==M){
                cur_vip_seat_num = N+1;
            }
            else {
                cur_vip_seat_num = scanner.nextInt();
            }
            //이전부터 지금 VIP 전까지 덩어리를 잘라 List에 넣는다.
            seat_set.add(cur_vip_seat_num-prev_vip_seat_num-1);
            prev_vip_seat_num = cur_vip_seat_num;
        }
        int result = Exec(seat_set, DP);

        Print_result(result);
    }

    public static void Print_result(int num){
        System.out.println(num);
    }
    public static int Exec(List<Integer> seat_set, int[][] DP){
        int result = 1;
        for(int i =0; i<seat_set.size(); i++){
            if(seat_set.get(i) == 0){
                continue;
            }
            result *= Exec_DP(seat_set.get(i), DP, 0) + Exec_DP(seat_set.get(i), DP, 1);
        }
        return result;
    }

    public static int Exec_DP(int num, int[][] DP, int index){
        if(num==1){
            return DP[1][index];
        }
        if (DP[num][index] <= 0) {
            if(index == 0) {
                DP[num][index] = Exec_DP(num - 1, DP, 1);
            }
            else{
                DP[num][index] = Exec_DP(num - 1, DP, 0) + Exec_DP(num - 1, DP, 1);
            }
        }
        return DP[num][index];
    }
}