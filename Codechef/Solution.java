import java.io.*;
import java.util.*;
class Solution{
    public static void main(String[] args)throws Exception {
        Scanner sc = new Scanner(System.in);
        long x = sc.nextLong();
        long y = sc.nextLong();
        long Z = 2 * Math.max(x, y);
        long max = Integer.MIN_VALUE;
        for(long i = 0; i <= Z; i++)
        {
            long sol = (x & i) * (y & i);
            if(sol > max)
            {
                max = sol;
                System.out.println(max + " at i = " + i);
            }
        }
        long orV = (x | y);
        System.out.println((x & orV) * (y & orV));
        System.out.println(x | y);
    }

}
