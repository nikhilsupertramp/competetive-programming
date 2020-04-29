/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.ArrayList;

public class QueensAttackII
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}
//  cd competetive-programming/src/Hackerrank
//  javac -d ../../classes QueensAttackII.java
//  problem link : https://www.hackerrank.com/challenges/queens-attack-2/problem

class Solver {
    final Helper hp;

    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;
    void solve() throws Exception
    {
        int n = hp.nextInt();
        int k = hp.nextInt();
        Pair queen = new Pair(hp.nextInt(), hp.nextInt());
        HashMap<String, Pair> hm = new HashMap<>();

        hm.put("L", new Pair(0, queen.y));
        hm.put("R", new Pair(n + 1, queen.y));
        hm.put("T", new Pair(queen.x, n + 1));
        hm.put("B", new Pair(queen.x, 0));

        int min = Math.min(queen.x, queen.y);
        int max = n -  Math.max(queen.x, queen.y);
        hm.put("BL", new Pair(queen.x - min, queen.y - min));
        hm.put("TR", new Pair(queen.x + max + 1, queen.y + max + 1));

        int tl = Math.min((n - queen.y), (queen.x - 1));
        int br = Math.min((n - queen.x), (queen.y - 1));
        hm.put("BR", new Pair((queen.x + br + 1), (queen.y - br - 1)));
        hm.put("TL", new Pair((queen.x - tl - 1), (queen.y + tl + 1)));

        while(k-- > 0)
        {
            int x = hp.nextInt();
            int y = hp.nextInt();
            getDirection(x, y, queen, hm);
        }
        int totalDist = 0;
        int ld = Math.abs(queen.x - hm.get("L").x) - 1;
        int rd = Math.abs(queen.x - hm.get("R").x) - 1;
        int td = Math.abs(queen.y - hm.get("T").y) - 1;
        int bd = Math.abs(queen.y - hm.get("B").y) - 1;

        //hp.println(ld + " " + rd + " " + td + " " + bd);

        int trd = Math.abs(queen.x - hm.get("TR").x) - 1;
        int brd = Math.abs(queen.x - hm.get("BR").x) - 1;
        int bld = Math.abs(queen.x - hm.get("BL").x) - 1;
        int tld = Math.abs(queen.x - hm.get("TL").x) - 1;

        //hp.println(trd + " " + tld + " " + brd + " " + bld );

        totalDist = (ld + rd + td + bd + trd + brd + tld + bld);
        hp.println(totalDist);

        hp.flush();
    }

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
    }

    void getDirection(int x, int y, Pair queen, HashMap<String, Pair> hm)throws Exception
    {
        int px = queen.x;
        int py = queen.y;
        int xdiff = x - px;
        int ydiff = y - py;
        if((Math.abs(xdiff) == Math.abs(ydiff)) || (xdiff == 0 || ydiff == 0))
        {
            //hp.println("it matters");
            if((Math.abs(xdiff) == Math.abs(ydiff)))
            {
                if(xdiff == ydiff)
                {
                    if(xdiff < 0 && ydiff < 0)
                    {
                        Pair p = hm.get("BL");
                        if(x > p.x && y > p.y)
                            hm.put("BL", new Pair(x, y));
                    }
                    else if(xdiff > 0 && ydiff > 0)
                    {
                        Pair p = hm.get("TR");
                        if(x < p.x && y < p.y)
                            hm.put("TR", new Pair(x, y));
                    }
                }
                else
                {
                    if(xdiff < 0 && ydiff > 0)
                    {
                        Pair p = hm.get("TL");
                        if(x > p.x && y < p.y)
                            hm.put("TL", new Pair(x, y));
                    }
                    else if(xdiff > 0 || ydiff < 0)
                    {
                        //hp.println("im here");
                        Pair p = hm.get("BR");
                        if(x < p.x && y > p.y){
                            hm.put("BR", new Pair(x, y));
                            //hp.println("changed values ti " + x + " " + y);
                        }
                    }
                }
            }
            else if((xdiff == 0) || (ydiff == 0))
            {
                if(xdiff == 0 && ydiff != 0)
                {
                    if(ydiff < 0)
                    {
                        Pair p = hm.get("B");
                        if(y > p.y)
                            hm.put("B", new Pair(px, y));
                    }
                    else
                    {
                        Pair p = hm.get("T");
                        if(y < p.y)
                            hm.put("T", new Pair(px, y));
                    }
                }
                else if(xdiff != 0 && ydiff == 0)
                {
                    if(xdiff > 0)
                    {
                        Pair p = hm.get("R");
                        if(x < p.x)
                            hm.put("R", new Pair(x, py));
                    }
                    else if(xdiff < 0)
                    {
                        //hp.println("changing left");
                        Pair p = hm.get("L");
                        if(x > p.x)
                            hm.put("L", new Pair(x, py));
                    }
                }
            }
        }
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
