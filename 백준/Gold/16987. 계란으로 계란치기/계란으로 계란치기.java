import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구하는 출력
 * - 계란으로 깰 수 있는 계란의 최대 개수
 *
 * 풀이 방법
 * - 모든 계란을 깨부수는 경우의 수를 탐방한다.
 * - 1. 계란을 손에 쥔다.
 * - 2. 손에 쥔 계란이 깨지지 않았고, 아직 깰 수 있는 다른 계란이 있다면 하나씩 확인해서 부딪힌다.
 * - 3. 이후 다음 계란을 손에 쥐게 한다.
 * - 4. 계산이 끝나면 다시 원래대로 값을 되돌리고, 2번의 다른 계란을 골라 부딪힌다.
 */

class Egg{
    int durability; //내구도
    int weight;     //무게
    Egg(int durability, int weight){
        this.durability = durability;
        this.weight = weight;
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Egg[] eggs = new Egg[N];
        boolean[] is_broken = new boolean[N];
        for(int i = 0; i<N; i++){
            StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
            eggs[i] = new Egg(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
        }
        max_count = 0;

        //실행
        exec(eggs, is_broken, N, 0, N);

        //결과출력
        System.out.println(max_count);
    }

    static int max_count;

    public static void exec(Egg[] eggs, boolean[] is_broken, int N, int hand_index, int rest_egg){
        if(hand_index == N){
            int count = 0;
            for(int i = 0; i<N; i++){
                if(is_broken[i]){
                    count++;
                }
            }
            max_count = Math.max(max_count, count);
            return;
        }
        //손에 든 계란이 깨져있지 않고 아직 남은 계란이 있다면 계란을 하나 골라 치기
        if((!is_broken[hand_index])&&(rest_egg > 1)){   //남은 계란에 1은 왜 빼냐면, 손에 든 계란이기 때문
            //계란을 선택하기
            for(int i = 0; i<N; i++){
                //선택된 계란이 깨져있지도 않고, 손에 쥔 계란이랑 다르다면
                if(!is_broken[i] && (i != hand_index)) {
                    int new_broken_egg_count = 0;   //새로 추가된 깨진 계란들

                    //계란을 서로 부딪힌다.
                    eggs[i].durability -= eggs[hand_index].weight;
                    eggs[hand_index].durability -= eggs[i].weight;

                    //선택된 계란이 깨졌는지 체크
                    if(eggs[i].durability <= 0){
                        new_broken_egg_count++;
                        is_broken[i] = true;
                    }

                    //손에 쥔 계란이 깨졌는지 체크
                    if(eggs[hand_index].durability <= 0){
                        new_broken_egg_count++;
                        is_broken[hand_index] = true;
                    }
                    exec(eggs, is_broken, N, hand_index + 1, rest_egg - new_broken_egg_count);

                    //원래대로 돌려두기
                    eggs[i].durability += eggs[hand_index].weight;
                    eggs[hand_index].durability += eggs[i].weight;
                    is_broken[i] = false;
                    is_broken[hand_index] = false;
                }
            }
        }
        else{
            exec(eggs, is_broken, N, hand_index+1, rest_egg);
        }
    }
}