import java.util.*;
import java.io.*;

class AddingReversedNumbers{
    public static void main(String[] args){
        Scanner hp = new Scanner(System.in);
        int n = hp.nextInt();
        StringBuilder sb = new StringBuilder();
        while(n --> 0){
            int a = hp.nextInt();
            int b = hp.nextInt();
            sb.append(reverse(reverse(a) + reverse(b)) + "\n");
        }
        System.out.println(sb);
    }

    static int reverse(int n){
        int sum = 0;
        while(n > 0){
            sum = (sum * 10 + (n % 10));
            n /= 10;
        }
        return sum;
    }
}
