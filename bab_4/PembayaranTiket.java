/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_4;

import bab_3.*;

/**
 *
 * @author jamil
 */
public class PembayaranTiket extends PemesananTiket{
    private String metodePembayaran;
    private int jumlahPesan;

    public PembayaranTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket,
                           int jumlahPesan, String metodePembayaran) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
        this.jumlahPesan = jumlahPesan;
        this.metodePembayaran = metodePembayaran;
    }

    public void prosesPembayaran(String namaPemesan, String email, String noHP, javax.swing.JTextArea outputArea) {
        outputArea.append("\n=== Proses Pemesanan & Pembayaran ===\n");

        if (getJumlahTiket() >= jumlahPesan) {
            setJumlahTiket(getJumlahTiket() - jumlahPesan);
            double totalBayar = getHarga() * jumlahPesan;

            // Info Pemesanan
            outputArea.append("Nama Event    : " + getNamaEvent() + "\n");
            outputArea.append("Nama Pemesan  : " + namaPemesan + "\n");
            outputArea.append("Email         : " + email + "\n");
            outputArea.append("No. HP        : " + noHP + "\n");
            outputArea.append("Jumlah Tiket  : " + jumlahPesan + "\n");
            outputArea.append("Total Bayar   : Rp" + totalBayar + "\n");
            outputArea.append("Metode Bayar  : " + metodePembayaran + "\n");

            // Validasi metode pembayaran
            if (metodePembayaran.equalsIgnoreCase("Transfer Bank") ||
                metodePembayaran.equalsIgnoreCase("E-Wallet") ||
                metodePembayaran.equalsIgnoreCase("Kartu Kredit")) {
                outputArea.append("Pembayaran berhasil diproses.\n");
                outputArea.append("E-ticket akan dikirim ke email.\n");
            } else {
                outputArea.append("Metode pembayaran tidak dikenal.\n");
            }
        } else {
            outputArea.append("Maaf, jumlah tiket tidak mencukupi.\n");
        }
    }
}
