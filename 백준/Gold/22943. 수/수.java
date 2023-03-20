import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 특이한 두 규칙을 만족하는 수의 개수
 *
 * 특이한 두 규칙
 * - 1. 서로 다른 두 개의 소수의 합으로 나타낼 수 있는 경우
 * - 2. M으로 나누어 떨어지지 않을때까지 나눈 수가 두 개의 소수의 곱인 경우, 이 때, 두 개의 소수가 같아도 된다.
 *
 * <p>
 * 이 문제는 어떻게 풀까?
 * - 두 단계로 나뉜다.
 * - 1. 0부터 9까지 K가지의 숫자를 한 번씩만 사용하여 숫자를 만든다.
 * - 2. 해당 숫자가 첫 번째 규칙을 만족하는지 확인한다.
 * - 3. 해당 숫자가 두 번째 규칙을 만족하는지 확인한다.
 * - 4. 남은 개수를 출력한다.
 *
 * 시간 복잡도
 * - 1번 : 9*9*8*7*6 = 27216
 * - 2번 : O(N^2)
 * - 3번 : O(N^2)
 *
 * @author HeejoPark
 */

class Main {
    static byte[] isAlreadyCheck = new byte[100000];    //빠른 계산을 위해, 소수 값을 저장해두자
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();  //가지 수
        int M = sc.nextInt();  //나눌 수
        //1. 숫자를 뽑는다.
        Queue<Integer> queue = new LinkedList<>();
        chooseNumbers(queue, K, 0, 0, new boolean[10]);
        //2. 규칙 1을 만족하는지 본다.
        int size = queue.size();
        while(size > 0){
            int temp = queue.poll();
            //계산하기
            //규칙 1을 만족하면 다시 큐에 넣는다.
            for(int i = 2; i<= temp / 2; i++){
                if(isPrimeNumber(i) && isPrimeNumber(temp - i)){
                    if(i != (temp - i)) {
                        queue.offer(temp);
                        break;
                    }
                }
            }
            size--;
        }
        size = queue.size();
        //3. 규칙 2를 만족하는지 본다.
        while(size > 0){
            int temp = queue.poll();
            //규칙 2를 만족하면 다시 큐에 넣는다.
            while(temp % M == 0){
                temp /= M;
            }
            for(int i = 2; i<= Math.sqrt(temp); i++){
                if(temp % i == 0){
                    if(isPrimeNumber(i) && isPrimeNumber(temp / i)){
                        queue.offer(temp);
                        break;
                    }
                }
            }
            size--;
        }
        //4. 결과 출력
        System.out.println(queue.size());
    }

    //num이 소수인지 아닌지를 판별하기
    public static boolean isPrimeNumber(int num){
        //이미 소수로 판별난 숫자라면
        if(isAlreadyCheck[num] == 1){
            return true;
        }
        //이미 소수가 아니라고 판별난 숫자라면
        else if(isAlreadyCheck[num] == -1){
            return false;
        }
        //1보다 작다면
        if(num <= 1){
            isAlreadyCheck[num] = -1;
            return false;
        }
        //소수인지 확인해보기
        for(int i = 2; i<= Math.sqrt(num); i++){
            //1과 자기자신을 제외하고 나누어 떨어지는 값이 있다면
            if(num % i == 0){
                isAlreadyCheck[num] = -1;
                return false;
            }
        }
        //나누어 떨어지는 값을 찾지 못했다면
        isAlreadyCheck[num] = 1;
        return true;
    }

    //0부터 9까지 K가지의 숫자를 한 번씩만 사용하여 만든 수
    public static void chooseNumbers(Queue<Integer> queue, int K, int count, int tempNum, boolean[] isSelected){
        if(count == K){
            queue.offer(tempNum);
            return;
        }
        for(int i = 0; i<=9; i++){
            if(count == 0 && i == 0){
                continue;
            }
            if(!isSelected[i]){
                isSelected[i] = true;
                chooseNumbers(queue, K, count+1, tempNum * 10 + i, isSelected);
                isSelected[i] = false;
            }
        }
    }
}