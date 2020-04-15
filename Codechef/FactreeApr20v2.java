/* @nikhil_supertramp */

import java.io.*;
import java.math.*;
import java.util.*;
//import java.math.*;
import static java.lang.Math.*;

class FactreeApr20v2
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

    int n, depth[], parent[][], LOG, subtree_size[];

    int chain[], chainHead[], position[], chainID = 0, pos = 0;//HLD stuff

    int arr[];Combo tree[];//Segement tree stuff

    void solve() throws Exception
    {
        long startTime = System.nanoTime();
        for(int tc = hp.nextInt(); tc > 0; tc--)
        {
            chainID = 0;
            pos = 0;
            n = hp.nextInt();
            LOG = (int)(log(n) / log(2));

            HashMap<Integer, ArrayList<Integer>> adj = new HashMap<>();

            createAdjList(n, adj);

            depth = new int[n + 1];//lca thing
            parent = new int[n + 1][LOG + 1];//lca thing
            subtree_size = new int[n + 1];//lca and HLD

            chainHead = new int[n + 1];
            position = new int[n + 1];
            chain = new int[n + 1];

            int size = (int) pow(2, ceil(log(n) / log(2)) + 1);
            arr = new int[n + 1];
            tree = new Combo[size];

            int[] values = new int[n + 1];
            for(int i = 1; i <= n; i++)
            {
                values[i] = hp.nextInt();
            }

            dfs(1, 0, 1, adj);
            chainHead[0] = 1;

            HLD(1, 0, adj, values);

            build(1, 1 , n);
            int q = hp.nextInt();

            for(int i = 0; i < q; i++)
            {
                int u = hp.nextInt();
                int v = hp.nextInt();
                HashMap<Integer, Integer> ans = new HashMap<>();
                int lca = LCA(u, v);
                ans= merge(ans, queryUp(u, lca));
                //hp.println(u + " to " + lca + " = " +ans);
                ans = merge(ans, queryUp(v, lca));
                //hp.println(v + " to " + lca + " = " +queryUp(v, lca));
                HashMap<Integer, Integer> lca_map = new HashMap<>();
                reduce(values[lca], lca_map);
                //hp.println(lca_map);
                ans = merge(ans, lca_map);
                //hp.println(u + " to " + v + " = " +ans);
                hp.println(mul(ans));


            }

            /*
            for(int i = 0; i < size; i++)
            {
                if(tree[i] == null)hp.println(i + " null");
                else
                hp.println(i + " " + tree[i].toString());
            }

            hp.println("arr " + Arrays.toString(arr));
            hp.println("positions " + Arrays.toString(position));
            hp.println("chain " + Arrays.toString(chain));
            hp.println("chainHead " + Arrays.toString(chainHead));
            */

        }
        long endTime = System.nanoTime();
        hp.flush();
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

    void dfs(int v, int p, int d, HashMap<Integer, ArrayList<Integer>> adj)
    {
        depth[v] = d;
        parent[v][0] = p;
        for(int i = 1; i <= LOG; ++i)
        {
            if(parent[v][i - 1] != 0)
            {
                parent[v][i] = parent[parent[v][i - 1]][i - 1];
            }
        }
        subtree_size[v] += 1;
        for(int c : adj.get(v))
        {
            if(c  != p)
            {
                dfs(c, v, d + 1, adj);
                subtree_size[v] += subtree_size[c];
            }
        }
    }

    int LCA(int u, int v)
    {
        if(depth[u] > depth[v])
        {
            int temp = u;
            u = v;
            v = temp;
        }
        int diff = depth[v] - depth[u];
        for(int i = LOG; i >= 0; --i)
        {
            if((diff & (1 << i)) != 0)
                v = parent[v][i];
        }
        if(u == v)return u;

        for(int i = LOG; i >= 0; --i)
        {
            if(parent[u][i] != parent[v][i])
            {
                v = parent[v][i];
                u = parent[u][i];
            }
        }
        return parent[u][0];
    }

    HashMap<Integer, Integer> queryUp(int from, int to)throws Exception
    {
        int curr = from;
        HashMap<Integer, Integer> ans = new HashMap<>();
        while(chain[curr] != chain[to]) {
            ans = merge(ans, query(position[chainHead[chain[curr]]], position[curr]));
            curr = parent[chainHead[chain[curr]]][0];
        }
        ans = merge(ans, query(position[to] + 1, position[curr]));
        //hp.println("at quuery up " + ans);
        return ans;
    }

    void HLD(int v, int par,  HashMap<Integer, ArrayList<Integer>> adj, int[] values)
    {
        int heavyChild = -1, heavySize = 0;
        chain[v] = chainID;
        pos++;
        arr[pos] = values[v];
        position[v] = pos;
        for(int c : adj.get(v))
        {
            if(c != par)
            {
                if(subtree_size[c] > heavySize)
                {
                    heavySize = subtree_size[c];
                    heavyChild = c;
                }
            }
        }
        if(heavyChild != -1)
        {
            HLD(heavyChild, v, adj, values);
        }

        for(int c : adj.get(v))
        {
            if(c != par && c != heavyChild)
            {
                chainID++;
                chainHead[chainID] = c;
                HLD(c, v, adj, values);
            }
        }
    }

    void build(int treein,int low,int high)
    {
        if(low==high)
        {
            HashMap<Integer, Integer> hm = new HashMap<>();
            int value = arr[low];
            reduce(value, hm);
            tree[treein] = new Combo(value, hm);
        }
        else
        {
            int mid = (low+high)>>1;
            build(2*treein,low,mid);
            build(2*treein+1,mid+1,high);
            HashMap<Integer, Integer> hm = merge(tree[2*treein].hm, tree[2*treein + 1].hm);

            tree[treein] = new Combo(-1, hm);
            //tree[2*treein] + tree[2*treein+1];
        }
    }

    HashMap<Integer, Integer> query(int treein,int low,int high,int l,int r)
    {
        if(l<=low && high<=r)
            return tree[treein].hm;
        if(low>r || high<l)
            return new HashMap<Integer, Integer>();
        int mid = (low+high)>>1;
        return merge(query(2*treein,low,mid,l,r),query(2*treein+1,mid+1,high,l,r));
    }

    HashMap<Integer, Integer> query(int l,int r)
    {
        if(l>r) {
            return new HashMap<Integer, Integer>();
        }

        return query(1,1,n,l,r);
    }

    HashMap<Integer, Integer> merge(HashMap<Integer, Integer> hm1, HashMap<Integer, Integer> hm2)
    {
        @SuppressWarnings("unchecked")
        HashMap<Integer, Integer> hm = (HashMap<Integer, Integer>)hm1.clone();
        for(int key : hm2.keySet())
        {
            if(hm.containsKey(key))
            {
                hm.put(key, hm.get(key) + hm2.get(key));
            }
            else
            {
                hm.put(key, hm2.get(key));
            }
        }
        return hm;
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

}

class Combo
{
    int value;
    HashMap<Integer, Integer> hm;
    Combo( int value, HashMap<Integer, Integer> hm)
    {
        this.hm = hm;
        this.value = value;
    }
    public String toString()
    {
        return value + hm.toString();
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

/*
2
10
1 5
1 10
1 2
2 9
2 3
2 4
3 8
3 7
3 6
15 24 1 3 13 20 12 20 40 20
11
5 9
3 8
6 7
2 7
4 10
7 4
2 4
10 6
5 2
7 8
7 7

7
1 2
1 3
1 4
3 5
3 6
3 7
4 2 3 5 5 2 3
3
2 5
1 7
4 5

126
6
20
18
72
24
12
96
48
20
6
16
9
18
*/
