/* @nikhil_supertramp */

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;


class SortingVasesUpdated
{
    public static void main(String[] args)throws Exception
    {
        new Solver().solve();
    }
}

//  cd competetive-programming/src/Codechef
//  javac -d ../../classes SortingVasesUpdated.java
//  java SortingVasesUpdated
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
            int[] destination = new int[n + 1];
            int[] arr = new int[n + 1];
            int[] start = new int[n + 1];
            int[] pos = new int[n + 1];
            HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
            hm.put(0, new ArrayList<Integer>());
            for(int i = 1;i <= n; i++){
                hm.put(i, new ArrayList<Integer>());
                arr[i] = hp.nextInt();
                start[i] = i;
            }
            populate(pos, start);
            for(int i = 1;i <= n; i++)
                destination[arr[i]] = i;

            for(int i = 0; i < k; i++)
            {
                int x = hp.nextInt();
                int y = hp.nextInt();
                hm.get(x).add(y);
                hm.get(y).add(x);
            }

            int count = 0, min = 0;
            ArrayList<ArrayList<Integer>> list;
            list = getAllCycles(arr);
            for(ArrayList<Integer> li : list)hp.println(li);

            for(int i = 0; i <list.size(); i++)
            {
                count += check(list, arr)
                
            }


            hp.println(count + " x\n");
        }
        hp.flush();
    }



    ArrayList<ArrayList<Integer>>  getAllCycles(int arr[])throws Exception
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int n = arr.length;
        HashMap<Integer, Integer> hm = new HashMap<>();
        for(int i = 0; i < n; i++)hm.put(arr[i], i);
        boolean[] visited = new boolean[n + 1];

        for(int i = 1; i < n; i++)
        {
            int j = arr[i];
            ArrayList<Integer> cycle = new ArrayList<>();
            while(!visited[j])
            {
                visited[j] = true;
                cycle.add(j);
                j = hm.get(j);
            }
            if(cycle.size() > 1)
            {
                list.add(new ArrayList<Integer>(cycle));
                //hp.println(cycle);
            }
            cycle.clear();
        }
        return list;
    }

    void populate(int[] pos, int start[])
    {
        for(int i = 0; i < pos.length; i++)
            pos[start[i]] = i;
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
