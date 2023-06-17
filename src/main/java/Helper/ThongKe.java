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
import java.time.Month;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public abstract class ThongKe {

    private LocalDate thoiGianBatDau;
    private LocalDate thoiGianKetThuc;

    public ThongKe(LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc) {
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public static ArrayList<LocalDate> getMinMaxDate(String col, String tab) {
        try {
            ArrayList<LocalDate> minMaxDate = new ArrayList<>();
            Connection con = DatabaseManager.getConnection();
            PreparedStatement stmt;
            ResultSet res;
            String sql = "select min(" + col + ") from " + tab;
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            if (!res.next()) {
                return null;
            }
            if (res.getDate(1) == null){
                return null;
            }
            minMaxDate.add(res.getDate(1).toLocalDate());

            sql = "select max(" + col + ") from " + tab;
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            res.next();
            minMaxDate.add(res.getDate(1).toLocalDate());

            return minMaxDate;
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public abstract ThongKe layThongKe();

    /**
     * @return the thoiGianBatDau
     */
    public LocalDate getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    /**
     * @return the thoiGianBatDau
     */
    public LocalDate getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    /**
     * @param thoiGianBatDau the thoiGianBatDau to set
     */
    public void setThoiGianBatDau(LocalDate thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    /**
     * @param thoiGianKetThuc the thoiGianKetThuc to set
     */
    public void setThoiGianKetThuc(LocalDate thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

}
