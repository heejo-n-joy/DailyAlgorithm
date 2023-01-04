import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 상어 초등학교 학생의 자리를 정한다.
 *
 * 요구하는 출력
 * - 학생의 만족도의 총 합을 구하라
 *
 * 입력 변수
 * - N : 한 줄의 크기
 * - 학생의 번호 + 그 학생이 좋아하는 학생 4명의 번호
 *
 * 문제 유의사항
 * - 인접의 기준은 행의 차이 + 열의 차이가 1 => 상하좌우
 * - 조건을 순서대로, 그리고 우선순위대로 처리한다.
 *
 * 체감 난이도 : ★★★☆☆
 * - 문제를 풀어가는 시간이 오래걸린다.
 *
 * 회고할 내용
 * - 비어있는 자리인지 체크하는 과정을 빼먹었다.
 * - 역시 코딩은 항상 완벽해야 가능한 것 같다.
 * - 거의 1시간 걸려서 풀었다.
 *
 * @author HeejoPark
 *
 */
public class Main {
    static int N;
    static int desks[][];
    static int survey[][];
    static int surveyResultLocation[][];
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());    //한 줄의 크기를 입력받는다.
        survey = new int[N*N][5];   //학생들의 설문조사
        surveyResultLocation = new int[N*N][2];    //설문조사한 학생의 최종 위치를 저장
        desks = new int[N][N];      //설문조사를 바탕으로 한 결과 배치도
        //설문지 입력받기
        for(int i = 0; i<N*N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 5; j++) {
                survey[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int studentNum = 0; studentNum<N*N; studentNum++) {
            //자리배치
            Queue<int[]> queue1 = new LinkedList<>();
            int currentMax = 0;
            //규칙1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 자리
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    //비어있는 칸인지 먼저 체크
                    if(desks[i][j] >0){
                        continue;
                    }
                    //해당 위치를 기준으로 주변을 탐색
                    int count = findBestFriendsNearby(i, j, survey[studentNum]);
                    if (count > currentMax) {
                        queue1.clear(); //queue 초기화
                        currentMax = count; //현재 최대값 갱신
                        queue1.offer(new int[]{i, j});   //queue에 현재 책상 위치 추가
                    } else if (count == currentMax) {
                        queue1.offer(new int[]{i, j});   //queue에 현재 책상 위치 추가
                    }
                }
            }
            //만약 규칙 1만으로도 유일한 자리가 생겼다면
            if(queue1.size()==1){
                int[] confirmed = queue1.poll();
                desks[confirmed[0]][confirmed[1]] = survey[studentNum][0];  //해당 자리에 학생을 앉히고
                surveyResultLocation[studentNum] = confirmed;               //해당 위치를 저장
                continue;   //다음으로 넘어가기
            }

            //규칙2. 인접한 칸에서 비어있는 칸이 가장 많은 자리
            Queue<int[]> queue2 = new LinkedList<>();
            currentMax = 0;
            for(int i =0 ; i<queue1.size(); i++){
                int[] temp = queue1.poll();
                int count = findEmptyDeskNearby(temp[0], temp[1]);
                if (count > currentMax) {
                    queue2.clear(); //queue 초기화
                    currentMax = count; //현재 최대값 갱신
                    queue2.offer(temp);   //queue에 현재 책상 위치 추가
                } else if (count == currentMax) {
                    queue2.offer(temp);   //queue에 현재 책상 위치 추가
                }
                queue1.offer(temp);
            }
            //어차피 규칙3 순서대로 확인을 하고 있었기 때문에 규칙2을 거친 후 남은 가장 첫번째 위치에 앉히면 된다.
            int[] confirmed = queue2.poll();
            desks[confirmed[0]][confirmed[1]] = survey[studentNum][0];  //해당 자리에 학생을 앉히고
            surveyResultLocation[studentNum] = confirmed;               //해당 위치를 저장
        }
        int result = 0;
        //디버깅
//        for(int i =0; i<N; i++){
//            for(int j =0; j<N; j++){
//                System.out.print(desks[i][j] + " ");
//            }
//            System.out.println();
//        }
        //만족도 조사
        for(int i =0; i<N*N; i++){
            result += findSatisfactionScore(surveyResultLocation[i][0], surveyResultLocation[i][1], survey[i]);
        }

        System.out.println(result);
    }

    static int dr[] = {-1, 1, 0, 0};
    static int dc[] = {0, 0, -1, 1};
    public static int findBestFriendsNearby(int r, int c, int friendsList[]){
        return exploreDeskNearby(r, c, friendsList);
    }
    public static int findEmptyDeskNearby(int r, int c){
        return exploreDeskNearby(r, c, new int[]{0, 0});
    }
    public static int findSatisfactionScore(int r, int c, int friendsList[]){
        int score = exploreDeskNearby(r, c, friendsList);
        switch(score){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 10;
            case 3:
                return 100;
            case 4:
                return 1000;
        }
        System.out.println("ERROR!");
        return -1;
    }
    public static int exploreDeskNearby(int r, int c, int friendsList[]){
        int count = 0;
        for(int i = 0; i<4; i++){
            //인접한 책상의 상하좌우 위치가 배열 범위 안에 들어오는 경우
            if(0<=r+dr[i] && r+dr[i]<N && c+dc[i]>=0 && c+dc[i]<N){
                for(int j = 1; j<friendsList.length; j++) {
                    //원하는 값인지 확인 후 값 처리(친구 or 빈자리)
                    if (desks[r + dr[i]][c + dc[i]] == friendsList[j]){
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }
}