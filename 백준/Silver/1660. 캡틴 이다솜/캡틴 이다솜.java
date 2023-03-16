import java.util.*;

/**
 * 요구하는 출력
 * - N개의 대포알을 활용해서 만들 수 있는 사면체의 최소 개수
 * <p>
 * 이 문제는 어떻게 풀까? DP
 * - 1. 일단 N개 이하로 만들 수 있는 사면체를 모두 구해놓는다.
 * - 2. 이제 정확히 N개를 사용해서 사면체들을 만들어야 한다. 미리 만들어준 사면체들을 조합해보자
 * - 3. 하나씩 대포알 개수를 증가시켜 만들 수 있는 사면체의 최소값을 갱신하자.
 *
 * @author HeejoPark
 */

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] DP = new int[N+1];
        for(int i = 0; i<=N; i++){
            DP[i] = i;    //최대값으로 설정
        }
        List<Integer> list = new ArrayList<>();
        list.add(0);
        int current_dummy = 0;          //현재 쌓아야 하는 대포 더미
        int need_count_for_dummy = 1;   //더미에 추가해야 하는 대포알 개수
        //가장 큰 사면체가 나올 때까지 계산
        while (true) {
            current_dummy += need_count_for_dummy; //현재 더미에 대포알을 추가한다.
            int newValue = current_dummy + list.get(list.size() - 1);
            if (newValue > N) {  //만약 총 대포알 개수보다 많이 쌓았으면
                //그렇게 쌓을 수는 없으니 여기서 계산 종료
                break;
            }
            list.add(newValue);
            DP[newValue] = 1;
            need_count_for_dummy++; //다음 더미에는 대포알이 1개 더 필요하다.
        }

        for(int i = 1; i<=N; i++){
            for(int j = 0; j<list.size(); j++){
                if(i-list.get(j) <= 0){
                    break;
                }
                DP[i] = Math.min(DP[i-list.get(j)] + 1, DP[i]);
            }
        }
        //결과 출력
        System.out.println(DP[N]);
    }
}