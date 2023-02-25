import java.util.HashSet;
import java.util.Scanner;

/*
 * 전략
 * - HashSet을 배워보자
 *
 */
public class Solution{
    public static void main(String args[]) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();
        for(int test_case = 1; test_case <= T; test_case++)
        {
            HashSet<String> hashSet = new HashSet<>();
            int N = sc.nextInt();
            int M = sc.nextInt();
            for(int i = 0 ; i<N; i++){
                hashSet.add(sc.next());
            }
            int count = 0;
            for(int j = 0; j<M; j++){
                if(hashSet.contains(sc.next())){
                    count++;
                }
            }
            //결과 출력
            System.out.println("#" + test_case + " " + count);
        }
    }
}
