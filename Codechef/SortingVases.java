/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


class SortingVases
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}

//  cd competetive-programming/src/Codechef
//  javac -d ../../classes SortingVases.java
//  java SortingVases
//  problem link : https://www.codechef.com/MAY20B/problems/SORTVS

class Solver {
    HashMap<Integer, HashSet<Integer>> uv;
    @SuppressWarnings("unchecked")
    void solve() throws Exception
    {

        for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int n = hp.nextInt();
            int k = hp.nextInt();
            int[] arr = new int[n + 1];
            int[] pos = new int[n + 1];
            int[] start = new int[n + 1];
            HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
            hm.put(0, new ArrayList<Integer>());
            for(int i = 1;i <= n; i++){
                hm.put(i, new ArrayList<Integer>());
                pos[i] = hp.nextInt();
            }
            for(int i = 1;i <= n; i++)
                arr[pos[i]] = i;

            for(int i = 0; i < k; i++)
            {
                int x = hp.nextInt();
                int y = hp.nextInt();
                hm.get(x).add(y);
                hm.get(y).add(x);
            }
            int[] ref = new int[n + 1];
            for(int i = 1; i <= n; i++){
                ref[i] = i;
                start[i] = i;
            }//hp.println("\n"+ Arrays.toString(pos));
            uv = new HashMap<>();

            for(int i = 0; i <= n; i++)
            {
                uv.put(i, new HashSet<Integer>());
            }

            for(int i = 1; i <= n; i++)
            {
                for(int j = 1; j <= n; j++)
                {
                    if(checkPath(i, j, hm))
                    {
                        uv.get(i).add(j);
                        //uv.get(j).add(i);
                    }
                }
                //hp.println(uv.get(i));
            }

            int min = Integer.MAX_VALUE;
            ///*
            for(int i = 0; i < (1 << n); i++)
            {
                String bits = getBits(i, n);
                int[] prr = new int[n + 1];
                for(int j = 1; j <= n; j++){
                    prr[j] = arr[j];
                    start[j] = j;
                    ref[j] = j;
                }
                //hp.println(i);
                //hp.println(Arrays.toString(start));
                int count = process(bits, prr, start, ref);
                //
                count += countSwaps(start, arr, n);
                //hp.println(count);
                if(count < min)min  = count;
                //hp.println();
            } //*/

            //int[] arr2 = {0, 2, 1, 3, 11, 5, 7, 6, 8, 9, 10, 4, 12};
            //int[] req = {0, 8, 9, 6, 7, 5, 11, 1, 4, 3, 12, 2, 10};
            //hp.println(countSwaps(arr2, req, 12));

            hp.println(min );

        }
        hp.flush();
    }

    int process(String bits, int[] arr, int[] start, int[] pos)throws Exception
    {
        int n = arr.length - 1;

        hp.println(Arrays.toString(arr));
        hp.println(Arrays.toString(start));

        hp.println(bits);
        int count = 0;
        for(int i = 1; i <= n; i++)
        {
            int x = i;
            int y = pos[arr[i]];
            //hp.println(x + " " + y);

            if(bits.charAt(i) == '1')
            {
                if(uv.get(x).contains(y))
                {
                    swap(start, x, y);
                    pos[start[x]] = x;
                    pos[start[y]] = y;
                    /*
                    hp.println("needed     " + Arrays.toString(arr));
                    hp.println("staet      " +Arrays.toString(start));
                    hp.println("positions  " + Arrays.toString(pos));
                    */
                }
                else
                {
                    swap(start, x, y);
                    pos[start[x]] = x;
                    pos[start[y]] = y;
                    count++;
                }
            }

        }
        hp.println(Arrays.toString(start));
        return count;
    }

    String getBits(int n, int k)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i <= k; i++)
        {
            sb.insert(0, (n & 1));
            n >>= 1;
        }
        return sb.toString();
    }




    int countSwaps(int[] arr, int[] arr2, int n)
    {
        int swaps = 0;
        boolean[] visited = new boolean[n + 1];

        HashMap<Integer, Integer> hm = new HashMap<>();
        for(int i = 1; i <= n; i++)
            hm.put(arr[i], arr2[i]);
        for(int i = 1; i <= n; i++)
        {
            int j = arr[i], cycle = 0;
            while(!visited[j])
            {
                //hp.println("here");
                visited[j] = true;
                j = hm.get(j);
                cycle++;
            }
            //hp.println(cycle);
            if(cycle != 0)
                swaps += (cycle - 1);
        }
        return swaps;
    }

    void swap(int[] arr, int i, int j)throws Exception
    {
        //hp.println("beforeswap  = " + Arrays.toString(arr));
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        //hp.println("after swap  = " + Arrays.toString(arr));
    }


    boolean checkPath(int u, int v, HashMap<Integer, ArrayList<Integer>> hm)
    {
        Queue<Integer> queue = new LinkedList<>();
        if(u == v)return true;
        boolean[] visited = new boolean[(hm.size())];
        visited[u] = true;
        queue.offer(u);
        while(!queue.isEmpty())
        {
            u = queue.poll();
            ArrayList<Integer> li = hm.get(u);
            for(int i = 0; i < li.size(); i++)
            {
                if(li.get(i) == v)return true;
                if(!visited[li.get(i)])
                {
                    visited[li.get(i)] = true;
                    queue.offer(li.get(i));
                }
            }
        }
        return false;
    }

    final Helper hp;
    final int MAXN = 1000_006;
    final long MOD = (long) 1e9 + 7;

    Solver() {
        hp = new Helper(MOD, MAXN);
        //hp.initIO(System.in, System.out);
        hp.initIO("../tests/SortingVases.txt", "../tests/SortingVasesOut.txt");
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
