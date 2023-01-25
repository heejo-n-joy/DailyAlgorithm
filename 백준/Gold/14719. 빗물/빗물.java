import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 문제 내용
 * - 2차원 세계와 빗물
 * <p>
 * 요구하는 출력
 * - 고이는 빗물의 총량
 * <p>
 * 입력 변수
 * - H : 세계의 세로 길이
 * - W : 세계의 가로 길이
 * <p>
 * 문제 유의사항
 * - 바닥은 항상 막혀있다.
 * <p>
 * 전략
 * - 물을 담을 기둥들을 stack으로 담는다.
 * - 순차적으로 기둥들은 올라가고, 내려간다고 생각
 * - 선별한 기둥들에 True를 체크하고, 두 기둥 사이에 담길 물의 높이 차를 이용하여 합산
 *
 * <p>
 * 예상 난이도 : ★★
 * 체감 난이도 : ★★
 * <p>
 * 회고
 *
 * @author HeejoPark
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int H = Integer.parseInt(st.nextToken());   //2차원 세계의 세로 크기
        int W = Integer.parseInt(st.nextToken());   //2차원 세계의 가로 크기
        int[] blocks = new int[W];
        st = new StringTokenizer(br.readLine());
        for(int i =0; i<W; i++){
            blocks[i] = Integer.parseInt(st.nextToken());
        }
        boolean[] check = new boolean[W];
        //계산
        Stack<Integer> upStack = new Stack<>();  //오름차순 기둥 스택
        Stack<Integer> downStack = new Stack<>();  //내림차순 기둥 스택
        upStack.push(0);    //맨 첫 기둥은 기본값으로 넣어주고
        downStack.push(0);  //맨 첫 기둥은 기본값으로 넣어주고

        //두 번째부터 마지막 위치까지 다 확인
        for(int i = 1; i<W; i++){
            //기둥이 이전 기둥보다 높아진다면 up 스택에 추가
            if(blocks[upStack.peek()] < blocks[i]){
                upStack.push(i);
            }

            //기둥이 이전 기둥보다 낮아진다면 down 스택에 추가
            if(blocks[downStack.peek()] > blocks[i]){
                downStack.push(i);
            }
            //기둥이 직전 기둥보다 높다면
            else{
                //혹시 직전 기둥만 유난히 작았던거라면 다 없애버리기
                while(!downStack.isEmpty() && (blocks[downStack.peek()] <= blocks[i])){
                    downStack.pop();
                }
                //결론적으로 내림차순 기둥이 유지된다.
                downStack.push(i);
            }
        }

        //기둥의 오르막 내리막을 모두 확인했다면, 해당 기둥들은 물이 담길 기준이 될 기둥들이라는 것을 체크해두기
        while(!upStack.isEmpty()){
            int temp = upStack.pop();
            if(blocks[temp] == 0){  //이때, 높이가 0인 기둥은 제거해버린다. 기둥의 역할을 하지 못해 쓸모가 없기 때문
                continue;
            }
            check[temp] = true;
        }
        while(!downStack.isEmpty()){
            int temp = downStack.pop();
            if(blocks[temp] == 0){  //이때, 높이가 0인 기둥은 제거해버린다. 기둥의 역할을 하지 못해 쓸모가 없기 때문
                continue;
            }
            check[temp] = true;
        }

        int totalWater = 0;
        int base = -1;
        for(int i =0; i<W; i++){
            //기둥을 발견
            if(check[i]){
                //기준 기둥이 있는 상태라면
                if(base != -1){
                    //계산
                    int height = Math.min(blocks[i], blocks[base]); //두 기둥 중 작은 높이를 기준 삼고
                    for(int j = base+1; j<i; j++){
                        int diff = height - blocks[j];
                        if(diff > 0){   //물이 담길 수 있으면 해당 양만큼 물을 담기
                            totalWater += diff;
                        }
                    }
                }
                //기준 기둥을 세운다.
                base = i;
            }
        }
        //결과 출력
        System.out.println(totalWater);
    }
}