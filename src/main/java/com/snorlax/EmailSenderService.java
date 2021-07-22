package com.snorlax;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Service to send Email with SMTP
 */
public class EmailSenderService {

    private String subject;
    private ArrayList<String> send;
    private String sent;
    private Session session;
    private String body;
    private List<File> files = null;
    private Transport transport;
    private MimeMessage message;

    /**
     * Constructor of the class who handled one destination
     * @param transport to be used to send the message
     * @param session with authentication properties
     * @param subject of the e-mail
     * @param sent e-mail address who receive the message
     * @param body or body of the e-mail
     * @param files who be attached to the message
     */
    public EmailSenderService(Transport transport, Session session, String subject, String sent, String body, List<File> files) {
        this.transport = transport;
        this.session = session;
        this.subject = subject;
        this.sent = sent;
        this.body = body;
        this.files = files;
        this.message = new MimeMessage(session);
    }

    /**
     * Constructor of the class who handled multiple destinations
     * @param transport to be used to send the message
     * @param session with authentication properties
     * @param subject of the e-mail
     * @param send e-mail address who receive the message
     * @param body or message of the e-mail
     * @param files who be attached to the message
     */
    public EmailSenderService(Transport transport, Session session, String subject, ArrayList<String> send, String body, List<File> files) {
        this.transport = transport;
        this.session = session;
        this.subject = subject;
        this.send = send;
        this.body = body;
        this.files = files;
        this.message = new MimeMessage(session);
    }

    /**
     *  Send the message with Current values from this Object
     * @throws MessagingException When the connections fails
     * @throws NullPointerException Null values
     */
    public void sendMessage() throws MessagingException, NullPointerException {
        this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.sent));
        this.message.setSubject(this.subject);
        BodyPart part = new MimeBodyPart();
        part.setContent(this.body,"text/html");
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(part);
        if (files != null){
            System.out.println("Files");
            for (File file: this.files) {
                BodyPart files = new MimeBodyPart();
                files.setDataHandler(new DataHandler(new FileDataSource(file.getPath())));
                files.setFileName(file.getName());
                multipart.addBodyPart(files);
            }
        }
        this.message.setText(this.body, "text/html");
        this.message.setContent(multipart);
        this.transport.sendMessage(this.message, this.message.getAllRecipients());
    }
}
