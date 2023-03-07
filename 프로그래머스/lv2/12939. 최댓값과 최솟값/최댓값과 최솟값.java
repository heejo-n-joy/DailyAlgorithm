class Solution {
    public String solution(String s) {
        String[] str = s.split(" ");
        int max_val = Integer.MIN_VALUE;
        int min_val = Integer.MAX_VALUE;
        for(int i =0; i<str.length; i++){
            int num = Integer.parseInt(str[i]);
            max_val = Math.max(max_val, num);
            min_val = Math.min(min_val, num);
        }
        String answer = min_val + " " + max_val;
        return answer;
    }
}