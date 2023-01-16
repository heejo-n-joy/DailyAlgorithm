import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 원판들을 돌리고 인접한 숫자 제거하기
 * <p>
 * 요구하는 출력
 * - 원판의 남은 수들의 합
 * <p>
 * 입력 변수
 * - N : 원판의 개수
 * - M : 원판에 적힌 숫자의 수
 * - T : 회전하고 계산하는 횟수
 * - 원판의 숫자들
 * - x : 원판 번호가 약수로 가진 수
 * - d : 회전 방향. 0은 시계, 1은 반시계 방향
 * - k : 몇 칸 회전하는지
 * <p>
 * 전략
 * - 원판을 계속 회전하면서 인접한 숫자를 찾아야 한다. -> 인접한 숫자 찾기는 BFS를 이용하자.
 * - 원판을 회전하는 로직을 포인터로 잡는게 깔끔해보이긴 하지만.. 일단 회전할 때마다 배열 값을 직접 변경하는걸로 해보자.
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★☆
 * <p>
 * 회고할 내용
 * - 별 거 아닌 변수를 잘못 입력해서 생기는 오류는 디버깅하는데 시간이 오래 걸린다. 실수를 줄여보자
 *
 * @author HeejoPark
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //원판의 개수
        int M = Integer.parseInt(st.nextToken());   //원판 내 숫자 개수
        int T = Integer.parseInt(st.nextToken());   //회전 횟수
        int[][] circles = new int[N][M];
        //원판의 정보를 입력받는다.
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                circles[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //원판을 회전하자
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());   //원판의 배수
            int d = Integer.parseInt(st.nextToken());   //회전방향
            int k = Integer.parseInt(st.nextToken());   //회전 개수
            //원판들을 선택하여 회전
            for (int j = 0; j < N; j++) {
                //원판 번호가 x의 배수라면
                if ((j + 1) % x == 0) {
                    //해당 원판을 회전한다.
                    rotateArray(M, circles[j], d, k);
                }
            }
            //원판들 사이에 인접한 수들을 확인하고 제거하기
            deleteNumbers(N, M, circles);
        }

        //결과 계산하기
        int result = sumRestNumbers(N, M, circles);
        System.out.println(result);
    }

    public static int sumRestNumbers(int N, int M, int[][] circles) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (circles[i][j] != -1) {
                    count += circles[i][j];
                }
            }
        }
        return count;
    }

    public static int countRestNumbers(int N, int M, int[][] circles) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (circles[i][j] != -1) {
                    count++;
                }
            }
        }
        return count;
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void deleteNumbers(int N, int M, int[][] circles) {
        boolean[][] check = new boolean[N][M];
        boolean isSameNumbers = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                //탐색을 아직 하지 않았고, 해당 원판이 이미 지워진 값(-1)이 아니라면
                if (!check[i][j] && (circles[i][j] != -1)) {
                    //해당 점을 기준으로 탐색 시작
                    Queue<int[]> exploreLocation = new LinkedList<>();
                    Queue<int[]> collectLocation = new LinkedList<>();
                    exploreLocation.offer(new int[]{i, j});
                    collectLocation.offer(new int[]{i, j});
                    int value = circles[i][j];
                    while (!exploreLocation.isEmpty()) {
                        int[] temp = exploreLocation.poll();
                        for (int k = 0; k < 4; k++) {
                            int nextI = temp[0] + dr[k];
                            int nextJ = temp[1] + dc[k];
                            //해당 위치가 원판의 범위를 벗어난다면 패스
                            if (nextI < 0 || nextI >= N) {
                                continue;
                            }
                            //원판의 좌우 끝은 서로 연결되어있으니 이를 반영해주자
                            nextJ = (nextJ + M) % M;
                            //해당 위치가 이미 확인이 된 곳이라면 패스
                            if (check[nextI][nextJ]) {
                                continue;
                            }
                            //해당 위치의 값이 다르다면 패스
                            if (circles[nextI][nextJ] != value) {
                                continue;
                            }
                            //모두 통과를 했다면 같은 부분이라 생각하고 queue에 추가, 탐색을 이어간다.
                            exploreLocation.offer(new int[]{nextI, nextJ});
                            collectLocation.offer(new int[]{nextI, nextJ});
                            check[nextI][nextJ] = true;
                        }
                    }
                    //해당 값에 인접한 곳이 혼자만 있던게 아니었다면
                    if (collectLocation.size() > 1) {
                        isSameNumbers = true;
                        while (!collectLocation.isEmpty()) {
                            int[] temp = collectLocation.poll();
                            circles[temp[0]][temp[1]] = -1;         //모두 -1처리
                        }
                    }
                }
            }
        }

        //실제 인접한 수가 같은 경우가 없다면
        if (!isSameNumbers) {
            //0이라 나눌 수 없으면 생략
            if(countRestNumbers(N,M,circles)==0){
                return;
            }
            //원판에 적힌 수의 평균을 구하고
            int avg = sumRestNumbers(N, M, circles) / countRestNumbers(N, M, circles);
            int rest = sumRestNumbers(N, M, circles) % countRestNumbers(N, M, circles);
            //평균보다 큰 수에서는 1을 빼고, 작은 수에는 1을 더한다.
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (circles[i][j] == -1) {
                        continue;
                    }
                    if (rest == 0) {
                        if (circles[i][j] > avg) {
                            circles[i][j]--;    //평균보다 큰 수는 -1
                        } else if (circles[i][j] < avg) {
                            circles[i][j]++;    //평균보다 작은 수는 +1
                        }
                    } else {
                        if (circles[i][j] > avg) {
                            circles[i][j]--;    //평균보다 큰 수는 -1
                        } else if (circles[i][j] <= avg) {
                            circles[i][j]++;    //평균보다 작은 수는 +1
                        }
                    }
                }
            }
        }
    }

    public static void rotateArray(int M, int[] circle, int direction, int count) {
        //count횟수만큼 한칸씩 이동
        for (int i = 0; i < count; i++) {
            if (direction == 1) {
                //반시계방향(왼쪽)으로 이동
                int temp = circle[0];
                for (int j = 0; j < M - 1; j++) {
                    circle[j] = circle[j + 1];
                }
                circle[M - 1] = temp;
            } else {
                //시계방향(오른쪽)으로 이동
                int temp = circle[M - 1];
                for (int j = M - 1; j > 0; j--) {
                    circle[j] = circle[j - 1];
                }
                circle[0] = temp;
            }
        }
    }
}