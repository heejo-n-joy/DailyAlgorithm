import java.util.HashSet;

/**
 * 문제 내용
 * - n개의 직선들이 교차하는 점들 중 x,y값이 모두 정수인 부분들의 좌표를 구하려 한다.
 *
 * 풀이 방법
 * - 1. 모든 교차하는 점들을 보며 x,y값이 모두 정수인지 확인
 *  - 정수가 맞다면 x와 y를 저장하고, 이 때 x,y의 최대값과 최소값을 갱신한다.
 * - 2. 최대값과 최소값에 맞는 좌표를 출력한다.
 *  - 해당 x,y 정보가 저장이 되어있다면 '*'을, 아니라면 '.'을 출력하도록 한다.
 *
 * 필요한 변수
 * - HashMap : (x,y) 정보를 저장 및 확인한다.
 * - x_min, x_max, y_min, y_max : 출력시 출력 범위를 설정하기 위한 변수
 *
 * 필요함 함수
 * - 두 직선의 교차지점을 구하는 함수.
 * - ax + by + c = 0
 * - dx + ey + f = 0
 * - x = (bf - ce) / (ae - bd)
 * - y = (cd - af) / (ae - bd)
 *
 * 예상 시간복잡도
 * - 두 직선을 선택 및 좌표 저장 : n * (n-1) / 2
 * - 출력 : 1000 * 1000
 *
 * 예상 범위
 * - 200001 * 200001은 40,000,000,001 (400억)이 되므로, 좌표의 계산 값들은 전부 long으로 계산
 *
 * 회고
 * - int 값들을 계산해서 long으로 저장하는 과정에서, long 타입으로 cast를 우선적으로 해야 한다.
 */

class Solution {
    public String[] solution(int[][] line) {
        HashSet<String> hashSet = new HashSet<>();   //직선이 교차하는 정수 좌표들을 저장

        long x_min = Long.MAX_VALUE;
        long x_max = Long.MIN_VALUE;
        long y_min = Long.MAX_VALUE;
        long y_max = Long.MIN_VALUE;
        for(int i = 0; i<line.length; i++){
            for(int j = i + 1; j<line.length; j++){
                if(i == j){ //같은 직선끼리는 비교할 수 없다.
                    continue;
                }
                //계산의 편의를 위한 변수 사용
                long a = line[i][0];
                long b = line[i][1];
                long c = line[i][2];
                long d = line[j][0];
                long e = line[j][1];
                long f = line[j][2];

                // 두 직선이 무한 교차하는 경우는 없다 했으니, 평행하거나 한 점에서 만나는 경우만 생긴다.
                long x_numerator = (b * f - c * e);  //x의 분자
                long y_numerator = (c * d - a * f);  //y의 분자
                long denominator = (a * e - b * d);  //분모


                //0으로 나눌 수 없는 경우
                if(denominator == 0){
                    //평행하니 패스
                    continue;
                }

                //교차하는 지점이 정수라면
                if((x_numerator % denominator == 0) && (y_numerator % denominator == 0)){
                    long add_x = x_numerator / denominator;
                    long add_y = y_numerator / denominator;
                    hashSet.add(add_x + "," + add_y);
                    // System.out.println("(" + add_x + ", " + add_y + ")");
                    x_max = Math.max(add_x, x_max);
                    x_min = Math.min(add_x, x_min);
                    y_max = Math.max(add_y, y_max);
                    y_min = Math.min(add_y, y_min);
                }
            }
        }

        // System.out.println("@@@" + hashSet.size());
        int answer_range_y = (int) (y_max - y_min + 1);
        String[] answer = new String[answer_range_y];
        for(int j = 0; j < answer_range_y; j++){
            answer[j] = "";
            for(long i = x_min; i<=x_max; i++){
                // System.out.println("(" + i + ", " + (y_max - j) + ")");
                if(hashSet.contains(i + "," + (y_max - j))){
                    answer[j] += "*";
                }
                else{
                    answer[j] += ".";
                }
            }
        }
        // for(int i = 0 ; i<answer_range_y; i++){
        //     System.out.println(answer[i]);
        // }
        
        return answer;
    }
}
