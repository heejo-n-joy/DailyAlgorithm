import java.util.Arrays;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 세준이가 운전해야하는 거리의 최소값
 *
 * 풀이 방법
 * - 1.지름길을 오름차순으로 정렬한다.
 * - 2. 고속도로 위치별 걸리는 시간을 저장한다.
 * - 3. 지름길을 하나씩 돌아보며 값을 갱신한다.
 *
 * 시간복잡도
 * - 오름차순 정렬 : O(N^2) (그래도 n 최대가 12)
 * - 지름길 비교 및 반영 : O(D * N) 
 */

class Shortcut{
    int start;
    int end;
    int distance;
    Shortcut(int start, int end, int distance){
        this.start = start;
        this.end = end;
        this.distance = distance;
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int D = sc.nextInt();
        Shortcut[] shortcuts = new Shortcut[N];
        for(int i =0; i<N; i++){
            shortcuts[i] = new Shortcut(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        Arrays.sort(shortcuts, (o1, o2) -> {
           if(o1.start == o2.start){
               if(o1.end == o2.end){
                   return o1.distance - o2.distance;
               }
               else {
                   return o1.end - o2.end;
               }
           }
           else{
               return o1.start - o2.start;
           }
        });

        int[] distance = new int[D+1];
        for(int i = 0; i<=D; i++){
            distance[i] = i;
        }

        for(int i = 0 ; i<N; i++){
            //도착 지점이 종료 지점보다 더 멀면 패스
            if(shortcuts[i].end > D){
                continue;
            }
            distance[shortcuts[i].end] = Math.min(distance[shortcuts[i].end], distance[shortcuts[i].start] + shortcuts[i].distance);
            for(int j = shortcuts[i].end + 1; j<=D; j++){
                distance[j] = Math.min(distance[j], distance[j - 1] + 1);
            }
        }

        //결과출력
        System.out.println(distance[D]);
    }
}