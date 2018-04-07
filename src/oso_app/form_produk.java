/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oso_app;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sun.misc.Cleaner;
/**
 *
 * @author DELL
 */
    public class form_produk extends javax.swing.JFrame {
koneksi kon=new koneksi();
private Object [][] dataproduk=null;
private String[]label={"Id Produk","Id Kategori","Nama Produk","Unit Cost","Id_Supplier"};
/**
* Creates new form Form_Produk
*/
public form_produk() {
initComponents();
kon.setKoneksi();
nonaktif();
BacaTabelProduk();
isiNamaKategori();
isiNamaSupplier();
tid_kategori.setVisible(false);
tid_supplier.setVisible(false);
}
void isiNamaKategori(){
try{
String sql="Select *From kategori";
kon.rs=kon.st.executeQuery(sql);
while (kon.rs.next()){
cbkategori.addItem(kon.rs.getString("nama_kategori"));
}
}catch(SQLException e){
System.out.println("Koneksi Gagal"+ e.toString());
}
}
void isiNamaSupplier(){
try{
String sql="Select *From supplier ";
kon.rs=kon.st.executeQuery(sql);
while (kon.rs.next()){
cbsupplier.addItem(kon.rs.getString("nama_supplier"));
}
}catch(SQLException e){
System.out.println("Koneksi Gagal"+ e.toString());
}
}
private String idProduk()
{
String no=null;
try{
kon.setKoneksi();
String sql = "Select right(id_produk,3)+1 from produk ";
ResultSet rs = kon.st.executeQuery(sql);
if (rs.next()){
rs.last();
no = rs.getString(1);
while (no.length()<3){
no="00"+no;
no="B"+no;
tid_produk.setText(no);
}
}else{
no="B001";
tid_produk.setText(no);
}
}catch (Exception e){
}return no;
}
private void BacaTabelProduk(){
try{
String sql="Select *From produk order by id_produk";
kon.rs=kon.st.executeQuery(sql);
ResultSetMetaData m=kon.rs.getMetaData();
int kolom=m.getColumnCount();
int baris=0;
while(kon.rs.next()){
baris=kon.rs.getRow();
}
dataproduk=new Object[baris][kolom];
int x=0;
kon.rs.beforeFirst();
while(kon.rs.next()){
dataproduk[x][0]=kon.rs.getString("id_produk");
dataproduk[x][1]=kon.rs.getString("id_kategori");
dataproduk[x][2]=kon.rs.getString("nama_produk");
dataproduk[x][3]=kon.rs.getString("unitcost");
dataproduk[x][4]=kon.rs.getString("id_supplier");
x++;
}
tbl_produk.setModel(new DefaultTableModel(dataproduk,label));
}
catch(SQLException e){
JOptionPane.showMessageDialog(null, e);
}
}
private void BacaTabelProduk2(){
try{
String sql="select *from produk where nama_produk like '%" +tcari.getText()+ "%' ";
kon.rs=kon.st.executeQuery(sql);
ResultSetMetaData m=kon.rs.getMetaData();
int kolom=m.getColumnCount();
int baris=0;
while(kon.rs.next()){
baris=kon.rs.getRow();
}
dataproduk=new Object[baris][kolom];
int x=0;
kon.rs.beforeFirst();
while(kon.rs.next()){
dataproduk[x][0]=kon.rs.getString("id_produk");
dataproduk[x][1]=kon.rs.getString("id_kategori");
dataproduk[x][2]=kon.rs.getString("nama_produk");
dataproduk[x][3]=kon.rs.getString("unitcost");
dataproduk[x][4]=kon.rs.getString("id_supplier");
x++;
}
tbl_produk.setModel(new DefaultTableModel(dataproduk,label));
}
catch(SQLException e){
JOptionPane.showMessageDialog(null, e);
}
}
void isiNamaKategori2(){
try{
kon.setKoneksi();
String sql="Select *From kategori where id_kategori='"+tid_kategori.getText()+"'";
kon.rs=kon.st.executeQuery(sql);
if (kon.rs.next()){
cbkategori.setSelectedItem(kon.rs.getString("nama_kategori"));
}
}catch(SQLException e){
System.out.println("Koneksi Gagal"+ e.toString());
}
}
void isiNamaSupplier2(){
try{
String sql="Select *From supplier where id_supplier='"+tid_supplier.getText()+"'";
kon.rs=kon.st.executeQuery(sql);
if(kon.rs.next()){
cbsupplier.setSelectedItem(kon.rs.getString("nama_supplier"));
}
}catch(SQLException e){
System.out.println("Koneksi Gagal"+ e.toString());
}
}
private void setTable(){
int row=tbl_produk.getSelectedRow();
tid_produk.setText((String)tbl_produk.getValueAt(row,0));
tid_kategori.setText((String)tbl_produk.getValueAt(row,1));
tnm_produk.setText((String)tbl_produk.getValueAt(row,2));
tunit_cost.setText((String)tbl_produk.getValueAt(row,3));
tid_supplier.setText((String)tbl_produk.getValueAt(row,4));
}
private void BersihField(){
tunit_cost.setText("");
tid_produk.setText("");
tnm_produk.setText("");
tnm_produk.setText("");
tcari.setText("");
}
private void aktif(){
tid_produk.setEnabled(true);
tnm_produk.setEnabled(true);
tunit_cost.setEnabled(true);
cbkategori.setEnabled(true);
cbsupplier.setEnabled(true);
}
private void nonaktif(){
tid_produk.setEnabled(false);
tnm_produk.setEnabled(false);
tunit_cost.setEnabled(false);
cbkategori.setEnabled(false);
cbsupplier.setEnabled(false);
bt_edit.setEnabled(false);
bt_update.setEnabled(false);
bt_hapus.setEnabled(false);
bt_simpan.setEnabled(false);
}
private void SimpanData(){
try{
String sql="insert into produk values('"+tid_produk.getText()+"','"+tid_kategori.getText()+"','"+tnm_produk.getText()+"','"+tunit_cost.getText()+"','"+tid_supplier.getText()+"')";
kon.st.executeUpdate(sql);
JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
BersihField();
BacaTabelProduk();
}
catch(SQLException e){
JOptionPane.showMessageDialog(null,e);
}
}
private void EditData(){
try{
String sql="Update produk set id_produk='"+tid_produk.getText()+"',id_kategori='"+tid_kategori.getText()+"',id_supplier='"+tid_supplier.getText()+"',nama_produk='"+tnm_produk.getText()+"',unitcost='"+tunit_cost.getText()+"' where id_produk='"+tid_produk.getText()+"'";
kon.st.executeUpdate(sql);
JOptionPane.showMessageDialog(null,"Data berhasil diupdate");
BersihField();
BacaTabelProduk();
kon.st.close();
}
catch(SQLException e){
JOptionPane.showMessageDialog(null,e);
}
}
private void HapusData(){
try{
String sql="Delete from produk where id_produk='"+tid_produk.getText()+"'";
kon.st.executeUpdate(sql);
JOptionPane.showMessageDialog(null,"Data berhasil dihapus");
BersihField();
BacaTabelProduk();
kon.st.close();
}
catch(SQLException e){
JOptionPane.showMessageDialog(null, e);
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

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jFrame5 = new javax.swing.JFrame();
        jFrame6 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tid_produk = new javax.swing.JTextField();
        cbkategori = new javax.swing.JComboBox<>();
        cbsupplier = new javax.swing.JComboBox<>();
        tnm_produk = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tunit_cost = new javax.swing.JTextField();
        tid_kategori = new javax.swing.JTextField();
        tid_supplier = new javax.swing.JTextField();
        bt_keluar = new javax.swing.JButton();
        bt_tambah = new javax.swing.JButton();
        bt_simpan = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_update = new javax.swing.JButton();
        bt_batal = new javax.swing.JButton();
        bt_hapus = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_produk = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame5Layout = new javax.swing.GroupLayout(jFrame5.getContentPane());
        jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame5Layout.setVerticalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame6Layout = new javax.swing.GroupLayout(jFrame6.getContentPane());
        jFrame6.getContentPane().setLayout(jFrame6Layout);
        jFrame6Layout.setHorizontalGroup(
            jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame6Layout.setVerticalGroup(
            jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Id Produk");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel2.setText("Kategori");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        jLabel3.setText("Supplier");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel4.setText("Nama Produk");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        tid_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tid_produkActionPerformed(evt);
            }
        });
        getContentPane().add(tid_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 100, -1));

        cbkategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=PILIH=" }));
        cbkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkategoriActionPerformed(evt);
            }
        });
        getContentPane().add(cbkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 100, -1));

        cbsupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=PILIH=" }));
        cbsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbsupplierActionPerformed(evt);
            }
        });
        getContentPane().add(cbsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 100, -1));
        getContentPane().add(tnm_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 100, -1));

        jLabel5.setText("Unit Cost");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        tunit_cost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tunit_costActionPerformed(evt);
            }
        });
        getContentPane().add(tunit_cost, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 70, -1));

        tid_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tid_kategoriActionPerformed(evt);
            }
        });
        getContentPane().add(tid_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 70, -1));

        tid_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tid_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(tid_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 70, -1));

        bt_keluar.setText("Tutup");
        bt_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(bt_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 90, 40));

        bt_tambah.setText("Tambah");
        bt_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(bt_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        bt_simpan.setText("Simpan");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });
        getContentPane().add(bt_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, -1, -1));

        bt_edit.setText("Edit");
        bt_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_editActionPerformed(evt);
            }
        });
        getContentPane().add(bt_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 70, -1));

        bt_update.setText("Update");
        bt_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_updateActionPerformed(evt);
            }
        });
        getContentPane().add(bt_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, -1, -1));

        bt_batal.setText("Batal");
        bt_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_batalActionPerformed(evt);
            }
        });
        getContentPane().add(bt_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, -1, -1));

        bt_hapus.setText("Hapus");
        bt_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(bt_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, -1, -1));

        jLabel6.setText("Cari Peoduk");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        tcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcariKeyTyped(evt);
            }
        });
        getContentPane().add(tcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 140, -1));

        tbl_produk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_produk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_produkMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_produk);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, 90));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tabel Data Produk"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 480, 160));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Input Data Produk"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 430, 170));

        setBounds(0, 0, 554, 455);
    }// </editor-fold>//GEN-END:initComponents

    private void tid_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tid_produkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tid_produkActionPerformed

    private void cbkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkategoriActionPerformed

    private void tid_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tid_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tid_kategoriActionPerformed

    private void tid_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tid_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tid_supplierActionPerformed

    private void bt_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_batalActionPerformed
nonaktif();
BersihField();
bt_tambah.setEnabled(true);
cbkategori.setSelectedItem("=PILIH=");
cbsupplier.setSelectedItem("=PILIH=");
try {
kon.st.close();
} catch (SQLException ex) {
Logger.getLogger(form_produk.class.getName()).log(Level.SEVERE, null, ex);
}        // TODO add your handling code here:
    }//GEN-LAST:event_bt_batalActionPerformed

    private void tunit_costActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tunit_costActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tunit_costActionPerformed

    private void bt_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_keluarActionPerformed
dispose();
    }//GEN-LAST:event_bt_keluarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
BersihField();
nonaktif();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void bt_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tambahActionPerformed
idProduk();
aktif();
bt_batal.setEnabled(true);
bt_tambah.setEnabled(false);
bt_simpan.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_bt_tambahActionPerformed

    private void cbsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbsupplierActionPerformed
try{
kon.setKoneksi();
String sql="Select *From supplier where nama_supplier='"+cbsupplier.getSelectedItem()+"'";
kon.rs=kon.st.executeQuery(sql);
if (kon.rs.next()){
tid_supplier.setText(kon.rs.getString("id_supplier"));
}
}catch(SQLException e){
System.out.println("Koneksi Gagal"+ e.toString());
}        // TODO add your handling code here:
    }//GEN-LAST:event_cbsupplierActionPerformed

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
if (tid_produk.getText().isEmpty() || tnm_produk.getText().isEmpty() || tunit_cost.getText().isEmpty()) {
JOptionPane.showMessageDialog(this, "Lengkapi Data", "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
bt_tambah.setEnabled(true);
} else {
bt_tambah.setEnabled(true);
bt_keluar.setEnabled(true);
SimpanData();
cbkategori.setSelectedItem("=PILIH=");
cbsupplier.setSelectedItem("=PILIH=");
try {
kon.st.close();
} catch (SQLException ex) {
Logger.getLogger(form_produk.class.getName()).log(Level.SEVERE, null, ex);
}
}        // TODO add your handling code here:
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void tcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcariKeyTyped
kon.setKoneksi();
BacaTabelProduk2();        // TODO add your handling code here:
    }//GEN-LAST:event_tcariKeyTyped

    private void tbl_produkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_produkMouseClicked
setTable();
isiNamaKategori2();
isiNamaSupplier2();
bt_hapus.setEnabled(true);
bt_edit.setEnabled(true);
bt_tambah.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_produkMouseClicked

    private void bt_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapusActionPerformed
if (JOptionPane.showConfirmDialog(this, "yakin mau dihapus?", "konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
HapusData();
bt_tambah.setEnabled(true);
nonaktif();
BersihField();
cbkategori.setSelectedItem("=PILIH=");
cbsupplier.setSelectedItem("=PILIH=");
} else {
JOptionPane.showMessageDialog(this, "Data Batal Dihapus", "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
bt_tambah.setEnabled(true);
nonaktif();
BersihField();
cbkategori.setSelectedItem("=PILIH=");
cbsupplier.setSelectedItem("=PILIH=");
return;
}
formWindowActivated(null);        // TODO add your handling code here:
    }//GEN-LAST:event_bt_hapusActionPerformed

    private void bt_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_editActionPerformed
aktif();
tid_produk.setEnabled(false);
bt_edit.setEnabled(false);
bt_update.setEnabled(true);
bt_batal.setEnabled(true);
bt_hapus.setEnabled(false);
bt_tambah.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_bt_editActionPerformed

    private void bt_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_updateActionPerformed
bt_update.setEnabled(false);
bt_tambah.setEnabled(true);
EditData();
cbkategori.setSelectedItem("=PILIH=");
cbsupplier.setSelectedItem("=PILIH=");        // TODO add your handling code here:
    }//GEN-LAST:event_bt_updateActionPerformed

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
            java.util.logging.Logger.getLogger(form_produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_produk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_batal;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_keluar;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JButton bt_tambah;
    private javax.swing.JButton bt_update;
    private javax.swing.JComboBox<String> cbkategori;
    private javax.swing.JComboBox<String> cbsupplier;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JFrame jFrame6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_produk;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tid_kategori;
    private javax.swing.JTextField tid_produk;
    private javax.swing.JTextField tid_supplier;
    private javax.swing.JTextField tnm_produk;
    private javax.swing.JTextField tunit_cost;
    // End of variables declaration//GEN-END:variables
}
