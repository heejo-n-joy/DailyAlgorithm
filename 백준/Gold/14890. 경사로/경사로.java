import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 지도가 있고, 경사로를 놓을 수 있다.
 * <p>
 * 요구하는 출력
 * - 지나갈 수 있는 길의 개수를 구하라
 * <p>
 * 입력 변수
 * - N : 지도의 크기
 * - L : 경사로의 길이
 * - 지도의 경사 상태
 * <p>
 * 문제 유의사항
 * - 경사로는 꼭 길이에 맞춰서 놓아야 한다.
 * <p>
 * 전략
 * - 일단 2N의 길을 하나씩 받아서 하나의 배열로 만든다.
 * - 경사로를 놓아야 하는 상황을 확인하여 놓일 수 있는지 판단
 * <p>
 * 체감 난이도 : ★★★☆☆
 * <p>
 * 회고할 내용
 * - 같은 로직으로 가로를 탐색하고 세로를 탐색한다. 또, 같은 로직으로 이전을 비교하고 이후를 비교한다.
 * - 로직의 중복으로 이를 함수화하면 코드를 간결하게 할 수 있지 않을까 하는 마음으로 함수 모듈화를 고려하여 설계했다.
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //지도의 크기
        int L = Integer.parseInt(st.nextToken());   //경사로의 길이
        int[][] map = new int[N][N];              //지도
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken()); //지도의 경사 상태를 넣는다.
            }
        }

        //가능한 길 수
        int possibleRouteCount = execute(map, N, L);

        //결과 출력
        System.out.println(possibleRouteCount);
    }

    public static int execute(int[][] map, int N, int L){
        int count = 0;
        //가로 줄 탐방
        for(int i =0; i<N; i++){
            count += checkRoute("horizontal", i, map, N, L);
        }
        //세로 줄 탐방
        for(int j =0; j<N; j++){
            count += checkRoute("vertical", j, map, N, L);
        }
        return count;
    }

    public static int checkRoute(String version, int n, int[][] map, int N, int L){
        int horizontal = 0; //세로 계산
        if(version.equals("horizontal")){
            //가로 계산
            horizontal = 1;
        }
        //임시로 길 한 줄을 뽑아온다.
        int[] tempRoute = new int[N];
        boolean[] checkTempRoute = new boolean[N];

        for(int i = 0; i<N; i++){
            tempRoute[i] = map[i * (1 - horizontal) + n * (horizontal)][i * (horizontal) + n * (1 - horizontal)];
        }

        //성능 테스트
        for(int i = 0; i<N-1; i++){
            int nowLoc = i;    //현재 위치
            int nextLoc = i+1; //다음 위치
            //높이 차가 2 이상이라면 경사로를 원천적으로 놓을 수 없다.
            if(Math.abs(tempRoute[nowLoc]-tempRoute[nextLoc]) >= 2){
                return 0;
            }
            //현재 위치보다 다음 위치가 더 높다면
            if(tempRoute[nowLoc]<tempRoute[nextLoc]){
                //경사로 놓일 수 있는지 확인해보기
                if(!isPossible("left", tempRoute, checkTempRoute, N, nowLoc, L)){
                    return 0;
                }
            }
            //현재 위치가 다음 위치보다 더 높다면
            else if(tempRoute[nowLoc] > tempRoute[nextLoc]){
                if(!isPossible("right", tempRoute, checkTempRoute, N, nextLoc, L)){
                    return 0;
                }
            }
        }
        return 1;
    }

    public static boolean isPossible(String version, int[] tempRoute, boolean[] checkTempRoute, int N, int nowLoc, int L){
        Queue<Integer> queue = new LinkedList<>();
        int direction = 1;   //오른쪽 정방향
        //만약 역방향이라면
        if(version.equals("left")){
            direction = -1;
        }
        int originalHeight = tempRoute[nowLoc]; //현재 위치의 높이
        //1. 현재 위치에서 경사로를 놓을 수 있는 이전 위치까지 높이가 같은지 확인
        int count = 0;
        while(count < L){
            queue.offer(nowLoc);
            //배열 범위를 벗어나면 false
            if(nowLoc<0 || nowLoc>= N){
                return false;
            }
            //시작 위치의 높이와 다르면 false
            if(tempRoute[nowLoc] != originalHeight){
                return false;
            }
            //혹시 이미 경사로가 놓아진 곳이라면 false
            if(checkTempRoute[nowLoc]){
                return false;
            }
            //다음으로 이동
            nowLoc += direction;
            count++;
        }
        //모두 통과했다면 해당 위치에 경사로를 놓고 true
        while(!queue.isEmpty()){
            checkTempRoute[queue.poll()] = true;
        }
        return true;
    }
}