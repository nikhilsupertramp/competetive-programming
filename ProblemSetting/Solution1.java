void solve() throws Exception
{
    for(int tc = hp.nextInt(); tc > 0; tc--)
    {
        int n = hp.nextInt();
        int[] a = hp.getIntArray(n);
        int total = 0;
        for(int i = 0; i < n; i++)
        {
            total += a[i];
        }
        int p = hp.nextInt();


        if(total <= p)
        {
            hp.println(n);
        }
        else
        {
            double res = 0.;
            double[][] dp = new double[n + 1][p + 1];
            dp[0][0] = 1.0;
            for (int i = 0; i < n; ++i) {
                int cur = a[i];
                for (int oldCount = n - 1; oldCount >= 0; --oldCount)
                {
                    for (int oldSum = 0; oldSum + cur <= p; ++oldSum)
                    {
                        dp[oldCount + 1][oldSum + cur] += dp[oldCount][oldSum] / (n - oldCount) * (oldCount + 1);
                    }
                }
                /*//uncomment this to check how DP is getting updated with each itreation
                hp.println("at iteration " + i + " : ");

                for(int k = 0; k <= n; k++)
                {
                    hp.println(Arrays.toString(dp[k]));
                }*/
            }

            for (int count = 1; count <= n; ++count)
            {
                for (int size = 0; size <= p; ++size)
                {
                    res += dp[count][size];
                }
            }
            /*//uncomment this to check how DP array looks like
            for(int i = 0; i <= n; i++)
            {
                hp.println(Arrays.toString(dp[i]));
            }
            */
            res = (double)Math.round((res * 100.0)) / 100;
            hp.println(res);
        }
    }
    hp.flush();
}
