/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.controllers;

import com.sss.wc.facades.WebUserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author User
 */
@Named
@SessionScoped
public class CommonController implements Serializable {

    @EJB
    WebUserFacade webUserFacade;

    /**
     * Creates a new instance of LanguageController
     */
    public CommonController() {
    }

    public String toMaintenance() {
        return "/maintenance/index";
    }

    public String toAdministration() {
        return "/admin/index";
    }

    public Date firstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.getActualMinimum(Calendar.MONTH));
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }

    public Date lastDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.getActualMaximum(Calendar.MONTH));
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
        return c.getTime();
    }

    public String doubleToString(Double d) {
        if (d == null) {
            d = 0.0;
        }
        return String.format("%1$,.2f", d);
    }

    
    
    
     @SuppressWarnings("unused")
//    @Schedule(minute = "1", second = "1", dayOfMonth = "*", month = "*", year = "*", hour = "1", persistent = false)
    @Schedule(minute = "59", second = "59", dayOfMonth = "*", month = "*", year = "*", hour = "*", persistent = false)
//    @Schedule(minute = "59", second = "59", hour = "23", dayOfMonth = "Last", info = "2nd Scheduled Timer", persistent = false)
//    @Schedule(second="*/1", minute="*",hour="*", persistent=false)
    public void myTimer() {
        long userCount = 0;
        String errorMessage;
        try {
            userCount = webUserFacade.count();
            sendEmail1("sunila.soft@gmail.com",  "SE is OK", "SE is OK");
        } catch (Exception e) {
            errorMessage = e.getMessage();
            sendEmail1("Error in SE", errorMessage);
        }
        System.out.println("userCount = " + userCount);
    }

    public void sendEmail1(String messageHeading, String messageBody) {
        sendEmail1("buddhika.ari@gmail.com", messageHeading, messageBody, "lakmedipro@gmail.com", "Bud7NilG");
    }

    public void sendEmail1(String toEmail, String messageHeading, String messageBody) {
        sendEmail1(toEmail, messageHeading, messageBody, "lakmedipro@gmail.com", "Bud7NilG");
    }

    public void sendEmail1(String toEmail, String messageHeading, String messageBody, String fromUserName, String fromPassword) {
        final String username = fromUserName;
        final String password = fromPassword;
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUserName));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject(messageHeading);
            message.setText(messageBody);
//            BodyPart msbp1 = new MimeBodyPart();
//            msbp1.setText("Final Lab report of patient");

//            MimeBodyPart msbp2 = new MimeBodyPart();
//            DataSource source = new FileDataSource("LabReport.pdf");
//            msbp2.setDataHandler(new DataHandler(source));
//            msbp2.setFileName("/Labreport.pdf");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(msbp1);
//            multipart.addBodyPart(msbp2);
//            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Send Successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    
    
}
