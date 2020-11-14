
import java.util.*;


class MaxLengthTree{
    static Scanner sc;
    static HashMap<Integer, HashSet<Integer>> hm;

    public static void main(String[] args){
        sc = new Scanner(System.in);
        hm = new HashMap<>();
        int n = sc.nextInt();
        createHashMap(n);
        getInputs(n);
        int answer = bruteforce(n);
        System.out.println(answer);
    }

    static int bruteforce(int n){
        int sum = 0;
        for(int i = 0; i < n - 1; i++){
            Integer key = (Integer)hm.keySet().toArray()[0];
            sum += getMaxPath(key, n);
        }
        return sum;
    }

    static int getMaxPath(int key, int n){
        Pair primarylongestNodePair = getLongestNodeFrom(key, n);
        int primarylongestNode = primarylongestNodePair.getY();
        Pair secondaryLongestNodepair = getLongestNodeFrom(primarylongestNode, n);
        int longestDistance = secondaryLongestNodepair.getX();
        int longestNode = secondaryLongestNodepair.getY();

        Integer v = (Integer)hm.get(longestNode).toArray()[0];

        hm.get(v).remove(longestNode);
        hm.remove(longestNode);
        // System.out.println("from " + primarylongestNode +
        //                     " to " + longestNode + " distance is "
        //                     + longestDistance);
        // System.out.println(hm.keySet());
        return longestDistance;
    }

    static Pair getLongestNodeFrom(int key, int n){
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        boolean visited[] = new boolean[n + 1];
        q1.offer(key);

        int lastNode = -1;
        int distance = 0;

        while(!q1.isEmpty()){
            int last = q1.poll();
            visited[last] = true;
            for(int i : hm.get(last)){
                if(!visited[i]){
                    q2.offer(i);
                }
            }
            if(q1.isEmpty()){
                distance++;
                lastNode = last;
                q1 = q2;
                q2 = new LinkedList<>();
            }
        }
        return new Pair(distance - 1, lastNode);
    }

    static void createHashMap(int n){
        for(int i = 1; i <= n; i++){
            hm.put(i, new HashSet<>());
        }
    }

    static void getInputs(int n){
        for(int i = 1; i < n; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();
            hm.get(u).add(v);
            hm.get(v).add(u);
        }
    }
}

class Pair{
    private int x, y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
