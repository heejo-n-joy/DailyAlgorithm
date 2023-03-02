import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * No.39 : 광고 시간 정하기
 *
 * 문제 내용
 * - 한나가 L분짜리 광고를 올린다.
 * - 이 때, 광고에 효과가 좋은 피크 시간대들이 주어진다.
 * - L분의 광고에 최대한 많은 피크 시간대가 겹쳤으면 좋겠다.
 * - 그 시간의 최대값을 출력
 *
 * 전략1
 * - boolean타입으로 피크 시간대들을 true로 바꿔둔다.
 * - start와 end 포인터를 한 칸씩 이동하며, 최대값을 찾는다.
 * - 시간초과로 실패
 *
 * 전략2 (인터넷 참고)
 * - 피크 시간대를 클래스로 묶는다.
 * - 가지고 있는 변수는 시작 시간(startTime), 끝 시간(endTime), 지금까지의 피크 시간대의 합(sum)
 * - 하나의 피크 시간(choosePeek)을 선택하고, L분 뒤 끝나는 시간에 있는 피크 시간대(endPeek)를 이진 탐색으로 찾는다.
 * - 해당 시간대까지의 피크 시간을 더한다. endPeek.sum - choosePeek.sum + (choosePeek.end - choosePeek.start)
 * - 혹시 endPeek 사이에 endTime이 존재한다면, 그 종료시간까지만 합하도록 한다.
 *
 * @author HeejoPark
 */

class Peek039{
    int start;
    int end;
    int sum;
    Peek039(int start, int end, int sum){
        this.start = start;
        this.end = end;
        this.sum = sum;
    }
}
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            int L = sc.nextInt();   //한나의 광고 시간
            int N = sc.nextInt();   //피크 시간대 개수
            Peek039[] peeks = new Peek039[N];
            int sumUntilNow = 0;    //모든 피크 시간대의 합
            for(int j =0; j<N; j++){
                int s = sc.nextInt();   //해당 피크시간대의 시작
                int e = sc.nextInt();   //해당 피크시간대의 끝
                sumUntilNow += e - s;
                peeks[j] = new Peek039(s, e, sumUntilNow);
            }

            //모든 피크시간대를 하나씩 꺼내서 계산한다. 시작시간은 피크시간대의 시작시간이다.
            int max_count = 0;
            for(int i =0; i<N; i++){
                int current_count = 0;
                Peek039 startPeek = peeks[i];
                int startTime = startPeek.start;
                int endTime = startTime + L;
                Peek039 endPeek = findEndPeek(peeks, endTime);
                current_count += endPeek.sum - startPeek.sum + (startPeek.end - startPeek.start);
                if(endPeek.start < endTime && endPeek.end > endTime){
                    current_count -= (endPeek.end - endTime);
                }
                else if(endPeek.end > endTime){
                    current_count -= (endPeek.end - endPeek.start);
                }
                max_count = Math.max(max_count, current_count);
            }
            //출력
            System.out.println("#" + test_case + " " + max_count);
        }
    }

    public static Peek039 findEndPeek(Peek039[] peeks, int target){
        int start = 0;
        int end = peeks.length - 1;
        while(end > start){
            int mid = (start + end) /2 ;
            //만약 해당 값이 mid보다 작다면
            if(peeks[mid].end >= target){
                end = mid;
            }
            //mid보다 크면
            else{
                start = mid + 1;
            }
        }
        //해당 위치의 피크를 리턴한다.
        return peeks[end];
    }
}