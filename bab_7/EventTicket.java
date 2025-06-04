/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_7;


/**
 *
 * @author jamil
 */
public abstract class EventTicket {
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

    // Getter and Setter
    public String getNamaEvent() { return namaEvent; }
    public void setNamaEvent(String namaEvent) { this.namaEvent = namaEvent; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getJumlahTiket() { return jumlahTiket; }
    public void setJumlahTiket(int jumlahTiket) { this.jumlahTiket = jumlahTiket; }

    // Abstract Method
    public abstract String cekStatusTiket();

    // Method untuk mencetak detail event
    public String cetakDetailEvent() {
        return "Event: " + namaEvent + "\n" +
               "Lokasi: " + lokasi + "\n" +
               "Tanggal: " + tanggal + "\n" +
               "Harga: " + harga + "\n" +
               "Jumlah Tiket: " + jumlahTiket;
    }
}

