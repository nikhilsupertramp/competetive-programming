/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.ArrayList;

public class EqualStacks
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
//  cd competetive-programming/src/Hackerrank
//  javac -d ../../classes EqualStacks.java
//  java EqualStacks
//  https://www.hackerrank.com/challenges/equal-stacks/problem

class Solver {

    int[] children = new int[100];

    void solve() throws Exception
    {
        //for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int n1 = hp.nextInt();
            int n2 = hp.nextInt();
            int n3 = hp.nextInt();
            Stack<Integer> st1 = new Stack<>();
            Stack<Integer> st2 = new Stack<>();
            Stack<Integer> st3 = new Stack<>();

            int sum1 = 0, sum2 = 0, sum3 = 0;

            int[] arr1 = hp.getIntArray(n1);
            int[] arr2 = hp.getIntArray(n2);
            int[] arr3 = hp.getIntArray(n3);

            for(int i = n1 - 1; i >= 0; i--)
            {
                int k = arr1[i];
                sum1 += k;
                st1.push(k);
            }
            for(int i = n2 - 1; i >= 0; i--)
            {
                int k = arr2[i];
                sum2 += k;
                st2.push(k);
            }
            for(int i = n3 - 1; i >= 0; i--)
            {
                int k = arr3[i];
                sum3 += k;
                st3.push(k);
            }

            while(!((sum1 == sum2) && (sum2 == sum3) && (sum1 == sum3)))
            {
                /*
                if(st1.peek() == 0 || st2.peek() == 0 || st3.peek() == 0)
                {
                    st1.push(0);
                    st3.push(0);
                    st2.push(0);
                    break;
                }
                */
                if(sum1 == 0 || sum2 == 0 || sum3 == 0)
                {
                    sum1 = 0;
                    sum2 = 0;
                    sum3 = 0;
                    break;
                }
                if(sum1 > sum2 && sum1 > sum3){
                    int k = st1.pop();
                    sum1 -= k;
                    //hp.println("peeks are " + k + " ftom stack 1" + st2.peek() + " " + st3.peek());
                    //System.out.println("popped " + k + " ftom stack 1 and top rn is " + sum1 );
                }
                else if(sum2 > sum3 && sum2 > sum1){
                    int k = st2.pop();
                    sum2 -= k;
                    //System.out.println("popped " + k + " ftom stack 2 " + sum2);
                }
                else if(sum3 > sum2 && sum3 > sum1){
                    int k = st3.pop();
                    sum3 -= k;
                    //System.out.println("popped " + k + " ftom stack 3 " + sum3);
                }
            }
            hp.println(sum1);

        }
        hp.flush();
    }

    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
    }
}

class TreeNode
{
    int val;
    TreeNode left, right;
    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;
    public TreeNode(int val)
    {
        hp = new Helper(MOD, MAXN);
        this.val = val;
        left = null;
        right = null;
    }


    void insert(int x)
    {
        if(x <= val)
        {
            if(left == null)
                left = new TreeNode(x);
            else
                left.insert(x);
        }
        else
        {
            if(right == null)
                right = new TreeNode(x);
            else
                right.insert(x);
        }
    }

    void inOrder()throws Exception
    {
        if(left != null)
            left.inOrder();
        hp.print(val + " " );
        if(right != null)
            right.inOrder();
    }

    void preOrder()throws Exception
    {
        hp.print(val + " ");
        if(left != null)
            left.preOrder();
        if(right != null)
            right.preOrder();
    }

    void postOrder()throws Exception
    {

        if(left != null)
            left.postOrder();
        if(right != null)
            right.postOrder();
        hp.print(val + " ");
    }

}

class Pair implements Comparable<Pair>{
    int x;
    int y;//long z;

    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
        //this.z = z;
    }
    @Override
    public int compareTo(Pair p)
    {
        if(p.y == y)
        return x - p.x;
        return y - p.y;
    }
}

class Helper {
    final long MOD;
    final int MAXN;
    final Random rnd;

    public Helper(long mod, int maxn) {
        MOD = mod;
        MAXN = maxn;
        rnd = new Random();
    }

    public static int[] sieve;
    public static ArrayList<Integer> primes;

    public void setSieve() {
        primes = new ArrayList<>();
        sieve = new int[MAXN];
        int i, j;
        for (i = 2; i < MAXN; ++i)
            if (sieve[i] == 0) {
                primes.add(i);
                for (j = i; j < MAXN; j += i) {
                    sieve[j] = i;
                }
            }
    }


    public static long[] factorial;

    public void setFactorial() {
        factorial = new long[MAXN];
        factorial[0] = 1;
        for (int i = 1; i < MAXN; ++i) factorial[i] = factorial[i - 1] * i % MOD;
    }

    public long getFactorial(int n) {
        if (factorial == null) setFactorial();
        return factorial[n];
    }

    public long ncr(int n, int r) {
        if (r > n) return 0;
        if (factorial == null) setFactorial();
        long numerator = factorial[n];
        long denominator = factorial[r] * factorial[n - r] % MOD;
        return numerator * pow(denominator, MOD - 2, MOD) % MOD;
    }


    public long[] getLongArray(int size) throws Exception {
        long[] ar = new long[size];
        for (int i = 0; i < size; ++i) ar[i] = nextLong();
        return ar;
    }

    public int[] getIntArray(int size) throws Exception {
        int[] ar = new int[size];
        for (int i = 0; i < size; ++i) ar[i] = nextInt();
        return ar;
	}

    public int[] getIntArray(String s)throws Exception
    {
        s = s.trim().replaceAll("\\s+", " ");
        String[] strs = s.split(" ");
        int[] arr = new int[strs.length];
        for(int i = 0; i < strs.length; i++)
        {
            arr[i] =  Integer.parseInt(strs[i]);
        }
        return arr;
    }

	public long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    public long max(long[] ar) {
        long ret = ar[0];
        for (long itr : ar) ret = Math.max(ret, itr);
        return ret;
    }

    public int max(int[] ar) {
        int ret = ar[0];
        for (int itr : ar) ret = Math.max(ret, itr);
        return ret;
    }

    public long min(long[] ar) {
        long ret = ar[0];
        for (long itr : ar) ret = Math.min(ret, itr);
        return ret;
    }

    public int min(int[] ar) {
        int ret = ar[0];
        for (int itr : ar) ret = Math.min(ret, itr);
        return ret;
    }


    public long sum(long[] ar) {
        long sum = 0;
        for (long itr : ar) sum += itr;
        return sum;
    }

    public long sum(int[] ar) {
        long sum = 0;
        for (int itr : ar) sum += itr;
        return sum;
    }

    public void shuffle(int[] ar) {
        int r;
        for (int i = 0; i < ar.length; ++i) {
            r = rnd.nextInt(ar.length);
            if (r != i) {
                ar[i] ^= ar[r];
                ar[r] ^= ar[i];
                ar[i] ^= ar[r];
            }
        }
    }

    public void shuffle(long[] ar) {
        int r;
        for (int i = 0; i < ar.length; ++i) {
            r = rnd.nextInt(ar.length);
            if (r != i) {
                ar[i] ^= ar[r];
                ar[r] ^= ar[i];
                ar[i] ^= ar[r];
            }
        }
    }

    public long pow(long base, long exp, long MOD) {
        base %= MOD;
        long ret = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) ret = ret * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return ret;
    }


    static byte[] buf = new byte[1000_006];
    static int index, total;
    static InputStream in;
    static BufferedWriter bw;


    public void initIO(InputStream is, OutputStream os) {
        try {
            in = is;
            bw = new BufferedWriter(new OutputStreamWriter(os));
        } catch (Exception e) {
        }
    }

    public void initIO(String inputFile, String outputFile) {
        try {
            in = new FileInputStream(inputFile);
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outputFile)));
        } catch (Exception e) {
        }
    }

    private int scan() throws Exception {
        if (index >= total) {
            index = 0;
            total = in.read(buf);
            if (total <= 0)
                return -1;
        }
        return buf[index++];
    }

    public String nextLine() throws Exception {
        int c;
        for (c = scan(); c <= 32; c = scan()) ;
        StringBuilder sb = new StringBuilder();
        for (; c >= 32; c = scan())
            sb.append((char) c);
        return sb.toString();
    }

    public String next() throws Exception {
        int c;
        for (c = scan(); c <= 32; c = scan()) ;
        StringBuilder sb = new StringBuilder();
        for (; c > 32; c = scan())
            sb.append((char) c);
        return sb.toString();
    }

    public int nextInt() throws Exception {
        int c, val = 0;
        for (c = scan(); c <= 32; c = scan()) ;
        boolean neg = c == '-';
        if (c == '-' || c == '+')
            c = scan();
        for (; c >= '0' && c <= '9'; c = scan())
            val = (val << 3) + (val << 1) + (c & 15);
        return neg ? -val : val;
    }

    public long nextLong() throws Exception {
        int c;
        long val = 0;
        for (c = scan(); c <= 32; c = scan()) ;
        boolean neg = c == '-';
        if (c == '-' || c == '+')
            c = scan();
        for (; c >= '0' && c <= '9'; c = scan())
            val = (val << 3) + (val << 1) + (c & 15);
        return neg ? -val : val;
    }

    public void print(Object a) throws Exception {
        bw.write(a.toString());
    }

    public void printsp(Object a) throws Exception {
        print(a);
        print(" ");
    }

    public void println() throws Exception {
        bw.write("\n");
    }

    public void println(Object a) throws Exception {
        print(a);
        println();
    }

    public void flush() throws Exception {
        bw.flush();
    }
}
