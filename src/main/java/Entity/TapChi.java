/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Helper.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public class TapChi extends TaiLieu {

    private String linhVuc;

    public TapChi() {
    }

    public TapChi(String linhVuc, int soHieu, String isbn, String ten, int namXuatBan, String nhaXuatBan, String tinhTrang, String moTa, int soTrang, byte[] anhBia, String soHieuXepGia, int soLuong, LocalDate ngayNhapKho, byte[] fileTaiLieu) {
        super(soHieu, isbn, ten, namXuatBan, nhaXuatBan, tinhTrang, moTa, soTrang, anhBia, soHieuXepGia, soLuong, ngayNhapKho, fileTaiLieu);
        this.linhVuc = linhVuc;
    }

    @Override
    public ArrayList<TaiLieu> layDanhSach() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<TaiLieu> danhSachTaiLieu = new ArrayList<>();
        String sql = "select * from TaiLieu, TapChi where TaiLieu.soHieu = TapChi.soHieu";
        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                TapChi tapChi = new TapChi();
                tapChi.setSoHieu(res.getInt("TaiLieu.soHieu"));
                tapChi.setISBN(res.getString("TaiLieu.isbn"));
                tapChi.setTen(res.getString("TaiLieu.ten"));
                tapChi.setNamXuatBan(res.getInt("TaiLieu.namXuatBan"));
                tapChi.setNhaXuatBan(res.getString("TaiLieu.nhaXuatBan"));
                tapChi.setTinhTrang(res.getString("TaiLieu.tinhTrang"));
                tapChi.setMoTa(res.getString("TaiLieu.moTa"));
                tapChi.setSoTrang(res.getInt("TaiLieu.soTrang"));
                tapChi.setAnhBia(res.getBytes("TaiLieu.anhBia"));
                tapChi.setSoHieuXepGia(res.getString("TaiLieu.soHieuXepGia"));
                tapChi.setSoLuong(res.getInt("TaiLieu.soLuong"));
                tapChi.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapkho").toLocalDate());
                tapChi.setFileTaiLieu(res.getBytes("TaiLieu.fileTaiLieu"));
                tapChi.setLinhVuc(res.getString("TapChi.linhVuc"));
                danhSachTaiLieu.add(tapChi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TapChi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTaiLieu;
    }

    @Override
    public void them() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "insert into TaiLieu values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getISBN());
            stmt.setString(2, this.getTen());
            stmt.setInt(3, this.getNamXuatBan());
            stmt.setString(4, this.getNhaXuatBan());
            stmt.setString(5, this.getTinhTrang());
            stmt.setString(6, this.getMoTa());
            stmt.setInt(7, this.getSoTrang());
            stmt.setBytes(8, this.getAnhBia());
            stmt.setString(9, this.getSoHieuXepGia());
            stmt.setInt(10, this.getSoLuong());
            stmt.setDate(11, java.sql.Date.valueOf(this.getNgayNhapkho()));
            stmt.setBytes(12, this.getFileTaiLieu());
            stmt.executeUpdate();
            res = stmt.getGeneratedKeys();
            res.next();

            int soHieu = res.getInt(1);
            sql = "insert into TapChi values (?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            stmt.setString(2, this.linhVuc);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TapChi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sua() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update TaiLieu set isbn = ?, ten = ?, namXuatBan = ?, nhaXuatBan = ?, tinhTrang = ?, moTa = ?, soTrang = ?, anhBia = ?, soHieuXepGia = ?, soLuong = ?, ngayNhapKho = ?, fileTaiLieu = ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, this.getISBN());
            stmt.setString(2, this.getTen());
            stmt.setInt(3, this.getNamXuatBan());
            stmt.setString(4, this.getNhaXuatBan());
            stmt.setString(5, this.getTinhTrang());
            stmt.setString(6, this.getMoTa());
            stmt.setInt(7, this.getSoTrang());
            stmt.setBytes(8, this.getAnhBia());
            stmt.setString(9, this.getSoHieuXepGia());
            stmt.setInt(10, this.getSoLuong());
            stmt.setDate(11, java.sql.Date.valueOf(this.getNgayNhapkho()));
            stmt.setBytes(12, this.getFileTaiLieu());
            stmt.setInt(13, this.getSoHieu());
            stmt.executeUpdate();

            sql = "update TapChi set linhVuc = ? where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, this.linhVuc);
            stmt.setInt(2, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TapChi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void xoa() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from TapChi where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();

            sql = "delete from TaiLieu where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TapChi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<TaiLieu> timKiemTheoTen(String ten) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<TaiLieu> danhSachTaiLieu = new ArrayList<>();
        String sql = "select * from TaiLieu, TapChi where TaiLieu.soHieu = TapChi.soHieu and TaiLieu.ten like ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + ten + "%");
            res = stmt.executeQuery();
            while (res.next()) {
                TapChi tapChi = new TapChi();
                tapChi.setSoHieu(res.getInt("TaiLieu.soHieu"));
                tapChi.setISBN(res.getString("TaiLieu.isbn"));
                tapChi.setTen(res.getString("TaiLieu.ten"));
                tapChi.setNamXuatBan(res.getInt("TaiLieu.namXuatBan"));
                tapChi.setNhaXuatBan(res.getString("TaiLieu.nhaXuatBan"));
                tapChi.setTinhTrang(res.getString("TaiLieu.tinhTrang"));
                tapChi.setMoTa(res.getString("TaiLieu.moTa"));
                tapChi.setSoTrang(res.getInt("TaiLieu.soTrang"));
                tapChi.setAnhBia(res.getBytes("TaiLieu.anhBia"));
                tapChi.setSoHieuXepGia(res.getString("TaiLieu.soHieuXepGia"));
                tapChi.setSoLuong(res.getInt("TaiLieu.soLuong"));
                tapChi.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapkho").toLocalDate());
                tapChi.setFileTaiLieu(res.getBytes("TaiLieu.fileTaiLieu"));
                tapChi.setLinhVuc(res.getString("TapChi.linhVuc"));
                danhSachTaiLieu.add(tapChi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TapChi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTaiLieu;
    }

    public ArrayList<TaiLieu> timKiemTheoLinhVuc(String linhVuc) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<TaiLieu> danhSachTaiLieu = new ArrayList<>();
        String sql = "select * from TaiLieu, TapChi where TaiLieu.soHieu = TapChi.soHieu and TapChi.linhVuc like ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + linhVuc + "%");
            res = stmt.executeQuery();
            while (res.next()) {
                TapChi tapChi = new TapChi();
                tapChi.setSoHieu(res.getInt("TaiLieu.soHieu"));
                tapChi.setISBN(res.getString("TaiLieu.isbn"));
                tapChi.setTen(res.getString("TaiLieu.ten"));
                tapChi.setNamXuatBan(res.getInt("TaiLieu.namXuatBan"));
                tapChi.setNhaXuatBan(res.getString("TaiLieu.nhaXuatBan"));
                tapChi.setTinhTrang(res.getString("TaiLieu.tinhTrang"));
                tapChi.setMoTa(res.getString("TaiLieu.moTa"));
                tapChi.setSoTrang(res.getInt("TaiLieu.soTrang"));
                tapChi.setAnhBia(res.getBytes("TaiLieu.anhBia"));
                tapChi.setSoHieuXepGia(res.getString("TaiLieu.soHieuXepGia"));
                tapChi.setSoLuong(res.getInt("TaiLieu.soLuong"));
                tapChi.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapkho").toLocalDate());
                tapChi.setFileTaiLieu(res.getBytes("TaiLieu.fileTaiLieu"));
                tapChi.setLinhVuc(res.getString("TapChi.linhVuc"));
                danhSachTaiLieu.add(tapChi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TapChi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTaiLieu;
    }

    /**
     * @return the linhVuc
     */
    public String getLinhVuc() {
        return linhVuc;
    }

    /**
     * @param linhVuc the linhVuc to set
     */
    public void setLinhVuc(String linhVuc) {
        this.linhVuc = linhVuc;
    }
    
    public Object[] getValues(){
        return new Object[]{this.getSoHieu(), this.getISBN(), this.getTen(), this.getNamXuatBan(), this.getNhaXuatBan(), this.getTinhTrang(), this.getSoTrang(), this.getSoHieuXepGia(), this.getSoLuong(), this.getNgayNhapkho().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), this.getLinhVuc()};
    }
}
