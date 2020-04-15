import java.io.*;
import java.util.*;
class Solution{
    public static void main(String[] args)throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = 1000;
        int count = 0;
        for(int i = 2; i < n; i++)
            if(isPrime(i)){
                System.out.print((i) + ", ");
                count++;
            }
            System.out.println("\n" + count);
    }
    static boolean isPrime(int n)
    {
        //int count = 0;
        for(int i = 2; i * i <= n; i++)
        {
            if(n % i == 0)
                return false;
        }
        return true;
    }
}
