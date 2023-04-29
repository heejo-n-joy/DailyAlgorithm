import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 요구하는 출력 : 틱택토 게임이 끝났는지 여부
 *
 * 풀이 방법 : 구현
 * - 1. 입력받은 문자열을 가지고 틱택토 판을 만든다.
 * - 2. 현재 틱택토 게임 상태가 유효한지 판별한다.
 *  2-1. O의 개수와 X의 개수. X가 먼저 놓여지기 때문에 홀수라면 X가 1개 더 많아야 하고, 짝수라면 둘의 개수가 같아야 한다.
 *  2-2. 혹시 빙고가 완성되었다면, X가 완성인지 O가 완성인지 확인한다.
 *      2-2-1. X가 완성이라면 홀수여야 하고
 *      2-2-2. O가 완성이라면 짝수여야 한다.
 *      2-2-3. 둘 다 빙고가 나올 수는 없다.
 * - 3. 결과를 출력한다.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String str = "";
        while(!(str = bufferedReader.readLine()).equals("end")){
            if(exec(str)){
                sb.append("valid\n");
            }
            else{
                sb.append("invalid\n");
            }
        }

        //결과 출력
        System.out.println(sb.toString());
    }

    public static boolean exec(String str){
        //Stage1. X의 개수와 O의 개수가 적절한가?
        int x_count = 0;
        int o_count = 0;
        int total_count = 0;    //x와 o의 개수 합
        for(int i = 0; i<str.length(); i++){
            switch(str.charAt(i)){
                case 'O':
                    o_count++;
                    total_count++;
                    break;
                case 'X':
                    x_count++;
                    total_count++;
                    break;
            }
        }
        //만약 전체 개수가 홀수라면
        if(total_count % 2 == 1){
            //x의 개수가 o의 개수보다 정확히 1 많아야 한다.
            if(x_count - 1 != o_count){
                return false;
            }
        }
        //만약 전체 개수가 짝수라면
        else{
            if(x_count != o_count){
                return false;
            }
        }

        //Stage2. 빙고가 완성되었는가?
        //036,147,258
        //012,345,678
        //048,246
        int[][] set = {{0,3,6}, {1,4,7}, {2,5,8}, {0,1,2}, {3,4,5}, {6,7,8}, {0,4,8}, {2,4,6}};
        int x_bingo = 0;
        int o_bingo = 0;
        for(int i =0; i<set.length; i++){
            //빙고가 발생했다
            if(compare(str.charAt(set[i][0]), str.charAt(set[i][1]), str.charAt(set[i][2]))){
                switch(str.charAt(set[i][0])){
                    case 'O':
                        o_bingo++;
                        break;
                    case 'X':
                        x_bingo++;
                }
            }
        }

        //둘 다 빙고가 나오면 안된다..
        if(o_bingo > 0 && x_bingo > 0){
            return false;
        }
        //둘 다 빙고가 안나왔다면
        if(o_bingo == 0 && x_bingo == 0){
            //개수가 꽉 찬 상태가 아니라면
            if(total_count < 9){
                return false;
            }
        }
        //만약 o만 빙고가 나왔다면
        if(o_bingo > 0){
            //개수는 같아야 한다.
            if(x_count != o_count){
                return false;
            }
        }
        //만약 x만 빙고가 나왔다면
        if(x_bingo > 0){
            //개수는 x가 1 더 많아야 한다.
            if(x_count - 1 != o_count){
                return false;
            }

        }
        return true;
    }

    public static boolean compare(char a, char b, char c){
        return (a == b) && (a == c);
    }
}