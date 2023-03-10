import java.util.Arrays;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 1과 자기 자신을 제외한 약수들을 활용해 원래 숫자를 구하라
 * <p>
 * 이 문제는 어떻게 풀까?
 * - 오름차순 정렬로 해결하자.
 * - 정렬 후 가장 작은 숫자, 가장 큰 숫자를 곱하는 값이 문제에서 요구하는 원래 숫자이다.
 *
 * @author HeejoPark
 */
class Solution {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int T = sc.nextInt();
        for (int test_case = 1; test_case <= T; test_case++) {
            //인풋값 입력받기
            int[] nums = new int[sc.nextInt()];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = sc.nextInt();
            }
            Arrays.sort(nums);
            //결과 출력
            sb.append("#" + test_case + " " + (nums[0] * nums[nums.length - 1]) + "\n");
        }
        System.out.println(sb.toString());
    }
}