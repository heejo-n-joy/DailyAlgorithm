import java.util.Arrays;
import java.util.Scanner;

/**
 * 요구하는 출력
 * - 홍익 제국의 모든 행성까지의 최단 비용
 *
 * 최소 스패닝 트리
 * - 우선 스패닝 트리는 그래프의 모든 노드를 포함하는 트리를 의미한다.
 * - 이 중, 가장 가중치가 적은 스패닝 트리를 최소 스패닝 트리라고 한다.
 * - 여러 방법들 중 크루스칼 알고리즘을 사용한다.
 *
 * 크루스칼 알고리즘의 방식
 * - 모든 간선들을 오름차순으로 정렬한다.
 * - 차례대로 간선들을 선택하며 반영한다.
 * - 이 때, 선택하는 간선으로 하나의 싸이클이 발생한다면 (ex 1-2-3-1) 이 간선은 반영하지 않고 패스한다.
 * - 싸이클의 기준은 가장 근본의 parent가 동일한지 확인(parent의 기준은 연결된 노드들 중 가장 낮은 번호)
 *
 * 주의할 점
 * - 플로우 하나의 설치 비용은 최대 1억이기 때문에, 합을 저장하는 변수는 long 타입으로 지정
 *
 * 시간복잡도
 * - 오름차순 정렬 : O(N^2)
 * - 간선 선택 : O(N)
 * - 부모노드 찾아서 비교 : O(N)
 * - 결론 : O(N^2)
 * @author HeejoPark
 */


class Main {
    static int[] parents;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   //행성의 수
        int planet_count = 0;   //현재까지 입력받은 행성 수
        int[][] arrays = new int[((int) Math.pow(N, 2) - N) / 2][3]; //[0]은 가중치, [1]과 [2]는 노드
        parents = new int[N];   //행성 별 부모 행성의 숫자
        for(int i =0; i<N; i++){
            parents[i] = i; //자기 자신을 기본값으로 가진다.
        }
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(j <= i){
                    //해당 값들은 버린다. (어차피 대칭이니까)
                    sc.nextInt();
                }
                else{
                    arrays[planet_count][0] = sc.nextInt(); //가중치
                    arrays[planet_count][1] = i;            //노드1
                    arrays[planet_count][2] = j;            //노드2
                    planet_count++;
                }
            }
        }

        //오름차순 정렬
        Arrays.sort(arrays, (o1, o2) -> {
            if(o1[0] == o2[0]){
                if(o1[1] == o2[1]){
                    return Integer.compare(o1[2], o2[2]);
                }
                else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
            else{
                return Integer.compare(o1[0], o2[0]);
            }
        });

        long total_result = 0;
        for(int i = 0; i<planet_count; i++){
            //해당 간선의 부모가 같은가?
            if(findParent(arrays[i][1]) == findParent(arrays[i][2])){
                continue;
            }
            //그게 아니라면 간선을 반영하고
            total_result += arrays[i][0];

            //부모를 업데이트 하자
            UnionParent(arrays[i][1], arrays[i][2]);
        }

        //결과 출력
        System.out.println(total_result);
    }

    //근원 부모를 찾기
    public static int findParent(int num){
        //근원 부모 특징: 자기 자신을 부모로 가지고 있음
        if(parents[num] == num){
            return num;
        }
        //근원 부모가 아니라면, 다시 근원 부모를 찾기 (그러면서 갱신이 필요한 부분을 만나면(Union 된 부분들) 갱신하기)
        parents[num] = findParent(parents[num]);
        //근원 부모의 값 출력
        return parents[num];
    }

    public static void UnionParent(int num1, int num2){
        num1 = findParent(num1);    //행성1의 근원 부모 찾기
        num2 = findParent(num2);    //행성2의 근원 부모 찾기

        //두 근원 부모들 중 작은 값으로 흡수
        if(num1 < num2){
            parents[num2] = num1;
        }
        else{
            parents[num1] = num2;
        }
    }
}