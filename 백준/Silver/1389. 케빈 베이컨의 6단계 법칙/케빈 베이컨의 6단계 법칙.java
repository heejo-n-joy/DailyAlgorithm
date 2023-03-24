import java.util.Arrays;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 케빈 베이컨의 수가 가장 작은 사람 (그냥 연결이 가장 짧은 사람)
 *
 * 풀이 방법
 * - 플로이드-워셜 알고리즘을 사용하자
 * - 1. 2차원 지도를 생성하고, 친구 관계를 체크한다.
 * - 2. 모든 친구를 돌면서 관계의 최소값을 갱신한다.
 * - 3. 모든 친구를 돌면서, 관계의 합이 가장 작은 친구를 찾는다.
 *
 * 플로이드-워셜 알고리즘
 * - 친구들의 관계를 나타내는 2차원 배열을 준비한다.
 * - 친구들의 관계 사이에 다른 친구를 경유했을 때, 최소값이 갱신이 되는지를 체크하는 알고리즘
 *
 * 시간복잡도
 * - 지도 만들고 친구 관계 매핑하기 : O(n^2) or O(m) 
 * - 친구 선택 및 2차원 배열 탐색 : O(n) * O(n^2)
 * - 최소값 찾기 : O(n^2)
 * - 총 O(n^3)
 */

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] map = new int[N+1][N+1];

        //1-1. 2차원 지도의 모든 값을 나올 수 있는 최대값+1로 지정한다.
        for(int i = 0 ; i<=N; i++){
            for(int j = 0; j<=N; j++){
                map[i][j] = N+1;    //MAX_VALUE
            }
        }

        //1-2. 친구 관계를 2차원 지도에 체크해준다. 이 때, 가중치는 1이다.
        for(int i = 0 ; i<M; i++){
            int friendA = sc.nextInt();
            int friendB = sc.nextInt();
            map[friendA][friendB] = 1;
            map[friendB][friendA] = 1;
        }

        //2. 중간에 경유할 친구를 정하고, 모든 친구 관계를 돌아보며 최소값을 갱신한다.
        for(int between_friend = 1 ; between_friend <= N ; between_friend++){
            for(int friendA = 1; friendA<=N; friendA++){
                for(int friendB = 1; friendB<=N; friendB++){
                    //friendA-friendB의 거리와 friendA-between_friend-friendB의 거리 중 짧은 값을 반영
                    map[friendA][friendB] = Math.min(map[friendA][friendB], map[friendA][between_friend] + map[between_friend][friendB]);
                }
            }
        }

        int minimum_relationship_friend_index = 0;  //케빈 베이컨의 수가 가장 적은 사람의 번호
        int minimum_relationship_friend_sum = 0;    //케빈 베이컨의 수가 가장 적은 사람의 케빈 베이컨 수

        //3. 모든 친구들 중 케빈 베이컨의 수가 제일 적은 사람을 탐색
        for(int friend = 1; friend<=N; friend++){
            int current_minimum = 0;
            //선택한 친구와 다른 친구들과의 모든 관계를 더한다.
            for(int other_friend = 1; other_friend<=N; other_friend++){
                //이 때, 자기 자신과의 관계는 제외
                if(friend == other_friend){
                    continue;
                }
                current_minimum += map[friend][other_friend];
            }
            //탐색을 하는 가장 첫번째 사람이라면 그냥 반영하기
            if(minimum_relationship_friend_index == 0){
                minimum_relationship_friend_index = friend;
                minimum_relationship_friend_sum = current_minimum;
            }
            //두번째 사람부터는 케빈 베이컨 수를 비교하고 갱신
            else{
                if(current_minimum < minimum_relationship_friend_sum){
                    minimum_relationship_friend_sum = current_minimum;
                    minimum_relationship_friend_index = friend;
                }
            }
        }

        //결과 출력
        System.out.println(minimum_relationship_friend_index);
    }
}