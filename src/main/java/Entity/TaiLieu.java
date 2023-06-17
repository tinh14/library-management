/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Helper.DatabaseManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.compress.utils.IOUtils;
import org.json.JSONObject;

/**
 *
 * @author tinhlam
 */
public abstract class TaiLieu implements CRUD<TaiLieu> {
    private int soHieu;
    private String isbn;
    private String ten;
    private int namXuatBan;
    private String nhaXuatBan;
    private String tinhTrang;
    private String moTa;
    private int soTrang;
    private byte[] anhBia;
    private String soHieuXepGia;
    private int soLuong;
    private LocalDate ngayNhapkho;
    private byte[] fileTaiLieu;

    public TaiLieu() {
    }

    public TaiLieu(int soHieu, String isbn, String ten, int namXuatBan, String nhaXuatBan, String tinhTrang, String moTa, int soTrang, byte[] anhBia, String soHieuXepGia, int soLuong, LocalDate ngayNhapKho, byte[] fileTaiLieu) {
        this.soHieu = soHieu;
        this.isbn = isbn;
        this.ten = ten;
        this.namXuatBan = namXuatBan;
        this.nhaXuatBan = nhaXuatBan;
        this.tinhTrang = tinhTrang;
        this.moTa = moTa;
        this.soTrang = soTrang;
        this.anhBia = anhBia;
        this.soHieuXepGia = soHieuXepGia;
        this.soLuong = soLuong;
        this.ngayNhapkho = ngayNhapKho;
        this.fileTaiLieu = fileTaiLieu;
    }
    
    @Override
    public abstract ArrayList<TaiLieu> layDanhSach();

    @Override
    public abstract void them();

    @Override
    public abstract void sua();

    @Override
    public abstract void xoa();

    public abstract ArrayList<TaiLieu> timKiemTheoTen(String ten);

    public void muon(int soLuong){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update TaiLieu set soLuong = soLuong - ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setInt(2, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaiLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tra(int soLuong){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update TaiLieu set soLuong = soLuong + ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setInt(2, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaiLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean laAnPhamGiay(){
        return this.getFileTaiLieu() == null;
    }
    
    public static TaiLieu napDuLieu(TaiLieu taiLieu, String isbn) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        try {
            InputStream stream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            StringBuilder builder = new StringBuilder();
            int len;
            while ((len = reader.read()) != -1) {
                builder.append((char) len);
            }
            JSONObject json = new JSONObject(builder.toString());
            if (json.length() == 2) {
                return null;
            }
            JSONObject json2 = json.getJSONArray("items").getJSONObject(0);
            JSONObject json3 = json2.getJSONObject("volumeInfo");
            String ten = json3.getString("title");
            int namXuatBan = Integer.valueOf(json3.getString("publishedDate").split("-")[0]);
            String nhaXuatBan = null;
            if (!json3.isNull("publisher")){
                nhaXuatBan = json3.getString("publisher");
            }
            String moTa = null;
            if (!json3.isNull("description")){
                moTa = json3.getString("description");
            }
            int soTrang = 0;
            if (!json3.isNull("pageCount")){
                soTrang = json3.getInt("pageCount");
            }
            byte[] anhBia = null;
            if (!json3.isNull(("imageLinks"))){
                anhBia = IOUtils.toByteArray(new URL(json3.getJSONObject("imageLinks").getString("thumbnail")).openStream());
            }
            LocalDate ngayNhapKho = LocalDate.now();

            taiLieu.setISBN(isbn);
            taiLieu.setTen(ten);
            taiLieu.setNamXuatBan(namXuatBan);
            taiLieu.setNhaXuatBan(nhaXuatBan);
            taiLieu.setMoTa(moTa);
            taiLieu.setSoTrang(soTrang);
            taiLieu.setAnhBia(anhBia);
            taiLieu.setNgayNhapkho(ngayNhapKho);
        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return taiLieu;
    }

    /**
     * @return the soHieu
     */
    public int getSoHieu() {
        return soHieu;
    }

    /**
     * @param soHieu the soHieu to set
     */
    public void setSoHieu(int soHieu) {
        this.soHieu = soHieu;
    }

    /**
     * @return the isbn
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the namXuatBan
     */
    public int getNamXuatBan() {
        return namXuatBan;
    }

    /**
     * @param namXuatBan the namXuatBan to set
     */
    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    /**
     * @return the nhaXuatBan
     */
    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    /**
     * @param nhaXuatBan the nhaXuatBan to set
     */
    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    /**
     * @return the tinhTrang
     */
    public String getTinhTrang() {
        return tinhTrang;
    }

    /**
     * @param tinhTrang the tinhTrang to set
     */
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    /**
     * @return the moTa
     */
    public String getMoTa() {
        return moTa;
    }

    /**
     * @param moTa the moTa to set
     */
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    /**
     * @return the soTrang
     */
    public int getSoTrang() {
        return soTrang;
    }

    /**
     * @param soTrang the soTrang to set
     */
    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    /**
     * @return the anhBia
     */
    public byte[] getAnhBia() {
        return anhBia;
    }

    /**
     * @param anhBia the anhBia to set
     */
    public void setAnhBia(byte[] anhBia) {
        this.anhBia = anhBia;
    }

    /**
     * @return the soHieuXepGia
     */
    public String getSoHieuXepGia() {
        return soHieuXepGia;
    }

    /**
     * @param soHieuXepGia the soHieuXepGia to set
     */
    public void setSoHieuXepGia(String soHieuXepGia) {
        this.soHieuXepGia = soHieuXepGia;
    }

    /**
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the ngayNhapkho
     */
    public LocalDate getNgayNhapkho() {
        return ngayNhapkho;
    }

    /**
     * @param ngayNhapkho the ngayNhapkho to set
     */
    public void setNgayNhapkho(LocalDate ngayNhapkho) {
        this.ngayNhapkho = ngayNhapkho;
    }

    /**
     * @return the fileTaiLieu
     */
    public byte[] getFileTaiLieu() {
        return fileTaiLieu;
    }

    /**
     * @param fileTaiLieu the fileTaiLieu to set
     */
    public void setFileTaiLieu(byte[] fileTaiLieu) {
        this.fileTaiLieu = fileTaiLieu;
    }
    
  
    
}
