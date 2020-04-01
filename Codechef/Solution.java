import java.io.*;
import java.util.*;
class Solution{
    static int hcf(int a, int b){
        if(a%b==0) return b;
        return hcf(b,a%b);
    }
    public static void main(String[] args)throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int i, temp, max, lcm, ans,n,m,t = Integer.parseInt(in.readLine());
        int[] arr = new int[10000];
        String ip[];
        //System.out.println(t);
        while(t-->0){
            ip = in.readLine().split(" ");
            n = Integer.parseInt(ip[0]);
            m = Integer.parseInt(ip[1]);
            //System.out.println(m + " " + n);
            ip = in.readLine().split(" ");
            for(i=0;i<n;i++) arr[i] = Integer.parseInt(ip[i]);
            lcm = arr[0];
            for(i=1;i<n;i++) lcm = (arr[i]*lcm)/hcf(arr[0],lcm);
            ans = 1;
            max = lcm;
            for(i=2;i<=m;i++){
                temp = (i*lcm)/hcf(i,lcm);
                if (temp > max){
                    max = temp;
                    ans = i;
                }
            }
            System.out.println(ans);
        }
    }
}
