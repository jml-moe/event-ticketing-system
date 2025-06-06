/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bab_4;

import bab_3.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jamil
 */
public class GUI_Pesan extends javax.swing.JFrame {
    /**
     * Creates new form GUI_Pesan
     */
    private static final String NAMA_EVENT = "Konser Musik";
    private static final String LOKASI = "Jakarta";
    private static final String TANGGAL = "2025-04-20";
    private static final double HARGA_TIKET = 500000;
    private static int JUMLAH_TIKET = 100;
    
    public GUI_Pesan() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtNomor = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setText("Jumlah Tiket");

        jLabel5.setText("Total Bayar");

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

        btnBayar.setText("Cetak Tiket");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nama ");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Email");

        jLabel3.setText("No. HP");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBayar, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtNomor, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtNama)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(btnBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBayar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBayarActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // TODO add your handling code here:
        // Step 1: Mengambil input dari form
        String namaPemesan = txtNama.getText();
        String email = txtEmail.getText();
        String noHP = txtNomor.getText();
        String jumlahTiketStr = txtJumlah.getText();

        // Step 2: Validasi input
        if (namaPemesan.isEmpty() || email.isEmpty() || noHP.isEmpty() || jumlahTiketStr.isEmpty()) {
            // Jika ada input yang kosong, tampilkan pesan error
            JOptionPane.showMessageDialog(this, "Semua data harus diisi dengan benar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 3: Memeriksa apakah jumlah tiket yang dimasukkan valid
        int jumlahPesan = 0;
        boolean validJumlahTiket = false;

        // Memeriksa apakah jumlah tiket merupakan angka dan lebih dari 0
        try {
            jumlahPesan = Integer.parseInt(jumlahTiketStr);
            if (jumlahPesan > 0) {
                validJumlahTiket = true;
            }
        } catch (NumberFormatException e) {
            // Jika terjadi kesalahan dalam konversi string ke angka, program akan menangkapnya
        }

        // Jika jumlah tiket tidak valid, tampilkan pesan error
        if (!validJumlahTiket) {
            JOptionPane.showMessageDialog(this, "Jumlah Tiket harus berupa angka lebih dari 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 4: Memeriksa apakah jumlah tiket yang dipesan lebih dari stok yang tersedia
        if (jumlahPesan > JUMLAH_TIKET) {
            JOptionPane.showMessageDialog(this, "Jumlah tiket yang dipesan melebihi stok.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 5: Membuat objek PemesananTiket dan melakukan pemesanan
        PemesananTiket tiket = new PemesananTiket(NAMA_EVENT, LOKASI, TANGGAL, HARGA_TIKET, JUMLAH_TIKET);
        tiket.pesanTiket(namaPemesan, email, noHP, jumlahPesan);

        // Step 6: Mengupdate jumlah tiket yang tersedia dan menghitung total bayar
        JUMLAH_TIKET -= jumlahPesan;  // Mengurangi jumlah tiket yang tersedia
        double totalBayar = HARGA_TIKET * jumlahPesan;  // Menghitung total bayar

        // Step 7: Menonaktifkan input pada field txtBayar dan menampilkan total bayar
        txtBayar.setEnabled(false);  // Menonaktifkan agar tidak bisa diubah oleh pengguna
        txtBayar.setText("Rp" + totalBayar);  // Menampilkan total bayar di text field

        // Step 8: Menampilkan konfirmasi pemesanan di JTextArea dengan informasi event
        jTextArea1.setText("=== Konfirmasi Pemesanan ===\n");  // Menambahkan judul konfirmasi
        jTextArea1.append("Event: " + NAMA_EVENT + "\n");  // Menambahkan nama event
        jTextArea1.append("Lokasi: " + LOKASI + "\n");  // Menambahkan lokasi event
        jTextArea1.append("Tanggal: " + TANGGAL + "\n");  // Menambahkan tanggal event
        jTextArea1.append("Harga Tiket: Rp" + HARGA_TIKET + "\n\n");  // Menambahkan harga tiket

        // Menampilkan data pemesan
        jTextArea1.append("Nama Pemesan: " + namaPemesan + "\n");
        jTextArea1.append("Email: " + email + "\n");
        jTextArea1.append("No. HP: " + noHP + "\n");
        jTextArea1.append("Jumlah Tiket Dipesan: " + jumlahPesan + "\n");
        jTextArea1.append("Total Bayar: Rp" + totalBayar + "\n");
        jTextArea1.append("Tiket telah dipesan. E-ticket akan dikirim ke email Anda.\n");
    }//GEN-LAST:event_btnBayarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_Pesan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Pesan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Pesan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Pesan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Pesan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNomor;
    // End of variables declaration//GEN-END:variables
}
