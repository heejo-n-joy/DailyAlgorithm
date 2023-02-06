import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - IF문 대신 만들어주기
 * <p>
 * 요구하는 출력
 * - 캐릭터의 전투력에 맞는 칭호들
 * <p>
 * 전략
 * - 전투력별 칭호 배열을 만들고, 시작점과 끝점에서 중심값을 기준으로 값이 큰지 작은지를 계속 비교할 계획
 * - 일반적으로 매 번 계산을 하면 100000 * 100000이기 때문..
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //칭호의 개수
        int M = Integer.parseInt(st.nextToken());   //캐릭터들의 개수
        String[] names = new String[N];
        int[] values = new int[N];
        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            names[i] = st.nextToken();  //칭호 이름
            values[i] = Integer.parseInt(st.nextToken());   //해당 칭호 이름의 조건
        }
        StringBuilder sb = new StringBuilder();
        //실행
        for(int character = 0; character<M; character++){
            int characterScore = Integer.parseInt(br.readLine());
            int characterResult = findResultOfCharacter(N, names, values, characterScore);
            sb.append(names[characterResult] + "\n");
        }

        //결과 출력
        System.out.println(sb);
    }

    public static int findResultOfCharacter(int N, String[] names, int[] values, int characterScore){
        int startIndex = 0;
        int endIndex = N-1;
        int averageIndex = -1;
        while(true){
            if(averageIndex == (startIndex + endIndex) / 2){
                averageIndex++;
            }
            else {
                averageIndex = (startIndex + endIndex) / 2;
            }
            if(startIndex >= endIndex){
                return endIndex;
            }
            //해당 점수가 중간 값보다 크다면 다음 인덱스로 이동
            if(values[averageIndex] < characterScore){
                startIndex = averageIndex;
                continue;
            }
            else{
                //만약 첫번째 인덱스가 아니라면
                if(averageIndex > 0){
                    //해당 인덱스의 -1의 값보다는 크다면
                    if(values[averageIndex-1] < characterScore){
                        //끝. 해당 인덱스의 칭호를 출력한다.
                        return averageIndex;
                    }
                    else{
                        endIndex = averageIndex;
                        continue;
                    }
                }
                //만약 첫번째 인덱스였다면
                else{
                    //끝. 첫 번째 칭호를 출력한다.
                    return averageIndex;
                }
            }
        }
//        return -1;  //에러
    }
}