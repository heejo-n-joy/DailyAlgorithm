import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 갤러리를 위에서 바라봤을 때, 길이 2의 그림을 걸 수 있는 최대 개수
 *
 * 아이디어 : 구현
 * - 그림을 걸기 위해선 반드시 공간-벽으로 구성이 되어야 한다.
 * - 이 구성은 상하좌우 4가지 경우로 가능하다.
 * - 즉 탐색을 4번 진행하도록 한다.
 * 1. 경우를 선택하고 전체 벽을 탐지한다.
 * 2. 연속된 벽의 개수에 /2 를 하여 걸 수 있는 그림 수를 카운트한다.
 * 3. 이를 각 4가지 경우별로 탐지를 한다.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());   //세로 길이
        int N = Integer.parseInt(st.nextToken());   //가로 길이

        char[][] map = new char[M][N];  //갤러리 지도

        //지도 데이터 입력받기
        for(int i = 0; i<M; i++){
            String str = br.readLine();
            for(int j =0; j<N; j++){
                map[i][j] = str.charAt(j);
            }
        }

        //4가지 경우. 각각 공간을 기준으로 벽이 상하좌우에 있음을 뜻한다.
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int result = 0;

        //탐색을 가로로 진행한다. (벽-공간이 상,하로 이뤄진 경우)
        for(int k = 0; k<2; k++){
            //테두리는 모두 벽으로 되어있기 때문에, 가운데 공간만 확인한다.
            for(int i = 1; i<M-1; i++){
                int count = 0;
                for(int j = 1; j<N-1; j++){
                    //탐색에 활용될 변수

                    //해당 위치가 빈 공간이라면
                    if(map[i][j]=='.'){
                        //현재 위치에 해당 벽의 경우가 유효하다면 개수를 1 증가
                        if(map[i+dr[k]][j+dc[k]] == 'X') {
                            count++;
                            continue;
                        }
                    }

                    //그 외의 경우라면 기존 개수를 결과에 반영하고 초기화
                    result += count / 2;
                    count = 0;
                }

                //벽의 한 줄이 유효한 채로 끝났다면, 이 값도 반영해주기
                result += count / 2;
            }
        }

        //탐색을 세로로 진행한다. (벽-공간이 좌,우로 이뤄진 경우)
        for(int k = 2; k<4; k++){
            //테두리는 모두 벽으로 되어있기 때문에, 가운데 공간만 확인한다.
            for(int j = 1; j<N-1; j++){
                int count = 0;
                for(int i = 1; i<M-1; i++){
                    //탐색에 활용될 변수

                    //해당 위치가 빈 공간이라면
                    if(map[i][j]=='.'){
                        //현재 위치에 해당 벽의 경우가 유효하다면 개수를 1 증가
                        if(map[i+dr[k]][j+dc[k]] == 'X') {
                            count++;
                            continue;
                        }
                    }

                    //그 외의 경우라면 기존 개수를 결과에 반영하고 초기화
                    result += count / 2;
                    count = 0;
                }

                //벽의 한 줄이 유효한 채로 끝났다면, 이 값도 반영해주기
                result += count / 2;
            }
        }



        //결과 출력
        System.out.println(result);
    }

}