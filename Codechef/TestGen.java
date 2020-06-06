import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;
class TestGen{
    public static void main(String[] args)throws Exception {
        Scanner sc = new Scanner(System.in);
        int tcs = sc.nextInt();
        Random random = new Random();
        String filename = "../tests/SampleProblemTestInputs.txt";
        BufferedWriter wr = new BufferedWriter(new FileWriter(filename));
        wr.write(tcs + "\n");
        for(int tc = 0; tc < tcs; tc++)
        {
            int n = rand(100000000, 1000000000);
            wr.write(n + "\n");
        }
        wr.close();
    }

    public static int rand(int min,int max)
	{
		if (min > max) {
			throw new IllegalArgumentException("Invalid range");
		}

		double rand = Math.random();
		return (int)(rand * ((max - min) + 1)) + min;
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
