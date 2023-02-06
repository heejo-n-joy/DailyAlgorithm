import java.util.*;

public class Main{
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        long A = scan.nextLong();
        long B = scan.nextLong();
        long V = scan.nextLong();
        System.out.println(calc(A, B, V));
    }
    
    public static long calc(long A, long B, long V){
        if(A>=V){
            return 1;
        }
        else if ((V-A) % (A - B) == 0){
            
            return ((V-A) / (A - B)) +1;
        }
            else{
                
            return ((V-A) / (A - B)) +2;
            }
    }
}