/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_10;

import bab_7.*;


/**
 *
 * @author jamil
 */
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

    // Overriding cekStatusTiket
    @Override
    public String cekStatusTiket() {
        return getJumlahTiket() >= jumlahPesan ? "Tiket cukup untuk dipesan." : "Tiket tidak cukup.";
    }

    // Proses Pembayaran (Overloading)
    public double prosesPembayaran() {
        return getHarga() * jumlahPesan;
    }
}
