import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 요구하는 출력 : 남아있는 파이어볼 질량의 합
 *
 * 풀이 방법 : 구현
 * - 1. 모든 파이어볼이 자신의 방향과 속력만큼 이동
 * - 2. 같은 칸에 2개의 파이어볼이 있다면 파이어볼을 합치고 4개로 나눈다.
 *  - 2-1. 질량: 합친 질량의 합 / 5
 *  - 2-2. 속력: 평균
 *  - 2-3. 방향: 방향이 모두 홀수이거나 짝수라면 0,2,4,6, 아니면 1,3,5,7
 * - 3. 질량이 0인 파이어볼은 소멸된다.
 *
 * 포인트
 * - 파이어볼은 큐, 맵은 리스트를 활용한다.
 * - 파이어볼을 하나씩 꺼내면서 도착하는 위치에 맵에 저장한다.
 * - 맵을 하나씩 보면서 파이어볼을 합치고 나눠 생긴 파이어볼은 다시 큐에 넣는다.
 */

class FireBall{
    int r;  //행
    int c;  //열
    int m;  //질량
    int s;  //속력
    int d;  //방향

    FireBall(int r, int c, int m, int s, int d){
        this.r = r;
        this.c = c;
        this.m = m;
        this.s = s;
        this.d = d;
    }
}
public class Main {

    static int dr[] = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int dc[] = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
        int N = Integer.parseInt(st.nextToken());   //지도 사이즈
        int M = Integer.parseInt(st.nextToken());   //파이어볼 개수
        int K = Integer.parseInt(st.nextToken());   //이동 횟수

        Queue<FireBall> queue = new LinkedList<>();
        List<FireBall>[][] map = new List[N][N];
        for(int i = 0 ; i<N; i++){
            for(int j = 0; j<N; j++){
                map[i][j] = new ArrayList<>();
            }
        }

        //파이어볼 입력 받기
        for(int i = 0; i<M; i++){
            st = new StringTokenizer(bufferedReader.readLine());
            queue.offer(new FireBall(Integer.parseInt(st.nextToken()) -1,
                    Integer.parseInt(st.nextToken()) -1,
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())));
        }

        //K번 명령을 실행하기
        for(int count = 0; count < K; count++){
            //파이어볼을 이동시키기
            while(!queue.isEmpty()){
                FireBall fireBall = queue.poll();
                fireBall.r = ((fireBall.r + dr[fireBall.d] * fireBall.s) + (1000 * N)) % N;
                fireBall.c = ((fireBall.c + dc[fireBall.d] * fireBall.s) + (1000 * N)) % N;
                map[fireBall.r][fireBall.c].add(fireBall);
            }
            //이동한 파이어볼을 합치고 나누기
            for(int i = 0; i<N; i++){
                for(int j = 0; j<N; j++){
                    //해당 위치에 파이어볼이 2개 이상이라면 계산을 하자
                    if(map[i][j].size() > 1){
                        //모든 파이어볼의 질량, 속력, 방향을 확인하기
                        int m = 0;
                        int s = 0;
                        int d = -1; //-2면 방향이 다르다는 것 의미
                        for(FireBall fireBall : map[i][j]){
                            m += fireBall.m;
                            s += fireBall.s;

                            //방향 체크하기
                            if(d == -1){
                                d= fireBall.d;
                            }
                            //아직 방향이 결정되지 않았다면
                            else if(d > -1){
                                //지금 저장되어있는 방향과, 새로 보는 파이어볼의 방향이 다르다면
                                if((d % 2) != (fireBall.d % 2)){
                                    d = -2;
                                }
                                //방향이 같다면
                                else{
                                    d = fireBall.d;
                                }
                            }
                        }

                        //질량이 0보다 클 때만 새로운 파이어볼 생성
                        if(m >= 5) {
                            for (int k = d == -2 ? 1 : 0; k < 8; k += 2) {
                                FireBall newFireBall = new FireBall(i, j, m / 5, s / map[i][j].size(), k);
                                //큐에 다시 넣는다.
                                queue.offer(newFireBall);
                            }
                        }
                    }
                    //해당 위치에 파이어볼이 1개라면 다시 큐에 넣자
                    else if(map[i][j].size() == 1){
                        //다시 큐에 넣는다.
                        queue.offer(map[i][j].get(0));
                    }

                    //해당 위치의 파이어볼 모두 없애기
                    map[i][j].clear();
                }
            }
        }

        //명령이 끝난 후 남아있는 파이어볼의 질량 합 꺼내기
        int result = 0;
        while(!queue.isEmpty()){
            result += queue.poll().m;
        }

        //결과 출력
        System.out.println(result);
    }
}