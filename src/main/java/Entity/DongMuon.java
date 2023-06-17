/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author tinhlam
 */
public class DongMuon {
    private TaiLieu taiLieu;
    private int soLuong;

    public DongMuon(){
    }
    
    public DongMuon(TaiLieu taiLieu, int soLuong) {
        this.taiLieu = taiLieu;
        this.soLuong = soLuong;
    }

    /**
     * @return the taiLieu
     */
    public TaiLieu getTaiLieu() {
        return taiLieu;
    }

    /**
     * @param taiLieu the taiLieu to set
     */
    public void setTaiLieu(TaiLieu taiLieu) {
        this.taiLieu = taiLieu;
    }

    /**
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    
}
