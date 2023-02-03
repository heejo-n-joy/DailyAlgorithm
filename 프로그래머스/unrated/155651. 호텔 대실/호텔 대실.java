/*
1시간에 60분. 24시간에 1440분
입력받을때 청소시간까지 포함하자
*/

class Solution {
    public int solution(String[][] book_time) {
        int[] minutes = new int[1440];  //00:00부터 23:59까지 총 1440분
        for(int i =0; i<book_time.length; i++){
            int startTime = convertTime(book_time[i][0]);
            int endTime = convertTime(book_time[i][1]) + 9;    //청소시간 10분 추가
            if(endTime > 1439){
                endTime = 1439;
            }
            for(int j = startTime; j<=endTime; j++){
                minutes[j]++;
            }
        }
        int answer = 0;
        for(int i = 0; i<minutes.length; i++){
            answer = Math.max(answer, minutes[i]);
        }
        return answer;
    }
    
    public int convertTime(String time){
        int hour = Integer.parseInt(time.substring(0, 2));
        int min = Integer.parseInt(time.substring(3,5));
        
        return hour * 60 + min;
    }
}