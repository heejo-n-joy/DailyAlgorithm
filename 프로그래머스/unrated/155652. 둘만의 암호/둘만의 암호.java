class Solution {
    public String solution(String s, String skip, int index) {
        String answer = "";
        boolean[] skip_alphabet = new boolean[26];  //스킵해야하는 알파벳을 체크
        for(int i =0; i<skip.length(); i++){
            skip_alphabet[skip.charAt(i)-'a'] = true;
        }
        int len = s.length();
        for(int i =0; i<len; i++){
            char alpha = s.charAt(i);
            int count = 0;  //index만큼 이동하기 위한 카운트
            while(count < index){  //카운트가 index만큼 확인됐을 때 종료
                alpha++;    //알파벳 증가
                if(alpha>'z'){ //z를 넘어섰다면
                    alpha = 'a';    //다시 a부터 시작
                }
                
                if(skip_alphabet[alpha-'a']==false){   //스킵하는 알파벳이 아니라면
                    count++;
                }
            }
            //계산이 끝나면 결과에 추가한다.
            answer += alpha;
        }
        return answer;
    }
}