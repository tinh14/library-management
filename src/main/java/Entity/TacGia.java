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
public class TacGia extends ConNguoi {

    public TacGia() {
    }

    public TacGia(int soHieu, String ho, String ten, LocalDate ngaySinh) {
        super(soHieu, ho, ten, ngaySinh);
    }
    
    public static Object[] getProperties(){
        return new Object[]{"Số hiệu", "Họ", "Tên", "Ngày sinh"};
    }
    
    public Object[] getValues(){
        return new Object[]{this.getSoHieu(), this.getHo(), this.getTen(), this.getNgaySinh().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))};
    }

    @Override
    public ArrayList<ConNguoi> layDanhSach(){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<ConNguoi> danhSachTacGia = new ArrayList<>();
        String sql = "select * from ConNguoi, TacGia where ConNguoi.soHieu = TacGia.soHieu";
        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()){
                TacGia tacGia = new TacGia();
                tacGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                tacGia.setHo(res.getString("ConNguoi.ho"));
                tacGia.setTen(res.getString("ConNguoi.ten"));
                tacGia.setNgaySinh(res.getDate("ConNguoi.ngaySinh").toLocalDate());
                danhSachTacGia.add(tacGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TacGia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTacGia;
    }

    @Override
    public void them(){
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
            
            sql = "insert into tacGia values (?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, soHieu);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TacGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sua(){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "update ConNguoi set ho = ?, ten = ?, ngaySinh = ? where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, this.getHo());
            stmt.setString(2, this.getTen());
            stmt.setDate(3, java.sql.Date.valueOf(this.getNgaySinh()));
            stmt.setInt(4, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TacGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void xoa(){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        String sql = "delete from TacGia where soHieu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();
            
            sql = "delete from ConNguoi where soHieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getSoHieu());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TacGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ConNguoi> timKiemTheoTen(String ten){
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt;
        ResultSet res;
        ArrayList<ConNguoi> danhSachTacGia = new ArrayList<>();
        String sql = "select * from ConNguoi, TacGia where ConNguoi.soHieu = TacGia.soHieu and (ConNguoi.ho like ? or ConNguoi.ten like ?)";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + ten + "%");
            stmt.setString(2, "%" + ten + "%");
            res = stmt.executeQuery();
            while (res.next()){
                TacGia tacGia = new TacGia();
                tacGia.setSoHieu(res.getInt("ConNguoi.soHieu"));
                tacGia.setHo(res.getString("ConNguoi.ho"));
                tacGia.setTen(res.getString("ConNguoi.ten"));
                tacGia.setNgaySinh(res.getDate("ConNguoi.ngaySinh").toLocalDate());
                danhSachTacGia.add(tacGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TacGia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSachTacGia;
    }
    
}
