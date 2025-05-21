import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalTime;

class DuLieuChung {
    private final List<Integer> danhSach = new ArrayList<>();
    private int n ;
    
    public DuLieuChung(int n) {
        this.n = n;
    }
    
    public synchronized void ghiDuLieu(int giaTri, String tenLuong) {
        while (danhSach.size() >= n) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        danhSach.add(giaTri);
        System.out.println(tenLuong + ": " + giaTri + " - " + LocalTime.now());
        notifyAll(); 
    }

    public synchronized int layDuLieu(String tenLuong) {
        while (danhSach.isEmpty()) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int giaTri = danhSach.remove(0); 
        int ketQua = xuLyGiaTri(giaTri);
        System.out.println(tenLuong + ": " + giaTri + " - Kết quả: " + ketQua + " - " + LocalTime.now());
        notifyAll(); 
        return giaTri;
    }

    private int xuLyGiaTri(int giaTri) {
        return giaTri * 2;
    }
}

class LuongSinhDuLieu extends Thread {
    private DuLieuChung duLieuChung;
    private String tenLuong;

    public LuongSinhDuLieu(DuLieuChung dlc, int index) {
        this.duLieuChung = dlc;
        this.tenLuong = "P" + index;
    }

    public void run() {
        while (true) {
            int giaTri = ThreadLocalRandom.current().nextInt(1, 100);
            duLieuChung.ghiDuLieu(giaTri, tenLuong);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class LuongXuLyDuLieu extends Thread {
    private final DuLieuChung duLieuChung;
    private final String tenLuong;

    public LuongXuLyDuLieu(DuLieuChung dlc, int index) {
        this.duLieuChung = dlc;
        this.tenLuong = "C" + index;
    }

    public void run() {
        while (true) {
            duLieuChung.layDuLieu(tenLuong);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Nhap n: ");
		int n = scanner.nextInt();
        DuLieuChung duLieuChung = new DuLieuChung(n);
        System.out.print("Nhập k (số luồng sinh dữ liệu): ");
        int k = scanner.nextInt();

        System.out.print("Nhập h (số luồng xử lý dữ liệu): ");
        int h = scanner.nextInt();

        for (int i = 1; i <= k; i++) {
            new LuongSinhDuLieu(duLieuChung, i).start();
        }

        for (int i = 1; i <= h; i++) {
            new LuongXuLyDuLieu(duLieuChung, i).start();
        }
    }
}
