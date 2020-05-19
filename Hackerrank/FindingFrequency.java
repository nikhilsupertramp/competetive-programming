/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


public class FindingFrequency
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
//  cd competetive-programming/src/Hackerrank
//  javac -d ../../classes FindingFrequency.java
//  java FindingFrequency
//  problem Link : https://www.hackerrank.com/contests/smart-interviews/challenges/si-finding-frequency/problem

class Solver {
    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;
    void solve() throws Exception
    {
        //for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int n = hp.nextInt();
            int[] arr = hp.getIntArray(n);
            int q = hp.nextInt();
            Pair[] queries = new Pair[q];
            for(int i = 0; i < q; i++)
            {
                int query = hp.nextInt();
                queries[i] = new Pair(i , query);
            }
            Arrays.sort(arr);
            //hp.println(Arrays.toString(arr));

            for(Pair p : queries)
            {
                int query = p.y;
                int ans = findFreq(arr, query);
                hp.println(ans);
            }
        }
        hp.flush();
    }

    int findFreq(int[] arr, int query)
    {
        int lb = findLowerBound(arr, query);
        int ub = findUpperBound(arr, query);
        if(lb == -1 || ub == -1)return 0;
        return (ub - lb + 1);
    }

    int findLowerBound(int[] arr, int query)
    {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int ans = -1;
        while(low <= high)
        {
            int mid = (high + low)/2;
            if(arr[mid] >= query){
                if(arr[mid] == query)
                    ans = mid;
                high = mid - 1;
            }
            else
                low = mid + 1;
        }
        return ans;
    }

    int findUpperBound(int[] arr, int query)
    {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int ans = -1;
        while(low <= high)
        {
            int mid = (high + low)/2;
            if(arr[mid] > query)
                high = mid - 1;
            else
            {
                if(arr[mid] == query)
                    ans = mid;
                low = mid + 1;
            }
        }
        return ans;
    }

/*
    int findFreq(int[] arr, int query)throws Exception
    {
        int n = arr.length;
        int high = n - 1, low = 0;
        int ans = 0;
        while(low <= high)
        {
            int mid = (low + high)/2;
            if(arr[mid] < query)
            {
                low = mid + 1;
            }
            else if(arr[mid] > query)
            {
                high = mid - 1;
            }
            else if(arr[mid] == query)
            {
                int left = exploreleft(arr, mid - 1, query);
                int right = exploreright(arr, mid + 1, query);
                ans += (1 + left + right);
                //hp.println("query = " + query + " " + ans + " " + left + " " + right );
                break;
            }
        }
        return ans;
    }


    int exploreleft(int[] arr, int index, int query)
    {
        int count = 0;
        while(index >= 0 && arr[index] == query)
        {
            count++;index--;
        }
        return count;
    }

    int exploreright(int[] arr, int index, int query)
    {
        int count = 0;
        int n = arr.length;
        while(index < n && arr[index] == query)
        {
            count++;index++;
        }
        return count;
    }
    */

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
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
