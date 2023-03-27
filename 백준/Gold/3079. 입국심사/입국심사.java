import java.util.Scanner;

/**
 * 요구하는 출력
 * - 상근이와 친구들이 심사를 받는 최소 시간
 *
 * 풀이 방법
 * - 이분 탐색(Binary Search)을 활용한다.
 * - 시간 T가 주어질 때, 모든 심사대별 심사 가능한 인원 수를 구한 후 합한다.
 * - 가능한 인원이 상근이와 친구들 인원 이상이라면, 시간을 줄여본다.
 * - 가능한 인원이 그 밑이라면, 시간을 늘려본다.
 * - 이러한 방식으로 가장 최소값의 T를 찾는다.
 *
 * 주의 사항
 * - 상근이와 친구들의 수 M은 최대 10억, 심사대 별 걸리는 시간 또한 최대 10억이기 때문에, long 타입을 사용한다.
 *
 * 시간복잡도
 * - 적합한 시간 찾기 : O(logN)
 * - 심사대의 최대 가능 인원 : O(logN)
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   //심사대 수
        int M = sc.nextInt();   //총 인원

        int[] tables = new int[N];
        int table_max_time = 0;
        for(int i = 0; i<N; i++){
            tables[i] = sc.nextInt();   //심사대 별 소요 시간 체크
            table_max_time = Math.max(table_max_time, tables[i]);
        }

        long start = 1; //이론상 가능한 최소 시간
        long end = ((long) M) * table_max_time;    //이론상 가능한 최대 시간

        //가능한 시간 찾기
        long resultTime = calc(start, (start + end) / 2, end, N, M, tables);

        //결과 출력
        System.out.println(resultTime);
    }

    public static long calc(long start, long time, long end, int N, int M, int[] tables){
        long resultTime = end;
        while(true){
            if(start > end){
                break;
            }
            //시간 내 심사대 별 가능 인원 합산
            long possiblePeople = 0;
            for(int i = 0; i<N; i++){
                possiblePeople += time / tables[i];
                if(possiblePeople > M){ //M을 초과하면 계산 끊기
                    break;
                }
            }
            //만약 인원이 M명 이상이라면 저장
            if(possiblePeople >= M){
                resultTime = Math.min(resultTime, time);    //시간 갱신하기
                end = time - 1;
            }
            //만약 인원이 M명 미만이라면
            else{
                start = time + 1;
            }
            time = (start + end) / 2;
        }
        return resultTime;
    }
}