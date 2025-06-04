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
public class GUI_Event extends javax.swing.JFrame {

    // Variabel untuk koneksi database
    public Connection conn;
    // Variabel untuk statement SQL
    public Statement stmt;
    private String eventIdDipilih; // Untuk menyimpan ID event yang dipilih dari tabel

    /**
     * Creates new form GUI_Event
     */
    public GUI_Event() {
        initComponents();
        koneksi(); // Panggil method koneksi
        tampilData(); // Panggil method untuk menampilkan data awal
        clearInputFields(); // Bersihkan input fields saat awal
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

    // Method untuk menampilkan data dari database ke JTable
    private void tampilData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Event");
        model.addColumn("Lokasi");
        model.addColumn("Tanggal");
        model.addColumn("Harga");
        model.addColumn("Jumlah Tiket");
        tblData.setModel(model);

        try {
            if (conn == null || conn.isClosed()) {
                koneksi(); // Pastikan koneksi terbuka
            }
            if (stmt == null || stmt.isClosed()) {
                stmt = conn.createStatement(); // Pastikan statement dibuat jika null atau tertutup
            }
            String sql = "SELECT * FROM tb_event";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nama_event"),
                    rs.getString("lokasi"),
                    rs.getString("tanggal"),
                    rs.getDouble("harga"),
                    rs.getInt("jumlah_tiket")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
        }
    }

    // Method untuk membersihkan input fields
    private void clearInputFields() {
        cmb_event.setSelectedIndex(0);
        cmb_lokasi.setSelectedIndex(0);
        cmb_tanggal.setSelectedIndex(0);
        txtHarga.setText("");
        txtJumlah.setText("");
        eventIdDipilih = null; // Reset ID yang dipilih
        btnSimpan.setEnabled(true); // Aktifkan tombol simpan
        btnUpdate.setEnabled(false); // Non-aktifkan tombol update sampai item dipilih
        btnHapus.setEnabled(false); // Non-aktifkan tombol hapus sampai item dipilih

    }

    // Method untuk mengisi form input ketika item di tabel dipilih
    private void itemPilih() {
        int selectedRow = tblData.getSelectedRow();
        if (selectedRow != -1) { // Pastikan ada baris yang dipilih
            DefaultTableModel model = (DefaultTableModel) tblData.getModel();
            eventIdDipilih = model.getValueAt(selectedRow, 0).toString(); // Simpan nama_event sebagai ID

            cmb_event.setSelectedItem(model.getValueAt(selectedRow, 0).toString());
            cmb_lokasi.setSelectedItem(model.getValueAt(selectedRow, 1).toString());
            cmb_tanggal.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
            txtHarga.setText(model.getValueAt(selectedRow, 3).toString());
            txtJumlah.setText(model.getValueAt(selectedRow, 4).toString());

            btnSimpan.setEnabled(false); // Non-aktifkan tombol simpan saat mode edit/update
            btnUpdate.setEnabled(true);
            btnHapus.setEnabled(true);
        }

    }

    // Method untuk logika penyimpanan data
    private void prosesSimpanData() {
        String namaEvent = cmb_event.getSelectedItem().toString();
        String lokasi = cmb_lokasi.getSelectedItem().toString();
        String tanggal = cmb_tanggal.getSelectedItem().toString();
        String hargaStr = txtHarga.getText();
        String jumlahTiketStr = txtJumlah.getText();

        if (namaEvent.isEmpty() || lokasi.isEmpty() || tanggal.isEmpty() || hargaStr.isEmpty() || jumlahTiketStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            int jumlahTiket = Integer.parseInt(jumlahTiketStr);

            if (conn == null || conn.isClosed()) {
                koneksi();
            }

            // Cek apakah event sudah ada (jika nama_event adalah primary key unik)
            String checkSql = "SELECT COUNT(*) FROM tb_event WHERE nama_event = ?";
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setString(1, namaEvent);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Event dengan nama '" + namaEvent + "' sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String sql = "INSERT INTO tb_event (nama_event, lokasi, tanggal, harga, jumlah_tiket) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, namaEvent);
                ps.setString(2, lokasi);
                ps.setString(3, tanggal);
                ps.setDouble(4, harga);
                ps.setInt(5, jumlahTiket);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                    tampilData();
                    clearInputFields();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Jumlah Tiket harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk logika update data
    private void prosesUpdateData() {
        if (eventIdDipilih == null || eventIdDipilih.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String namaEventBaru = cmb_event.getSelectedItem().toString();
        String lokasi = cmb_lokasi.getSelectedItem().toString();
        String tanggal = cmb_tanggal.getSelectedItem().toString();
        String hargaStr = txtHarga.getText();
        String jumlahTiketStr = txtJumlah.getText();

        if (namaEventBaru.isEmpty() || lokasi.isEmpty() || tanggal.isEmpty() || hargaStr.isEmpty() || jumlahTiketStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            int jumlahTiket = Integer.parseInt(jumlahTiketStr);

            if (conn == null || conn.isClosed()) {
                koneksi();
            }

            // Cek jika nama_event diubah dan nama baru sudah ada (kecuali untuk event yang sedang diedit)
            if (!eventIdDipilih.equals(namaEventBaru)) {
                String checkSql = "SELECT COUNT(*) FROM tb_event WHERE nama_event = ?";
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setString(1, namaEventBaru);
                    ResultSet rs = checkPs.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "Event dengan nama '" + namaEventBaru + "' sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            String sql = "UPDATE tb_event SET nama_event = ?, lokasi = ?, tanggal = ?, harga = ?, jumlah_tiket = ? WHERE nama_event = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, namaEventBaru);
                ps.setString(2, lokasi);
                ps.setString(3, tanggal);
                ps.setDouble(4, harga);
                ps.setInt(5, jumlahTiket);
                ps.setString(6, eventIdDipilih); // ID lama untuk klausa WHERE

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                    tampilData();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau tidak ada perubahan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Jumlah Tiket harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk logika penghapusan data
    private void prosesHapusData() {
        if (eventIdDipilih == null || eventIdDipilih.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus event '" + eventIdDipilih + "'?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (conn == null || conn.isClosed()) {
                    koneksi();
                }
                String sql = "DELETE FROM tb_event WHERE nama_event = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, eventIdDipilih);

                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                        tampilData();
                        clearInputFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Data tidak ditemukan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmb_lokasi = new javax.swing.JComboBox<>();
        cmb_event = new javax.swing.JComboBox<>();
        cmb_tanggal = new javax.swing.JComboBox<>();
        txtHarga = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Lokasi");

        jLabel3.setText("Tanggal");

        jLabel4.setText("Harga");

        jLabel5.setText("Jumlah Tiket");

        jLabel6.setText("Nama Event");

        cmb_lokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jakarta Convention Center", "Bandung Mall" }));

        cmb_event.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Konser Musik Indie", "Fansign Carmen" }));

        cmb_tanggal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "20 Mei 2025", "29 Juli 2025" }));

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Event", "Lokasi", "Tanggal", "Harga"
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
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(cmb_event, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmb_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(49, 49, 49)
                        .addComponent(txtHarga))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmb_lokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmb_event, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_lokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmb_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnUpdate))
                .addContainerGap(39, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(GUI_Event.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Event.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Event.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Event.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new GUI_Event().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmb_event;
    private javax.swing.JComboBox<String> cmb_lokasi;
    private javax.swing.JComboBox<String> cmb_tanggal;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    // End of variables declaration//GEN-END:variables
}
