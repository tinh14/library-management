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
public abstract class ConNguoi implements CRUD<ConNguoi>{
    private int soHieu;
    private String ho;
    private String ten;
    private LocalDate ngaySinh;

    public ConNguoi(){
    }
    
    public ConNguoi(int soHieu, String ho, String ten, LocalDate ngaySinh) {
        this.soHieu = soHieu;
        this.ho = ho;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
    }
    
    @Override
    public abstract ArrayList<ConNguoi> layDanhSach();

    @Override
    public abstract void them();

    @Override
    public abstract void sua();

    @Override
    public abstract void xoa();

    /**
     * @return the soHieu
     */
    public int getSoHieu() {
        return soHieu;
    }

    /**
     * @param soHieu the soHieu to set
     */
    public void setSoHieu(int soHieu) {
        this.soHieu = soHieu;
    }

    /**
     * @return the ho
     */
    public String getHo() {
        return ho;
    }

    /**
     * @param ho the ho to set
     */
    public void setHo(String ho) {
        this.ho = ho;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the ngaySinh
     */
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    /**
     * @param ngaySinh the ngaySinh to set
     */
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
}
