import java.io.*;
import java.util.*;

public class QuadripletSum {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++)
        {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        System.out.println(getCountBrute(arr, n, k));
        int ans = getCount(arr, n, k);

        System.out.println(ans);
    }

    public static int getCountBrute(int [] arr, int n, int sum)
    {
        int count = 0;
        for(int i = 0; i < n - 3; i++)
        {
            for(int j = i + 1; j < n - 1; j++)
            {
                for(int k = j + 1; j < n - 1; j++)
                {
                    for(int l = k +  1; k < n; k++)
                    {
                        if(arr[i] + arr[j] + arr[k] + arr[l] == sum)
                        {
                            System.out.println("Quadruplet Found ("
                                        + arr[i] + ", " + arr[j] + ", "
                                        + arr[k] + ", " + arr[l] + ")");
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public static int getCount(int[] arr, int n, int sum)
    {
        HashMap<Integer, List<Pair>> hm = new HashMap<>();
        HashSet<String> hs = new HashSet<>();
        int count = 0;
        for(int i = 0; i < n - 1; i++)
        {
            for(int j = i + 1; j < n; j++)
            {
                int val = sum - (arr[i] + arr[j]);
                if(hm.containsKey(val))
                {
                    for(Pair p : hm.get(val))
                    {
                        int x = p.x;
                        int y = p.y;
                        if ((x != i && x != j) && (y != i && y != j))
                        {
                            System.out.println("Quadruplet Found ("
                                        + arr[i] + ", " + arr[j] + ", "
                                        + arr[x] + ", " + arr[y] + ")");

                            int[] indices = {i , j , x, y};
                            Arrays.sort(indices);
                            if(hs.add(Arrays.toString(indices)))
                                count++;
                        }
                    }
                }
                hm.putIfAbsent(arr[i] + arr[j], new ArrayList<>());
                hm.get(arr[i] + arr[j]).add(new Pair(i, j));
            }
        }
        return count;
    }
}
class Pair {
    public int x, y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
