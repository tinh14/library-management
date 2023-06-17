/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import Entity.DocGia;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author tinhlam
 */
public class Email {

    private String tieuDe;
    private String noiDung;
    private ArrayList<DocGia> danhSachDocGia;

    private static final String USERNAME = "gaconchaylontonj1@gmail.com";
    private static final String PASSWORD = "kqaxqlyggkgysqjb";

    public void guiEmail() {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        try {
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(USERNAME));

            for (DocGia docGia : danhSachDocGia) {
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(docGia.getEmail(), false));
            }

            msg.setSubject(tieuDe);
            msg.setText(noiDung);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the tieuDe
     */
    public String getTieuDe() {
        return tieuDe;
    }

    /**
     * @param tieuDe the tieuDe to set
     */
    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    /**
     * @return the noiDung
     */
    public String getNoiDung() {
        return noiDung;
    }

    /**
     * @param noiDung the noiDung to set
     */
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    /**
     * @return the danhSachDocgia
     */
    public ArrayList<DocGia> getDanhSachDocGia() {
        return danhSachDocGia;
    }

    /**
     * @param danhSachDocgia the danhSachDocgia to set
     */
    public void setDanhSachDocGia(ArrayList<DocGia> danhSachDocGia) {
        this.danhSachDocGia = danhSachDocGia;
    }

}
