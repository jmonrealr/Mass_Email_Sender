package com.snorlax;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Service to send Email with SMTP
 */
public class EmailSenderService {

    private String remitente="", clave="", destino="", asunto = "", contenido = "";

    ArrayList<String> destinatarios = new ArrayList<>();
    ArrayList<String> fileRoute = new ArrayList<>();
    ArrayList<String> fileName = new ArrayList<>();

    public EmailSenderService(String user, String pass, ArrayList des, String asu, String content, ArrayList fileR, ArrayList fileN  ){
        remitente=user;
        clave=pass;
        destinatarios=des;
        asunto=asu;
        contenido=content;
        fileRoute=fileR;
        fileName=fileN;
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
