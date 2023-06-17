/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.ThuThu;

import Entity.DatTruoc;
import Entity.DongDatTruoc;
import Entity.DongMuon;
import Entity.Muon;
import Helper.ThongKe;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tinhlam
 */
public class GiaoDienMuonTra extends javax.swing.JFrame {

    JFrame parent;
    ArrayList<Muon> ds;

    /**
     * Creates new form GiaoDienTra
     */
    public GiaoDienMuonTra(JFrame parent) {
        this.parent = parent;
        initComponents();
        setup();
        loadDefaultList();
        renderTable();
    }

    private void setup() {
        final Color BLUE_COLOR = new Color(51, 102, 255, 255);
        final Color WHITE_COLOR = new Color(255, 255, 255, 255);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(BLUE_COLOR);
        headerRenderer.setForeground(WHITE_COLOR);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getModel().getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        jTable1.setGridColor(BLUE_COLOR);
        jTable1.setForeground(BLUE_COLOR);
        jTable1.setShowGrid(true);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    
    public void loadDefaultList(){
        ds = new Muon().layDanhSach();
    }
    
    public void renderTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Muon m : ds) {
            String sh = String.valueOf(m.getDocGia().getSoHieu());
            String tgm = m.getThoiGianMuon().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
            String tgtdk = m.getThoiGianTraDuKien().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
            String tgttt = (m.getThoiGianTraThucTe() == null) ? "" : m.getThoiGianTraThucTe().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
            String ghiChu = m.getGhiChu();
            ArrayList<DongMuon> dm = m.getDanhSachDongMuon();
            ArrayList<Integer> tl = new ArrayList<>();
            ArrayList<Integer> sl = new ArrayList<>();
            for (DongMuon dmm : dm) {
                tl.add(dmm.getTaiLieu().getSoHieu());
                sl.add(dmm.getSoLuong());
            }
            model.addRow(new Object[]{sh, tgm, tgtdk, tgttt, tl, sl, ghiChu});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        topLb = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        centerPanel = new javax.swing.JPanel();
        centerTopPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ngayBatDauDC = new com.toedter.calendar.JDateChooser();
        ngayKetThucDC = new com.toedter.calendar.JDateChooser();
        timTheoNgayBtn = new javax.swing.JButton();
        soHieuDocGiaTf = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        timTheoSoHieuDocGiaBtn = new javax.swing.JButton();
        nhanBtn = new javax.swing.JButton();
        themBtn = new javax.swing.JButton();
        suaBtn = new javax.swing.JButton();
        xoaBtn = new javax.swing.JButton();
        centerCenterPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel.setLayout(new java.awt.BorderLayout());

        topPanel.setBackground(new java.awt.Color(51, 102, 255));
        topPanel.setPreferredSize(new java.awt.Dimension(1000, 100));

        topLb.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        topLb.setForeground(new java.awt.Color(255, 255, 255));
        topLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topLb.setText("Mượn / Trả Tài Liệu");

        backBtn.setBackground(new java.awt.Color(255, 255, 255));
        backBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        backBtn.setForeground(new java.awt.Color(51, 102, 255));
        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/go_back.png"))); // NOI18N
        backBtn.setText("Trở Về");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(topLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(176, 176, 176))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(topLb, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(topPanel, java.awt.BorderLayout.PAGE_START);

        centerPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        centerPanel.setLayout(new java.awt.BorderLayout());

        centerTopPanel.setBackground(new java.awt.Color(255, 255, 255));
        centerTopPanel.setPreferredSize(new java.awt.Dimension(1000, 100));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setText("Ngày bắt đầu");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setText("Ngày kết thúc");

        ngayBatDauDC.setBackground(new java.awt.Color(51, 102, 255));
        ngayBatDauDC.setDateFormatString("dd-MM-yyyy");
        ngayBatDauDC.setMaxSelectableDate(new java.util.Date(253370743299000L));
        ngayBatDauDC.setPreferredSize(new java.awt.Dimension(150, 28));

        ngayKetThucDC.setBackground(new java.awt.Color(51, 102, 255));
        ngayKetThucDC.setDateFormatString("dd-MM-yyyy");
        ngayKetThucDC.setPreferredSize(new java.awt.Dimension(150, 28));

        timTheoNgayBtn.setBackground(new java.awt.Color(51, 102, 255));
        timTheoNgayBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timTheoNgayBtn.setForeground(new java.awt.Color(255, 255, 255));
        timTheoNgayBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        timTheoNgayBtn.setText("Tìm");
        timTheoNgayBtn.setBorder(null);
        timTheoNgayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timTheoNgayBtnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setText("Số hiệu độc giả");

        timTheoSoHieuDocGiaBtn.setBackground(new java.awt.Color(51, 102, 255));
        timTheoSoHieuDocGiaBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timTheoSoHieuDocGiaBtn.setForeground(new java.awt.Color(255, 255, 255));
        timTheoSoHieuDocGiaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        timTheoSoHieuDocGiaBtn.setText("Tìm");
        timTheoSoHieuDocGiaBtn.setBorder(null);
        timTheoSoHieuDocGiaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timTheoSoHieuDocGiaBtnActionPerformed(evt);
            }
        });

        nhanBtn.setBackground(new java.awt.Color(51, 102, 255));
        nhanBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nhanBtn.setForeground(new java.awt.Color(255, 255, 255));
        nhanBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/return_30px.png"))); // NOI18N
        nhanBtn.setText("Nhận");
        nhanBtn.setBorder(null);
        nhanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhanBtnActionPerformed(evt);
            }
        });

        themBtn.setBackground(new java.awt.Color(51, 102, 255));
        themBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        themBtn.setForeground(new java.awt.Color(255, 255, 255));
        themBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        themBtn.setText("Thêm");
        themBtn.setBorder(null);
        themBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themBtnActionPerformed(evt);
            }
        });

        suaBtn.setBackground(new java.awt.Color(51, 102, 255));
        suaBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        suaBtn.setForeground(new java.awt.Color(255, 255, 255));
        suaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        suaBtn.setText("Sửa");
        suaBtn.setBorder(null);
        suaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaBtnActionPerformed(evt);
            }
        });

        xoaBtn.setBackground(new java.awt.Color(51, 102, 255));
        xoaBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoaBtn.setForeground(new java.awt.Color(255, 255, 255));
        xoaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        xoaBtn.setText("Xóa");
        xoaBtn.setBorder(null);
        xoaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout centerTopPanelLayout = new javax.swing.GroupLayout(centerTopPanel);
        centerTopPanel.setLayout(centerTopPanelLayout);
        centerTopPanelLayout.setHorizontalGroup(
            centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerTopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ngayKetThucDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ngayBatDauDC, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timTheoNgayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soHieuDocGiaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timTheoSoHieuDocGiaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(themBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xoaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(nhanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        centerTopPanelLayout.setVerticalGroup(
            centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerTopPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerTopPanelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(ngayBatDauDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ngayKetThucDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(centerTopPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(themBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(suaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(xoaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(centerTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(timTheoNgayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(soHieuDocGiaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(timTheoSoHieuDocGiaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nhanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        centerPanel.add(centerTopPanel, java.awt.BorderLayout.PAGE_START);

        centerCenterPanel.setBackground(new java.awt.Color(255, 255, 255));
        centerCenterPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
        centerCenterPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Số hiệu độc giả", "Thời gian mượn", "Thời gian trả dự kiến", "Thời gian trả thực tế", "Số hiệu tài liệu", "Số lượng tương ứng", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setOpaque(false);
        jTable1.setSelectionBackground(new java.awt.Color(51, 204, 255));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        centerCenterPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        centerPanel.add(centerCenterPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(centerPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        this.parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void timTheoNgayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timTheoNgayBtnActionPerformed
        ArrayList<LocalDate> dates = ThongKe.getMinMaxDate("thoiGianMuon", "Muon");
        if (dates == null){
            return;
        }
        LocalDate bd = dates.get(0);
        LocalDate kt = dates.get(1);
        if (ngayBatDauDC.getDate() != null) {
            bd = ngayBatDauDC.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (ngayKetThucDC.getDate() != null) {
            kt = ngayKetThucDC.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        ds = Muon.timKiemTheoThoiGian(bd, kt);
        renderTable();
    }//GEN-LAST:event_timTheoNgayBtnActionPerformed

    private void timTheoSoHieuDocGiaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timTheoSoHieuDocGiaBtnActionPerformed
        if (soHieuDocGiaTf.getText().equals("")) {
            ds = new Muon().layDanhSach();
            renderTable();
            return;
        }
        try {
            int soHieu = Integer.valueOf(soHieuDocGiaTf.getText());
            ds = Muon.timKiemTheoSoHieuDocGia(soHieu);
        } catch (NumberFormatException e) {
            ds = new ArrayList<>();
        }
        renderTable();
    }//GEN-LAST:event_timTheoSoHieuDocGiaBtnActionPerformed

    private void nhanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhanBtnActionPerformed
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng dữ liệu");
            return;
        }
        if (jTable1.getModel().getValueAt(row, 3) != "") {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ");
            return;
        }
        Muon muon = ds.get(row);
        muon.setThoiGianTraThucTe(LocalDateTime.now());
        muon.nhan();
        JOptionPane.showMessageDialog(this, "Nhận thành công");
        ds = new Muon().layDanhSach();
        renderTable();
    }//GEN-LAST:event_nhanBtnActionPerformed

    private void themBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themBtnActionPerformed
        GiaoDienThemMuon form = new GiaoDienThemMuon(this);
        form.setVisible(true);
    }//GEN-LAST:event_themBtnActionPerformed

    private void suaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaBtnActionPerformed
        int index = jTable1.getSelectedRow();
        if (index == -1){
            JOptionPane.showMessageDialog(this, "Dữ liệu chọn không hợp lệ");
            return;
        }
        if (ds.get(index).getThoiGianTraThucTe() != null){
            JOptionPane.showMessageDialog(this, "Chỉ được phép sửa trên bản mượn chưa nhận");
            return;
        }
        GiaoDienSuaMuon form = new GiaoDienSuaMuon(this, ds.get(index));
        form.setVisible(true);
    }//GEN-LAST:event_suaBtnActionPerformed

    private void xoaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaBtnActionPerformed
        int index = jTable1.getSelectedRow();
        if (index == -1){
            JOptionPane.showMessageDialog(this, "Dữ liệu chọn không hợp lệ");
            return;
        }
        if (ds.get(index).getThoiGianTraThucTe() != null){
            JOptionPane.showMessageDialog(this, "Chỉ được phép xóa trên bản mượn chưa nhận");
            return;
        }
        int op = JOptionPane.showConfirmDialog(this, "Xác nhận xóa", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
        if (op == JOptionPane.OK_OPTION){
            ds.get(index).xoa();
            this.loadDefaultList();
            this.renderTable();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
    }//GEN-LAST:event_xoaBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel centerCenterPanel;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JPanel centerTopPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel mainPanel;
    private com.toedter.calendar.JDateChooser ngayBatDauDC;
    private com.toedter.calendar.JDateChooser ngayKetThucDC;
    private javax.swing.JButton nhanBtn;
    private javax.swing.JTextField soHieuDocGiaTf;
    private javax.swing.JButton suaBtn;
    private javax.swing.JButton themBtn;
    private javax.swing.JButton timTheoNgayBtn;
    private javax.swing.JButton timTheoSoHieuDocGiaBtn;
    private javax.swing.JLabel topLb;
    private javax.swing.JPanel topPanel;
    private javax.swing.JButton xoaBtn;
    // End of variables declaration//GEN-END:variables
}