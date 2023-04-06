import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * 요구하는 출력 : 문제를 추천해주는 시스템을 만들어라
 *
 * TreeSet
 * - 중복 데이터를 저장하지 않고, 저장 순서를 유지하지 않는다.
 * - 이진 탐색 트리 구조로 이루어져 있다.
 * - Comparator 객체를 이용하여 원하는 방법으로 정렬을 할 수 있다.
 */

class Problem{
    int no;
    int level;
    Problem(int no, int level){
        this.no = no;
        this.level = level;
    }
}
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, Problem> hashMap = new HashMap<>();
        TreeSet<Problem> treeSet = new TreeSet<>(new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                if (o1.level == o2.level) {
                    //번호를 기준으로 내림차순
                    return o2.no - o1.no;
                }
                //레벨이 다르다면
                else {
                    //레벨을 기준으로 내림차순
                    return o2.level - o1.level;
                }
            }
        });

        int N = Integer.parseInt(br.readLine());   //현재 추천리스트에 있는 문제 개수
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Insert(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), treeSet, hashMap);
        }
        int M = Integer.parseInt(br.readLine());   //입력될 명령문
        for (int i = 0; i < M; i++) {
            String[] str = br.readLine().split(" ");
            switch (str[0]) {
                //추가하기
                case "add":
                    Insert(Integer.parseInt(str[1]), Integer.parseInt(str[2]), treeSet, hashMap);
                    break;
                case "recommend":
                    if(str[1].equals("1")){
                        System.out.println(treeSet.first().no);
                    }
                    else if(str[1].equals("-1")){
                        System.out.println(treeSet.last().no);
                    }
                    else{
                        //ERROR
                        System.out.println("ERROR : X VALUE IS NOT 1 or -1");
                    }
                    break;
                case "solved":
                    Delete(Integer.parseInt(str[1]), treeSet, hashMap);
                    break;
                default:
                    //ERROR
                    System.out.println("ERROR : CANNOT FIND COMMAND "  + str[0]);
                    break;
            }
        }
    }
    public static void Insert(int no, int level, TreeSet<Problem> treeSet, HashMap<Integer, Problem> hashMap){
        Problem problem = new Problem(no, level);
        treeSet.add(problem);
        hashMap.put(problem.no, problem);
    }

    public static void Delete(int no, TreeSet<Problem> treeSet, HashMap<Integer, Problem> hashMap){
        Problem problem = hashMap.get(no);
        treeSet.remove(problem);
        hashMap.remove(no);
    }
}