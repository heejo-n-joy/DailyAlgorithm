import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 인구가 규칙에 맞게 땅 안에서 우르르 이동한다.
 * <p>
 * 요구하는 출력
 * - 인구 이동이 며칠 동안 발생하는가?
 * <p>
 * 입력 변수
 * - N : 땅의 크기(땅 1x1에 나라1)
 * - L,R : 인구의 차이 범위
 * - 나라별 인구
 * <p>
 * 문제 유의사항
 * - 계산시 소수점은 생략
 * <p>
 * 전략
 * - Queue와 check[][]를 활용하여 인접한 나라들을 한꺼번에 찾고, 국경을 열고 인구를 이동시킨다.
 * <p>
 * 체감 난이도 : ★★☆☆☆
 * <p>
 * 회고할 내용
 * - 나라를 탐색하는 while문에서 종료가 되지 않아 확인해보니, 해당 나라가 이미 인접한 나라로 확인됐는지 체크하는 로직이 빠져있었다.
 *
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());   //땅의 크기
        int L = Integer.parseInt(st.nextToken());   //인구 차이 L명 이상
        int R = Integer.parseInt(st.nextToken());   //인구 차이 R명 이하
        int countries[][] = new int[N][N];      //땅(나라)

        for(int i =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j =0; j<N; j++){
                countries[i][j] = Integer.parseInt(st.nextToken()); //나라별 초기 인구수 입력
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean isPeopleMoved = true;   //오늘 인구가 이동했는지 확인
        int totalCount = 0;             //인구이동이 일어난 날들
        while(isPeopleMoved){
            boolean isCountryOpened[][] = new boolean[N][N];    //인구이동용 체크로 활용하는 변수
            isPeopleMoved = false;
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    //해당 나라가 인구이동을 안했는지 확인
                    if(!isCountryOpened[i][j]){
                        Queue<int[]> haveToCheckCounty = new LinkedList<>();    //확인이 필요한 나라
                        Queue<int[]> openCountryList = new LinkedList<>();  //인접해서 국경 오픈이 확정된 나라들
                        haveToCheckCounty.offer(new int[]{i, j});   //확인이 필요한 나라에 입력
                        openCountryList.offer(new int[]{i, j}); //인구이동이 확정된 나라에 입력
                        isCountryOpened[i][j] = true;
                        int totalPeopleInCountries = countries[i][j];     //인구이동을 할 총 인구수
                        while(!haveToCheckCounty.isEmpty()) {
                            int[] currentCountry = haveToCheckCounty.poll();
                            //주변 나라들을 하나씩 수색
                            for (int k = 0; k < 4; k++) {
                                int nearX = currentCountry[0] + dr[k];
                                int nearY = currentCountry[1] + dc[k];
                                //해당 나라의 위치가 유효한지 확인
                                if (nearX >= 0 && nearX < N && nearY >= 0 && nearY < N) {
                                    //해당 나라가 이미 탐색이 됐는지 확인
                                    if(!isCountryOpened[nearX][nearY]) {
                                        //해당 나라와의 인구 차이가 범위 내에 들어오는지 확인
                                        int differenceValue = Math.abs(countries[currentCountry[0]][currentCountry[1]] - countries[nearX][nearY]);
                                        if (differenceValue >= L && differenceValue <= R) {
                                            //해당 나라는 인접하고 국경을 허물 수 있다!
                                            isCountryOpened[nearX][nearY] = true;   //해당 나라는 확인했음을 체크
                                            haveToCheckCounty.offer(new int[]{nearX, nearY});   //또 인접한 나라가 있는지 확인할 기준 나라 추가
                                            openCountryList.offer(new int[]{nearX, nearY});     //이동이 확정된 나라에 포함
                                            totalPeopleInCountries += countries[nearX][nearY];  //이동이 확정된 총 인구수에 포함
                                        }
                                    }
                                }
                            }
                        }
                        //인구 이동이 필요한지 확인
                        if(openCountryList.size() > 1){
                            isPeopleMoved = true;
                            int averagePeopleInCountries = totalPeopleInCountries / openCountryList.size(); //인구 수를 평균내기 (소수점 제외)
                            while(!openCountryList.isEmpty()){
                                int[] country = openCountryList.poll();
                                countries[country[0]][country[1]] = averagePeopleInCountries;   //평균낸 인구를 이동
                            }
                        }
                    }
                }
            }
            //인구 이동이 일어났다면
            if(isPeopleMoved) {
                totalCount++;
            }
        }
        System.out.println(totalCount);
    }
}