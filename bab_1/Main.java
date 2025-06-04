/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_1;

/**
 *
 * @author jamil
 */
// Kelas utama untuk menjalankan program
public class Main {
    public static void main(String[] args) {
        // Membuat objek event konser
        EventTicket konser = new EventTicket();
        
        // Mengisi atribut event konser
        konser.namaEvent = "Konser Musik";
        konser.lokasi = "Jakarta";
        konser.tanggal = "25 Maret 2025";
        konser.harga = 250000;

        // Menampilkan detail event konser
        System.out.println("=== Detail Event ===");
        konser.cetakDetailEvent();  // Memanggil method untuk mencetak detail event
        
        // Menampilkan jumlah tiket yang tersedia
        System.out.println("\nJumlah Tiket  : " + konser.jumlahTiket(100));

        // Menampilkan status tiket
        System.out.print("Status Tiket  : ");
        konser.tiketTersedia();  // Memanggil method untuk menampilkan status tiket
    }
}
