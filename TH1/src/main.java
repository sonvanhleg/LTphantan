import java.util.Random;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;
public class main {
    static int[] A;

    public static void main(String[] args) throws InterruptedException {
    	Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập số phần tử của mảng N: ");
        int N = scanner.nextInt();

        System.out.print("Nhập số luồng k: ");
        int k = scanner.nextInt();

        A = new int[N]; 
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            A[i] = rand.nextInt(200) + 1; 
        }
        A[N-1] = 3;
        
        PrimeCounter[] threads = new PrimeCounter[k];

        int len = N / k;
        for (int i = 0; i < k; i++) {
            int start = i * len;
            int end = (i == k - 1) ? N : start + len; 
            threads[i] = new PrimeCounter("T" + (i + 1), start, end);
            threads[i].start();
        }

        int totalPrime = 0;
        for (PrimeCounter t : threads) {
            t.join();
            totalPrime += t.getCount();
        }
        

        System.out.println("Tổng số nguyên tố trong mảng là: " + totalPrime);
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    }

    static class PrimeCounter extends Thread {
        private int start, end;
        private int count = 0;
        

        public PrimeCounter(String name, int start, int end) {
            super(name);
            this.start = start;
            this.end = end;
        }

        public void run() {
            for (int i = start; i < end; i++) {
                if (isPrime(A[i])) {
                	count++;
                	LocalTime thoiGianHienTai = LocalTime.now();
                	System.out.println(getName() + ": Kết quả của " + getName() + " là: " + A[i] +
                            "; Thời gian: "+ thoiGianHienTai);
                }
            }
            //LocalTime thoiGianHienTai = LocalTime.now();
            //System.out.println(getName() + ": Kết quả của " + getName() + " là: " + count +
                               //"; Thời gian: "+ thoiGianHienTai);
        }

        public int getCount() {
            return count;
        }
	}
}
