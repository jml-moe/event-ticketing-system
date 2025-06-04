/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_8;



/**
 *
 * @author jamil
 */
import java.util.Scanner;

public class PembayaranTiket extends PemesananTiket {
    private int jumlahPesan;
    private String metodePembayaran;
    
    // Constructor
    public PembayaranTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket, int jumlahPesan, String metodePembayaran) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
        this.jumlahPesan = jumlahPesan;
        this.metodePembayaran = metodePembayaran;
    }
    
    // Getter dan Setter
    public int getJumlahPesan() {
        return jumlahPesan;
    }
    
    public void setJumlahPesan(int jumlahPesan) {
        this.jumlahPesan = jumlahPesan;
    }
    
    public String getMetodePembayaran() {
        return metodePembayaran;
    }
    
    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
    
    // Override method cekStatusTiket
    @Override
    public String cekStatusTiket() {
        if (getJumlahTiket() >= jumlahPesan) {
            return "Tiket cukup untuk dipesan.";
        } else {
            return "Tiket tidak cukup.";
        }
    }
    
    // Hitung total pembayaran
    public double hitungTotal() {
        return getHarga() * jumlahPesan;
    }
    
    // Input data pembayaran dengan validasi sederhana
    public void inputPembayaran(Scanner scanner) {
        try {
            System.out.print("Masukkan jumlah tiket yang ingin dibeli: ");
            int jumlah = Integer.parseInt(scanner.nextLine());
            setJumlahPesan(jumlah);
            
            System.out.print("Masukkan metode pembayaran (Cash/Debit/Credit): ");
            String metode = scanner.nextLine();
            setMetodePembayaran(metode);
            
            System.out.println("Data pembayaran berhasil diinput!");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Jumlah tiket harus berupa angka!");
        }
    }
    
    // Proses pembayaran dengan penanganan error yang diperbaiki
    public void prosesPembayaran() {
        try {
            // Validasi input
            if (jumlahPesan <= 0) {
                throw new Exception("Jumlah pesanan harus lebih dari 0!");
            }
            
            if (getJumlahTiket() < jumlahPesan) {
                throw new Exception("Stok tiket tidak mencukupi!");
            }
            
            if (metodePembayaran == null || metodePembayaran.trim().isEmpty()) {
                throw new Exception("Metode pembayaran harus dipilih!");
            }
            
            // Hitung total
            double totalBayar = hitungTotal();
            
            // Update stok tiket
            setJumlahTiket(getJumlahTiket() - jumlahPesan);
            
            // Tampilkan hasil
            System.out.println("\n=== PEMBAYARAN BERHASIL ===");
            System.out.println("Event: " + getNamaEvent());
            System.out.println("Jumlah tiket: " + jumlahPesan);
            System.out.println("Total pembayaran: Rp " + totalBayar);
            System.out.println("Metode pembayaran: " + metodePembayaran);
            System.out.println("Sisa tiket: " + getJumlahTiket());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Main method untuk testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SISTEM PEMBAYARAN TIKET ===");
        
        // Buat objek pembayaran tiket
        PembayaranTiket pembayaran = new PembayaranTiket(
            "Festival Musik", "Bandung", "2024-12-31", 200000, 100, 0, ""
        );
        
        System.out.println("\n=== DETAIL EVENT ===");
        System.out.println(pembayaran.cetakDetailEvent());
        
        // Input data pembayaran
        System.out.println("\n=== INPUT PEMBAYARAN ===");
        pembayaran.inputPembayaran(scanner);
        
        // Cek status tiket
        System.out.println("\nStatus: " + pembayaran.cekStatusTiket());
        
        // Proses pembayaran
        System.out.println("\n=== PROSES PEMBAYARAN ===");
        pembayaran.prosesPembayaran();
        
        scanner.close();
    }
}
