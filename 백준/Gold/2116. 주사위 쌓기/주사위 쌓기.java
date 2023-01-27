import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 주사위를 가지고 탑을 쌓아보자
 *
 * 요구하는 출력
 * - 탑의 한쪽 면의 합의 최대값
 *
 * 주의사항
 * - 아래 주사위와 위 주사위의 맞닿는 면의 숫자는 같아야 한다.
 * - 일반적인 주사위처럼 주사위의 정반대 면의 합이 7은 아니다.
 * - 1번 주사위는 마음대로 놓을 수 있다.
 *
 * 전략
 * - 1번 주사위는 마음대로 놓을 수 있기 때문에, 총 6가지의 경우의 수를 생각해야 한다.
 * - 나머지 주사위들은 놓여진 1번 주사위를 토대로 돌려가면서 놓자
 *
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * @author HeejoPark
 */
class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    //주사위 개수
        int[][] dices = new int[N+1][6];  //(0,5), (1,3), (2,4)가 서로 마주보는 상황
        for(int i =1; i<=N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j =0; j<6; j++){
                dices[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int max = 0;
        //첫번째 주사위의 아랫면에 따른 6가지 경우의 수
        for(int bottomNumOfFirstDice = 1; bottomNumOfFirstDice<=6 ;bottomNumOfFirstDice++){
            int sum = 0;
            int topOfDownDice = bottomNumOfFirstDice;
            int bottomOfUpDice;
            //두번째 주사위부터
            for(int dice = 1; dice<=N; dice++){
                //아랫면 결정
                bottomOfUpDice = topOfDownDice;
                //옆의 4면에서 큰 값 골라서 sum에 반영
                int[] sides = findSideNumsOfDice(dices[dice], bottomOfUpDice);
                sum += maxValueOfSideNums(sides);
                //윗면 값 지정
                topOfDownDice = findTopNumOfDice(dices[dice], bottomOfUpDice);
            }
            //최대값 갱신되었는지 반영
            max = Math.max(max, sum);
        }

        //결과 출력
        System.out.println(max);
    }

    public static int findTopNumOfDice(int[] dice, int bottomNum){
        //bottom 숫자의 위치 확인
        int bottomIndex = -1;
        for(int i =0; i<6; i++){
            if(dice[i] == bottomNum){
                bottomIndex = i;
                break;
            }
        }
        return dice[findOppositeIndexOfDice(bottomIndex)];
    }

    public static int[] findSideNumsOfDice(int[] dice, int bottomNum){
        int[] side = new int[4];
        int index = 0;
        for(int i =0; i<6; i++){
            if(dice[i] == bottomNum){
                continue;
            }
            if(dice[i] == findTopNumOfDice(dice, bottomNum)){
                continue;
            }
            side[index++] = dice[i];
        }
        return side;
    }

    public static int maxValueOfSideNums(int[] side){
        int max = 0;
        for(int i =0; i<4; i++){
            max = Math.max(max, side[i]);
        }
        return max;
    }

    public static int findOppositeIndexOfDice(int bottomIndex){
        switch(bottomIndex){
            case 0:
                return 5;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 0;
            default:
                System.out.println("ERROR : cannot find opposite index of dice");
                return -1;  //에러
        }
    }
}