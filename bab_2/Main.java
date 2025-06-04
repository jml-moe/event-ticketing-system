/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_2;

/**
 *
 * @author jamil
 */
public class Main {
    public static void main(String[] args) {
        // Membuat objek event konser menggunakan constructor
        EventTicket konser = new EventTicket("Konser Musik", "Jakarta", "25 Maret 2025", 250000, 100);

        // Menampilkan detail event konser
        System.out.println("=== Detail Event ===");
        konser.cetakDetailEvent();

        // Menampilkan status tiket
        System.out.print("Status Tiket  : ");
        konser.cekStatusTiket();
    }
}
