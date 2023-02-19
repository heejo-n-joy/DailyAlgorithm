import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 영역 구하기
 * <p>
 * 요구하는 출력
 * - 모눈종이 위에 직사각형들을 그렸을 때, 분리된 영역의 개수
 * <p>
 * 전략
 * - 직사각형들을 전부 check처리
 * - 이후 BFS로 풀이
 * - 좌표 -> 맵으로 변경시 위아래가 뒤집어지긴 하지만, 계산에는 문제가 없다.
 *
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());   //좌표의 행
        int N = Integer.parseInt(st.nextToken());   //좌표의 열
        int K = Integer.parseInt(st.nextToken());   //직사각형 개수
        boolean[][] map_check = new boolean[N][M];  //좌표
        for(int i = 0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            int fromX = Integer.parseInt(st.nextToken());
            int fromY = Integer.parseInt(st.nextToken());
            int toX = Integer.parseInt(st.nextToken());
            int toY = Integer.parseInt(st.nextToken());
            for(int x = fromX; x<toX; x++){
                for(int y = fromY; y<toY; y++){
                    map_check[x][y] = true;
                }
            }
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        //계산
        List<Integer> list = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        for(int i =0; i<N; i++){
            for(int j =0; j<M; j++){
                if(map_check[i][j]){
                    continue;
                }
                int count = 1;
                queue.offer(new int[]{i, j});
                map_check[i][j] = true;
                while(!queue.isEmpty()){
                    int[] temp = queue.poll();
                    //상하좌우 탐색
                    for(int k = 0; k<4; k++) {
                        int nextX = temp[0] + dr[k];
                        int nextY = temp[1] + dc[k];
                        //해당 위치가 벗어났는지 체크
                        if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= M){
                            continue;
                        }
                        //해당 위치가 이미 check인지 체크
                        if(map_check[nextX][nextY]){
                            continue;
                        }
                        //그게 아니라면 queue에 삽입
                        queue.offer(new int []{nextX, nextY});
                        map_check[nextX][nextY] = true;
                        count++;
                    }
                }
                list.add(count);
            }
        }
        //결과 출력 전 list 정렬
        Collections.sort(list);
        System.out.println(list.size());
        for(int i =0; i< list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
}