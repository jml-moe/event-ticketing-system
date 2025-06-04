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
public class GUI_Pembayaran extends javax.swing.JFrame {

    // Variabel untuk koneksi database
    public Connection conn;
    // Variabel untuk statement SQL
    public Statement stmt;
    private int idPembayaranDipilih = -1; // Untuk menyimpan ID pembayaran yang dipilih

    /**
     * Creates new form GUI_Pembayaran
     */
    public GUI_Pembayaran() {
        initComponents();
        koneksi();
        tampilData();
        clearInputFields();
    }
    
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
        DefaultTableModel currentModel = new DefaultTableModel();
        // Kolom untuk ID (disembunyikan)
        currentModel.addColumn("ID_Pembayaran_Hidden");
        // Kolom yang terlihat
        currentModel.addColumn("Nama Pembayar");
        currentModel.addColumn("Email");
        currentModel.addColumn("No. HP");
        currentModel.addColumn("Jumlah Tiket Dibayar");
        currentModel.addColumn("Metode Pembayaran");

        try {
            if (conn == null || conn.isClosed()) {
                koneksi();
            }
            String sql = "SELECT id_pembayaran, nama_pembayar, email_pembayar, no_hp_pembayar, jumlah_tiket_dibayar, metode_pembayaran FROM tb_pembayaran";
            try (Statement tempStmt = conn.createStatement();
                 ResultSet rs = tempStmt.executeQuery(sql)) {

                while (rs.next()) {
                    currentModel.addRow(new Object[]{
                        rs.getInt("id_pembayaran"), // ID
                        rs.getString("nama_pembayar"),
                        rs.getString("email_pembayar"),
                        rs.getString("no_hp_pembayar"),
                        rs.getInt("jumlah_tiket_dibayar"),
                        rs.getString("metode_pembayaran")
                    });
                }
            }
            tblData.setModel(currentModel);

            // Sembunyikan kolom ID (indeks 0)
            if (tblData.getColumnModel().getColumnCount() > 0) {
                tblData.getColumnModel().getColumn(0).setMinWidth(0);
                tblData.getColumnModel().getColumn(0).setMaxWidth(0);
                tblData.getColumnModel().getColumn(0).setWidth(0);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data pembayaran: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void clearInputFields() {
        txtNama.setText("");
        txtEmail.setText("");
        txtNomor.setText("");
        txtTiket.setText("");
        cmb_bayar.setSelectedIndex(0); // Set ke item pertama
        idPembayaranDipilih = -1;

        btnSimpan.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        txtNama.requestFocus();
    }

    private void itemPilih(){
        int selectedRow = tblData.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel currentModel = (DefaultTableModel) tblData.getModel();
            // ID diambil dari model (indeks 0), meskipun tidak terlihat
            idPembayaranDipilih = (Integer) currentModel.getValueAt(selectedRow, 0);

            txtNama.setText(currentModel.getValueAt(selectedRow, 1).toString());        // Nama di indeks 1
            txtEmail.setText(currentModel.getValueAt(selectedRow, 2).toString());       // Email di indeks 2
            txtNomor.setText(currentModel.getValueAt(selectedRow, 3).toString());       // No HP di indeks 3
            txtTiket.setText(currentModel.getValueAt(selectedRow, 4).toString());       // Jml Tiket di indeks 4
            cmb_bayar.setSelectedItem(currentModel.getValueAt(selectedRow, 5).toString());// Metode Bayar di indeks 5

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
        String metodeBayar = cmb_bayar.getSelectedItem().toString();

        if (nama.isEmpty() || email.isEmpty() || nomorHp.isEmpty() || jumlahTiketStr.isEmpty() || metodeBayar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int jumlahTiket = Integer.parseInt(jumlahTiketStr);
            if (jumlahTiket <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah tiket harus lebih dari 0.", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (conn == null || conn.isClosed()) {
                koneksi();
            }

            String sql = "INSERT INTO tb_pembayaran (nama_pembayar, email_pembayar, no_hp_pembayar, jumlah_tiket_dibayar, metode_pembayaran) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nama);
                ps.setString(2, email);
                ps.setString(3, nomorHp);
                ps.setInt(4, jumlahTiket);
                ps.setString(5, metodeBayar);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data pembayaran berhasil disimpan!");
                    tampilData();
                    clearInputFields();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah Tiket harus berupa angka yang valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data pembayaran: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void prosesUpdateData() {
        if (idPembayaranDipilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pembayaran yang akan diupdate dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nama = txtNama.getText();
        String email = txtEmail.getText();
        String nomorHp = txtNomor.getText();
        String jumlahTiketStr = txtTiket.getText();
        String metodeBayar = cmb_bayar.getSelectedItem().toString();

        if (nama.isEmpty() || email.isEmpty() || nomorHp.isEmpty() || jumlahTiketStr.isEmpty() || metodeBayar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int jumlahTiket = Integer.parseInt(jumlahTiketStr);
            if (jumlahTiket <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah tiket harus lebih dari 0.", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (conn == null || conn.isClosed()) {
                koneksi();
            }

            String sql = "UPDATE tb_pembayaran SET nama_pembayar = ?, email_pembayar = ?, no_hp_pembayar = ?, jumlah_tiket_dibayar = ?, metode_pembayaran = ? WHERE id_pembayaran = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nama);
                ps.setString(2, email);
                ps.setString(3, nomorHp);
                ps.setInt(4, jumlahTiket);
                ps.setString(5, metodeBayar);
                ps.setInt(6, idPembayaranDipilih);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data pembayaran berhasil diupdate!");
                    tampilData();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Data pembayaran tidak ditemukan atau tidak ada perubahan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah Tiket harus berupa angka yang valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data pembayaran: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void prosesHapusData() {
        if (idPembayaranDipilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pembayaran yang akan dihapus dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data pembayaran dengan ID " + idPembayaranDipilih + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (conn == null || conn.isClosed()) {
                    koneksi();
                }
                String sql = "DELETE FROM tb_pembayaran WHERE id_pembayaran = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idPembayaranDipilih);

                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "Data pembayaran berhasil dihapus!");
                        tampilData();
                        clearInputFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Data pembayaran tidak ditemukan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data pembayaran: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
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
        cmb_bayar = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama");

        jLabel2.setText("Email");

        jLabel3.setText("No. HP");

        jLabel4.setText("Jumlah Tiket");

        jLabel5.setText("Metode Pembayaran");

        cmb_bayar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "E-Wallet", "Kartu Kredit", "Transfer Bank" }));

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Email", "No. HP", "Jml Tiket", "Metode Bayar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
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
                    .addComponent(jLabel5)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmb_bayar, 0, 112, Short.MAX_VALUE)
                    .addComponent(txtTiket)
                    .addComponent(txtNomor)
                    .addComponent(txtEmail)
                    .addComponent(txtNama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                            .addComponent(cmb_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnUpdate))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        prosesSimpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        prosesHapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
        itemPilih();
    }//GEN-LAST:event_tblDataMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        prosesUpdateData();
    }//GEN-LAST:event_btnUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Pembayaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmb_bayar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNomor;
    private javax.swing.JTextField txtTiket;
    // End of variables declaration//GEN-END:variables
}
