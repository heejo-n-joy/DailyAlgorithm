import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 학생회장 후보들을 추천하기
 * <p>
 * 요구하는 출력
 * - 최종 후보가 누구인지 출력
 * <p>
 * 전략
 * - 배열을 만들자
 *
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());        //사진틀의 개수
        int[][] frames = new int[N][3];  //[학생 번호] [사진틀에 들어온 시간]
        int[] studentsVoteCount = new int[101]; //학생 당 받은 투표수
        boolean[] isStudentsInFrame = new boolean[101]; //해당 학생이 사진틀에 걸려있는지를 체크하는 배열
        int curCount = 0;
        int totalCount = Integer.parseInt(br.readLine());    //총 추천 횟수
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int time =0; time<totalCount; time++){
            int newNum = Integer.parseInt(st.nextToken());  //추천 받은 번호
            //해당 학생이 이미 사진틀에 걸려있는지 확인
            if(isStudentsInFrame[newNum]){
                //있다면, 해당 투표수를 1 증가시킨다.
                studentsVoteCount[newNum]++;
            }
            else{
                //없다면, 투표수를 1 증가시키고,
                studentsVoteCount[newNum]++;
                //사진틀이 아직 남아있다면
                if(curCount < N){
                    //바로 올리기
                    frames[curCount][0] = newNum;   //학생 번호 입력
                    frames[curCount][1] = time;     //현재 시간 입력
                    isStudentsInFrame[newNum] = true;
                    curCount++;
                }
                //사진틀에 남아있는 자리가 없다면
                else {
                    //새로 사진틀에 업로드될 수 있는지 확인하기
                    int minStudentFrameNo = 0;
                    for (int j = 1; j < N; j++) {
                        //기존 최저 개수보다 더 작은 투표수를 가진 학생을 찾는다면
                        if(studentsVoteCount[frames[j][0]] < studentsVoteCount[frames[minStudentFrameNo][0]]){
                            //해당 학생 번호로 바꾸기
                            minStudentFrameNo = j;
                        }
                        //만약 투표수가 같다면
                        else if(studentsVoteCount[frames[j][0]] == studentsVoteCount[frames[minStudentFrameNo][0]]){
                            //만약 기존 최저 학생보다 더 오래됐다면
                            if(frames[j][1] < frames[minStudentFrameNo][1]){
                                //해당 학생 번호로 바꾸기
                                minStudentFrameNo = j;
                            }
                        }
                    }
                    //갱신
                    isStudentsInFrame[newNum] = true;
                    isStudentsInFrame[frames[minStudentFrameNo][0]] = false;
                    studentsVoteCount[frames[minStudentFrameNo][0]] = 0;
                    frames[minStudentFrameNo][0] = newNum;
                    frames[minStudentFrameNo][1] = time;
                }
            }
        }
        //결과 출력
        boolean[] printCheck = new boolean[N];
        for(int i =0; i<N; i++){
            int minNum = 0;
            for(int j =0; j<N; j++) {
                if(printCheck[j]){
                    continue;
                }
                minNum = j;
                break;
            }
            for(int j =0; j<N; j++) {
                if (printCheck[j]) {
                    continue;
                }
                if (frames[j][0] < frames[minNum][0]){
                    minNum = j;
                }
            }
            printCheck[minNum] = true;
            if(frames[minNum][0] != 0) {
                System.out.print(frames[minNum][0] + " ");
            }
        }
        System.out.println();
    }
}