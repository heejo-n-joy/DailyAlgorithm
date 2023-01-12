import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 배열A에 연산을 적용해서 사이즈를 키우고 정렬을 다시 한다.
 * <p>
 * 요구하는 출력
 * - 원하는 결과값이 나오기 위한 최소 시간
 * <p>
 * 입력 변수
 * - r,c : 원하는 결과값이 나올 배열 위치
 * - k : 원하는 결과값
 * - 배열의 상태
 * <p>
 * 문제 유의사항
 * - 배열의 우선순위가 있다. (수의 등장 횟수, 수의 크기 모두 오름차순)
 * - 크기가 100을 넘어가면 100까지만 잘라서 사용
 * <p>
 * 전략
 * - 배열A 크기를 100으로 잡고 시작하자
 * - 계산용 일차원 배열을 크기 100으로 하여 새로 만들어 계산하고 값을 갱신하고 등의 순서로 해보자.
 * <p>
 * 체감 난이도 : ★★★☆☆
 * <p>
 * 회고할 내용
 * - R연산과 C연산은 i, j 순서가 반대로 진행된다. 중복된 코드를 복사해서 순서만 바꾸다 보니 미처 바꾸지 못한 부분으로 오류가 생겼었다.
 * - 값은 100이 최대이기 때문에, 배열 생성시 array[100]이 아닌 array[101]을 해줘야한다.
 *
 * @author HeejoPark
 */
public class Main {
    static final int MAX = 100;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken()) -1;   //원하는 결과값이 나올 배열 A의 행 위치
        int c = Integer.parseInt(st.nextToken()) -1;   //원하는 결과값이 나올 배열 A의 열 위치
        int k = Integer.parseInt(st.nextToken());   //원하는 결과값
        int[][] A = new int[MAX][MAX];              //배열 A
        for(int i =0; i<3; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<3; j++){
                A[i][j] = Integer.parseInt(st.nextToken()); //배열 A의 값 넣는다
            }
        }

        int time = 0;
        int realR = 3;
        int realC = 3;
        //프로그램 실행
        while(true){
            //문제에서 원하는 값을 찾았다면
            if(A[r][c] == k){
                break;
            }
            //시간이 100초가 넘었다면 프로그램 중단
            if(time>100){
                time = -1;
                break;
            }
            //무슨 배열을 선택할지 정하자
            if(realR>=realC){
                int maxC = 0;
                //R 연산
                for(int i =0; i<realR; i++){
                    int[] tempA = new int[MAX+1];                 //기존 배열의 정보를 저장
                    boolean[] checkTempA = new boolean[MAX+1];    //기존 배열에서 사용한 숫자를 체크
                    int countTempA = 0;                         //기존 배열에서 사용한 숫자 종류 개수를 저장
                    for(int j =0; j<realC; j++){
                        if(A[i][j]>0) {
                            tempA[A[i][j]]++;   //현재 수를 파악
                            if (!checkTempA[A[i][j]]) {   //현재 숫자를 사용했다고 체크하지 않았다면
                                checkTempA[A[i][j]] = true; //사용했다는 체크
                                countTempA++;   //사용한 숫자 종류 개수 1 증가
                            }
                        }
                    }
                    int[] newA = new int[MAX];
                    int newAIndex = 0;  //새로운 배열의 크기
                    int findOrder = 1;  //기존 배열에서의 등장횟수를 확인. 순차적으로 증가한다
                    while(countTempA>0){
                        if(newAIndex==MAX){ //새 배열의 크기가 제한 범위를 벗어나면 나머지는 그냥 버리자
                            break;
                        }
                        for(int j =1; j<=MAX; j++){  //1부터 탐색 (0은 필요가 없기 때문)
                            if(tempA[j]==findOrder){
                                newA[newAIndex++] = j;
                                newA[newAIndex++] = findOrder;
                                countTempA--;
                            }
                        }
                        findOrder++;
                    }
                    //이 값을 기존 배열에 새롭게 이식
                    for(int j = 0; j<MAX; j++){
                        A[i][j] = newA[j];
                    }
                    maxC = Math.max(newAIndex, maxC);
                }
                realC = maxC;
            }
            else{
                //C 연산
                var maxR = 0;
                for(int i =0; i<realC; i++){
                    int[] tempA = new int[MAX+1];                 //기존 배열의 정보를 저장
                    boolean[] checkTempA = new boolean[MAX+1];    //기존 배열에서 사용한 숫자를 체크
                    int countTempA = 0;                         //기존 배열에서 사용한 숫자 종류 개수를 저장
                    for(int j =0; j<realR; j++){
                        if(A[j][i]>0) {
                            tempA[A[j][i]]++;   //현재 수를 파악
                            if (!checkTempA[A[j][i]]) {   //현재 숫자를 사용했다고 체크하지 않았다면
                                checkTempA[A[j][i]] = true; //사용했다는 체크
                                countTempA++;   //사용한 숫자 종류 개수 1 증가
                            }
                        }
                    }
                    int[] newA = new int[MAX];
                    int newAIndex = 0;  //새로운 배열의 크기
                    int findOrder = 1;  //기존 배열에서의 등장횟수를 확인. 순차적으로 증가한다
                    while(countTempA>0){
                        if(newAIndex==MAX){ //새 배열의 크기가 제한 범위를 벗어나면 나머지는 그냥 버리자
                            break;
                        }
                        for(int j =1; j<=MAX; j++){  //1부터 탐색 (0은 필요가 없기 때문)
                            if(tempA[j]==findOrder){
                                newA[newAIndex++] = j;
                                newA[newAIndex++] = findOrder;
                                countTempA--;
                            }
                        }
                        findOrder++;
                    }
                    //이 값을 기존 배열에 새롭게 이식
                    for(int j = 0; j<MAX; j++){
                        A[j][i] = newA[j];
                    }
                    maxR = Math.max(newAIndex, maxR);
                }
                realR = maxR;
            }
            time++; //시간 증가
        }

        //결과 출력
        System.out.println(time);
    }
}