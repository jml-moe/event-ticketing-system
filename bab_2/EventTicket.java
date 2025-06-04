/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_2;

/**
 *
 * @author jamil
 */
public class EventTicket {
    // Atribut (property) dari tiket event
    String namaEvent;
    String lokasi;
    String tanggal;
    double harga;
    int jumlahTiket;

    // Constructor untuk menginisialisasi atribut
    public EventTicket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket) {
        this.namaEvent = namaEvent;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.harga = harga;
        this.jumlahTiket = jumlahTiket;
    }

    // Method untuk menampilkan status tiket
    void cekStatusTiket() {
        if (jumlahTiket > 0) {
            System.out.println("Tiket masih tersedia.");
        } else {
            System.out.println("Tiket telah habis.");
        }
    }

    // Method untuk mencetak detail event
    void cetakDetailEvent() {
        System.out.println("Nama Event  : " + namaEvent);
        System.out.println("Lokasi      : " + lokasi);
        System.out.println("Tanggal     : " + tanggal);
        System.out.println("Harga Tiket : Rp" + harga);
        System.out.println("Jumlah Tiket: " + jumlahTiket);
    }
}
