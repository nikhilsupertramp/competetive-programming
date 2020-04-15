/* @nikhil_supertramp */

import java.io.*;
import java.math.*;
import java.util.*;
import java.math.*;

class FactreeApr20
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

    Solver() {
        hp = new Helper(MOD, MAXN);
        hp.initIO(System.in, System.out);
    }

    int[] depth, parent;
    void solve() throws Exception
    {
        long startTime = System.nanoTime();
        for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            int n = hp.nextInt();
            HashMap<Integer, ArrayList<Integer>> adj = new HashMap<>();
            //if(n < 100 * 100)
            {
                depth = new int[n + 1];
                parent = new int[n + 1];
                createAdjList(n, adj);
                int[] arr = new int[n + 1];
                HashMap<Integer, Integer>[] hm_arr = new HashMap[n + 1];
                for(int i = 1; i <= n; i++)
                {
                    arr[i] = hp.nextInt();
                    hm_arr[i] = new HashMap<Integer, Integer>();
                }
                dfs(1, -1, 1, adj);
                int q = hp.nextInt();
                for(int i = 0; i < q; i++)
                {
                    int u = hp.nextInt();
                    int v = hp.nextInt();
                    ArrayList<Integer> li = getPath(u, v);
                    //hp.println(li);
                    int ans = getNoOfFactors(li, arr);
                    hp.println(ans);
                }

            }
        }
        long endTime = System.nanoTime();
        hp.println((endTime - startTime) / 1e6);
        hp.flush();
    }

    int getNoOfFactors(ArrayList<Integer> li, int[] arr)throws Exception
    {
        HashMap<Integer, Integer> hm = new HashMap<>();
        for(int i : li)
        {
            reduce(arr[i], hm);
            //hp.println(Arrays.toString(freq));
        }
        int ans = mul(hm);
        return ans;
    }

    int mul(HashMap<Integer, Integer> hm)
    {
        long ans = 1;
        for(int key : hm.keySet())
        {
            ans = (ans * (hm.get(key) + 1) ) % MOD;
        }
        return (int)ans;
    }

    void reduce(int n, HashMap<Integer, Integer> hm)
    {
        while(n % 2 == 0)
        {
            hm.put(2, hm.getOrDefault(2, 0) + 1);
            n /= 2;
        }
        for(int i = 3; i * i <= n; i += 2)
        {
            while(n % i == 0)
            {
                //hs.add(i);
                hm.put(i, hm.getOrDefault(i, 0) + 1);
                n /= i;
            }
        }
        if(n > 2)
            hm.put(n, hm.getOrDefault(n, 0) + 1);
    }

    void dfs(int v, int p, int d, HashMap<Integer, ArrayList<Integer>> adj)
    {
        depth[v] = d;
        parent[v] = p;
        for(int c : adj.get(v))
        {
            if(c  != p)
            {
                dfs(c, v, d + 1, adj);
            }
        }
    }

    ArrayList<Integer> getPath(int u, int v)throws Exception
    {
        ArrayList<Integer> li = new ArrayList<>();
        ArrayList<Integer> path1 = new ArrayList<>();
        ArrayList<Integer> path2 = new ArrayList<>();
        path1.add(u);
        path2.add(v);
        while(u != v)
        {
            if(depth[u] < depth[v])
            {
                path2.add(v = parent[v]);
            }
            else if(depth[u] > depth[v])
            {
                path1.add(u = parent[u]);
            }
            else if(depth[u] == depth[v])
            {
                path1.add(u = parent[u]);
                path2.add(v = parent[v]);
            }
        }
        //hp.println(path1 + " " + path2);
        path2.remove(path2.size() - 1);
        Collections.reverse(path2);
        for(int i : path2)
        {
            path1.add(i);
        }
        return path1;
    }

    void createAdjList(int n, HashMap<Integer, ArrayList<Integer>> hm)throws Exception
    {
        for(int i = 0; i < n - 1;i++)
        {
            int p = hp.nextInt();
            int q = hp.nextInt();
            if(!hm.containsKey(p))
            {
                hm.put(p, new ArrayList<Integer>());
            }
            if(!hm.containsKey(q))
            {
                hm.put(q, new ArrayList<Integer>());
            }
            hm.get(p).add(q);
            hm.get(q).add(p);
        }
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
