import java.util.*;
import java.math.*;

class SmallFactorials{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String answers[] = new String[101];
        preProcess(answers);
        int n = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        while(n --> 0){
            sb.append(answers[sc.nextInt()] + "\n");
        }
        System.out.println(sb);
    }
    static void preProcess(String[] arr){
        BigInteger answers = new BigInteger("1");
        for(int i = 1; i < arr.length; i++)
        {
            answers = answers.multiply(new BigInteger(i + ""));
            arr[i] = answers.toString();
        }
    }
}
