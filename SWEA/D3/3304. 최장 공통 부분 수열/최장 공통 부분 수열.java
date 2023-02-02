import java.util.Scanner;

/**
 * No.20 : 최장 공통 부분 수열
 *
 * 문제 내용
 * - 두 문자열의 최대 공통 부분 수열을 구하려고 한다. (연속x)
 *
 * 요구하는 출력
 * - 최대 공통 부분 수열의 길이
 *
 * 입력
 * - T : 테스트케이스
 *  - 두 문자열
 *
 *  전략
 *  - DP를 기반으로 문제를 푼다.
 *  - 가로는 str1, 세로는 str2
 *  - 세로의 해당 문자에서 탐색을 시작하는 컨셉
 *
 *  난이도(예상/실제) : 2
 *
 * @author HeejoPark
 */
public class Solution {
    public static void main(String args[]) throws Exception
    {
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		 */
        //System.setIn(new FileInputStream("res/input.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		 */
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

        for(int test_case = 1; test_case <= T; test_case++)
        {
            String str1 = sc.next();
            String str2 = sc.next();
            int[][] DP = new int[str1.length()+1][str2.length()+1];
            for(int i =1 ; i<=str1.length(); i++){
                for(int j =1; j<=str2.length(); j++){
                    if(str1.charAt(i-1) == str2.charAt(j-1)){
                        DP[i][j] = DP[i-1][j-1] + 1;
                    }
                    else{
                        DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);
                    }
                }
            }
            //결과 출력
            System.out.println("#" + test_case + " " + DP[str1.length()][str2.length()]);
        }
    }

}