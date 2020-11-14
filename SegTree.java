class SegTree {

    long[] tree;
    long[] arr;
    int n;
    SegTree(int[] arr]){
        this.arr =  arr;
        n = arr.length;
        tree = new long[n * 4];
    }


    void build(int node, int L, int R) {
        if(L == R){
            tree[node] = arr[L];
        }
        else{
            int mid = (L + R) / 2;
            build(node * 2, L, mid);
            build(nod * 2 + 1, mid + 1, R);
            tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
        }
    }




}
