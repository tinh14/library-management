/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public class ThongKeTaiLieu extends ThongKe {
    private int soLuongDauSach;
    private int soLuongBanSach;
    private int soLuongDauTapChi;
    private int soLuongBanTapChi;
    private int soLuongAnPhamDienTu;

    public ThongKeTaiLieu(LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc) {
        super(thoiGianBatDau, thoiGianKetThuc);
    }
    
    public static Object[] getProperties(){
        return new Object[]{"Số lượng đầu sách", "Số lượng bản sách", "Số lượng đầu tạp chí", "Số lượng bản tạp chí", "Số lượng ấn phẩm điện tử"};
    }
    
    public Object[] getValues(){
        return new Object[]{this.getSoLuongDauSach(), this.getSoLuongBanSach(), this.getSoLuongDauTapChi(), this.getSoLuongBanTapChi(), this.getSoLuongAnPhamDienTu()};
    }
    
    @Override
    public ThongKe layThongKe() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select count(TaiLieu.soHieu) from TaiLieu, Sach where tailieu.soHieu = sach.soHieu and tailieu.ngayNhapKho BETWEEN ? and ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setSoLuongDauSach(res.getInt(1));

            sql = "select count(TaiLieu.soHieu) from TaiLieu, TapChi where tailieu.soHieu = tapchi.soHieu and tailieu.ngayNhapKho BETWEEN ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setSoLuongDauTapChi(res.getInt(1));

            sql = "select sum(TaiLieu.soLuong) from TaiLieu, Sach where tailieu.soHieu = sach.soHieu and tailieu.ngayNhapKho BETWEEN ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setSoLuongBanSach(res.getInt(1));

            sql = "select sum(TaiLieu.soLuong) from TaiLieu, TapChi where tailieu.soHieu = tapchi.soHieu and tailieu.ngayNhapKho BETWEEN ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setSoLuongBanTapChi(res.getInt(1));
            
            sql = "select count(soHieu) from tailieu where fileTaiLieu is not null and ngayNhapKho BETWEEN ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setSoLuongAnPhamDienTu(res.getInt(1));
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    /**
     * @return the soLuongDauSach
     */
    public int getSoLuongDauSach() {
        return soLuongDauSach;
    }

    /**
     * @param soLuongDauSach the soLuongDauSach to set
     */
    public void setSoLuongDauSach(int soLuongDauSach) {
        this.soLuongDauSach = soLuongDauSach;
    }

    /**
     * @return the soLuongBanSach
     */
    public int getSoLuongBanSach() {
        return soLuongBanSach;
    }

    /**
     * @param soLuongBanSach the soLuongBanSach to set
     */
    public void setSoLuongBanSach(int soLuongBanSach) {
        this.soLuongBanSach = soLuongBanSach;
    }

    /**
     * @return the soLuongDauTapChi
     */
    public int getSoLuongDauTapChi() {
        return soLuongDauTapChi;
    }

    /**
     * @param soLuongDauTapChi the soLuongDauTapChi to set
     */
    public void setSoLuongDauTapChi(int soLuongDauTapChi) {
        this.soLuongDauTapChi = soLuongDauTapChi;
    }

    /**
     * @return the soLuongBanTapChi
     */
    public int getSoLuongBanTapChi() {
        return soLuongBanTapChi;
    }

    /**
     * @param soLuongBanTapChi the soLuongBanTapChi to set
     */
    public void setSoLuongBanTapChi(int soLuongBanTapChi) {
        this.soLuongBanTapChi = soLuongBanTapChi;
    }

    /**
     * @return the soLuongAnPhamDienTu
     */
    public int getSoLuongAnPhamDienTu() {
        return soLuongAnPhamDienTu;
    }

    /**
     * @param soLuongAnPhamDienTu the soLuongAnPhamDienTu to set
     */
    public void setSoLuongAnPhamDienTu(int soLuongAnPhamDienTu) {
        this.soLuongAnPhamDienTu = soLuongAnPhamDienTu;
    }
    
    public static String[] getColumnNames(){
        return new String[]{"Số lượng đầu sách", "Số lượng bản sách", "Số lượng đầu tạp chí", "Số lượng bản tạp chí", "Số lượng ấn phẩm điện tử"};
    }
}
