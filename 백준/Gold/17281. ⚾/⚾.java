import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 야구 시뮬레이션을 돌렸을 때, 가장 많은 득점을 하는 타순과 그 때의 득점
 *
 * 풀이 방법 : 시뮬레이션
 * - 4번 타자(1번 선수)를 제외한 나머지 선수들을 배치해야 한다.
 * - 1. 선수들을 배치한다.
 * - 2. 배치된 선수 명단을 바탕으로 경기를 플레이한다.
 * - 3. 가장 큰 값이 나오는 득점을 출력한다.
 *
 */

class Player{
    int no;     //선수 번호
    int[] val;  //능력치
    Player(int no, int index){
        this.no = no;
        val = new int[index];
    }
}
public class Main {

    static int result_val;
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());
        Player[] players = new Player[9];
        for(int i = 0; i<9; i++){
            players[i] = new Player(i, N);
        }
        for(int i = 0; i<N; i++){
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            for(int j = 0; j<9; j++) {
                players[j].val[i] = Integer.parseInt(stringTokenizer.nextToken());
            }
        }

        result_val = 0;

        int[] order = new int[9];           //계산에 사용될 선수 순서
        boolean[] check = new boolean[9];   //계산에 사용될 선수 체크
        order[3] = 0;   //첫번째 플레이어를 4번째로 미리 결정
//        int[] order = {4,5,6,0,1,2,3,7,8};
        check[0] = true;
        //선수 순서를 정한 후 계산해본다.
        exec(0, order, players, check, N);
//        exec(9, order, players, check, N);

        //결과 출력
        System.out.println(result_val);
    }

    public static void exec(int count, int[] order, Player[] players, boolean[] check, int N){
        //순열을 다 채웠다면
        if(count == 9){
            //계산 시작
            int total_score = 0;
            int out = 0;
            int play_time = 0;
            int current_order = 0;
            boolean[] current_board = new boolean[3];   //1루, 2루, 3루

            //경기 진행
            while(play_time < N){
                int player_val = players[order[current_order]].val[play_time];  //현재 타자의 능력치
                
                //타자가 아웃이라면
                if(player_val == 0) {
                    out++;
                }
                //안타 이상이라면
                else {
                    //현재 필드의 사람들을 이동시키기
                    for (int i = 2; i >= 0; i--) {
                        //만약 1루,2루,3루 필드에 선수가 있고
                        if (current_board[i]) {
                            //홈으로 돌아오는 상황이라면
                            if(i+player_val >= 3){
                                total_score++;  //점수 증가
                            }
                            //그게 아니라면
                            else{
                                //다음 필드로 이동
                                current_board[i + player_val] = true;
                            }
                            //기존의 위치는 삭제
                            current_board[i] = false;
                        }
                    }

                    //타자도 진출
                    if(player_val == 4){
                        total_score++;
                    }
                    else {
                        current_board[player_val - 1] = true;    //해당 필드로 진출
                    }
                }
                
                //만약 아웃이 3개가 됐다면
                if(out>=3){
                    play_time++;    ///이닝 수를 증가하고
                    out = 0;    //아웃을 초기화한다.
                    for(int i = 0; i<=2; i++) {
                        current_board[i] = false;
                    }
                }
                
                //다음 선수로 변경
                current_order +=1;
                current_order %= 9;
            }
            
            //경기가 끝나면 결과를 비교해보고, 갱신한다.
            result_val = Math.max(total_score, result_val);
            return;
        }

        //선수를 뽑자
        for(int i = 1; i<9; i++){
            if(check[i]){
                continue;
            }
            check[i] = true;
            order[count] = i;
            if(count == 2) {    //3은 건너뛰어야 한다.
                exec(count + 2, order, players, check, N);
            }
            else{
                exec(count +1, order, players, check, N);
            }
            check[i] = false;
        }
    }
}
