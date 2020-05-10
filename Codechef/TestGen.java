import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;
class TestGen{
    public static void main(String[] args)throws Exception {
        Scanner sc = new Scanner(System.in);
        int tcs = sc.nextInt();
        Random random = new Random();
        String filename = "../tests/ChefAndBitwiseProduct.txt";
        BufferedWriter wr = new BufferedWriter(new FileWriter(filename));
        wr.write(tcs + "\n");
        for(int tc = 0; tc < tcs; tc++)
        {
            long x = rand(0, 1000000000L);
            long y = rand(0, 1000000000L);
            long l = rand(0, 1000000000L);
            //long r = 2 * Math.max(x, y);
            long r = rand(l, 1000000000L);//2 * Math.max(x, y);

            wr.write(x + " " + y + " " + l + " "+ r + "\n");
        }
        wr.close();
    }

    public static long rand(long min, long max)
	{
		if (min > max) {
			throw new IllegalArgumentException("Invalid range");
		}

		double rand = Math.random();
		return (long)(rand * ((max - min) + 1)) + min;
	}

}
