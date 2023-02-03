import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 내용
 * - 병합정렬을 연습해보기 위한 오름차순 정렬 문제
 * <p>
 * 요구하는 출력
 * - 오름차순으로 정렬하여 출력
 * <p>
 * 전략
 * - 병합정렬을 사용해보자
 *
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 *
 * @author HeejoPark
 */
class Main {

    static int[] newArray;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = Integer.parseInt(br.readLine());
        }
        newArray = new int[N];
        letsMergeSort(array, 0, N-1);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<array.length; i++){
            sb.append(array[i] + "\n");
        }

        //결과 출력
        System.out.println(sb);
    }

    public static void letsMergeSort(int[] array, int left, int right) {
        if(left>=right){    //배열이 남은게 1개일 때부터 나누는 과정은 없다.
            return;
        }
        int mid = (left + right) / 2;   //배열의 중반부를 나누고
        letsMergeSort(array, left, mid);    //왼쪽 쪼개기
        letsMergeSort(array, mid+1, right); //오른쪽 쪼개기
        merge(array, left, mid, right); //쪼개진 둘을 합치기
    }

    public static void merge(int[] array, int left, int mid, int right){
        int indexA = left;  //왼쪽 배열의 인덱스
        int indexB = mid+1; //오른쪽 배열의 인덱스
        int index = left;   //전체 배열의 인덱스
        while(index <= right){  //전체 배열의 인덱스가 주어진 배열을 넘어서지 않을 때까지 실행
            if(indexA > mid){   //이미 왼쪽 배열을 다 사용했다면
                newArray[index++] = array[indexB++];    //오른쪽 배열 나머지를 넣자
                continue;
            }
            if(indexB > right){ //오른쪽 배열을 다 사용했다면
                newArray[index++] = array[indexA++];    //왼쪽 배열 나머지를 넣자
                continue;
            }
            if(array[indexA] > array[indexB]){  //왼쪽값이 오른쪽보다 크다면
                newArray[index++] = array[indexB++];    //오른쪽 값을 넣자
            }
            else{   //반대라면
                newArray[index++] = array[indexA++];    //왼쪽 값을 넣자
            }
        }
        //잘 정렬된 배열을 저장한 newArray에서 꺼내서 array로 값 이동
        for(int i = left; i<=right; i++){
            array[i] = newArray[i];
        }
    }
}