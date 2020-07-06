/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


public class ReadingBooks
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
//  cd competetive-programming/src/Codeforces
//  javac -d ../../classes ReadingBooks.java
//  java ReadingBooks


class Solver {
    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;
    void solve() throws Exception
    {
        //sfor(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int n = hp.nextInt();
            int filled = hp.nextInt();
            ArrayList<Integer> both = new ArrayList<>();
            ArrayList<Integer> al = new ArrayList<>();
            ArrayList<Integer> bo = new ArrayList<>();
            //ArrayList<Pair> one = new ArrayList<>();
            for(int i = 0; i < n; i++)
            {
                int ti = hp.nextInt();
                int a = hp.nextInt();
                int b = hp.nextInt();
                if(a == 1 && b == 1)
                    both.add(ti);
                else if(a == 1)
                    al.add(ti);
                else if(b == 1)
                    bo.add(ti);
            }

            Collections.sort(both);
            Collections.sort(al);
            Collections.sort(bo);

            int alice = 0, bob = 0, i = 0, j = 0, k = 0;
            long totaltime = 0;
            while(i < both.size() && j < al.size() && k < bo.size())
            {
                int alicetime = al.get(j);
                int bobtime = bo.get(k);
                int bothtime = both.get(i);
                if(alicetime + bobtime < bothtime)
                {
                    alice++;
                    bob++;
                    totaltime += alicetime + bobtime;
                    j++;k++;
                }
                else
                {
                    alice++;
                    bob++;i++;
                    totaltime += bothtime;
                }

                if(alice == filled)break;
                else if(bob == filled)break;
            }

            while(alice < filled)
            {
                int alicetime = (al.size() > j) ? al.get(j) : Integer.MAX_VALUE;
                int bobtime = (bo.size() > k) ? bo.get(k) : Integer.MAX_VALUE;
                int bothtime = (both.size() > i) ? both.get(i) : Integer.MAX_VALUE;
                if(alicetime < bothtime)
                {
                    totaltime += alicetime;
                    alice++;
                }
                else
                {
                    totaltime += bothtime;
                    alice++;
                }
            }

            while(bob < filled)
            {

                int alicetime = (al.size() > j) ? al.get(j) : Integer.MAX_VALUE;
                int bobtime = (bo.size() > k) ? bo.get(k) : Integer.MAX_VALUE;
                int bothtime = (both.size() > i) ? both.get(i) : Integer.MAX_VALUE;
                if(bobtime < bothtime)
                {
                    totaltime += bobtime;
                    bob++;
                }
                else
                {
                    totaltime += bothtime;
                    bob++;
                }
            }
            if(bob == filled && alice == filled)
                hp.print(totaltime);
            else
                hp.print(-1);


        }
        hp.flush();
    }

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
    }

}

class Pair implements Comparable<Pair>{
    int t;
    int a;//long z;
    int b;


    public Pair(int t, int a, int b)
    {
        this.t = t;
        this.a = a;
        this.b = b;
        //this.z = z;
    }
    @Override
    public int compareTo(Pair p)
    {
        return t - p.t;
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
