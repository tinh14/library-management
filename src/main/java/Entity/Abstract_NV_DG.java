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
public abstract class Abstract_NV_DG extends ConNguoi{
    private String soChungMinh;
    private String diaChi;
    private String email;
    private String soDienThoai;
    private byte[] anhDaiDien;

    public Abstract_NV_DG() {
    }

    public Abstract_NV_DG(String soChungMinh, String diaChi, String email, String soDienThoai, byte[] anhDaiDien, int soHieu, String ho, String ten, LocalDate ngaySinh) {
        super(soHieu, ho, ten, ngaySinh);
        this.soChungMinh = soChungMinh;
        this.diaChi = diaChi;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.anhDaiDien = anhDaiDien;
    }
    
    @Override
    public abstract ArrayList<ConNguoi> layDanhSach();

    @Override
    public abstract void them();

    @Override
    public abstract void sua();

    @Override
    public abstract void xoa();

    public abstract ArrayList<ConNguoi> timKiemTheoSoHieu(int soHieu);

    /**
     * @return the soChungMinh
     */
    public String getSoChungMinh() {
        return soChungMinh;
    }

    /**
     * @param soChungMinh the soChungMinh to set
     */
    public void setSoChungMinh(String soChungMinh) {
        this.soChungMinh = soChungMinh;
    }

    /**
     * @return the diaChi
     */
    public String getDiaChi() {
        return diaChi;
    }

    /**
     * @param diaChi the diaChi to set
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the soDienThoai
     */
    public String getSoDienThoai() {
        return soDienThoai;
    }

    /**
     * @param soDienThoai the soDienThoai to set
     */
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    /**
     * @return the anhDaiDien
     */
    public byte[] getAnhDaiDien() {
        return anhDaiDien;
    }

    /**
     * @param anhDaiDien the anhDaiDien to set
     */
    public void setAnhDaiDien(byte[] anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }
    
    
    
}
