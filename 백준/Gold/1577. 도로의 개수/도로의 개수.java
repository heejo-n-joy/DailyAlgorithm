import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * 요구하는 출력 : 세준이가 학교까지 갈 수 있는 (최단 거리의) 경우의 수
 *
 * 풀이 방법 : DP + HashSet
 * - 1. 공사중인 위치를 HashSet에 저장한다.
 * - 2. 오른쪽 아래 방향으로 순차적으로 계산을 진행한다. 이 때, HashSet에 저장된 곳이면 해당 길의 값은 계산하지 않는다.
 *
 * 결과 값을 저장할 변수 타입 : Long
 * - 2의 64제곱은 8바이트이기 때문에, Long 타입을 사용한다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());   //도로의 가로 크기
        int M = Integer.parseInt(st.nextToken());   //도로의 세로 크기

        long[][] map = new long[N+1][M+1];

        int K = Integer.parseInt(br.readLine());    //공사중인 도로의 개수

        //공사중인 도로를 체크하기
        HashSet<String> hashSet = new HashSet<>();
        for(int i = 0; i < K; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            //도로가 가로 방향이라면
            if(a == c){
                //왼쪽에서 오른쪽으로. 형식은 "a,b/c,d"
                hashSet.add(a+","+Math.min(b,d)+"/"+c+","+Math.max(b,d));
            }
            //도로가 세로 방향이라면
            else{
                //아래에서 위로. 형식은 "a,b/c,d"
                hashSet.add(Math.min(a,c)+","+b+"/"+Math.max(a,c)+","+d);
            }
        }

        //시작 지점을 1로 설정
        map[0][0] = 1;

        //경우의 수 구하기
        for(int i =0; i<=N; i++){
            for(int j=0; j<=M; j++){
                //세로로 길을 간다.
                if((i>0)&&(!hashSet.contains((i-1)+","+j+"/"+i+","+j))){
                    map[i][j] += map[i-1][j];
                }
                //가로로 길을 간다.
                if((j>0)&&(!hashSet.contains(i+","+(j-1)+"/"+i+","+j))){
                    map[i][j] += map[i][j-1];
                }
            }
        }

        //결과 출력
        System.out.println(map[N][M]);
    }
}