import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * No.33 : 염라대왕의 이름 정렬
 *
 * 문제 내용
 * - 저승에 갑자기 지진이 발생해, 이승 명부가 흐트러졌다..
 * - 정렬 순서는 이름의 길이가 짧을수록 / 같은 길이라면 사전 순 / 똑같은 이름은 하나만 (중복 X)
 *
 * 전략
 *  - 머지 소트(Merge Sort)를 활용해 우선순위대로 정렬을 진행하자
 *  - 명부(life_book)라는 스트링 타입의 배열을 만들어 이름을 저장하기
 *  - 중복제거는 출력시 이전 값을 저장하고 있다가, 중복이 되면 패스하는 식으로 하자
 *
 * @author HeejoPark
 */
public class Solution {
    static int N;
    public static void main(String args[]) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++){
            N = Integer.parseInt(br.readLine());    //이름의 개수
            String[] life_book = new String[N];
            String[] merge_sort_temp = new String[N];
            for(int i =0; i<N; i++){
                life_book[i] = br.readLine();
            }
            //머지 소트 실행
            merge_sort(0, N-1, life_book, merge_sort_temp);

            //결과 출력
            System.out.println("#"+test_case);
            String prev_str = "";
            for(int i =0; i<N; i++){
                if(prev_str.equals(life_book[i])){
                    continue;
                }
                System.out.println(life_book[i]);
                prev_str = life_book[i];
            }
        }
    }

    public static void merge_sort(int start, int end, String[] life_book, String[] merge_sort_temp){
        if(start < end) {
            int mid = (start + end) / 2;
            merge_sort(start, mid, life_book, merge_sort_temp);
            merge_sort(mid + 1, end, life_book, merge_sort_temp);

            int pointer_a = start;
            int pointer_b = mid + 1;
            int index = start;

            execWhile : while(pointer_a <= mid && pointer_b <= end){
                //우선순위 1. 길이 체크
                if(life_book[pointer_a].length() < life_book[pointer_b].length()){
                    merge_sort_temp[index++] = life_book[pointer_a++];
                }
                else if(life_book[pointer_a].length() > life_book[pointer_b].length()){
                    merge_sort_temp[index++] = life_book[pointer_b++];
                }
                else {
                    //우선순위 2. 사전순서
                    for(int i =0; i<life_book[pointer_a].length(); i++){
                        //만약 a가 b보다 작다면 a가 앞으로 간다.
                        if(life_book[pointer_a].charAt(i) < life_book[pointer_b].charAt(i)){
                            merge_sort_temp[index++] = life_book[pointer_a++];
                            continue execWhile;
                        }
                        //b가 더 작다면 b가 앞으로 간다.
                        else if (life_book[pointer_a].charAt(i) > life_book[pointer_b].charAt(i)){
                            merge_sort_temp[index++] = life_book[pointer_b++];
                            continue execWhile;
                        }
                    }
                    //사전 순서로 결정이 나지 않았다면, 두 문자는 같다. a와 b 모두 넣어버리자
                    merge_sort_temp[index++] = life_book[pointer_a++];
                    merge_sort_temp[index++] = life_book[pointer_b++];
                }
            }

            //만약 pointer_a가 mid에 도달하지 않았다면 채워주기
            if(pointer_a <= mid){
                while(index <= end){
                    merge_sort_temp[index++] = life_book[pointer_a++];
                }
            }
            //만약 pointer_b가 end에 도달하지 않았다면 채워주기
            else if (pointer_b <= end){
                while(index <= end){
                    merge_sort_temp[index++] = life_book[pointer_b++];
                }
            }

            //정렬된 계산 결과를 오리지널 배열에 넣어주기
            for(int i = start; i <= end; i++){
                life_book[i] = merge_sort_temp[i];
            }
        }

    }
}