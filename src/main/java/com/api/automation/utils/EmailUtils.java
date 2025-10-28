package com.api.automation.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtils {
    private static final String FROM_EMAIL = "your-email@company.com";
    private static final String EMAIL_PASSWORD = "your-email-password";
    private static final String[] TO_EMAILS = {"client1@company.com", "client2@company.com"};

    public static void sendTestReport(String reportPath) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            
            // Add recipients
            for (String email : TO_EMAILS) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            }

            message.setSubject("API Test Automation Report - " + java.time.LocalDate.now());
            
            // Create the message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find attached the API test automation report.");

            // Create the attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(reportPath);

            // Create multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Test report sent successfully via email.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
