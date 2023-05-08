import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 요구하는 출력 : 뿌요뿌요 게임에서 연쇄가 몇 번 연속 일어나는지 체크
 *
 * 풀이 방법 : 구현
 * 1. 현재 맵에서 터질 위치들을 모두 확인하고 터뜨린다.
 * 2. 기존의 뿌요들을 아래로 떨어뜨린다.
 * 3. 1과 2를 반복한다.
 *
 */

public class Main {
    static final int SIZE_R = 12;
    static final int SIZE_C = 6;
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        char[][] map = new char[SIZE_R][SIZE_C];
        for(int i = 0; i<SIZE_R; i++){
            String str = bufferedReader.readLine();
            for(int j = 0; j<SIZE_C; j++){
                map[i][j] = str.charAt(j);
            }
        }

        int count = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        //뿌요뿌요 실행
        while(true){
            boolean boom = false;
            //터질 곳이 있는지 체크하기
            boolean[][] check = new boolean[SIZE_R][SIZE_C];
            for(int i = 0; i<SIZE_R; i++){
                for(int j = 0; j<SIZE_C; j++){
                    //해당 위치에 뿌요가 있고, 확인하지 않은 곳이라면 탐색을 시작한다.
                    if((map[i][j] != '.') && (!check[i][j])){
                        Queue<int[]> exploreQueue = new LinkedList<>();
                        Queue<int[]> recordQueue = new LinkedList<>();
                        exploreQueue.offer(new int[]{i, j});
                        recordQueue.offer(new int[]{i, j});
                        check[i][j] = true;

                        while(!exploreQueue.isEmpty()){
                            int[] temp = exploreQueue.poll();

                            //상하좌우 탐색
                            for(int k = 0; k<4; k++){
                                int nextI = temp[0] + dr[k];
                                int nextJ = temp[1] + dc[k];

                                //맵의 범위를 벗어나면 패스
                                if(nextI < 0 || nextI >= SIZE_R || nextJ < 0 || nextJ >= SIZE_C){
                                    continue;
                                }

                                //뿌요가 다르다면 패스
                                if(map[nextI][nextJ] != map[i][j]){
                                    continue;
                                }

                                //이미 확인한 곳이라면 패스
                                if(check[nextI][nextJ]){
                                    continue;
                                }

                                //큐에 넣기
                                exploreQueue.offer(new int[]{nextI, nextJ});
                                recordQueue.offer(new int[]{nextI, nextJ});
                                check[nextI][nextJ] = true;
                            }
                        }

                        //해당 뿌요의 개수가 4개 이상이라면
                        if(recordQueue.size() >= 4){
                            //뿌요를 터뜨린다.
                            boom = true;
                            while(!recordQueue.isEmpty()){
                                int[] temp = recordQueue.poll();
                                map[temp[0]][temp[1]] = '.';
                            }
                        }
                    }
                }
            }

            //더이상 터질 곳이 없다면
            if(!boom){
                //게임 종료
                break;
            }

            //아래로 떨어뜨리기
            for(int j = 0; j<SIZE_C; j++){
                Queue<Integer> emptySpace = new LinkedList<>();
                for(int i = SIZE_R-1; i>=0; i--){
                    //해당 위치가 비어있다면
                    if(map[i][j] == '.'){
                        emptySpace.offer(i);
                    }
                    //해당 위치에 무언가 있다면
                    else{
                        //빈 곳을 체크해뒀다면
                        if(!emptySpace.isEmpty()){
                            //그 곳으로 떨어뜨린다.
                            map[emptySpace.poll()][j] = map[i][j];
                            map[i][j] = '.';
                            emptySpace.offer(i);
                        }
                    }
                }
            }

            //연쇄 횟수 증가
            count++;
        }

        //결과 출력
        System.out.println(count);
    }
}
