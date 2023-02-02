/*
* 주의사항
- 성격 유형 점수가 같으면 사전순으로 빠른 성격 유형으로 판단
*/

class Solution {
    public String solution(String[] survey, int[] choices) {
        int[] kind_score = new int[8];
        char[] kind_name = {'R', 'T', 'C', 'F', 'J', 'M', 'A', 'N'};
        int length = choices.length;    //설문지의 길이
        for(int i =0; i<length; i++){
            switch(choices[i]){
                case 1:
                case 2:
                case 3:
                    //왼쪽 유형에 점수를 준다.
                    kind_score[index(survey[i].charAt(0), kind_name)] += (4 - choices[i]);
                    break;
                case 4:
                    //중립을 선택했기에 아무 일도 일어나지 않는다.
                    break;
                case 5:
                case 6:
                case 7:
                    //오른쪽 유형에 점수를 준다.
                    kind_score[index(survey[i].charAt(1), kind_name)] += (choices[i] - 4);
                    break;
                    
            }
        }
        String answer = "";
        for(int i = 0; i<8; i += 2){
            if(kind_score[i] >= kind_score[i+1]){
                answer += kind_name[i];
            }
            else{
                answer += kind_name[i+1];
            }
        }
        return answer;
    }
    
    public int index(char name, char[] kind_name){
        //해당 이름을 가진 배열 인덱스를 출력
        for(int i =0; i<8; i++){
            if(kind_name[i] == name){
                return i;
            }
        }
        return -1;
    }
}