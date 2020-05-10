/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


class ChefAndBitwiseProduct2
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
//  cd competetive-programming/src/Codechef
//  javac -d ../../classes ChefAndBitwiseProduct2.java
//  java ChefAndBitwiseProduct2
//  problem link : https://www.codechef.com/MAY20B/problems/CHANDF

class Solver {
    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;
    void solve() throws Exception
    {
        for(int tc = hp.nextInt(); tc > 0; tc--)
        {
              long x = hp.nextLong();
              long y = hp.nextLong();
              long l = hp.nextLong();
              long r = hp.nextLong();
              if(x == 0 || y == 0)
              {
                  hp.println(l);
                  continue;
              }
              //long bruteforce = bruteforce(x, y, l, r);
              long optimized = optimized(x,y,l,r);
              hp.println(optimized);//+ " " + bruteforce);

              /*if(optimized != bruteforce){
                  hp.println(x + " " + y + " " + l + " " + r + " " + bruteforce + " " + optimized);
                  hp.println(val(x, y, bruteforce) + " " + val(x, y, optimized));
                  hp.println(getSBits(x).insert(0, "x          = ") + "\n" + getSBits(y).insert(0, "y          = "));
                  hp.println(getSBits(bruteforce).insert(0, "bruteforce = ") + "\n" + getSBits(optimized).insert(0, "optimized  = ") + "\n");
              }*/
        }
        hp.flush();
    }

    void getBits(int[] arr, long n)
    {
        int i = 40;
        while(n > 0)
        {
            arr[i--] = (int)(n & 1);
            n >>= 1;
        }
    }

    StringBuilder getSBits(long n)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 20; i++)
        {
            sb.insert(0, (n & 1));
            n >>= 1;
        }
        return sb;
    }

    /*
    long optimized(long x, long y, long l, long r) throws Exception
    {
        ArrayList<Long> li = new ArrayList<>();
        long z = x | y;
        long max = 0;
        for(int i = 42; i >= 0; i--)
        {
            //hp.println(((1L << i) & x));
            if((((1L << i) & x) >= 1) && ((1L << i) <= r))
            {
                long ans = (1L << i);
                boolean flag = false;
                if((ans & y) > 1)flag = true;
                //hp.println(ans);
                for(int j = 42; j >= 0; j--)
                {
                    if(j == i)continue;
                    if(!flag && (((1L << j) & y) > 0) && (ans + (1L << j) <= r))
                    {
                        flag = true;
                        ans += (1L << j);
                        //j = (i - 1);
                    }
                    else if(flag && (((1L << j) & z) > 0) && (ans + (1L << j) <= r))
                    {
                        ans += (1L << j);
                    }
                    //hp.println(ans);
                }
                //hp.println(i + " " + ans);
                long vala = val(x, y, ans);
                long valb = val(x, y, max);
                if(vala > valb)
                    max = ans;
                else if(vala == valb && ans < max)
                    max = ans;

            }
            //hp.println(i);
        }

        return max;
    }
    */

    long optimized(long x, long y, long l, long r)throws Exception
    {
        long Z = x|y;

        ArrayList<Long> li = new ArrayList<>();
        long same = 0, instance = 0;
        long temp = 0, ans = 0;
        li.add(r);

        int i = -1;
        for(i = 42; i >= 0; i--)
        {
            if(((r & (1L << i)) > 0 && ((l & (1L << i)) == 0)) ||
               ((l & (1L << i)) > 0 && ((r & (1L << i)) == 0)))
               break;
            if(((r & (1L << i)) > 0) && ((l & (1L << i)) > 0))same += (1L << i);
        }
        instance = same;
        for(int j = i; j >= 0; j--)
        {
            if((r & (1L << j)) > 0)
            {
                temp = instance;
                for(int k = j - 1; k >= 0; k--)
                {
                    int bit = 0;
                    if(((x & (1L << k)) != 0) || ((y & (1L << k)) != 0) ||
                        ((l & (1L << k)) != 0))
                        bit = 1;
                    temp += (1L << k) * bit;
                }

                if(temp >= l && temp <= r)
                    li.add(temp);
                if(instance >= l && instance <= r)
                    li.add(instance);
                instance += 1L << j;
            }
        }
        if(instance >= l && instance <= r)
            li.add(instance);
        instance = same;

        if(instance >= l && instance <= r)
            li.add(instance);

        Collections.sort(li);
        //hp.println(li);
        long product = 0;
        for(int itr = 0; itr < li.size(); itr ++)
        {
            long element = li.get(itr);

            if(val(x, y, element) > val(x, y, ans) && (element >= l) )
                ans = element;
        }


        for(int j = 42; j >= 0; j--)
        {
            long val = 1L << j;
            if((x & val) == 0 && (y & val) == 0 && (ans & val) > 0)
            {
                if(ans - val >= l)ans -= val;
            }
        }
        if(val(x, y, ans) == 0)return l;
        return ans;

    }


    long val(long x, long y, long ans)
    {
        return (x & ans) * (y & ans);
    }

    long bruteforce(long x, long y, long l, long r)throws Exception
    {
        long max = Integer.MIN_VALUE;
        long ans = 0;
        for(long i = l; i <= r; i++)
        {
            long sol = (x & i) * (y & i);
            if(sol > max)
            {
                max = sol;
                ans = i;
            }
        }

        //hp.println(x + " " + y + " " + r );
        //hp.println(ans);//+ " " + (x & ans) + " " + (y & ans));
        StringBuilder sb = getSBits((x | y));
        sb.insert(0,"X | Y         = ");


        sb.append("\nZ =           = " + getSBits(ans));
        sb.append("\nX&Z           = " + getSBits(x & ans));
        sb.append("\nY&Z           = " + getSBits(y & ans));
        return ans;
        //hp.println(sb.toString());
    }

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
        hp.initIO("../tests/ChefAndBitwiseProduct.txt", "../tests/ChefAndBitwiseProductOut.txt");
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
        return p.y - y;
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
