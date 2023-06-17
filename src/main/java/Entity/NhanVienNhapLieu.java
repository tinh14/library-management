/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author tinhlam
 */
public class NhanVienNhapLieu extends NhanVien {

    public NhanVienNhapLieu() {
    }

    public NhanVienNhapLieu(String taiKhoan, String matKhau, String soChungMinh, String diaChi, String email, String soDienThoai, byte[] anhDaiDien, int soHieu, String ho, String ten, LocalDate ngaySinh) {
        super(taiKhoan, matKhau, soChungMinh, diaChi, email, soDienThoai, anhDaiDien, soHieu, ho, ten, ngaySinh);
    }

    @Override
    public ArrayList<ConNguoi> layDanhSach() {
        return null;
    }

    @Override
    public void them() {
    }

    @Override
    public void sua() {
    }

    @Override
    public void xoa() {
    }

    @Override
    public ArrayList<ConNguoi> timKiemTheoSoHieu(int soHieu) {
        return null;
    }
}
