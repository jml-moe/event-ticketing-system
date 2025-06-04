/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_1;

/**
 *
 * @author jamil
 */
// Kelas EventTicket untuk merepresentasikan tiket sebuah event
public class EventTicket {
    // Atribut (property) dari tiket event
    String namaEvent;  // Nama event
    String lokasi;     // Lokasi event
    String tanggal;    // Tanggal event
    double harga;      // Harga tiket

    // Method untuk mengembalikan jumlah tiket tersedia
    int jumlahTiket(int jumlah) {
        return jumlah;  // Mengembalikan jumlah tiket
    }

    // Method untuk menampilkan status tiket tersedia
    void tiketTersedia() {
        System.out.println("Tiket masih tersedia.");
    }

    // Method untuk menampilkan status tiket habis
    void tiketHabis() {
        System.out.println("Tiket telah habis.");
    }

    // Method untuk mencetak detail event
    void cetakDetailEvent() {
        System.out.println("Nama Event  : " + namaEvent);
        System.out.println("Lokasi      : " + lokasi);
        System.out.println("Tanggal     : " + tanggal);
        System.out.println("Harga Tiket : Rp" + harga);
    }
}

