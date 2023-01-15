import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 상도의 나무 재테크, 봄 여름 가을 겨울.
 * <p>
 * 요구하는 출력
 * - K년이 지난 후 상도의 땅에 살아있는 나무의 총 개수
 * <p>
 * 입력 변수
 * - N : 상도의 땅의 크기
 * - M : 현재 나무의 개수
 * - K : 요구하는 시간의 흐름
 * - 땅의 정보 (양분)
 * - 나무의 정보 (x,y: 위치, z: 나이)
 * <p>
 * 전략
 * - 시뮬레이션을 돌리기 때문에 간단한 방식이라 생각한다.
 * - 다만, 나무의 정보에 같은 위치의 나무가 있다면 나이순으로 진행해야 하기 때문에 이를 정렬할 수단이 필요
 * - 나무에 관련된 3차원 배열(위치+나무나이)로 체크하면서 해보자
 * <p>
 * 예상 난이도 : ★★★
 * 체감 난이도 : ★★☆
 * <p>
 * 회고할 내용
 * - 가을부분에서 나무를 번식하는 과정에서 무한루프가 생기는 오류가 있었다.
 * - 나무의 최대 수명(maxTreeAge)을 1010살이 아닌 100살로 잘못 계산하고 있었다.
 *
 * @author HeejoPark
 */
public class Main {

    static final int MaxTreeAge = 1010;
    static int N;
    static int M;
    static int K;

    static int[][] map;
    static int[][] manureMap;
    static int[][][] trees;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   //땅의 크기
        M = Integer.parseInt(st.nextToken());   //현재 나무의 개수
        K = Integer.parseInt(st.nextToken());   //요구하는 시간의 흐름

        //초기 지도 만들기
        map = new int[N+1][N+1];
        for(int i = 1; i<=N; i++){
            for(int j = 1; j<=N; j++){
                map[i][j] = 5;  //초기 양분은 5로 통일
            }
        }

        //양분 지도 만들기
        manureMap = new int[N+1][N+1];
        for(int i = 1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j =1; j<=N; j++){
                manureMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //나무 정보 배열 만들기
        trees = new int[N+1][N+1][MaxTreeAge+1];   //나무의 최대 수명은 1010살이기 때문
        for(int i = 1; i<=M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            trees[x][y][z]++;
        }


        //시뮬레이션 시작
        for(int year = 0; year<K; year++){
            grownTrees = new LinkedList<>();
            deadTrees = new LinkedList<>();
            spring(N, trees, map);  //봄
            summer(map);            //여름
            fall();                 //가을
            winter();               //겨울
        }

        int totalTreesCount = 0;
        //결과 계산
        for(int i = 1; i<=N; i++){
            for(int j = 1; j<=N; j++){
                for(int k = 1; k<=MaxTreeAge; k++){
                    totalTreesCount += trees[i][j][k];
                }
            }
        }

        System.out.println(totalTreesCount);
    }
    static Queue<int[]> grownTrees;
    static Queue<int[]> deadTrees;

    public static void spring(int N, int[][][] trees, int[][] map){
        for(int i =1; i<=N; i++){
            for(int j =1; j<=N; j++){
                for(int k = 1; k<=MaxTreeAge; k++){
                    while(trees[i][j][k]>0){
                        //양분을 먹을 수 있으면
                        if(map[i][j] - k >= 0){
                            //양분을 먹고 나이를 1살 먹는다.
                            map[i][j] -= k;
                            grownTrees.offer(new int[]{i,j, k+1});  //나이를 먹은 나무는 새로 계산이 끝나고 일괄적으로 추가될 예정이다.
                        }
                        //양분을 먹을 수 없다면, 나무는 죽는다.
                        else{
                            deadTrees.offer(new int[]{i, j, k});  //죽은 나무 리스트에 추가한다.
                        }
                        //기존 표에서 해당 나무를 삭제한다.
                        trees[i][j][k]--;
                    }
                }
            }
        }
        //모든 계산이 끝나면 나이를 먹은 나무들을 일괄적으로 추가해준다.
        while(!grownTrees.isEmpty()){
            int[] temp = grownTrees.poll();
            trees[temp[0]][temp[1]][temp[2]]++;
        }
    }

    public static void summer(int[][] map){
        //죽은 나무가 양분으로 변한다.
        while(!deadTrees.isEmpty()){
            int[] tree = deadTrees.poll();
            map[tree[0]][tree[1]] += tree[2]/2;
        }
    }

    static int dr[] = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int dc[] = {-1, 0, 1, -1, 1, -1, 0, 1};
    public static void fall(){
        for(int i =1; i<=N; i++){
            for(int j = 1; j<=N; j++){
                //5의 배수인 나무를 찾아본다.
                for(int k =5; k<=MaxTreeAge; k+= 5){
                    //나무가 있다면
                    for(int l = 0; l<trees[i][j][k]; l++){
                        //주변 8개의 칸에 나무를 심는다.
                        for(int m = 0; m<8; m++){
                            int nearX = i + dr[m];
                            int nearY = j + dc[m];
                            //해당 위치가 상도의 땅이라면
                            if(nearX > 0 && nearX <=N && nearY >0 && nearY <=N){
                                trees[nearX][nearY][1]++; //1살인 나무를 심는다.
                            }
                        }
                    }
                }
            }
        }
    }

    public static void winter(){
        //양분을 추가
        for(int i =1; i<=N; i++){
            for(int j =1; j<=N; j++){
                map[i][j] += manureMap[i][j];
            }
        }
    }
}