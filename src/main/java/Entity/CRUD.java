/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.ArrayList;

/**
 *
 * @author tinhlam
 */
public interface CRUD<T> {
    public ArrayList<T> layDanhSach();
    public void them();
    public void sua();
    public void xoa();
}
