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
import javax.swing.text.DateFormatter;

/**
 *
 * @author tinhlam
 */
public class Sach extends TaiLieu {

    private String theLoai;
    private TacGia tacGia;

    public Sach() {
    }

    public Sach(String theLoai, TacGia tacGia, int soHieu, String isbn, String ten, int namXuatBan, String nhaXuatBan, String tinhTrang, String moTa, int soTrang, byte[] anhBia, String soHieuXepGia, int soLuong, LocalDate ngayNhapKho, byte[] fileTaiLieu) {
        super(soHieu, isbn, ten, namXuatBan, nhaXuatBan, tinhTrang, moTa, soTrang, anhBia, soHieuXepGia, soLuong, ngayNhapKho, fileTaiLieu);
        this.theLoai = theLoai;
        this.tacGia = tacGia;
    }

    @Override
    public ArrayList<TaiLieu> layDanhSach() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<TaiLieu> danhSachTaiLieu = new ArrayList<>();
        String sql = "select * from TaiLieu, Sach, ConNguoi where TaiLieu.soHieu = Sach.soHieu and Sach.soHieuTacGia = ConNguoi.soHieu";
        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Sach sach = new Sach();
                sach.setSoHieu(res.getInt("TaiLieu.soHieu"));
                sach.setISBN(res.getString("TaiLieu.isbn"));
                sach.setTen(res.getString("TaiLieu.ten"));
                sach.setNamXuatBan(res.getInt("TaiLieu.namXuatBan"));
                sach.setNhaXuatBan(res.getString("TaiLieu.nhaXuatBan"));
                sach.setTinhTrang(res.getString("TaiLieu.tinhTrang"));
                sach.setMoTa(res.getString("TaiLieu.moTa"));
                sach.setSoTrang(res.getInt("TaiLieu.soTrang"));
                sach.setAnhBia(res.getBytes("TaiLieu.anhBia"));
                sach.setSoHieuXepGia(res.getString("TaiLieu.soHieuXepGia"));
                sach.setSoLuong(res.getInt("TaiLieu.soLuong"));
                sach.setFileTaiLieu(res.getBytes("TaiLieu.fileTaiLieu"));
                sach.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapKho").toLocalDate());
                sach.setTheLoai(res.getString("Sach.theLoai"));
                TacGia tacGia = new TacGia();
                tacGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                tacGia.setHo(res.getString("ConNguoi.ho"));
                tacGia.setTen(res.getString("ConNguoi.ten"));
                sach.setTacGia(tacGia);
                danhSachTaiLieu.add(sach);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sach.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = "insert into Sach values (?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            stmt.setString(2, this.theLoai);
            stmt.setInt(3, this.tacGia.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Sach.class.getName()).log(Level.SEVERE, null, ex);
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

            sql = "update Sach set theLoai = ?, soHieuTacGia = ? where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, this.theLoai);
            stmt.setInt(2, this.tacGia.getSoHieu());
            stmt.setInt(3, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Sach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void xoa() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from Sach where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();

            sql = "delete from TaiLieu where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Sach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<TaiLieu> timKiemTheoTen(String ten) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<TaiLieu> danhSachTaiLieu = new ArrayList<>();
        String sql = "select * from TaiLieu, Sach, ConNguoi where TaiLieu.soHieu = Sach.soHieu and Sach.soHieuTacGia = ConNguoi.soHieu and TaiLieu.ten like ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + ten + "%");
            res = stmt.executeQuery();
            while (res.next()) {
                Sach sach = new Sach();
                sach.setSoHieu(res.getInt("TaiLieu.soHieu"));
                sach.setISBN(res.getString("TaiLieu.isbn"));
                sach.setTen(res.getString("TaiLieu.ten"));
                sach.setNamXuatBan(res.getInt("TaiLieu.namXuatBan"));
                sach.setNhaXuatBan(res.getString("TaiLieu.nhaXuatBan"));
                sach.setTinhTrang(res.getString("TaiLieu.tinhTrang"));
                sach.setMoTa(res.getString("TaiLieu.moTa"));
                sach.setSoTrang(res.getInt("TaiLieu.soTrang"));
                sach.setAnhBia(res.getBytes("TaiLieu.anhBia"));
                sach.setSoHieuXepGia(res.getString("TaiLieu.soHieuXepGia"));
                sach.setSoLuong(res.getInt("TaiLieu.soLuong"));
                sach.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapkho").toLocalDate());
                sach.setFileTaiLieu(res.getBytes("TaiLieu.fileTaiLieu"));
                sach.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapKho").toLocalDate());
                sach.setTheLoai(res.getString("Sach.theLoai"));
                TacGia tacGia = new TacGia();
                tacGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                tacGia.setHo(res.getString("ConNguoi.ho"));
                tacGia.setTen(res.getString("ConNguoi.ten"));
                sach.setTacGia(tacGia);
                danhSachTaiLieu.add(sach);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sach.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTaiLieu;
    }

    public ArrayList<TaiLieu> timKiemTheoTheLoai(String theLoai) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<TaiLieu> danhSachTaiLieu = new ArrayList<>();
        String sql = "select * from TaiLieu, Sach, ConNguoi where TaiLieu.soHieu = Sach.soHieu and Sach.soHieuTacGia = ConNguoi.soHieu and Sach.theLoai like ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + theLoai + "%");
            res = stmt.executeQuery();
            while (res.next()) {
                Sach sach = new Sach();
                sach.setSoHieu(res.getInt("TaiLieu.soHieu"));
                sach.setISBN(res.getString("TaiLieu.isbn"));
                sach.setTen(res.getString("TaiLieu.ten"));
                sach.setNamXuatBan(res.getInt("TaiLieu.namXuatBan"));
                sach.setNhaXuatBan(res.getString("TaiLieu.nhaXuatBan"));
                sach.setTinhTrang(res.getString("TaiLieu.tinhTrang"));
                sach.setMoTa(res.getString("TaiLieu.moTa"));
                sach.setSoTrang(res.getInt("TaiLieu.soTrang"));
                sach.setAnhBia(res.getBytes("TaiLieu.anhBia"));
                sach.setSoHieuXepGia(res.getString("TaiLieu.soHieuXepGia"));
                sach.setSoLuong(res.getInt("TaiLieu.soLuong"));
                sach.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapkho").toLocalDate());
                sach.setFileTaiLieu(res.getBytes("TaiLieu.fileTaiLieu"));
                sach.setNgayNhapkho(res.getDate("TaiLieu.ngayNhapKho").toLocalDate());
                sach.setTheLoai(res.getString("Sach.theLoai"));
                TacGia tacGia = new TacGia();
                tacGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                tacGia.setHo(res.getString("ConNguoi.ho"));
                tacGia.setTen(res.getString("ConNguoi.ten"));
                sach.setTacGia(tacGia);
                danhSachTaiLieu.add(sach);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sach.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTaiLieu;
    }

    /**
     * @return the theLoai
     */
    public String getTheLoai() {
        return theLoai;
    }

    /**
     * @param theLoai the theLoai to set
     */
    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    /**
     * @return the tacGia
     */
    public TacGia getTacGia() {
        return tacGia;
    }

    /**
     * @param tacGia the tacGia to set
     */
    public void setTacGia(TacGia tacGia) {
        this.tacGia = tacGia;
    }
    
    public static Object[] getProperties(){
        return new Object[]{"Số hiệu", "ISBN", "Tên", "Năm xuất bản", "Nhà xuất bản", "Tình trạng", "Số trang", "Số hiệu xếp giá", "Số lượng", "Ngày nhập kho", "Thể loại"};
    }
    
    public Object[] getValues(){
        return new Object[]{this.getSoHieu(), this.getISBN(), this.getTen(), this.getNamXuatBan(), this.getNhaXuatBan(), this.getTinhTrang(), this.getSoTrang(), this.getSoHieuXepGia(), this.getSoLuong(), this.getNgayNhapkho().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), this.getTheLoai()};
    }
}
