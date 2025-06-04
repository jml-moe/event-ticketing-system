/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_3;

import bab_2.*;

/**
 *
 * @author jamil
 */
public class EventTicket {
    // Atribut private (enkapsulasi)
    private String namaEvent;
    private String lokasi;
    private String tanggal;
    private double harga;
    private int jumlahTiket;

    // Constructor
    public EventTicket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket) {
        this.namaEvent = namaEvent;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.harga = harga;
        this.jumlahTiket = jumlahTiket;
    }

    // Getter dan Setter
    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    // Method untuk menampilkan status tiket
    public void cekStatusTiket() {
        if (jumlahTiket > 0) {
            System.out.println("Tiket masih tersedia.");
        } else {
            System.out.println("Tiket telah habis.");
        }
    }

    // Method untuk mencetak detail event
    public void cetakDetailEvent() {
        System.out.println("Nama Event  : " + namaEvent);
        System.out.println("Lokasi      : " + lokasi);
        System.out.println("Tanggal     : " + tanggal);
        System.out.println("Harga Tiket : Rp" + harga);
        System.out.println("Jumlah Tiket: " + jumlahTiket);
    }
}

