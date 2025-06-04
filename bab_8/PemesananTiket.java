/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_8;

import bab_7.*;


/**
 *
 * @author jamil
 */
import java.util.Scanner;

public class PemesananTiket extends EventTicket {
    // Constructor
    public PemesananTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
    }
    
    // Override cekStatusTiket
    @Override
    public String cekStatusTiket() {
        return getJumlahTiket() > 0 ? "Tiket tersedia." : "Tiket habis.";
    }
    
    // Method untuk pemesanan tiket dengan exception handling
    public void pesanTiket(Scanner scanner) {
        try {
            System.out.print("Masukkan jumlah tiket yang ingin dipesan: ");
            int jumlahPesan = Integer.parseInt(scanner.nextLine());
            
            if (jumlahPesan <= 0) {
                System.out.println("Jumlah tiket harus lebih dari 0!");
                return;
            }
            
            if (jumlahPesan > getJumlahTiket()) {
                System.out.println("Tiket tidak mencukupi! Tersisa: " + getJumlahTiket() + " tiket");
                return;
            }
            
            // Kurangi jumlah tiket
            setJumlahTiket(getJumlahTiket() - jumlahPesan);
            double totalHarga = getHarga() * jumlahPesan;
            
            System.out.println("Pemesanan berhasil!");
            System.out.println("Jumlah tiket dipesan: " + jumlahPesan);
            System.out.println("Total harga: " + totalHarga);
            System.out.println("Sisa tiket: " + getJumlahTiket());
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Jumlah tiket harus berupa angka!");
        }
    }
    
    // Main method untuk testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SISTEM PEMESANAN TIKET ===");
        
        // Buat objek pemesanan tiket
        PemesananTiket tiket = new PemesananTiket("Konser Rock", "Jakarta", "2024-12-25", 150000, 50);
        
        System.out.println("\n=== DETAIL EVENT ===");
        System.out.println(tiket.cetakDetailEvent());
        System.out.println("Status: " + tiket.cekStatusTiket());
        
        // Proses pemesanan
        System.out.println("\n=== PEMESANAN TIKET ===");
        tiket.pesanTiket(scanner);
        
        System.out.println("\n=== STATUS SETELAH PEMESANAN ===");
        System.out.println("Status: " + tiket.cekStatusTiket());
        System.out.println("Sisa tiket: " + tiket.getJumlahTiket());
        
        scanner.close();
    }
}   




