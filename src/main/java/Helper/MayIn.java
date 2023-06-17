/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import Entity.DocGia;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

/**
 *
 * @author tinhlam
 */
public class MayIn {

    public static void xuatFileExcel(ThongKe thongKe, String path) {
        final int PERIOD_ROW_INDEX = 1;
        final int PERIOD_COL_INDEX = 0;
        final int CREATION_DATE_ROW_INDEX = 2;
        final int CREATION_DATE_COL_INDEX = 0;
        final int VALUE_ROW_INDEX = 5;

        final String filePath;
        final String sheetName = "sheet1";
        final String pattern = "dd/MM/yyyy";
        if (thongKe instanceof ThongKeTaiLieu) {
            filePath = "/thong_ke_tai_lieu.xlsx";
        } else {
            filePath = "/thong_ke_giao_dich.xlsx";
        }

        Class cl = thongKe.getClass();
        Field[] fields = cl.getDeclaredFields();
        int len = fields.length;

        InputStream resourcePath;
        try {
            resourcePath = MayIn.class.getResource(filePath).openStream();
            XSSFWorkbook workbook = new XSSFWorkbook(resourcePath);

            XSSFSheet sheet = workbook.getSheet(sheetName);

            Row periodRow = sheet.getRow(PERIOD_ROW_INDEX);
            Cell periodCell = periodRow.getCell(PERIOD_COL_INDEX);
            
            String val1 = thongKe.getThoiGianBatDau().format(DateTimeFormatter.ofPattern(pattern));
            String val2 = thongKe.getThoiGianKetThuc().format(DateTimeFormatter.ofPattern(pattern));
            
            periodCell.setCellValue(val1 + " - " + val2);

            Row creationRow = sheet.getRow(CREATION_DATE_ROW_INDEX);
            Cell creationCell = creationRow.getCell(CREATION_DATE_COL_INDEX);
            creationCell.setCellValue(creationCell.getStringCellValue() + LocalDate.now().format(DateTimeFormatter.ofPattern(pattern)));
            Row valueRow = sheet.getRow(VALUE_ROW_INDEX);
            
            for (int i = 0; i < len; i++) {
                Cell valueCell = valueRow.getCell(i);
                try {
                    fields[i].setAccessible(true);
                    valueCell.setCellValue(Integer.valueOf(fields[i].get(thongKe).toString()));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(MayIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(new File(path));
                workbook.write(out);
                out.close();
            } catch (IOException e) {
                System.out.print(e);
            }

        } catch (IOException ex) {
            Logger.getLogger(MayIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void xuatFileWord(DocGia docGia, String path) {
        try {
            InputStream resourcePath = MayIn.class.getResource("/the_thu_vien.docx").openStream();
            final XWPFDocument document = new XWPFDocument(resourcePath);
            final XWPFTable table = document.getTables().get(0);

            List<XWPFParagraph> paragraphs0 = table.getRow(0).getCell(0).getParagraphs();
            for (XWPFParagraph para : paragraphs0) {
                for (XWPFRun r : para.getRuns()) {
                    CTR ctr = r.getCTR();
                    List<CTDrawing> drawings = ctr.getDrawingList();
                    for (int i = 0; i < drawings.size(); i++) {
                        ctr.removeDrawing(i);
                    }
                }
            }
            XWPFParagraph p = table.getRow(0).getCell(0).addParagraph();
            XWPFRun r = p.createRun();
            ByteArrayInputStream bis = new ByteArrayInputStream(docGia.getAnhDaiDien());
            r.addPicture(bis, XWPFDocument.PICTURE_TYPE_PNG, "filename", Units.toEMU(85), Units.toEMU(113));

            XWPFTableCell row2_cell2 = table.getRow(1).getCell(1);
            List<XWPFParagraph> paragraphs = row2_cell2.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    text = text.replace("${full_name}", docGia.getHo() + " " + docGia.getTen());
                    text = text.replace("${date_of_birth}", docGia.getNgaySinh().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    text = text.replace("${address}", docGia.getDiaChi());
                    run.setText(text, 0);
                }
            }

            XWPFTableCell row3_cell1 = table.getRow(2).getCell(0);
            List<XWPFParagraph> paragraphs3 = row3_cell1.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs3) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    text = text.replace("${id}", String.valueOf(docGia.getSoHieu()));
                    run.setText(text, 0);
                }
            }

            LocalDate current_date = LocalDate.now();
            XWPFTableCell row3_cell2 = table.getRow(2).getCell(1);
            List<XWPFParagraph> paragraphs2 = row3_cell2.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs2) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    text = text.replace("${day}", String.valueOf(current_date.getDayOfMonth()));
                    text = text.replace("${month}", String.valueOf(current_date.getMonth().getValue()));
                    text = text.replace("${year}", String.valueOf(current_date.getYear()));
                    run.setText(text, 0);
                }
            }

            FileOutputStream outStream = new FileOutputStream(path);
            document.write(outStream);
            outStream.close();
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MayIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(MayIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
