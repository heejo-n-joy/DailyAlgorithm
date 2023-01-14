import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 사다리가 의미가 없어졌으면 좋겠다.
 * <p>
 * 요구하는 출력
 * - i번 사다리는 i번 도착지에 가게 사다리를 조작하려면 몇개의 선이 더 필요한가?
 * <p>
 * 입력 변수
 * - N : 세로선의 개수
 * - M : 가로선의 개수
 * - H : 가로선을 놓을 수 있는 위치의 개수
 * - 현재까지 놓인 가로선의 정보들
 * <p>
 * 전략
 * - 최대 3개까지만 가로선을 추가할 수 있다고 했으니, 조합으로 문제를 풀어보자
 * - 사다리의 가로선은 (가로로) 연속해서 있을 수 없다.
 * <p>
 * 예상 난이도 : ★★★☆☆
 * 체감 난이도 : ★★★☆☆
 * <p>
 * 회고할 내용
 * - 생각했던 대로 풀어나가면서 하니 잘 됐다.
 * - 다만 생각이 구체적이지 않아 변수를 잘못 사용하는 등의 실수가 많이 있었다.
 *
 * @author HeejoPark
 */
public class Main {
    static int result;
    static int N;
    static int M;
    static int H;
    static boolean[][] horizonLines;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   //세로선의 개수
        M = Integer.parseInt(st.nextToken());   //가로선의 개수
        H = Integer.parseInt(st.nextToken());   //가로선을 놓을 수 있는 위치의 개수
        horizonLines = new boolean[H][N-1];  //가로선이 놓여져 있는 정보
        for(int i =0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());   //가로선의 정보
            int b = Integer.parseInt(st.nextToken());   //가로선의 정보
            horizonLines[a-1][b-1] = true;  //해당 가로선이 존재함을 표시
        }
        result = -1;
        //사다리가 0개부터 3개까지 되는지 체크해보자
        for(int i =0; i<=3; i++) {
            addHorizonLines(i, 0);
            if(result > -1){
                break;
            }
        }
        //결과 출력
        System.out.println(result);
    }

    public static void addHorizonLines(int ladder, int count){
        //가로선을 다 정했다면
        if(count == ladder){
            //사다리 실행해보기
            if(executeLadderProgram()){
                //만약 모든 선이 자기 번호로 끝난다면 원하는 결과를 찾았다고 할 수 있다.
                result = ladder;
            }
            return;
        }
        //가로선 하나 더 추가
        for(int i =0; i<H; i++){
            for(int j =0; j<N-1; j++){
                //이미 지정한 가로선이라면 패스
                if(horizonLines[i][j]){
                    continue;
                }
                //인접한 가로선이 있다면 지정할 수 없다.
                if(j != 0){
                    if(horizonLines[i][j-1]){
                        continue;
                    }
                }
                if(j != N-2){
                    if(horizonLines[i][j+1]){
                        continue;
                    }
                }
                //인접한 가로선이 없다면 가로선을 긋고 다음 가로선을 정하러 가자
                horizonLines[i][j] = true;
                addHorizonLines(ladder, count+1);
                horizonLines[i][j] = false;
            }
        }
    }

    //사다리가 유효한지 확인하는 프로그램
    public static boolean executeLadderProgram(){
        for(int i = 0; i<N; i++){   //시작하는 사다리의 번호
            int currentNum = i;
            for(int j = 0; j<H; j++){   //가로선 줄. 한단계씩 다음칸을 향해 내려간다.
                if(currentNum !=0){
                    //왼쪽으로 이동
                    if(horizonLines[j][currentNum-1]){
                        currentNum -= 1;
                        continue;
                    }
                }
                if(currentNum != N-1){
                    //오른쪽으로 이동
                    if(horizonLines[j][currentNum]){
                        currentNum += 1;
                        continue;
                    }
                }
            }
            //결과가 다르게 나왔으니 false 리턴
            if(currentNum != i){
                return false;
            }
        }

        //모든 사다리가 자기 자신의 번호로 끝이 났다면 true
        return true;
    }
}