/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_9;

import bab_7.*;


/**
 *
 * @author jamil
 */

public class PemesananTiket extends EventTicket implements Bookable{
    private String namaPemesan;
    private String emailPemesan;
    private String nomorHP;
    private int tiketDipesan;
    private double totalBayar;

    // Constructor
    public PemesananTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
        this.tiketDipesan = 0;
        this.totalBayar = 0.0;
    }

    // Constructor dengan data pemesan
    public PemesananTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket,
                         String namaPemesan, String emailPemesan, String nomorHP) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
        this.namaPemesan = namaPemesan;
        this.emailPemesan = emailPemesan;
        this.nomorHP = nomorHP;
        this.tiketDipesan = 0;
        this.totalBayar = 0.0;
    }

    // Getter dan Setter untuk data pemesan
    public String getNamaPemesan() { return namaPemesan; }
    public void setNamaPemesan(String namaPemesan) { this.namaPemesan = namaPemesan; }
    public String getEmailPemesan() { return emailPemesan; }
    public void setEmailPemesan(String emailPemesan) { this.emailPemesan = emailPemesan; }
    public String getNomorHP() { return nomorHP; }
    public void setNomorHP(String nomorHP) { this.nomorHP = nomorHP; }
    public int getTiketDipesan() { return tiketDipesan; }
    public double getTotalBayar() { return totalBayar; }

    // Override cekStatusTiket
    @Override
    public String cekStatusTiket() {
        return getJumlahTiket() > 0 ? "Tiket tersedia." : "Tiket habis.";
    }

    // Implementasi interface Bookable
    @Override
    public boolean bookTiket(int jumlah) {
        if (jumlah <= getJumlahTiket() && jumlah > 0) {
            this.tiketDipesan = jumlah;
            this.totalBayar = hitungTotalHarga(jumlah);
            // Kurangi stok tiket
            setJumlahTiket(getJumlahTiket() - jumlah);
            return true;
        }
        return false;
    }

    @Override
    public double hitungTotalHarga(int jumlah) {
        return jumlah * getHarga();
    }

    @Override
    public String getInfoBooking() {
        if (tiketDipesan > 0) {
            return "=== INFORMASI PEMESANAN ===\n" +
                   "Nama Pemesan: " + namaPemesan + "\n" +
                   "Email: " + emailPemesan + "\n" +
                   "No. HP: " + nomorHP + "\n" +
                   "Event: " + getNamaEvent() + "\n" +
                   "Lokasi: " + getLokasi() + "\n" +
                   "Tanggal: " + getTanggal() + "\n" +
                   "Jumlah Tiket: " + tiketDipesan + "\n" +
                   "Harga per Tiket: Rp " + String.format("%.0f", getHarga()) + "\n" +
                   "Total Bayar: Rp " + String.format("%.0f", totalBayar) + "\n" +
                   "Status: Berhasil Dipesan";
        }
        return "Belum ada pemesanan.";
    }
}   




