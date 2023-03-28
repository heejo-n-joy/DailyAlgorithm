import java.util.Scanner;

/**
 * 요구하는 출력
 * - 합이 S가 되는 가장 짧은 수열 일부의 길이
 *
 * 풀이 방법
 * - 시작 포인터, 끝 포인터를 활용한다.
 * - 합이 S 이하라면 끝 포인터를 한 칸씩 오른쪽으로 이동한다.
 * - 합이 S 이상이 된다면 현재까지의 최소 길이를 갱신하고, 시작 포인터를 이동한다.
 * - 끝 포인터가 더 갈 곳이 없으면 종료
 *
 * 주의사항
 * - 가능한 최대 합 (N * 10000이 10억이므로, 배열 타입은 int)
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();       //길이
        int S = sc.nextInt();       //원하는 최소합
        int[] array = new int[N];   //수열
        for(int i = 0; i <N; i++){
            array[i] = sc.nextInt();
        }

        int start_index = 0;
        int end_index = -1;
        int current_sum = 0;
        int minimum_sum = N + 1;    //절대 나올 수 없는 최대 기본값으로 시작

        while(true){
            //합이 S 이하라면, end 포인터를 1 이동
            if(current_sum < S){
                end_index++;
                //끝 포인터가 배열 범위를 벗어나면 종료
                if(end_index >= N){
                    break;
                }
                current_sum += array[end_index];
            }
            //합이 S이상이라면, start 포인터를 1 이동
            else{
                minimum_sum = Math.min(minimum_sum, end_index - start_index + 1);   //최소값 갱신
                current_sum -= array[start_index];  //시작 포인터 값 제거하고
                start_index++;  //시작 포인터 1 증가하기
            }
        }

        //절대 만들 수 없었다면
        if(minimum_sum == N+1){
            System.out.println(0);
        }
        else{
            System.out.println(minimum_sum);
        }
    }
}