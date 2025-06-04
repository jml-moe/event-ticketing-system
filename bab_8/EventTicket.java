/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_8;

import bab_7.*;


/**
 *
 * @author jamil
 */
import java.util.Scanner;

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
    
    // Method dengan exception handling untuk input menggunakan Scanner
    public void inputData(Scanner scanner) {
        try {
            System.out.print("Masukkan nama event: ");
            this.namaEvent = scanner.nextLine();
            
            System.out.print("Masukkan lokasi: ");
            this.lokasi = scanner.nextLine();
            
            System.out.print("Masukkan tanggal: ");
            this.tanggal = scanner.nextLine();
            
            System.out.print("Masukkan harga: ");
            this.harga = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Masukkan jumlah tiket: ");
            this.jumlahTiket = Integer.parseInt(scanner.nextLine());
            
            System.out.println("Data berhasil diinput!");
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Input harga dan jumlah tiket harus berupa angka!");
        }
    }
    
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
    
    // Main method untuk testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Membuat concrete class karena EventTicket abstract
        EventTicket tiket = new EventTicket("", "", "", 0, 0) {
            @Override
            public String cekStatusTiket() {
                if (getJumlahTiket() == 0) {
                    return "SOLD OUT";
                } else if (getJumlahTiket() < 10) {
                    return "HAMPIR HABIS";
                } else {
                    return "TERSEDIA";
                }
            }
        };
        
        System.out.println("=== INPUT DATA EVENT TICKET ===");
        tiket.inputData(scanner);
        
        System.out.println("\n=== DETAIL EVENT ===");
        System.out.println(tiket.cetakDetailEvent());
        System.out.println("Status Tiket: " + tiket.cekStatusTiket());
        
        scanner.close();
    }
}

