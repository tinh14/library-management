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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public class DatTruoc {

    private DocGia docGia;
    private ArrayList<DongDatTruoc> danhSachDongDatTruoc;
    private LocalDateTime thoiGianDat;

    public DatTruoc() {
    }

    public DatTruoc(DocGia docGia, ArrayList<DongDatTruoc> danhSachDongDatTruoc, LocalDateTime thoiGianDat) {
        this.docGia = docGia;
        this.danhSachDongDatTruoc = danhSachDongDatTruoc;
        this.thoiGianDat = thoiGianDat;
    }

    public static ArrayList<DatTruoc> layDanhSach() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from DatTruoc, DongDatTruoc where DatTruoc.soHieuDocGia = DongDatTruoc.soHieuDocGia and DatTruoc.thoiGianDat = DongDatTruoc.thoiGianDat";
        ArrayList<DatTruoc> danhSachDatTruoc = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, DatTruoc> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("DatTruoc.soHieuDocGia");
                LocalDateTime thoiGianDat = res.getTimestamp("DatTruoc.thoiGianDat").toLocalDateTime();
                int soHieuTaiLieu = res.getInt("DongDatTruoc.soHieuTaiLieu");
                int soLuong = res.getInt("DongDatTruoc.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianDat);

                if (!map.containsKey(p)) {
                    DatTruoc dt = new DatTruoc();
                    DocGia docGia = new DocGia();
                    docGia.setSoHieu(soHieuDocGia);
                    dt.setDocGia(docGia);
                    dt.setThoiGianDat(thoiGianDat);
                    dt.setDanhSachDongDatTruoc(new ArrayList<>());
                    map.put(p, dt);
                }

                DatTruoc dt = map.get(p);

                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongDatTruoc dtt = new DongDatTruoc(s, soLuong);
                dt.getDanhSachDongDatTruoc().add(dtt);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, DatTruoc> set : map.entrySet()) {
                DatTruoc datTruoc = set.getValue();
                danhSachDatTruoc.add(datTruoc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachDatTruoc;
    }

    public void guiBanDatTruoc() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "insert into DatTruoc values (?, ?)";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.docGia.getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.thoiGianDat));
            stmt.executeUpdate();

            sql = "insert into DongDatTruoc values (?, ?, ?, ?)";

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.docGia.getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.thoiGianDat));
            for (DongDatTruoc d : this.danhSachDongDatTruoc) {
                stmt.setInt(3, d.getTaiLieu().getSoHieu());
                stmt.setInt(4, d.getSoLuong());
                stmt.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void datTruoc() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "SET foreign_key_checks = 0";
        try {
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

            sql = "update DatTruoc set soHieuDocGia = ? where soHieuDocGia = ? and thoiGianDat = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.docGia.getSoHieu());
            stmt.setInt(2, -1);
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(this.thoiGianDat));
            stmt.executeUpdate();

            sql = "update DongDatTruoc set soHieuDocGia = ? where soHieuDocGia = ? and thoiGianDat = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.docGia.getSoHieu());
            stmt.setInt(2, -1);
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(this.thoiGianDat));
            stmt.executeUpdate();

            sql = "SET foreign_key_checks = 1";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<DatTruoc> timKiemTheoThoiGian(LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from dattruoc, dongdattruoc where dattruoc.soHieuDocGia = dongdattruoc.soHieuDocGia and dattruoc.thoiGianDat = dongdattruoc.thoiGianDat and date(dattruoc.thoiGianDat) between ? and ?";
        ArrayList<DatTruoc> danhSachDatTruoc = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, ArrayList<DongDatTruoc>> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(thoiGianBatDau));
            stmt.setDate(2, java.sql.Date.valueOf(thoiGianKetThuc));
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("DatTruoc.soHieuDocGia");
                LocalDateTime thoiGianDat = res.getTimestamp("DatTruoc.thoiGianDat").toLocalDateTime();
                int soHieuTaiLieu = res.getInt("DongDatTruoc.soHieuTaiLieu");
                int soLuong = res.getInt("DongDatTruoc.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianDat);
                if (!map.containsKey(p)) {
                    map.put(p, new ArrayList<>());
                }
                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongDatTruoc ddt = new DongDatTruoc(s, soLuong);
                map.get(p).add(ddt);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, ArrayList<DongDatTruoc>> set : map.entrySet()) {
                for (Map.Entry<Integer, LocalDateTime> set2 : set.getKey().entrySet()) {
                    int soHieuDocGia = set2.getKey();
                    LocalDateTime thoiGianDat = set2.getValue();
                    ArrayList<DongDatTruoc> dsDongDatTruoc = set.getValue();
                    DocGia docGia = new DocGia();
                    docGia.setSoHieu(soHieuDocGia);
                    DatTruoc datTruoc = new DatTruoc(docGia, dsDongDatTruoc, thoiGianDat);
                    danhSachDatTruoc.add(datTruoc);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachDatTruoc;
    }

    public static ArrayList<DatTruoc> timKiemTheoSoHieuDocGia(int soHieu) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from dattruoc, dongdattruoc where dattruoc.soHieuDocGia = dongdattruoc.soHieuDocGia and dattruoc.thoiGianDat = dongdattruoc.thoiGianDat and dattruoc.soHieuDocgia = ?";
        ArrayList<DatTruoc> danhSachDatTruoc = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, ArrayList<DongDatTruoc>> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("DatTruoc.soHieuDocGia");
                LocalDateTime thoiGianDat = res.getTimestamp("DatTruoc.thoiGianDat").toLocalDateTime();
                int soHieuTaiLieu = res.getInt("DongDatTruoc.soHieuTaiLieu");
                int soLuong = res.getInt("DongDatTruoc.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianDat);
                if (!map.containsKey(p)) {
                    map.put(p, new ArrayList<>());
                }
                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongDatTruoc ddt = new DongDatTruoc(s, soLuong);
                map.get(p).add(ddt);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, ArrayList<DongDatTruoc>> set : map.entrySet()) {
                for (Map.Entry<Integer, LocalDateTime> set2 : set.getKey().entrySet()) {
                    int soHieuDocGia = set2.getKey();
                    LocalDateTime thoiGianDat = set2.getValue();
                    ArrayList<DongDatTruoc> dsDongDatTruoc = set.getValue();
                    DocGia docGia = new DocGia();
                    docGia.setSoHieu(soHieuDocGia);
                    DatTruoc datTruoc = new DatTruoc(docGia, dsDongDatTruoc, thoiGianDat);
                    danhSachDatTruoc.add(datTruoc);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachDatTruoc;
    }

    public void muon() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from DongDatTruoc where soHieuDocGia = ? and thoiGianDat = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.docGia.getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.thoiGianDat));
            stmt.executeUpdate();

            sql = "delete from DatTruoc where soHieuDocGia = ? and thoiGianDat = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.docGia.getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.thoiGianDat));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the docGia
     */
    public DocGia getDocGia() {
        return docGia;
    }

    /**
     * @param docGia the docGia to set
     */
    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }

    /**
     * @return the danhSachDongDatTruoc
     */
    public ArrayList<DongDatTruoc> getDanhSachDongDatTruoc() {
        return danhSachDongDatTruoc;
    }

    /**
     * @param danhSachDongDatTruoc the danhSachDongDatTruoc to set
     */
    public void setDanhSachDongDatTruoc(ArrayList<DongDatTruoc> danhSachDongDatTruoc) {
        this.danhSachDongDatTruoc = danhSachDongDatTruoc;
    }

    /**
     * @return the thoiGianDat
     */
    public LocalDateTime getThoiGianDat() {
        return thoiGianDat;
    }

    /**
     * @param thoiGianDat the thoiGianDat to set
     */
    public void setThoiGianDat(LocalDateTime thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public static Object[] getAtt() {
        return new Object[]{"Số hiệu độc giả", "Thời gian đặt", "Số hiệu tài liệu đặt", "Số lượng tương ứng"};
    }

}
