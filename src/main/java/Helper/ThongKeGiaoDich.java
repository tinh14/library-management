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
public class ThongKeGiaoDich extends ThongKe {

    private int tongSoPhieuMuon;
    private int tongSoPhieuMuonTraDungHan;
    private int tongSoPhieuMuonTraTreHan;
    private int soLuongBanDatTruoc;

    public ThongKeGiaoDich(LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc) {
        super(thoiGianBatDau, thoiGianKetThuc);
    }
    
    @Override
    public ThongKe layThongKe() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select count(*) from Muon where date(thoiGianMuon) between ? and ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setTongSoPhieuMuon(res.getInt(1));

            sql = "select count(*) from Muon where thoiGianTraThucTe <= thoiGianTraDuKien and date(thoiGianMuon) between ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setTongSoPhieuMuonTraDungHan(res.getInt(1));

            sql = "select count(*) from Muon where thoiGianTraThucTe > thoiGianTraDuKien and date(thoiGianMuon) between ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            res = stmt.executeQuery();
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setTongSoPhieuMuonTraTreHan(res.getInt(1));

            sql = "select count(*) from DatTruoc where date(thoiGianDat) between ? and ?";
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(this.getThoiGianBatDau()));
            stmt.setDate(2, java.sql.Date.valueOf(this.getThoiGianKetThuc()));
            if (res.isBeforeFirst()){
                res.next();
            }
            this.setSoLuongBanDatTruoc(res.getInt(1));

        } catch (SQLException ex) {
            Logger.getLogger(ThongKeGiaoDich.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    /**
     * @return the tongSoPhieuMuon
     */
    public int getTongSoPhieuMuon() {
        return tongSoPhieuMuon;
    }

    /**
     * @param tongSoPhieuMuon the tongSoPhieuMuon to set
     */
    public void setTongSoPhieuMuon(int tongSoPhieuMuon) {
        this.tongSoPhieuMuon = tongSoPhieuMuon;
    }

    /**
     * @return the tongSoPhieuMuonTraDungHan
     */
    public int getTongSoPhieuMuonTraDungHan() {
        return tongSoPhieuMuonTraDungHan;
    }

    /**
     * @param tongSoPhieuMuonTraDungHan the tongSoPhieuMuonTraDungHan to set
     */
    public void setTongSoPhieuMuonTraDungHan(int tongSoPhieuMuonTraDungHan) {
        this.tongSoPhieuMuonTraDungHan = tongSoPhieuMuonTraDungHan;
    }

    /**
     * @return the tongSoPhieuMuonTraTreHan
     */
    public int getTongSoPhieuMuonTraTreHan() {
        return tongSoPhieuMuonTraTreHan;
    }

    /**
     * @param tongSoPhieuMuonTraTreHan the tongSoPhieuMuonTraTreHan to set
     */
    public void setTongSoPhieuMuonTraTreHan(int tongSoPhieuMuonTraTreHan) {
        this.tongSoPhieuMuonTraTreHan = tongSoPhieuMuonTraTreHan;
    }

    /**
     * @return the soLuongBanDatTruoc
     */
    public int getSoLuongBanDatTruoc() {
        return soLuongBanDatTruoc;
    }

    /**
     * @param soLuongBanDatTruoc the soLuongBanDatTruoc to set
     */
    public void setSoLuongBanDatTruoc(int soLuongBanDatTruoc) {
        this.soLuongBanDatTruoc = soLuongBanDatTruoc;
    }
    
    public static Object[] getProperties(){
        return new Object[]{"Tổng số phiếu mượn", "Tổng số phiếu mượn trả đúng hạn", "Tổng số phiếu mượn trả trễ hạn", "Tổng số bản đặt trước"};
    }
    
    public Object[] getValues(){
        return new Object[]{this.getTongSoPhieuMuon(), this.getTongSoPhieuMuonTraDungHan(), this.getTongSoPhieuMuonTraTreHan(), this.getSoLuongBanDatTruoc()};
    }
}
