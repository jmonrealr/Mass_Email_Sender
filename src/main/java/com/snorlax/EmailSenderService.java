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
    private Session session;
    private String body;
    private List<File> files = null;
    private Transport transport;
    private MimeMessage message;
    private String remitente="", clave="", destino="", asunto = "", contenido = "";

    ArrayList<String> destinatarios = new ArrayList<>();
    ArrayList<String> fileRoute = new ArrayList<>();
    ArrayList<String> fileName = new ArrayList<>();

    /**
     * Constructor of the class who handled one destination
     * @param transport to be used to send the message
     * @param session with authentication properties
     * @param subject of the e-mail
     * @param send e-mail address who receive the message
     * @param message or body of the e-mail
     * @param files who be attached to the message
     */
    public EmailSenderService(Transport transport, Session session, String subject, String send, String message, List<File> files) {
        // To do...
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

    public void sendMessage() throws MessagingException, NullPointerException {
        for (String email: this.send) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
        this.message.setSubject(this.subject);
        BodyPart part = new MimeBodyPart();
        //part.setContent(this.body)
        System.out.println("Body " + body);
        part.setContent(this.body,"text/html");
        System.out.println(part.toString());
        //this.message.setText(this.body,"text/hmtl");
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
        //System.out.println(this.message.toString());
        this.message.setContent(multipart);
        this.transport.sendMessage(this.message, this.message.getAllRecipients());
    }
}
