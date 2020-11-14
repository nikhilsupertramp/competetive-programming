import java.util.*;

public class MarsLandVaccine{
    public static void main(String[] args){
        Scanner hp = new Scanner(System.in);

        long n = hp.nextLong();
        long k = hp.nextLong();
        long x = hp.nextLong();
        long y = hp.nextLong();

        long low = 0, high = 2 * n;

        while(low < high){
            long mid = (low + high) / 2;
            if(isValidTime(mid, n, k, x, y)){
                high = mid;
            }
            else{
                low = mid + 1;
            }

        }
        System.out.println(low);
    }

    public static boolean isValidTime(long days, long N, long k, long x, long y){

        long top = Math.max(0, (days - (x - 1)));
        long lft = Math.max(0, (days - (y - 1)));
        long rgt = Math.max(0, (y - (N - days)));
        long btm = Math.max(0, (x - (N - days)));

        long topLft = Math.max(0, (1 + days) - (x + y));
        long topRgt = Math.max(0, (1 + days) - (x + N - y + 1));
        long botLft = Math.max(0, (1 + days) - (y + N - x + 1));
        long botRgt = Math.max(0, (1 + days) - (N - x + 1 + N - y + 1));

        long answer = ((days * days) + ((days + 1) * (days + 1)));

        answer -= ((top * top) + (btm * btm) + (lft * lft) + (rgt * rgt));

        answer += (topLft * (topLft + 1)) / 2;
        answer += (topRgt * (topRgt + 1)) / 2;
        answer += (botLft * (botLft + 1)) / 2;
        answer += (botRgt * (botRgt + 1)) / 2;

        return (answer >= k);
    }
}
