package com.snorlax;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
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
    private List<File> files;
    private Transport transport;
    private MimeMessage message;
    private String remitente="", clave="", destino="", asunto = "", contenido = "";

    ArrayList<String> destinatarios = new ArrayList<>();
    ArrayList<String> fileRoute = new ArrayList<>();
    ArrayList<String> fileName = new ArrayList<>();

    /**
     * Constructor of the class who handled one destination
     * @param transport to be used to send the message
     * @param session
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

    public void sendMessage() throws MessagingException {
        for (String email: this.send) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
        this.message.setSubject(this.subject);
        BodyPart part = new MimeBodyPart();
        //part.setContent(this.body);
        part.setContent(this.body,"text/hmtl");
        Multipart multipart = new MimeMultipart();
        for (File file: this.files) {
            part.setDataHandler(new DataHandler(new FileDataSource(file.getPath())));
            part.setFileName(file.getName());
            multipart.addBodyPart(part);
        }
        this.message.setContent(multipart);
        this.transport.sendMessage(this.message, this.message.getAllRecipients());
    }



    public void Enviar () {


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);

        Session session = Session.getDefaultInstance(props);
        MimeMessage mensaje = new MimeMessage(session);

        try{
            for(int i=0; i<destinatarios.size(); i++) {
                mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatarios.get(i)));
            }
            mensaje.setSubject(asunto);

            BodyPart parte_Tex = new MimeBodyPart();
            parte_Tex.setContent(contenido, "text/html");

            MimeMultipart todas = new MimeMultipart();
            todas.addBodyPart(parte_Tex);

            if(fileName != null && fileRoute != null) {
                BodyPart parte_File = new MimeBodyPart();
                parte_File.setDataHandler(new DataHandler(new FileDataSource(fileRoute.get(0))));
                parte_File.setFileName(fileName.get(0));
                todas.addBodyPart(parte_File);
            }

            mensaje.setContent(todas);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();
            System.out.println("Se envio el mensjae...\n\n\n\n ¿o no?");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
