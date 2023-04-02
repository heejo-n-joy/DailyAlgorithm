import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 요구하는 출력
 * - 한 번에 가장 많은 컴퓨터를 해킹할 수 있는 컴퓨터의 번호 (여러 개라면 오름차순으로 출력하기)
 *
 * 풀이 방법
 * - 1. 신뢰 관계를 받으면서 이 컴퓨터를 신뢰하는 컴퓨터의 개수를 클래스에 저장 및 반영한다.
 * - 2. 각 컴퓨터 별 해킹 가능한 횟수를 BFS를 활용하여 비교하고, 가장 큰 값들을 queue에 저장 및 출력한다.
 *
 */
class Computer{
    int data;   //현재 컴퓨터의 번호
    List<Computer> followers;  //현재 컴퓨터를 신뢰하는 컴퓨터 정보들
    Computer(int data){
        this.data = data;
        followers = new ArrayList<>();
    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());       //컴퓨터의 개수
        int M = Integer.parseInt(st.nextToken());       //신뢰 관계 개수

        //1. 컴퓨터 트리를 초기화
        Computer[] computers = new Computer[N + 1];   //컴퓨터의 신뢰 정보 노드들
        for (int i = 0; i <= N; i++) {
            computers[i] = new Computer(i);
        }

        //신뢰관계를 표시
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            //A가 B를 신뢰한다.
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            computers[B].followers.add(computers[A]);    //A가 B의 부모가 된다.
        }

        //2. 컴퓨터 별 가능 개수를 확인해보고 비교하기
        Queue<Integer> max_queue = new LinkedList<>();
        int max_count = 0;
        for (int i = 1; i <= N; i++) {
            //현재 값을 확인
            int current_count = 0;
            Queue<Integer> current_queue = new LinkedList<>();
            boolean[] check = new boolean[N + 1];
            check[i] = true;
            current_queue.offer(i);
            while (!current_queue.isEmpty()) {
                current_count++;
                int temp_computer = current_queue.poll();
                for (Computer list : computers[temp_computer].followers) {
                    //이미 확인을 한 컴퓨터라면 패스
                    if (check[list.data]) {
                        continue;
                    }
                    //아직 확인을 하지 않았다면 큐에 넣기
                    current_queue.offer(list.data);
                    check[list.data] = true;
                }
            }
            //기존 최대값보다 크다면, 큐와 최대값을 리셋하고 새로 넣기
            if (current_count > max_count) {
                if (!max_queue.isEmpty()) {
                    max_queue.clear();
                }
                max_count = current_count;
                max_queue.offer(i);
            }
            //기존 최대값이랑 동일하다면, 그냥 큐에만 추가
            else if (current_count == max_count) {
                max_queue.offer(i);
            }
        }

        //결과 출력
        StringBuilder sb = new StringBuilder();
        while (!max_queue.isEmpty()) {
            sb.append(max_queue.poll() + " ");
        }
        System.out.println(sb.toString());
    }
}