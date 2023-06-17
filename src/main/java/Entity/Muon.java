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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tinhlam
 */
public class Muon implements CRUD<Muon> {

    private DocGia docGia;
    private ArrayList<DongMuon> danhSachDongMuon;
    private LocalDateTime thoiGianMuon;
    private LocalDateTime thoiGianTraDuKien;
    private LocalDateTime thoiGianTraThucTe;
    private String ghiChu;

    public Muon() {
    }

    public Muon(DocGia docGia, ArrayList<DongMuon> danhSachDongMuon, LocalDateTime thoiGianMuon, LocalDateTime thoiGianTraDuKien, LocalDateTime thoiGianTraThucTe, String ghiChu) {
        this.docGia = docGia;
        this.danhSachDongMuon = danhSachDongMuon;
        this.thoiGianMuon = thoiGianMuon;
        this.thoiGianTraDuKien = thoiGianTraDuKien;
        this.thoiGianTraThucTe = thoiGianTraThucTe;
        this.ghiChu = ghiChu;
    }

    @Override
    public ArrayList<Muon> layDanhSach() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from Muon, DongMuon, Abstract_NV_DG where Muon.soHieuDocGia = DongMuon.soHieuDocGia and Muon.thoiGianMuon = DongMuon.thoiGianMuon and Muon.soHieuDocGia = Abstract_NV_DG.soHieu";
        ArrayList<Muon> danhSachMuon = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, Muon> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("Muon.soHieuDocGia");
                String email = res.getString("Abstract_NV_DG.email");
                LocalDateTime thoiGianMuon = res.getTimestamp("Muon.thoiGianMuon").toLocalDateTime();
                LocalDateTime thoiGianTraDuKien = res.getTimestamp("Muon.thoiGianTraDuKien").toLocalDateTime();
                LocalDateTime thoiGianTraThucTe = (res.getTimestamp("Muon.thoiGianTraThucTe") == null) ? null : res.getTimestamp("Muon.thoiGianTraThucTe").toLocalDateTime();
                String ghiChu = res.getString("Muon.ghiChu");
                int soHieuTaiLieu = res.getInt("DongMuon.soHieuTaiLieu");
                int soLuong = res.getInt("DongMuon.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianMuon);
                if (!map.containsKey(p)) {
                    Muon muon = new Muon();
                    DocGia docGia = new DocGia();
                    docGia.setSoHieu(soHieuDocGia);
                    docGia.setEmail(email);
                    muon.setDocGia(docGia);
                    muon.setThoiGianMuon(thoiGianMuon);
                    muon.setThoiGianTraDuKien(thoiGianTraDuKien);
                    muon.setThoiGianTraThucTe(thoiGianTraThucTe);
                    muon.setDanhSachDongMuon(new ArrayList<>());
                    muon.setGhiChu(ghiChu);
                    map.put(p, muon);
                }

                Muon muon = map.get(p);
                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongMuon dm = new DongMuon(s, soLuong);
                muon.getDanhSachDongMuon().add(dm);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, Muon> set : map.entrySet()) {
                Muon muon = set.getValue();

                danhSachMuon.add(muon);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachMuon;
    }

    @Override
    public void them() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "insert into Muon values (?, ?, ?, ?, ?)";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(this.getThoiGianTraDuKien()));
            stmt.setTimestamp(4, null);
            stmt.setString(5, this.ghiChu);
            stmt.executeUpdate();

            int total = 0;
            sql = "insert into DongMuon values (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            for (DongMuon dongMuon : this.getDanhSachDongMuon()) {
                stmt.setInt(3, dongMuon.getTaiLieu().getSoHieu());
                stmt.setInt(4, dongMuon.getSoLuong());
                stmt.executeUpdate();

                TaiLieu taiLieu = dongMuon.getTaiLieu();
                taiLieu.muon(dongMuon.getSoLuong());
                total += dongMuon.getSoLuong();
            }

            this.getDocGia().muon(total);

        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void sua() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from DongMuon where soHieuDocGia = ? and thoiGianMuon = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.executeUpdate();
            
            int total = 0;
            for (DongMuon dongMuon : this.getDanhSachDongMuon()) {
                TaiLieu taiLieu = dongMuon.getTaiLieu();
                taiLieu.tra(dongMuon.getSoLuong());
                total += dongMuon.getSoLuong();
            }
            this.docGia.tra(total);

            sql = "update Muon set soHieuDocGia = ?, thoiGianMuon = ?, thoiGianTraDuKien = ?, thoiGianTraThucTe = ?, ghiChu = ? where soHieuDocGia = ? and thoiGianMuon = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(this.getThoiGianTraDuKien()));
            stmt.setTimestamp(4, null);
            stmt.setString(5, this.getGhiChu());
            stmt.setInt(6, this.getDocGia().getSoHieu());
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.executeUpdate();

            total = 0;
            sql = "insert into DongMuon values (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            for (DongMuon dongMuon : this.getDanhSachDongMuon()) {
                stmt.setInt(3, dongMuon.getTaiLieu().getSoHieu());
                stmt.setInt(4, dongMuon.getSoLuong());
                stmt.executeUpdate();

                TaiLieu taiLieu = dongMuon.getTaiLieu();
                taiLieu.muon(dongMuon.getSoLuong());
                total += dongMuon.getSoLuong();
            }

            this.getDocGia().muon(total);

        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void xoa() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from DongMuon where soHieuDocGia = ? and thoiGianMuon = ? and soHieuTaiLieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            
            int total = 0;
            for (DongMuon dongMuon : this.getDanhSachDongMuon()) {
                stmt.setInt(3, dongMuon.getTaiLieu().getSoHieu());
                stmt.executeUpdate();
                
                TaiLieu taiLieu = dongMuon.getTaiLieu();
                taiLieu.tra(dongMuon.getSoLuong());
                total += dongMuon.getSoLuong();
            }
            this.docGia.tra(total);
            
            sql = "delete from Muon where soHieuDocGia = ? and thoiGianMuon = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Muon> layDanhSachTheoSoNgayMuonConLai(int soNgayConLai) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from Muon, DongMuon, Abstract_NV_DG where Muon.soHieuDocGia = DongMuon.soHieuDocGia and Muon.thoiGianMuon = DongMuon.thoiGianMuon and Muon.soHieuDocGia = Abstract_NV_DG.soHieu and Muon.thoiGianTraThucTe is null and (abs(datediff(curdate(), Muon.thoiGianTraDuKien)) = ?)";
        ArrayList<Muon> danhSachMuon = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, Muon> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soNgayConLai);
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("Muon.soHieuDocGia");
                String email = res.getString("Abstract_NV_DG.email");
                LocalDateTime thoiGianMuon = res.getTimestamp("Muon.thoiGianMuon").toLocalDateTime();
                LocalDateTime thoiGianTraDuKien = res.getTimestamp("Muon.thoiGianTraDuKien").toLocalDateTime();
                LocalDateTime thoiGianTraThucTe = (res.getTimestamp("Muon.thoiGianTraThucTe") == null) ? null : res.getTimestamp("Muon.thoiGianTraThucTe").toLocalDateTime();
                String ghiChu = res.getString("Muon.ghiChu");
                int soHieuTaiLieu = res.getInt("DongMuon.soHieuTaiLieu");
                int soLuong = res.getInt("DongMuon.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianMuon);
                if (!map.containsKey(p)) {
                    Muon muon = new Muon();
                    DocGia docGia = new DocGia();
                    docGia.setSoHieu(soHieuDocGia);
                    docGia.setEmail(email);
                    muon.setDocGia(docGia);
                    muon.setThoiGianMuon(thoiGianMuon);
                    muon.setThoiGianTraDuKien(thoiGianTraDuKien);
                    muon.setThoiGianTraThucTe(thoiGianTraThucTe);
                    muon.setDanhSachDongMuon(new ArrayList<>());
                    muon.setGhiChu(ghiChu);
                    map.put(p, muon);
                }

                Muon muon = map.get(p);
                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongMuon dm = new DongMuon(s, soLuong);
                muon.getDanhSachDongMuon().add(dm);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, Muon> set : map.entrySet()) {
                Muon muon = set.getValue();

                danhSachMuon.add(muon);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachMuon;
    }

    public void muon(DatTruoc datTruoc) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "insert into Muon values (?, ?, ?, ?, ?)";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(this.getThoiGianTraDuKien()));
            stmt.setTimestamp(4, null);
            stmt.setString(5, this.ghiChu);
            stmt.executeUpdate();

            int total = 0;
            sql = "insert into DongMuon values (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getDocGia().getSoHieu());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            for (DongMuon dongMuon : this.getDanhSachDongMuon()) {
                stmt.setInt(3, dongMuon.getTaiLieu().getSoHieu());
                stmt.setInt(4, dongMuon.getSoLuong());
                stmt.executeUpdate();

                TaiLieu taiLieu = dongMuon.getTaiLieu();
                taiLieu.muon(dongMuon.getSoLuong());
                total += dongMuon.getSoLuong();
            }

            this.getDocGia().muon(total);

            datTruoc.muon();
        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void nhan() {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update Muon set thoiGianTraThucTe = ?, ghiChu = ? where soHieuDocGia = ? and thoiGianMuon = ?";
        try {
            if (this.thoiGianTraThucTe.compareTo(this.thoiGianTraDuKien) > 0) {
                int dif = Math.abs((int) ChronoUnit.DAYS.between(this.thoiGianTraThucTe, this.thoiGianTraDuKien));
                int def_pri = 10000;
                if (dif != 0) {
                    this.ghiChu = "Trễ " + dif + " ngày";
                    this.docGia.phatTien(dif * def_pri);
                }
            }

            stmt = con.prepareStatement(sql);
            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(this.getThoiGianTraThucTe()));
            stmt.setString(2, this.ghiChu);
            stmt.setInt(3, this.getDocGia().getSoHieu());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(this.getThoiGianMuon()));
            stmt.executeUpdate();

            int total = 0;
            for (DongMuon dongMuon : this.getDanhSachDongMuon()) {
                TaiLieu taiLieu = dongMuon.getTaiLieu();
                taiLieu.tra(dongMuon.getSoLuong());
                total += dongMuon.getSoLuong();
            }
            this.docGia.tra(total);

        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Muon> timKiemTheoThoiGian(LocalDate thoiGianBatDau, LocalDate thoiGianKetThuc) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from Muon, DongMuon, Abstract_NV_DG where Muon.soHieuDocGia = DongMuon.soHieuDocGia and Muon.thoiGianMuon = DongMuon.thoiGianMuon and Muon.soHieuDocGia = Abstract_NV_DG.soHieu and date(Muon.thoiGianMuon) between ? and ?";
        ArrayList<Muon> danhSachMuon = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, Muon> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(thoiGianBatDau));
            stmt.setDate(2, java.sql.Date.valueOf(thoiGianKetThuc));
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("Muon.soHieuDocGia");
                String email = res.getString("Abstract_NV_DG.email");
                LocalDateTime thoiGianMuon = res.getTimestamp("Muon.thoiGianMuon").toLocalDateTime();
                LocalDateTime thoiGianTraDuKien = res.getTimestamp("Muon.thoiGianTraDuKien").toLocalDateTime();
                LocalDateTime thoiGianTraThucTe = (res.getTimestamp("Muon.thoiGianTraThucTe") == null) ? null : res.getTimestamp("Muon.thoiGianTraThucTe").toLocalDateTime();
                String ghiChu = res.getString("Muon.ghiChu");

                int soHieuTaiLieu = res.getInt("DongMuon.soHieuTaiLieu");
                int soLuong = res.getInt("DongMuon.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianMuon);
                if (!map.containsKey(p)) {
                    Muon muon = new Muon();
                    DocGia docGia = new DocGia();
                    docGia.setSoHieu(soHieuDocGia);
                    docGia.setEmail(email);
                    muon.setDocGia(docGia);
                    muon.setThoiGianMuon(thoiGianMuon);
                    muon.setThoiGianTraDuKien(thoiGianTraDuKien);
                    muon.setThoiGianTraThucTe(thoiGianTraThucTe);
                    muon.setDanhSachDongMuon(new ArrayList<>());
                    muon.setGhiChu(ghiChu);
                    map.put(p, muon);
                }

                Muon muon = map.get(p);
                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongMuon dm = new DongMuon(s, soLuong);
                muon.getDanhSachDongMuon().add(dm);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, Muon> set : map.entrySet()) {
                Muon muon = set.getValue();
                danhSachMuon.add(muon);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Muon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachMuon;
    }

    public static ArrayList<Muon> timKiemTheoSoHieuDocGia(int soHieu) {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        String sql = "select * from Muon, DongMuon, Abstract_NV_DG where Muon.soHieuDocGia = DongMuon.soHieuDocGia and Muon.thoiGianMuon = DongMuon.thoiGianMuon and Muon.soHieuDocGia = ? and Muon.soHieuDocGia = Abstract_NV_DG.soHieu";
        ArrayList<Muon> danhSachMuon = new ArrayList<>();
        HashMap<HashMap<Integer, LocalDateTime>, Muon> map = new HashMap<>();
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            res = stmt.executeQuery();
            while (res.next()) {
                int soHieuDocGia = res.getInt("Muon.soHieuDocGia");
                String email = res.getString("Abstract_NV_DG.email");
                LocalDateTime thoiGianMuon = res.getTimestamp("Muon.thoiGianMuon").toLocalDateTime();
                LocalDateTime thoiGianTraDuKien = res.getTimestamp("Muon.thoiGianTraDuKien").toLocalDateTime();
                LocalDateTime thoiGianTraThucTe = (res.getTimestamp("Muon.thoiGianTraThucTe") == null) ? null : res.getTimestamp("Muon.thoiGianTraThucTe").toLocalDateTime();
                String ghiChu = res.getString("Muon.ghiChu");
                int soHieuTaiLieu = res.getInt("DongMuon.soHieuTaiLieu");
                int soLuong = res.getInt("DongMuon.soLuong");

                HashMap<Integer, LocalDateTime> p = new HashMap<>();
                p.put(soHieuDocGia, thoiGianMuon);
                if (!map.containsKey(p)) {
                    Muon muon = new Muon();
                    DocGia docGia = new DocGia();
                    docGia.setEmail(email);
                    docGia.setSoHieu(soHieuDocGia);
                    muon.setDocGia(docGia);
                    muon.setThoiGianMuon(thoiGianMuon);
                    muon.setThoiGianTraDuKien(thoiGianTraDuKien);
                    muon.setThoiGianTraThucTe(thoiGianTraThucTe);
                    muon.setDanhSachDongMuon(new ArrayList<>());
                    muon.setGhiChu(ghiChu);
                    map.put(p, muon);
                }

                Muon muon = map.get(p);
                Sach s = new Sach();
                s.setSoHieu(soHieuTaiLieu);
                DongMuon dm = new DongMuon(s, soLuong);
                muon.getDanhSachDongMuon().add(dm);
            }

            for (Map.Entry<HashMap<Integer, LocalDateTime>, Muon> set : map.entrySet()) {
                Muon muon = set.getValue();
                danhSachMuon.add(muon);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatTruoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachMuon;
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
     * @return the danhSachDongMuon
     */
    public ArrayList<DongMuon> getDanhSachDongMuon() {
        return danhSachDongMuon;
    }

    /**
     * @param danhSachDongMuon the danhSachDongMuon to set
     */
    public void setDanhSachDongMuon(ArrayList<DongMuon> danhSachDongMuon) {
        this.danhSachDongMuon = danhSachDongMuon;
    }

    /**
     * @return the thoiGianMuon
     */
    public LocalDateTime getThoiGianMuon() {
        return thoiGianMuon;
    }

    /**
     * @param thoiGianMuon the thoiGianMuon to set
     */
    public void setThoiGianMuon(LocalDateTime thoiGianMuon) {
        this.thoiGianMuon = thoiGianMuon;
    }

    /**
     * @return the thoiGianTraDuKien
     */
    public LocalDateTime getThoiGianTraDuKien() {
        return thoiGianTraDuKien;
    }

    /**
     * @param thoiGianTraDuKien the thoiGianTraDuKien to set
     */
    public void setThoiGianTraDuKien(LocalDateTime thoiGianTraDuKien) {
        this.thoiGianTraDuKien = thoiGianTraDuKien;
    }

    /**
     * @return the thoiGianTraThucTe
     */
    public LocalDateTime getThoiGianTraThucTe() {
        return thoiGianTraThucTe;
    }

    /**
     * @param thoiGianTraThucTe the thoiGianTraThucTe to set
     */
    public void setThoiGianTraThucTe(LocalDateTime thoiGianTraThucTe) {
        this.thoiGianTraThucTe = thoiGianTraThucTe;
    }

    /**
     * @return the ghiChu
     */
    public String getGhiChu() {
        return ghiChu;
    }

    /**
     * @param ghiChu the ghiChu to set
     */
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
