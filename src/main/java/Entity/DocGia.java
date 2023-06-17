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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public class DocGia extends Abstract_NV_DG {

    private int soTienCoc;
    private int soTaiLieuMuon;

    public DocGia() {
    }

    public DocGia(int soTienCoc, int soTaiLieuMuon, String soChungMinh, String diaChi, String email, String soDienThoai, byte[] anhDaiDien, int soHieu, String ho, String ten, LocalDate ngaySinh) {
        super(soChungMinh, diaChi, email, soDienThoai, anhDaiDien, soHieu, ho, ten, ngaySinh);
        this.soTienCoc = soTienCoc;
        this.soTaiLieuMuon = soTaiLieuMuon;
    }

    @Override
    public ArrayList<ConNguoi> layDanhSach() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<ConNguoi> danhSachDocGia = new ArrayList<>();
        String sql = "select * from ConNguoi, Abstract_NV_DG, DocGia where ConNguoi.soHieu = Abstract_NV_DG.soHieu and Abstract_NV_DG.soHieu = DocGia.soHieu";
        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                DocGia docGia = new DocGia();
                docGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                docGia.setHo(res.getString("ConNguoi.ho"));
                docGia.setTen(res.getString("ConNguoi.ten"));
                if (res.getDate("ConNguoi.ngaySinh") != null) {
                    docGia.setNgaySinh(res.getDate("ConNguoi.ngaySinh").toLocalDate());
                }
                docGia.setSoChungMinh(res.getString("Abstract_NV_DG.soChungMinh"));
                docGia.setDiaChi(res.getString("Abstract_NV_DG.diaChi"));
                docGia.setEmail(res.getString("Abstract_NV_DG.email"));
                docGia.setSoDienThoai(res.getString("Abstract_NV_DG.soDienThoai"));
                docGia.setAnhDaiDien(res.getBytes("Abstract_NV_DG.anhDaiDien"));
                docGia.setSoTienCoc(res.getInt("DocGia.soTienCoc"));
                docGia.setSoTaiLieuMuon(res.getInt("DocGia.soTaiLieuMuon"));
                danhSachDocGia.add(docGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachDocGia;
    }

    public static Object[] getProperties() {
        return new Object[]{"Số hiệu", "Họ", "Tên", "Ngày sinh", "Số chứng minh", "Địa chỉ", "Email", "Số điện thoại", "Số tiền cọc", "Số tài liệu mượn"};
    }

    public Object[] getValues() {
        return new Object[]{this.getSoHieu(), this.getHo(), this.getTen(), this.getNgaySinh(), this.getSoChungMinh(), this.getDiaChi(), this.getEmail(), this.getSoDienThoai(), this.getSoTienCoc(), this.getSoTaiLieuMuon()};
    }

    @Override
    public void them() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "insert into ConNguoi values (null, ?, ?, ?)";
        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getHo());
            stmt.setString(2, this.getTen());
            stmt.setDate(3, java.sql.Date.valueOf(this.getNgaySinh()));
            stmt.executeUpdate();
            res = stmt.getGeneratedKeys();
            res.next();
            int soHieu = res.getInt(1);

            sql = "insert into Abstract_NV_DG values (?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            stmt.setString(2, this.getSoChungMinh());
            stmt.setString(3, this.getDiaChi());
            stmt.setString(4, this.getEmail());
            stmt.setString(5, this.getSoDienThoai());
            stmt.setBytes(6, this.getAnhDaiDien());
            stmt.executeUpdate();

            sql = "insert into DocGia values(?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            stmt.setInt(2, soTienCoc);
            stmt.setInt(3, soTaiLieuMuon);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sua() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update ConNguoi set ho = ?, ten = ?, ngaySinh = ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getHo());
            stmt.setString(2, this.getTen());
            stmt.setDate(3, java.sql.Date.valueOf(this.getNgaySinh()));
            stmt.setInt(4, this.getSoHieu());
            stmt.executeUpdate();

            sql = "update Abstract_NV_DG set soChungMinh = ?, diaChi = ?, email = ?, soDienThoai = ?, anhDaiDien = ? where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, this.getSoChungMinh());
            stmt.setString(2, this.getDiaChi());
            stmt.setString(3, this.getEmail());
            stmt.setString(4, this.getSoDienThoai());
            stmt.setBytes(5, this.getAnhDaiDien());
            stmt.setInt(6, this.getSoHieu());
            stmt.executeUpdate();

            sql = "update DocGia set soTienCoc = ?, soTaiLieuMuon = ? where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soTienCoc);
            stmt.setInt(2, soTaiLieuMuon);
            stmt.setInt(3, this.getSoHieu());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void xoa() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from DocGia where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();

            sql = "delete from Abstract_NV_DG where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();

            sql = "delete from ConNguoi where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ConNguoi> timKiemTheoSoHieu(int soHieu) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<ConNguoi> danhSachDocGia = new ArrayList<>();
        String sql = "select * from ConNguoi, Abstract_NV_DG, DocGia where ConNguoi.soHieu = Abstract_NV_DG.soHieu and Abstract_NV_DG.soHieu = DocGia.soHieu and ConNguoi.soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            res = stmt.executeQuery();
            while (res.next()) {
                DocGia docGia = new DocGia();
                docGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                docGia.setHo(res.getString("ConNguoi.ho"));
                docGia.setTen(res.getString("ConNguoi.ten"));
                docGia.setNgaySinh(res.getDate("ConNguoi.ngaySinh").toLocalDate());
                docGia.setSoChungMinh(res.getString("Abstract_NV_DG.soChungMinh"));
                docGia.setDiaChi(res.getString("Abstract_NV_DG.diaChi"));
                docGia.setEmail(res.getString("Abstract_NV_DG.email"));
                docGia.setSoDienThoai(res.getString("Abstract_NV_DG.soDienThoai"));
                docGia.setAnhDaiDien(res.getBytes("Abstract_NV_DG.anhDaiDien"));
                docGia.setSoTienCoc(res.getInt("DocGia.soTienCoc"));
                docGia.setSoTaiLieuMuon(res.getInt("DocGia.soTaiLieuMuon"));
                danhSachDocGia.add(docGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachDocGia;
    }

    public void muon(int soLuong) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update DocGia set soTaiLieuMuon = soTaiLieuMuon + ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setInt(2, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tra(int soLuong) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update DocGia set soTaiLieuMuon = soTaiLieuMuon - ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setInt(2, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void phatTien(int soTien) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update DocGia set soTienCoc = soTienCoc - ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soTien);
            stmt.setInt(2, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DocGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the soTienCoc
     */
    public int getSoTienCoc() {
        return soTienCoc;
    }

    /**
     * @param soTienCoc the soTienCoc to set
     */
    public void setSoTienCoc(int soTienCoc) {
        this.soTienCoc = soTienCoc;
    }

    /**
     * @return the soTaiLieuMuon
     */
    public int getSoTaiLieuMuon() {
        return soTaiLieuMuon;
    }

    /**
     * @param soTaiLieuMuon the soTaiLieuMuon to set
     */
    public void setSoTaiLieuMuon(int soTaiLieuMuon) {
        this.soTaiLieuMuon = soTaiLieuMuon;
    }

}
