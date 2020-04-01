/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;

public class Meeting
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
class Solver {
    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;

//javac -d ../../classes PanoramixPrediction.java
    void solve() throws Exception
    {
        //for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int x1 = hp.nextInt();
            int y1 = hp.nextInt();
            int x2 = hp.nextInt();
            int y2 = hp.nextInt();
            Pair[] arr = getPairs(x1, y1, x2, y2);
            boolean[] flag = new boolean[arr.length];

            int m = hp.nextInt();
            Radiator[] rds = new Radiator[m];
            for(int i = 0; i < m; i++)
            {
                int x = hp.nextInt();
                int y = hp.nextInt();
                int r = hp.nextInt();
                rds[i] = new  Radiator(x, y, r);
            }

            for(int j = 0; j < arr.length; j++)
            {
                Pair p = arr[j];
                for(int i = 0; i < m; i++)
                {
                    if(findDistance(p, rds[i]))
                    {
                        flag[j] = true;
                        break;
                    }
                }
            }

            int count = 0;
            for(int i = 0; i < arr.length; i++)
            {
                if(!flag[i])count++;
            }
            hp.println(count);
        }
        hp.flush();
    }

    boolean findDistance(Pair p, Radiator rad)
    {
        int x = p.x - rad.x;
        int y = p.y - rad.y;
        if(x*x + y*y <= (rad.r * rad.r))return true;
        return false;
    }

    Pair[] getPairs(int x1, int y1, int x2, int y2)throws Exception
    {
        int size = 2 * ((Math.abs(x1 - x2) + 1) + (Math.abs(y2 - y1) - 1));
        Pair[] arr = new Pair[size];
        int pointer = 0;
        //hp.println(size);
        for(int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++)
        {
            arr[pointer++] = new Pair(i, y1);
            //hp.println(arr[pointer - 1].toString());
            arr[pointer++] = new Pair(i, y2);
            //hp.println(arr[pointer - 1].toString());
        }
        for(int i = (Math.min(y1, y2) + 1); i < Math.max(y1, y2); i++)
        {

            arr[pointer++] = new Pair(x1, i);
            //hp.println(arr[pointer - 1].toString());
            arr[pointer++] = new Pair(x2, i);
            //hp.println(arr[pointer - 1].toString());
        }
        return arr;
    }

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
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
class Pair implements Comparable<Pair>{
    int x, y;
    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(x + " " + y);
        return sb.toString();
    }
    @Override
    public int compareTo(Pair p)
    {
        return x - p.x;
    }
}
class Radiator
{
    int x, y, r;
    Radiator(int x, int y, int r)
    {
        this.r = r;
        this.x = x;
        this.y = y;
    }
}
