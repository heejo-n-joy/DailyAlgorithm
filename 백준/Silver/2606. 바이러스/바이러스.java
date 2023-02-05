import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 내용
 * - 웜 바이러스의 네트워크 전파
 * <p>
 * 요구하는 출력
 * - 1번 컴퓨터가 바이러스에 걸렸을 때, 웜 바이러스에 걸리게 되는 컴퓨터 수
 * <p>
 * 전략
 * - 리스트 + BFS
 *
 * <p>
 * 예상 난이도 : ★
 * <p>
 *
 * @author HeejoPark
 */
class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());   //컴퓨터의 개수
        ArrayList<Integer>[] lists = new ArrayList[N+1];
        for(int i = 0; i<N+1; i++){
            lists[i] = new ArrayList<>();
        }
        boolean[] check = new boolean[N+1]; //컴퓨터 탐색이 끝났는지 확인
        int M = Integer.parseInt(br.readLine());   //컴퓨터의 개수
        for(int i =0; i<M; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int com1 = Integer.parseInt(st.nextToken());   //연결되는 컴퓨터
            int com2 = Integer.parseInt(st.nextToken());   //연결되는 컴퓨터
            lists[com1].add(com2);  //양방향으로 정보를 넣어준다.
            lists[com2].add(com1);  //양방향으로 정보를 넣어준다.
        }
        check[1] = true;    //1번 컴퓨터 체크
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        int count = 0;
        while(!queue.isEmpty()){
            int temp = queue.poll();
            for(int i=0; i<lists[temp].size(); i++){
                int computer = lists[temp].get(i);
                //해당 컴퓨터가 이미 확인이 됐다면 패스
                if(check[computer]){
                    continue;
                }
                //그게 아니라면, 바이러스 전파하기
                check[computer] = true;
                queue.offer(computer);
                count++;
            }
        }
        //결과 출력
        System.out.println(count);
    }
}