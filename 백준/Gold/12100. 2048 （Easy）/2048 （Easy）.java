import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 오랜만에 보는 추억의 2048게임
 * <p>
 * 요구하는 출력
 * - 5번을 이동해서 만들 수 있는 가장 큰 블록의 값
 * <p>
 * 입력 변수
 * - N : 보드의 크기
 * - 게임 판 초기 정보
 * <p>
 * 문제 유의사항
 * - 방향은 상하좌우
 * - 한 턴에 이미 합쳐진 숫자는 또 합쳐질 수 없다.
 * - 똑같은 수가 3개라면 이동하려고 하는 쪽의 칸들이 우선적으로 합쳐진다.
 * <p>
 * 전략
 * - 최대값을 구하는 경우의 수는 Math.pow(4,5)이기 때문에 시간 초과는 없다고 예상
 * - 한 턴을 하나의 함수로 만들어보자
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * @author HeejoPark
 */
public class Main {
    static int[][] originalBoard;
    static int maxNumber;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //보드의 크기
        int round = 5;
        //보드 정보를 입력
        originalBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                originalBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] dr = {0, 1, 2, 3};    //순서대로 상(0)하(1)좌(2)우(3)
        int[] direction = new int[round];
        maxNumber = 0;  //최대값을 구하기 위한 최소값(0) 설정
        Game(0, round, direction, N);

        //결과 출력
        System.out.println(maxNumber);
    }

    //게임의 전체 부분
    public static void Game(int count, int round, int[] direction, int N){
        //모든 라운드의 방향을 정했다면 게임을 시작한다.
        if(count==round){
            executeGame(direction, round, N);
            return;
        }
        //0~3중에 숫자를 선택하고 다음 라운드의 방향을 정하러 간다
        for(int i =0; i<4; i++){
            direction[count] = i;
            Game(count+1, round, direction, N);
        }
    }

    //모든 라운드의 방향이 정해지고 시작하는 본게임
    public static void executeGame(int[] direction, int round, int N){
        int[][] tempBoard = new int[N][N];   //게임에 사용될 임시 보드
        //기본 세팅 정보를 임시 보드에 옮긴다.
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                tempBoard[i][j] = originalBoard[i][j];
            }
        }
        //게임을 진행하고
        for(int i =0; i<round; i++){
            oneRound(tempBoard, direction[i], N);   //한라운드씩 진행
        }
        //결과를 확인해보기
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                maxNumber = Math.max(tempBoard[i][j], maxNumber);   //지금까지의 최대값보다 더 크다면 최대값 갱신하기
            }
        }
    }

    //게임 라운드
    public static void oneRound(int[][] tempBoard, int direction, int N){
        //방향은 순서대로 상(0)하(1)좌(2)우(3)
        for(int i =0; i<N; i++){
            Queue<Integer> queue = new LinkedList<>();
            for(int j =0; j<N; j++) {
                switch(direction){
                    case 0: //상
                        if (tempBoard[j][i]>0){
                            queue.offer(tempBoard[j][i]);
                        }
                        break;
                    case 1: //하
                        if (tempBoard[N-1-j][i]>0){
                            queue.offer(tempBoard[N-1-j][i]);
                        }
                        break;
                    case 2: //좌
                        if (tempBoard[i][j]>0){
                            queue.offer(tempBoard[i][j]);
                        }
                        break;
                    case 3: //우
                        if (tempBoard[i][N-1-j]>0){
                            queue.offer(tempBoard[i][N-1-j]);
                        }
                        break;
                }
            }
            int[] newArray = new int[N];    //새롭게 갈아끼울 배열 한 줄
            int index = 0;  //새로운 한 줄에 숫자가 들어갈 새 위치를 나타내는 인덱스
            boolean isUnion = true;      //지금 배열에 들어간 마지막 값이 합쳐진 숫자인지 아닌지 체크
            while(!queue.isEmpty()){
                int temp = queue.poll();    //순서대로 숫자를 꺼낸다
                //직전에 넣은 숫자가 합쳐진 숫자가 아니고, 지금 넣을 숫자와 같다면
                if(!isUnion && (newArray[index-1] == temp)){
                    newArray[index-1] *= 2; //직전에 넣은 숫자 위치에 2배의 값을 넣는다
                    isUnion = true; //숫자가 합쳐졌으니 true로 설정
                }
                //직전에 넣은 숫자가 합쳐진 숫자거나 지금 넣을 숫자와 다르다면
                else{
                    //직전 숫자와 합칠 수 없기 때문에 그냥 배열에 추가한다.
                    newArray[index++] = temp;
                    isUnion = false;    //안합쳐진 숫자를 새로 넣었으니 false로 설정
                }
            }
            //완성된 배열을 가지고 새롭게 넣는다
            for(int j =0; j<N; j++){
                switch (direction){
                    case 0: //상
                        tempBoard[j][i] = newArray[j];
                        break;
                    case 1: //하
                        tempBoard[N-1-j][i] = newArray[j];
                        break;
                    case 2: //좌
                        tempBoard[i][j] = newArray[j];
                        break;
                    case 3: //우
                        tempBoard[i][N-1-j] = newArray[j];
                        break;
                }
            }
        }
    }
}