import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case<=T; test_case++) {
			String inputValuesSplit[] = br.readLine().split(" ");	//test_case별 입력 1줄
			int a = Integer.parseInt(inputValuesSplit[0]);	//변수 a
			int b = Integer.parseInt(inputValuesSplit[1]);	//변수 b
			int result = 1;	//결과 값 변수. a의 0제곱인 1부터 시작
			for(int i = 1; i<=b; i++) {
				result *= a;	//지수를 1 늘리고
				result %= 10;	//10으로 나눈 나머지를 계산
			}
			//결과 값이 0이라면 이는 10번째 컴퓨터를 의미하므로 10으로 결과 값 변경
			if(result == 0) {
				result = 10;
			}
			//결과 출력
			System.out.println(result);
		}

	}

}