/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bab_7;


/**
 *
 * @author jamil
 */

public class PemesananTiket extends EventTicket {
     // Constructor
    public PemesananTiket(String namaEvent, String lokasi, String tanggal, double harga, int jumlahTiket) {
        super(namaEvent, lokasi, tanggal, harga, jumlahTiket);
    }

    // Override cekStatusTiket
    @Override
    public String cekStatusTiket() {
        return getJumlahTiket() > 0 ? "Tiket tersedia." : "Tiket habis.";
    }
}   




