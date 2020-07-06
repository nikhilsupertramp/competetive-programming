/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


class  QueriesAboutNumbers
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}

//  cd competetive-programming/src/Codechef
//  javac -d ../../classes QueriesAboutNumbers.java
//  java QueriesAboutNumbers
//  https://www.codechef.com/problems/QNUMBER

class Solver {
    HashMap<Long, Integer> hm;
    void solve() throws Exception
    {
        //for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            long n = hp.nextLong();
            int q = hp.nextInt();
            hm = new HashMap<>();

            primeFactorize(n);
            long allDivisorsOfN = 1;
            for(long key : hm.keySet())
            {
                allDivisorsOfN *= (hm.get(key) + 1);
            }

            for(int i = 0; i < q; i++)
            {
                int type = hp.nextInt();
                long k = hp.nextLong();
                if(type == 1){

                    long ans = findDivByBothNums(k);
                    hp.println(ans);
                }
                else if(type == 2){
                    long ans = findDivisorsOfNDivisibleByK(n, k);
                    hp.println(ans);
                }
                else{
                    long ans =  findDivisorsOfNDivisibleByK(n, k);
                    hp.println(allDivisorsOfN - ans);
                }
            }
        }
        hp.flush();
    }

    long findDivByBothNums(long k)throws Exception
    {
        long ans = 1;
        for(long key : hm.keySet())
        {
            long count = 0;
            while(k % key == 0)
            {
                k /= key;
                count++;
            }
            ans *= (Math.min(count, hm.get(key)) + 1);
        }
        return (ans);
    }

    long findDivisorsOfNDivisibleByK(long n, long k)throws Exception
    {
        if(n % k == 0)return (findDivByBothNums(n / k));
        return 0;

    }

    void primeFactorize(long n)
    {
        while(n % 2 == 0)
        {
            n /= 2;
            hm.put(2L, hm.getOrDefault(2L, 0) + 1);
        }
        for(long i = 3; i * i<= n; i += 2)
        {
            while(n % i == 0)
            {
                n /= i;
                hm.put(i, hm.getOrDefault(i, 0) + 1);
            }
        }

        if(n > 2)
            hm.put(n, 1);
    }

    boolean check(int n)
    {
        char[] s = (n + "").toCharArray();
        int count  = 0;
        for(int i = 0; i < s.length; i++)
            if(s[i] == '3')count++;
        return count >= 3;
    }

    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;


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
