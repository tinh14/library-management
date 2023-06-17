package Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tinhlam
 */
public class DatabaseManager {
        private static final String HOST = "localhost";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE = "QL_ThuVien";
    private static final int PORT = 3306;
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private static Connection con = null;
    
    private static final String MYSQL_PATH = "D:\\xampp\\mysql\\bin";
    
    public static Connection getConnection(){
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public static void saoLuuDuLieu(String duongDan) {
        String command = MYSQL_PATH + "\\mysqldump -u " + USERNAME + " --password=" + PASSWORD + " --databases " + DATABASE + " -r " + duongDan;
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void khoiPhucDuLieu(String duongDan){
        String[] command = new String[]{MYSQL_PATH + "\\mysql", DATABASE, "-u" + USERNAME, "--password=" + PASSWORD, "-e", " source " + duongDan};
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
