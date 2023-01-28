import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 사다리 한 줄을 조작해서 원하는 결과를 만들자
 *
 * 요구하는 출력
 * - 원하는 결과를 만들기 위한 조작된 한 줄
 *
 * 주의사항
 * - 원하는 결과가 나오지 않는다면 x를 출력
 * - 조작될 가로 줄은 case마다 다르다
 *
 * 전략
 * - 해당 줄 이전까지, 해당 줄 이후부터 끝까지 사다리를 돌려본다.
 * - 두 문자열을 비교한다. 이 때, 맞닿는 문자끼리 서로 자리를 바꿀 수 있다.
 * - 이 시스템이 성공하는지, 실패하는지 결과를 출력한다.
 *
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int k = Integer.parseInt(br.readLine());    //참가한 사람 수
        int n = Integer.parseInt(br.readLine());    //전체 가로 줄의 수
        char[][] ladder = new char[n][k-1]; //사다리 생성
        char[] wannaResult = new char[k];   //원하는 결과
        char[] wannaStart = new char[k];    //원하는 시작
        String str = br.readLine();
        for(int i =0; i<k; i++){    //시작과 끝 정하기
            wannaResult[i] = str.charAt(i);
            wannaStart[i] = (char) ('A' + i);
        }
        int questionIndex = -1;
        for(int i =0; i<n; i++){
            str = br.readLine();
            if(str.charAt(0) == '?'){
                questionIndex = i;  //해당 위치 기억해두기
            }
            for(int j = 0; j<k-1; j++){
                ladder[i][j] = str.charAt(j);
            }
        }
        //시작부터 questionIndex-1까지 실행
        wannaStart = fromStartToQuestion(ladder, wannaStart, k, n, questionIndex);
        //questionIndex+1부터 마지막까지 실행 (역순으로 재생)
        wannaResult = fromQuestionToEnd(ladder, wannaResult, k, n, questionIndex);
        //두 문자열을 사다리 타면서 비교 후 결과 출력
        compareStartAndResult(wannaStart, wannaResult, k);
    }

    public static char[] fromStartToQuestion(char[][] ladder, char[] wannaStart, int k, int n, int questionIndex){
        char[] temp = new char[k];
        //사다리를 타기
        for(int i = 0; i<k; i++){
            int currentLoc = i;
            //알파벳 하나를 골랐다면 해당 알파벳으로 사다리를 타기
            for(int j = 0; j<questionIndex; j++){
                //현재 사다리가 왼쪽 끝이 아니라면
                if(currentLoc > 0){
                    //왼쪽 사다리에 가로줄이 놓여있다면
                    if(ladder[j][currentLoc-1] == '-'){
                        currentLoc--;   //왼쪽으로 이동 후
                        continue;   //한 칸 내려간다.
                    }
                }
                //현재 사다리가 오른쪽 끝이 아니라면
                if(currentLoc < k-1){
                    //오른쪽 사다리에 가로줄이 놓여져있다면
                    if(ladder[j][currentLoc] == '-'){
                        currentLoc++;   //오른쪽으로 이동 후
                        continue;   //한 칸 내려간다
                    }
                }
            }
            //마지막까지 내려왔다면 해당 위치에 알파벳 저장
            temp[currentLoc] = wannaStart[i];
        }
        return temp;
    }

    public static char[] fromQuestionToEnd(char[][] ladder, char[] wannaStart, int k, int n, int questionIndex){
        char[] temp = new char[k];
        //사다리를 타기
        for(int i = 0; i<k; i++){
            int currentLoc = i;
            //알파벳 하나를 골랐다면 해당 알파벳으로 사다리를 타기
            for(int j = n-1; j>questionIndex; j--){
                //현재 사다리가 왼쪽 끝이 아니라면
                if(currentLoc > 0){
                    //왼쪽 사다리에 가로줄이 놓여있다면
                    if(ladder[j][currentLoc-1] == '-'){
                        currentLoc--;   //왼쪽으로 이동 후
                        continue;   //한 칸 내려간다.
                    }
                }
                //현재 사다리가 오른쪽 끝이 아니라면
                if(currentLoc < k-1){
                    //오른쪽 사다리에 가로줄이 놓여져있다면
                    if(ladder[j][currentLoc] == '-'){
                        currentLoc++;   //오른쪽으로 이동 후
                        continue;   //한 칸 내려간다
                    }
                }
            }
            //마지막까지 내려왔다면 해당 위치에 알파벳 저장
            temp[currentLoc] = wannaStart[i];
        }
        return temp;
    }

    public static void compareStartAndResult(char[] wannaStart, char[] wannaResult, int k){
        StringBuilder sb = new StringBuilder(); //결과용 String
        for(int i = 0 ; i < k-1; i++){
            //같은 문자라면 패스
            if(wannaStart[i] == wannaResult[i]){
                sb.append("*");
            }

            //다른 문자이지만, 뒤의 문자와 위치가 바뀐 상황일 경우
            else if(wannaStart[i] == wannaResult[i+1] || wannaStart[i+1] == wannaResult[i]){
                sb.append("-"); //사다리를 추가
                char tmp = wannaStart[i];   //위치를 올바르게 바꿔준다
                wannaStart[i] = wannaStart[i+1];
                wannaStart[i+1] = tmp;
            }
            //그런 경우가 아니라면 실패
            else{
                for(int j = 0 ; j < k-1 ; j++)
                    System.out.print("x");
                System.out.println();
                return;
            }
        }

        //성공했다면, 결과 출력
        System.out.println(sb);
    }
}