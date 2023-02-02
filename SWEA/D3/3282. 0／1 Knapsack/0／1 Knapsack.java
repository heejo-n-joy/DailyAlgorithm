import java.util.Scanner;

/**
 * No.21 : 0/1 Knapsack
 *
 * 문제 내용
 * - 가방에 물건들을 담자
 *
 * 요구하는 출력
 * - 부피 안에 담을 수 있는 최대 가치
 *
 * 입력
 * - T : 테스트케이스
 *  - N, K : 물건의 개수, 가방의 부피
 *  - 물건의 부피(V)와 가치(C)들
 *
 *  전략
 *  - DP를 기반으로 문제를 푼다.
 *  - 값은 가치
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
            int N = sc.nextInt();   //물건의 개수
            int K = sc.nextInt();   //가방의 부피
            int[][] objects = new int[N+1][2]; //물건의 정보들. 물건의 부피와 가치
            for(int i =1; i<=N; i++){
                objects[i][0] = sc.nextInt();   //물건의 부피
                objects[i][1] = sc.nextInt();   //물건의 가치
            }
            int[][] DP = new int[N+1][K+1];
            for(int i =1; i<=N; i++){
                for(int j = 1; j<=K; j++){
                    DP[i][j] = Math.max(DP[i][j-1], DP[i-1][j]);    //이전 물건 차례일 때 가방 상황, 현재 물건 차례의 직전 가방 상황
                    if(j-objects[i][0] >= 0) {  //물건을 담을 수 있다면
                        DP[i][j] = Math.max(DP[i-1][j - objects[i][0]] + objects[i][1], DP[i][j]);  //앞에서의 결과, 현재 물건을 새로 추가한 상황
                    }
                }
            }
            //결과 출력
            System.out.println("#" + test_case + " " + DP[N][K]);
        }
    }

}