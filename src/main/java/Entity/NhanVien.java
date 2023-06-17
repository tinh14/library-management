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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public abstract class NhanVien extends Abstract_NV_DG {
    private String taiKhoan;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public NhanVien(String taiKhoan, String matKhau, String soChungMinh, String diaChi, String email, String soDienThoai, byte[] anhDaiDien, int soHieu, String ho, String ten, LocalDate ngaySinh) {
        super(soChungMinh, diaChi, email, soDienThoai, anhDaiDien, soHieu, ho, ten, ngaySinh);
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }
    
    @Override
    public abstract ArrayList<ConNguoi> layDanhSach();

    @Override
    public abstract void them();

    @Override
    public abstract void sua();

    @Override
    public abstract void xoa();

    @Override
    public abstract ArrayList<ConNguoi> timKiemTheoSoHieu(int soHieu);

    public static NhanVien dangNhap(String taiKhoan, String matKhau){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        NhanVien nhanVien = null;
        String sql = "select * from ConNguoi, Abstract_NV_DG, NhanVien where ConNguoi.soHieu = abstract_nv_dg.soHieu and abstract_nv_dg.soHieu = nhanvien.soHieu and taiKhoan = ? and matKhau = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, taiKhoan);
            stmt.setString(2, matKhau);
            res = stmt.executeQuery();
            if (!res.next()){
                return nhanVien;
            }
            sql = "select * from ThuThu where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, res.getInt("soHieu"));
            ResultSet res2 = stmt.executeQuery();
            if (res2.next()){
                nhanVien = new ThuThu();
            }else {
                nhanVien = new NhanVienNhapLieu();
            }
            nhanVien.setSoHieu(res.getInt("soHieu"));
            nhanVien.setHo(res.getString("ho"));
            nhanVien.setTen(res.getString("ten"));
            nhanVien.setNgaySinh(res.getDate("ngaySinh").toLocalDate());
            nhanVien.setSoChungMinh(res.getString("soChungMinh"));
            nhanVien.setDiaChi(res.getString("diaChi"));
            nhanVien.setEmail(res.getString("email"));
            nhanVien.setSoDienThoai(res.getString("soDienThoai"));
            nhanVien.setAnhDaiDien(res.getBytes("anhDaiDien"));
            nhanVien.setTaiKhoan(taiKhoan);
            nhanVien.setMatKhau(matKhau);
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nhanVien;
    }
    
    /**
     * @return the taiKhoan
     */
    public String getTaiKhoan() {
        return taiKhoan;
    }

    /**
     * @param taiKhoan the taiKhoan to set
     */
    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    /**
     * @return the matKhau
     */
    public String getMatKhau() {
        return matKhau;
    }

    /**
     * @param matKhau the matKhau to set
     */
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
}
