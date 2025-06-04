/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bab_9;

/**
 *
 * @author jamil
 */
public interface Bookable {
    boolean bookTiket(int jumlah);
    double hitungTotalHarga(int jumlah);
    String getInfoBooking();
}
