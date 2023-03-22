import java.util.HashSet;

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