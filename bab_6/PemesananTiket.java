/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_6;

import bab_5.*;
import bab_3.*;

/**
 *
 * @author jamil
 */
// Subclass dari EventTicket untuk tiket online
// Subclass: PemesananTiket (turunan dari EventTicket)
public class PemesananTiket extends EventTicket {

    public PemesananTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
    }

    // Implementasi cekStatusTiket
    @Override
    public void cekStatusTiket(int minimumTiket) {
        if (jumlahTiket >= minimumTiket) {
            System.out.println("Tiket tersedia.");
        } else {
            System.out.println("Tiket terbatas.");
        }
    }

    // Overloading - Menambahkan pesanTiket dengan parameter tambahan "alamat"
    void pesanTiket(String namaPemesan, String email, String noHP, String alamat, int jumlahPesan) {
        System.out.println("\n=== Proses Pemesanan ===");
        System.out.println("Alamat        : " + alamat);
        pesanTiket(namaPemesan, email, noHP, jumlahPesan);
    }

    // Proses pemesanan tiket
    void pesanTiket(String namaPemesan, String email, String noHP, int jumlahPesan) {
        System.out.println("\n=== Proses Pemesanan ===");
        if (getJumlahTiket() >= jumlahPesan) {
            setJumlahTiket(getJumlahTiket() - jumlahPesan);

            System.out.println("Tiket berhasil dipesan!");
            System.out.println("Nama Pemesan : " + namaPemesan);
            System.out.println("Email        : " + email);
            System.out.println("No. HP       : " + noHP);
            System.out.println("Jumlah Tiket : " + jumlahPesan);
            System.out.println("Total Bayar  : Rp" + (getHarga() * jumlahPesan));
            System.out.println("E-ticket akan dikirim ke email Anda.");
        } else {
            System.out.println("Maaf, jumlah tiket tidak mencukupi.");
        }
    }
}




