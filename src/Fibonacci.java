import java.util.Scanner;
/**
 * Created by Darshan and Mahesh on 2/24/2016.
 *
 * This program contains solution for first problem of finding fibanocci using linear and logarithmic methods
 */
public class Fibonacci {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long p = 999953;
        long start = System.currentTimeMillis();
        System.out.println(linearFibonacci(n, p));
        System.out.println("Total time taken for liner is : " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        System.out.println(logFibonacci(n,p));
        System.out.println("Total time taken for logorithmic:" + (System.currentTimeMillis()-start));
    }

    /*
    * Finds f_n%p in linear method using just 3 variables
    * */
    public static long linearFibonacci(long n, long p) {
        long[] fibanocci = new long[3];
        fibanocci[0] = 0;
        fibanocci[1] = 1;
        long i;
        //circularly store current and previous 2 in the series using mod 3
        for (i = 2; i <= n; i++) {
            fibanocci[(int)(i%3)] = (fibanocci[(int)((i+1)%3)] + fibanocci[(int)((i+2)%3)])%p;
        }
        return fibanocci[(int)((i-1) % 3)] % p;
    }
/*
* Find the f_n %p by logarithmic method.
*
* use power function to calculate A to the power n-1
* */
    public static long logFibonacci(long n, long p) {
        long[][] result = new long[2][2];
        result[0] = new long[]{1,1};
        result[1] = new long[]{1,0};

        result = power(result, n-1, p);
        return result[0][0]%p;
    }
    /*
    * computes the power of 2X2 matrix and returns mod p of the result
    * */
    private static long[][] power(long[][] x, long n, long p) {
        long[][] result = new long[2][2];
        result[0] = new long[]{1,1};
        result[1] = new long[]{1,0};
        if (n == 1) {
            return result;
        } else {
            long[][] half = power(x, n/2, p);
            result = multiply(half, half, p);
            if (n % 2 == 0) {
                return result;
            } else{
                return multiply(result, x, p);
            }

        }

    }

    private static long[][] multiply(long[][] result, long[][] x, long p) {
        long[][] product = new long[2][2];
        //Multiply 2 2X2 matrices
        for(int i =0; i < 2; i++) {
            product[i][0] = (result[i][0]*x[0][0]%p + result[i][1]*x[1][0]%p)%p;
            product[i][1] = (result[i][0]*x[0][1]%p + result[i][1]*x[1][1]%p)%p;
        }
        return product;
    }
}
