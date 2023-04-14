import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 요구하는 출력 : 통나무를 오르락 내리락할 때의 높이 차를 최소로 만들기
 *
 * 풀이 방법 : 정렬 + 스택 2개
 * - 1. 정렬을 한다.
 * - 2. 통나무를 선택해서, 스택에 넣는다. 이 때, 스택의 peek 값을 기준으로 값이 더 작은 쪽에 통나무를 넣는다.
 * - 3. 2번을 계산하면서 높이 차의 최대값을 저장한다.
 * 
 * 문제는 알겠으니, 퀵소트를 연습하자
 * 
 */

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());   //테스트케이스
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());   //통나무 개수
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] logs = new int[N];
            for (int i = 0; i < N; i++) {
                logs[i] = Integer.parseInt(st.nextToken());
            }

            //오름차순 정렬
//            Arrays.sort(logs);
            quickSort(logs, 0, N-1);

            //스택 생성
            Stack<Integer> stack1 = new Stack<>();
            Stack<Integer> stack2 = new Stack<>();

            //초기 통나무 2개 세팅
            stack1.push(logs[0]);
            stack2.push(logs[1]);

            //결과 저장용 변수
            int max_diff = stack2.peek() - stack1.peek();

            //통나무 스택에 배치하기
            for(int i = 2; i<N; i++){
                //스택1 통나무 값이 더 작다면
                if(stack1.peek() < stack2.peek()){
                    //스택1에 통나무를 넣는다.
                    max_diff = Math.max(logs[i] - stack1.peek(), max_diff);
                    stack1.push(logs[i]);
                }
                //반대라면
                else{
                    //스택2에 통나무를 넣는다.
                    max_diff = Math.max(logs[i] - stack2.peek(), max_diff);
                    stack2.push(logs[i]);
                }
            }

            //결과 출력
            System.out.println(max_diff);
        }
    }

    public static int partition(int[] array, int start_index, int end_index){
        int pivot = end_index;  //기준은 맨 오른쪽 값으로 하자
        int i = start_index -1; //작은 값 정리가 어디까지 마무리되었나
        for(int j = start_index ; j < end_index; j++){
            //현재 확인하는 값이 pivot보다 작거나 같다면
            if(array[j] <= array[pivot]){
                i++;    //정리를 하러 이동
                swap(array, i, j);  //스왑을 하여, i자리에는 pivot보다 작은 값이 오게하기
            }
        }
        //현재 i까지 정리가 되었으니, i 다음의 위치와 pivot의 위치를 바꾼다.
        swap(array, i+1, pivot);

        return i+1; //바뀐 pivot의 위치를 리턴
    }

    public static void swap(int[] array, int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    public static void quickSort(int[] array, int start_index, int end_index){
        //시작 위치와 끝 위치가 같아지거나 역전되면 더이상의 정렬은 필요가 없다.
        if(start_index >= end_index){
            return;
        }

        //기준점을 찾는다.
        int standard_index = partition(array, start_index, end_index);

        quickSort(array, start_index, standard_index - 1);
        quickSort(array, standard_index + 1, end_index);
    }
}