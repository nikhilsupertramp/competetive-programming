import java.util.*;
import java.io.*;

public class test
{
    public static void main(String[] args)throws Exception
    {
        File file = new File("./SampleInput.txt");
        if(!file.exists())
            file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        try{
            Scanner sc = new Scanner(System.in);

            int tc = sc.nextInt();
            bw.write(tc + "\n");
            for(; tc > 0; tc--)
            {

                int n = getRandomNumber(5, 8);
                bw.write(n + " ");
                int k = getRandomNumber(1, (n * n));
                bw.write(k + "\n");
                for(int i = 0; i < n ; i++)
                {
                    int element =  getRandomNumber(0 , (10000));
                    bw.write(element + " ");
                }
                bw.write("\n");
                for(int i = 0; i < n ; i++)
                {
                    int element =  getRandomNumber(10000 , (10000));
                    bw.write(element + " ");
                }
                bw.write("\n");
            }


        }
        catch(IOException exception){
            System.out.println("Notdone");
        }

        finally
    	{
    	   try{
    	      if(bw!=null){
    		            bw.close();
                        System.out.println("done");
            }
    	   }catch(Exception ex){
    	       System.out.println("Error in closing the BufferedWriter"+ex);
    	    }
    	}



    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
