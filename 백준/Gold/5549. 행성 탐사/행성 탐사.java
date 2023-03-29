import java.util.Scanner;

/**
 * 요구하는 출력
 * - 조사 대상 영역 별 정글, 바다, 얼음의 수
 *
 * 풀이 방법
 * - (1,1)부터 (N,M)까지 정글, 바다, 얼음의 합을 저장한다. (ex (2,3)이라면 (1,1)부터 (2,3)까지의 모든 정보를 담는다)
 * - 조사 대사 영역이 나오면 (ex (a,b)부터 (c,d)까지) 포함되지 않는 영역을 제거한다.
 * - [더하기] (1,1)부터 (c,d)까지
 * - [빼기] (1,1)부터 (a,d)까지
 * - [빼기] (1,1)부터 (b,c)까지
 * - [더하기] (1,1)부터 (a,b)까지 (위에서 두 번 제거되었으니, 한 번은 더해준다)
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   //가로
        int M = sc.nextInt();   //세로
        int K = sc.nextInt();   //조사 대상 영역의 개수

        char[][] map_char = new char[N+1][M+1];
        for(int i =1; i<=N; i++){
            String str = sc.next();
            for(int j = 1; j<=M; j++){
                map_char[i][j] = str.charAt(j-1);
            }
        }
        char[] chars = {'J', 'O', 'I'};
        int[][][] map = new int[N+1][M+1][3];   //[0]은 정글, [1]은 바다, [2]는 얼음
        for(int k = 0; k<3; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    map[i][j][k] = map[i][j-1][k] - map[i-1][j-1][k] + map[i-1][j][k];  //개수 갱신
                    if(chars[k] == map_char[i][j]){
                        map[i][j][k]++;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        //결과 출력
        for(int i = 0; i<K; i++) {
            //출력할 범위
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            int d = sc.nextInt();
            int[] results = new int[3];
            for (int k = 0; k < 3; k++) {
                results[k] += map[c][d][k];     //전체 범위 더하기
                results[k] -= map[a-1][d][k];   //일부 범위 빼기
                results[k] -= map[c][b-1][k];   //일부 범위 빼기
                results[k] += map[a-1][b-1][k]; //중복된 범위 더하기

                sb.append(results[k] + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}