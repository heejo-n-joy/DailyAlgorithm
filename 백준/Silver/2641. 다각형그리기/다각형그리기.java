import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 내가 그린 다각형 그림은 저 그림과 같은 그림일까?
 * <p>
 * 요구하는 출력
 * - 같은 다각형 그림의 개수, 그리고 수열
 * <p>
 * 입력 변수
 * - 다각형의 길이
 * - 표본 다각형의 모양수열
 * - 모양수열의 개수
 * - 모양수열들
 * <p>
 * 전략
 * - 모든 수열의 정보를 저장하고
 * - 1. 주어진 표본을 쭉 일직선으로 비교하기 (시작점을 계속 바꾸면서)
 * - 2. 1번이 끝나면 주어진 표본을 뒤집고 다시 비교하기
 *
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★
 * <p>
 * @author HeejoPark
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());    //수열의 길이
        int[] original_array = new int[N];      //표본수열
        StringTokenizer st = new StringTokenizer(bufferedReader.readLine());
        for(int i =0; i<N; i++){
            original_array[i] = Integer.parseInt(st.nextToken());
        }
        int M = Integer.parseInt(bufferedReader.readLine());    //모양수열의 개수
        int[][] fake_array = new int[M][N];                     //모양 수열의 정보
        for(int i =0; i<M; i++){
            st = new StringTokenizer(bufferedReader.readLine());
            for(int j =0; j<N; j++){
                fake_array[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        boolean[] isReal = new boolean[M];  //진짜인지 확인하는 배열
        calc(N, M, original_array, fake_array, isReal); //주어진 방향대로 탐색
        //표본수열을 뒤집는다.
        int[] reverse_array = new int[N];
        for(int i =0; i<N; i++){
            int temp = original_array[i] + 2; //좌(3)<->우(1), 상(2)<->하(4)
            //변경된 값이 4를 초과한다면
            if(temp > 4){
                temp -= 4;  //4를 빼준다.
            }
            reverse_array[(N-1)-i] = temp;  //역방향은 순서도 역방향
        }
        calc(N, M, reverse_array, fake_array, isReal); //역방향으로 탐색

        int count = 0;
        for(int i =0; i<M; i++){
            if(isReal[i]){
                count++;
            }
        }
        System.out.println(count);
        for(int i =0; i<M; i++){
            if(isReal[i]){
                for(int j =0; j<N; j++){
                    System.out.print(fake_array[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
    public static void calc(int N, int M, int[] original_array, int[][] fake_array, boolean[] isReal){
        for(int i =0; i<M; i++){    //모양수열 선택
            ex : for(int j =0; j<N; j++){   //모양수열의 시작 자리를 계속 바꿔보면서 탐색한다.
                int index = j;
                int count = 0;
                while(count<N){
                    if(fake_array[i][index] == original_array[count]){
                        count++;
                        index++;
                        index %= N;
                    }
                    else{
                        continue ex;
                    }
                }
                //해당 모양수열은 표본 모양수열과 같다.
                isReal[i] = true;
                break ex;   //바로 다음 모양수열을 확인해보러 가자
            }
        }
    }
}