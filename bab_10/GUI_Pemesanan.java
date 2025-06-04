/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bab_10;

import bab_7.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author jamil
 */
public class GUI_Pemesanan extends javax.swing.JFrame {
    // Variabel untuk koneksi database
    public Connection conn;
    // Variabel untuk statement SQL
    public Statement stmt;
    // private String eventIdDipilih; // Hapus atau komentari baris ini
    private int idPemesananDipilih = -1; // Untuk menyimpan ID pemesanan yang dipilih dari tabel

    /**
     * Creates new form GUI_Pembayaran
     */
    public GUI_Pemesanan() {
        initComponents();
        koneksi(); // Panggil method koneksi
        tampilData(); // Panggil method untuk menampilkan data awal
        clearInputFields(); // Bersihkan input fields saat awal
//        DefaultTableModel model = (DefaultTableModel) tblData.getModel();
    }
    
    // Method untuk membangun koneksi ke database
    public void koneksi() {
        try {
            conn = null;
            // TODO: Ganti nama_database, user, dan password sesuai dengan konfigurasi MySQL Anda
            String url = "jdbc:mysql://localhost:3306/db_event_management?serverTimezone=UTC"; // Ganti db_event_management jika perlu
            String user = "root"; // User database Anda
            String password = ""; // Password database Anda

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            // JOptionPane.showMessageDialog(null, "Koneksi Berhasil!"); // Opsional: pesan koneksi berhasil
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan: " + e.getMessage());
            System.exit(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
            System.exit(0);
        } catch (Exception es) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan umum: " + es.getMessage());
            System.exit(0);
        }
    }
    
    private void tampilData(){
        DefaultTableModel model = new DefaultTableModel();
        // Kolom ID tetap ada di model untuk referensi internal
        model.addColumn("ID_Pemesanan_Hidden"); // Nama kolom di model bisa apa saja
        model.addColumn("Nama Pemesan");
        model.addColumn("Email");
        model.addColumn("No. HP");
        model.addColumn("Jumlah Tiket");
        model.addColumn("Total Bayar");
        // tblData.setModel(model); // Pindahkan setModel setelah selesai konfigurasi kolom

        try {
            if (conn == null || conn.isClosed()) {
                koneksi(); 
            }
            String sql = "SELECT id_pemesanan, nama_pemesan, email_pemesan, no_hp_pemesan, jumlah_tiket, total_bayar FROM tb_pemesanan";
            try (Statement tempStmt = conn.createStatement();
                 ResultSet rs = tempStmt.executeQuery(sql)) {

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id_pemesanan"), // ID tetap diambil
                        rs.getString("nama_pemesan"),
                        rs.getString("email_pemesan"),
                        rs.getString("no_hp_pemesan"),
                        rs.getInt("jumlah_tiket"),
                        rs.getDouble("total_bayar")
                    });
                }
            }
            tblData.setModel(model); // Set model ke JTable

            // Sembunyikan kolom pertama (ID_Pemesanan_Hidden)
            if (tblData.getColumnModel().getColumnCount() > 0) {
                tblData.getColumnModel().getColumn(0).setMinWidth(0);
                tblData.getColumnModel().getColumn(0).setMaxWidth(0);
                tblData.getColumnModel().getColumn(0).setWidth(0);
                // Jika Anda ingin kolom ID tidak bisa di-resize oleh pengguna sama sekali
                // tblData.getColumnModel().getColumn(0).setResizable(false);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data pemesanan: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
    
    private void clearInputFields() {
        txtNama.setText("");
        txtEmail.setText("");
        txtNomor.setText("");
        txtTiket.setText("");
        txtBayar.setText("");
        idPemesananDipilih = -1; // Reset ID yang dipilih
        
        btnSimpan.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        
        txtNama.requestFocus(); // Fokuskan kursor ke field nama
    }

    private void itemPilih() {
        int selectedRow = tblData.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) tblData.getModel();
            // ID diambil dari model pada indeks 0, meskipun tidak terlihat di tabel
            idPemesananDipilih = (Integer) model.getValueAt(selectedRow, 0); 

            txtNama.setText(model.getValueAt(selectedRow, 1).toString()); // Nama di indeks 1
            txtEmail.setText(model.getValueAt(selectedRow, 2).toString()); // Email di indeks 2
            txtNomor.setText(model.getValueAt(selectedRow, 3).toString()); // No HP di indeks 3
            txtTiket.setText(model.getValueAt(selectedRow, 4).toString()); // Jumlah Tiket di indeks 4
            txtBayar.setText(model.getValueAt(selectedRow, 5).toString()); // Total Bayar di indeks 5

            btnSimpan.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnHapus.setEnabled(true);
        }
    }
    
    private void prosesSimpanData(){
        String nama = txtNama.getText();
        String email = txtEmail.getText();
        String nomorHp = txtNomor.getText();
        String jumlahTiketStr = txtTiket.getText();
        String totalBayarStr = txtBayar.getText();

        if (nama.isEmpty() || email.isEmpty() || nomorHp.isEmpty() || jumlahTiketStr.isEmpty() || totalBayarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int jumlahTiket = Integer.parseInt(jumlahTiketStr);
            double totalBayar = Double.parseDouble(totalBayarStr);

            if (jumlahTiket <= 0 || totalBayar < 0) {
                JOptionPane.showMessageDialog(this, "Jumlah tiket harus lebih dari 0 dan total bayar tidak boleh negatif.", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (conn == null || conn.isClosed()) {
                koneksi();
            }

            String sql = "INSERT INTO tb_pemesanan (nama_pemesan, email_pemesan, no_hp_pemesan, jumlah_tiket, total_bayar) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nama);
                ps.setString(2, email);
                ps.setString(3, nomorHp);
                ps.setInt(4, jumlahTiket);
                ps.setDouble(5, totalBayar);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data pemesanan berhasil disimpan!");
                    tampilData();
                    clearInputFields();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah Tiket dan Total Bayar harus berupa angka yang valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data pemesanan: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void prosesUpdateData(){
        if (idPemesananDipilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pemesanan yang akan diupdate dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nama = txtNama.getText();
        String email = txtEmail.getText();
        String nomorHp = txtNomor.getText();
        String jumlahTiketStr = txtTiket.getText();
        String totalBayarStr = txtBayar.getText();

        if (nama.isEmpty() || email.isEmpty() || nomorHp.isEmpty() || jumlahTiketStr.isEmpty() || totalBayarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int jumlahTiket = Integer.parseInt(jumlahTiketStr);
            double totalBayar = Double.parseDouble(totalBayarStr);

            if (jumlahTiket <= 0 || totalBayar < 0) {
                JOptionPane.showMessageDialog(this, "Jumlah tiket harus lebih dari 0 dan total bayar tidak boleh negatif.", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (conn == null || conn.isClosed()) {
                koneksi();
            }
            
            String sql = "UPDATE tb_pemesanan SET nama_pemesan = ?, email_pemesan = ?, no_hp_pemesan = ?, jumlah_tiket = ?, total_bayar = ? WHERE id_pemesanan = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nama);
                ps.setString(2, email);
                ps.setString(3, nomorHp);
                ps.setInt(4, jumlahTiket);
                ps.setDouble(5, totalBayar);
                ps.setInt(6, idPemesananDipilih);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data pemesanan berhasil diupdate!");
                    tampilData();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Data pemesanan tidak ditemukan atau tidak ada perubahan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah Tiket dan Total Bayar harus berupa angka yang valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data pemesanan: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void prosesHapusData(){
        if (idPemesananDipilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pemesanan yang akan dihapus dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data pemesanan dengan ID " + idPemesananDipilih + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (conn == null || conn.isClosed()) {
                    koneksi();
                }
                String sql = "DELETE FROM tb_pemesanan WHERE id_pemesanan = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idPemesananDipilih);

                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "Data pemesanan berhasil dihapus!");
                        tampilData();
                        clearInputFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Data pemesanan tidak ditemukan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data pemesanan: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtNomor = new javax.swing.JTextField();
        txtTiket = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtBayar = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama");

        jLabel2.setText("Email");

        jLabel3.setText("No. HP");

        jLabel4.setText("Jumlah Tiket");

        jLabel5.setText("Total Bayar");

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Email", "No. HP", "Jml. Tiket", "T. Bayar"
            }
        ));
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblData);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTiket, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtNomor)
                    .addComponent(txtEmail)
                    .addComponent(txtNama)
                    .addComponent(txtBayar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTiket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnUpdate))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        prosesSimpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        prosesHapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        prosesUpdateData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
        itemPilih();
    }//GEN-LAST:event_tblDataMouseClicked

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
            java.util.logging.Logger.getLogger(GUI_Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Pemesanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNomor;
    private javax.swing.JTextField txtTiket;
    // End of variables declaration//GEN-END:variables
}
