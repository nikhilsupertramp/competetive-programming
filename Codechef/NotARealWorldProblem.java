/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


class NotARealWorldProblem
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}

//  cd competetive-programming/src/Codechef
//  javac -d ../../classes NotARealWorldProblem.java
//  java NotARealWorldProblem
//  problem link : https://www.codechef.com/MAY20B/problems/NRWP


class Solver {
    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
    }

    void solve() throws Exception
    {
        for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int h = hp.nextInt();
            int w = hp.nextInt();
            int n = hp.nextInt();
            int[][] arr = new int[h + 1][w + 1];
            for(int i = 1; i <= h; i++)
            {
                for(int j = 1; j <= w; j++)
                {
                    arr[i][j] = hp.nextInt();
                }
            }

            int[] x = new int[n];
            int[] y = new int[n];
            int[] p = new int[n];
            for(int i = 0; i < n; i++)
            {
                x[i] = hp.nextInt();
                y[i] = hp.nextInt();
                p[i] = hp.nextInt();
            }
            Long max = Long.MIN_VALUE;
            int max_at = -1;

            int[] bits = new int[n];
            for(int itr = 0; itr < (1 << n); itr++)
            {
                long instance = getValue(arr, x, y, p, itr);
                if(instance > max)
                {
                    max = instance;
                    max_at = itr;
                }
            }


            hp.println(max);
            int ans[] = new int[n];
            getBits(max_at, ans);
            for(int i : ans)hp.print(i + " ");
        }
        hp.flush();
    }

    long getValue(int[][] arr, int[] x, int[] y, int[] p, int curr)throws Exception
    {
        int[] bits = new int[x.length];
        getBits(curr, bits);
        long left = getLeftValue(p, bits, x, y, arr);
        long right = getRightValue(p, bits);
        return (left + right);
    }

    long getLeftValue(int[] p, int[] bits, int[] x, int[] y, int[][] arr)throws Exception
    {
        long sum = 0;
        int n = p.length;
        //hp.println(Arrays.toString(p) + "\n" +
        //            Arrays.toString(x) + "\n" + Arrays.toString(y));
        for(int i = 0; i < n; i++)
        {
            int pv = (p[i] * bits[i]);
            int h = arr[x[i]][y[i]];
            sum += (pv * h);
            //hp.println(pv + " " + h);
        }
        //hp.print(sum + " ");
        return sum;
    }

    long getRightValue(int[] p, int[] bits)throws Exception
    {
        int n = p.length;
        long sum = 0;
        for(int i = 0; i < n - 1; i++)
        {
            int p1 = p[i] * bits[i];
            int p2 = p[i + 1]  * bits[i + 1];
            sum += (p1 * p2);
        }
        //hp.println(sum + " ");
        return sum;
    }

    void getBits(int n, int[] arr)
    {
        int i = 0;
        while(n > 0)
        {
            arr[i++] = (n & 1);
            n >>= 1;
        }
        for(i = 0; i < arr.length; i++)
            if(arr[i] == 0)arr[i] = -1;
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
