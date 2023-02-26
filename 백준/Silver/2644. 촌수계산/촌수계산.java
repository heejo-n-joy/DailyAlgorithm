import java.util.Scanner;

/**
 * 문제 내용
 * - 우리 나라에는 가족과 친척들 사이의 관계를 촌수라는 단위로 표현한다.
 * <p>
 * 요구하는 출력
 * - 주어진 두 사람의 촌수를 계산하자
 * <p>
 * 전략
 * - 모든 트리 관계를 (일차원 배열로) 만들고,
 * - a라는 사람의 모든 부모를 체크한다. (거리를 따지며)
 * - b라는 사람은 자신의 부모들 중 같은 부모를 찾는다.
 * - 구한 거리를 더한다.
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   //전체 사람의 수
        int[] parent = new int[n+1];   //부모 지도
        for(int i =0; i<=n; i++){
            parent[i] = -1; //-1로 초기화
        }
        int a = sc.nextInt();   //촌수를 계산해야 하는 사람 1
        int b = sc.nextInt();   //촌수를 계산해야 하는 사람 2
        int m = sc.nextInt();   //관계의 개수
        for(int i =0; i<m; i++){
            int x = sc.nextInt();   //부모
            int y = sc.nextInt();   //자식
            parent[y] = x;
        }

        //a의 부모를 체크한다.
        int[] check_a_parent = new int[n+1];
        for(int i =0; i<=n; i++){
            check_a_parent[i] = -1;
        }
        int check_a = a;
        int count = 0;
        while(true){
            check_a_parent[check_a] = count++;
            check_a = parent[check_a];
            if(check_a == -1){
                break;
            }
        }
        int result = -1;
        int check_b = b;
        count = 0;
        while(true){
            //만약 a의 부모들 중에 있었다면
            if(check_a_parent[check_b] > -1){
                result = count + check_a_parent[check_b];
                break;
            }
            check_b = parent[check_b];
            count++;
            if(check_b == -1){
                break;
            }
        }
        System.out.println(result);
    }
}