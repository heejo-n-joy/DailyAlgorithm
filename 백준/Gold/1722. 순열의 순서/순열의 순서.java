import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * (예전에 시도했지만 풀지 못했던) 문제 내용
 * - 순열의 순서를 맞추기
 * <p>
 * 요구하는 출력
 * - k번째 순열 or 해당 순열이 몇 번째인지
 * <p>
 * 전략
 * - k번째 순열을 구하는 함수와, 해당 순열이 몇 번째인지를 구하는 함수를 따로 개발
 *
 * <p>
 * 예상 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //순열에 활용되는 숫자 개수
        StringTokenizer st = new StringTokenizer(br.readLine());
        int problem = Integer.parseInt(st.nextToken());   //어떤 문제인지
        factorial = new long[N+1];
        setFactorialArray(N);
        StringBuilder sb = new StringBuilder();
        switch(problem){
            case 1:
                //주어진 순열 순서를 입력받는다.
                long inputPermutationOrder = Long.parseLong(st.nextToken());
                findPermutationValue(N, inputPermutationOrder, sb); //입력받은 순열 순서를 바탕으로 순열을 출력한다.
                break;
            case 2:
                //주어진 순열 리스트를 입력받는다.
                int[] inputPermutationValue = new int[N];
                for(int i =0; i<N; i++){
                    inputPermutationValue[i] = Integer.parseInt(st.nextToken());
                }
                findPermutationOrder(N, inputPermutationValue, sb);  //입력받은 순열로 몇 번째인지 찾아서 출력한다.
                break;
            default:
                System.out.println("ERROR: CANNOT FIND VALID PROBLEM");
                break;
        }

        //결과 출력
        System.out.println(sb);
    }
    public static void printResult(long num, StringBuilder sb){
        sb.append(num);
    }
    public static void printResult(int[] permutation, StringBuilder sb){
        for(int i =0; i<permutation.length; i++){
            sb.append(permutation[i] + " ");
        }
    }
    public static void findPermutationOrder(int N, int[] inputPermutationValue, StringBuilder sb){
        long result = 1;
        boolean[] check = new boolean[N];
        for(int i =0; i<N; i++){
            //i번째에 들어있는 값이 현재 안쓴 번호들 중 몇 번째인지 확인
            int order = findIndex(inputPermutationValue[i]-1, N, check);
            if(order>0){
                result += factorial[N-1-i] * order;
            }
            check[inputPermutationValue[i]-1] = true;
        }
        printResult(result, sb);
    }
    public static void findPermutationValue(int N, long k, StringBuilder sb){
        int[] permutation = new int[N];
        boolean[] check = new boolean[N];
        int index;
        long currentK = 0;
        for(int i =0; i<N; i++){
            index = increaseIndex(-1, N, check);    //넣을 값은 다시 맨 처음에서부터 확인
            while(true) {
                //만약 현재 순열 번호의 최대값이 k번째보다 작다면, k는 다음 index로 이동한다.
                if (currentK + factorial[N - 1 - i] < k) {
                    currentK += factorial[N - 1 - i];
                    index = increaseIndex(index, N, check);
                }
                //범위 안에 들어온다면,
                else {
                    permutation[i] = index + 1;   //해당 숫자를 넣고
                    check[index] = true;    //인덱스에 차용했다고 선택
                    break;
                }
            }
        }
        printResult(permutation, sb);
    }
    public static int findIndex(int index, int N, boolean[] check){
        int count = 0;
        for(int i = 0; i<index; i++){
            if(!check[i]){
                count++;
            }
        }
        return count;
    }
    public static int increaseIndex(int index, int N, boolean[] check){
        int errorCount = 0;
        while(true){
            //한바퀴를 돌았다면
            if(errorCount > N){
                System.out.println("ERROR : CANNOT FIND VALID INDEX!");
                return 0;
            }
            index += 1;
            if(index >= N){
                index = 0;
            }
            if(!check[index]){
                return index;
            }
            errorCount++;
        }
    }
    static long[] factorial;
    public static void setFactorialArray(int N){
        factorial[0] = 1;
        for(int i = 1; i<=N; i++){
            factorial[i] = factorial[i-1] * i;
        }
    }
}