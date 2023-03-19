import java.util.Scanner;

/**
 * 문제 내용
 * - 욱제의 인생은 젊은 날과 늙은 날로 나뉜다.
 * - 임의의 젊은 날의 행복도 > 임의의 늙은 날의 행복도
 * - 임의의 젊은 날의 피로도 < 임의의 늙은 날의 피로도
 * - 0은 값이 누락된 것이다. 유리하게 해석할 수 있다.
 *
 * 요구하는 출력
 * - 욱제의 젊은 날이 될 수 있는 최대의 K일
 * <p>
 * 이 문제는 어떻게 풀까?
 * - 임의의 행복도와 피로도를 가지고 확인해야 하기 때문에, 날짜 범위 중 최대값과 최소값을 구해야 한다.
 * - 1. 1일~K일까지의 행복도의 최소값, K+1일~N일까지의 행복도의 최대값을 저장하자
 * - 2. 마찬가지로 1일~K일까지의 피로도의 최대값, K+1~N일까지의 피로도의 최소값을 저장하자
 * - 3. 1일부터 N-1일까지, 문제 내용을 만족하는 K 날짜의 최대값을 확인하고, 출력하자
 *
 * 시간 복잡도
 * - 1번 : O(N)
 * - 2번 : O(N)
 * - 3번 : O(N)
 *
 * 회고
 * - 77%에서 자꾸 틀렸다는 결과가 나왔다. 코드 구조를 바꿔봤지만 해결되지 않았다.
 * - 1일부터 차근차근 결과를 확인할 때 문제의 조건에 맞지 않는 날을 만나면, 그 이후의 모든 날이 문제 조건에 맞지 않는다고 이해를 했다.
 * - 그러나 3일에는 부합하지 않지만 5일에는 또 조건에 부합하는 경우가 있다.
 * - '가장 큰 날'을 요구하는 문제의 특성을 기억하여, N-1일부터 하나씩 감소하면 날짜를 찾고, 조건에 부합하면 그 즉시 종료하는 방식으로 바꿨다.
 * @author HeejoPark
 */

class Day{
    int happy;
    int tired;
    Day(int happy, int tired){
        this.happy = happy;
        this.tired = tired;
    }
    Day(){

    }
}
class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();  //시험 단원 개수
        Day[] days = new Day[N+1];
        days[0] = new Day();
        for(int i = 1; i<=N; i++){
            days[i] = new Day(sc.nextInt(), sc.nextInt()); //행복도
        }
        //젊은 날의 행복도, 피로도 계산
        Day[] young = new Day[N+1];
        for(int i = 0; i<=N; i++){
            young[i] = new Day();
        }
        int minHappy = Integer.MAX_VALUE;
        int maxTired = 0;
        for(int i = 1; i<=N; i++){
            if(days[i].happy != 0 && minHappy > days[i].happy){
                minHappy = days[i].happy;
            }
            young[i].happy = minHappy;
            maxTired = Math.max(maxTired, days[i].tired);
            young[i].tired = maxTired;
        }
        Day[] old = new Day[N+1];
        for(int i = 0; i<=N; i++){
            old[i] = new Day();
        }
        int maxHappy = 0;
        int minTired = Integer.MAX_VALUE;
        for(int i = N; i>=1; i--){
            if(days[i].tired != 0 && minTired > days[i].tired){
                minTired = days[i].tired;
            }
            old[i].tired = minTired;
            maxHappy = Math.max(maxHappy, days[i].happy);
            old[i].happy = maxHappy;
        }

        int K = -1;
        for(int i = N-1; i>=1; i--){
            if((young[i].happy > old[i+1].happy)&&(young[i].tired < old[i+1].tired)){
                K = i;
                break;
            }
        }
        //결과 출력
        System.out.println(K);
    }
}