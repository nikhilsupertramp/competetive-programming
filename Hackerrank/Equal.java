/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.ArrayList;

public class Equal
{
    public static long find_min_actions(int[] cookies) {

    Arrays.sort(cookies);

    long sum = Long.MAX_VALUE;

    for(int base = 0; base < 3; base++) {
        int current_sum = 0;
        for(int i = 0; i < cookies.length; i++) {
            int delta = cookies[i] - cookies[0] + base;
            current_sum += (int)delta / 5 + delta % 5 / 2 + delta % 5 % 2 / 1;
        }
        sum = Math.min(current_sum,sum);
    }

    return sum;
}

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while(n-->0) {
            int m = in.nextInt();
            int cookies[] = new int[m];
            for(int cookie_i=0; cookie_i < m; cookie_i++){
                cookies[cookie_i] = in.nextInt();
            }
            System.out.println(find_min_actions(cookies));
        }


    }

}
//  cd competetive-programming/src/Hackerrank
//  javac -d ../../classes Equal.java
//  java Equal
//  https://www.hackerrank.com/challenges/equal/problem
