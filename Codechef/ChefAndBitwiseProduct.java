/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


class ChefAndBitwiseProduct
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
//  cd competetive-programming/src/Codechef
//  javac -d ../../classes ChefAndBitwiseProduct.java
//  java ChefAndBitwiseProduct
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


            bruteforce(x, y , l, r);
            ///optimized(x, y, l, r);
            hp.println();

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

    void optimized(long x, long y, long l, long r)throws Exception
    {

    }

    long val(long x, long y, long ans)
    {
        return (x & ans) * (y & ans);
    }

    void bruteforce(long x, long y, long l, long r)throws Exception
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

        hp.println(x + " " + y + " " + l + " " + r + " " + ans);
        //hp.println(ans + " " + (x & ans) + " " + (y & ans));
        StringBuilder sb = getSBits((x | y));
        sb.insert(0,"X | Y         = ");

        sb.append("\nX =           = " + getSBits(x));
        sb.append("\nY =           = " + getSBits(y));
        sb.append("\nL             = " + getSBits(l));
        sb.append("\nr             = " + getSBits(r));
        sb.append("\nZ =           = " + getSBits(ans));
        sb.append("\nX&Z           = " + getSBits(x & ans));
        sb.append("\nY&Z           = " + getSBits(y & ans));

        hp.println(sb.toString());
    }

    Solver() {
        hp = new Helper(MOD, MAXN);
        //hp.initIO(System.in, System.out);
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


/* *observations*
    see its like taking the Bitwise OR of both x  &  y will give
    us the value that gives us the maximum value when we take individual Bitwise AND

    now the problem is to find a number which has got set bits in the setbit positions of
    Bitwise OR of x & y, in the range from L to R.

    Iteratively find the solution find the value for Z in the limits of L and R
    start Z from X|Y,
    i.e. check if Z > R, Z /= 2
         or if Z < L,    Z *=
*/
/*
50
6284 8314 0 9248
7292 7611 0 2428
5359 9270 0 7277
6473 7636 0 2383
7704 8731 0 8737
1381 5502 0 4559
3652 3932 0 8566
9310 9681 0 359
6232 7177 0 2888
6033 9935 0 2112
6663 7667 0 4496
9800 9856 0 3620
5111 9618 0 2387
1618 7736 0 896
4234 4709 0 7530
1668 4430 0 8996
8088 8166 0 6781
2833 6314 0 9937
6884 8090 0 9795
6910 7915 0 7829
5393 9020 0 6992
1955 6321 0 9605
5653 8194 0 4865
6343 6764 0 1304
1934 5732 0 2432
5905 8778 0 9595
840 3663 0 6313
686 9079 0 8834
3060 9897 0 3350
1397 6009 0 4181
6174 8333 0 7753
8127 9247 0 8322
3067 3666 0 3390
3843 5195 0 1800
5885 7009 0 2050
7968 9315 0 6601
6144 6885 0 1376
4104 8826 0 7754
2752 4258 0 4961
5789 7070 0 4896
8614 9125 0 5045
2889 9252 0 2776
5468 8936 0 812
3576 8046 0 8008
5217 7106 0 2877
5453 6800 0 6378
3628 4934 0 4829
8452 9158 0 6859
759 2652 0 3257
9286 9312 0 2176
8446 1163960
2428 5125920
5375 5777002
2381 5638244
8731 4679816
4479 1598646
3932 14359664
351 31678
2137 4393752
2015 3376191
4496 18415616
1736 2675712
2039 1447390
634 337392
4847 19937906
6094 7389240
6780 45130080
7099 17887562
8190 55691560
7829 53132436
5949 4465404
8115 12357555
4631 9258
751 123380
2030 3164024
9563 10721002
3919 3076920
8834 5589252
3349 2382100
4181 355045
6303 870534
8322 1065220
3387 7301670
1800 1849344
2049 2049
6499 636768
741 0
4730 2601936
4834 2997632
4895 22693478
935 393726
2669 94788
812 147936
8008 27227200
2851 92994
6365 26239824
4718 2600968
966 251160
2815 2012868
1126 1225280

*/
