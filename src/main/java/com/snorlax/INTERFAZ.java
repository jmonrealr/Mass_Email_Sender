package com.snorlax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class INTERFAZ {
    private String remitente="", clave="", destino="", asunto = "", contenido = "";

            ArrayList<String>fileRoute = new ArrayList<>();
            ArrayList<String> fileName = new ArrayList<>();

    public void formulario() throws IOException {
        ArrayList<String> destinatarios = new ArrayList<>();
        Scanner leer = new Scanner(System.in);

        System.out.println("Sistema de envio de correos UPV");
        System.out.println("Ingrese su correo");
        remitente = leer.nextLine();
        System.out.println("Ingrese la contraseña de su cuenta");
        clave=leer.nextLine();

        int x=0;
        System.out.println("Ingresar destinatarios por excel 1, manualmente 2");
        int op=leer.nextInt();
        if(op==2)
            do {
                System.out.println("Ingrese el correo de destino");
                destino = leer.nextLine();
                destinatarios.add(destino);
                System.out.println("Si desea  dejar deingresar otro correo\n ingrese un -1");
                x= leer.nextInt();
            }while(x!=-1);
        else
        {
            leer.nextLine();
            System.out.println("Ingrese el nombre del archivo xls");
            String arch=leer.nextLine();
            DestinatariosExcel a=new DestinatariosExcel();
            destinatarios=a.abrirContactos(arch);

        }

        System.out.println("Ingrese el asunto del correo");
        asunto=leer.nextLine();
        System.out.println("Ingrese el cuerpo del correo");
        contenido=leer.nextLine();

        System.out.println("Si desea adjuntar un archivo ingrese 1");
        x = leer.nextInt();
        if(x==1) {
            ADJUNTAR();
            do {
                System.out.println("Si desea adjuntar otro archivo ingrese 1");
                x = leer.nextInt();
                ADJUNTAR();
            } while (x == 1);
        }else{
            fileName=null;
            fileRoute=null;
        }

        EmailSenderService a = new EmailSenderService(remitente,clave,destinatarios,asunto,contenido,fileName,fileRoute);
        a.Enviar();

    }

    public void ADJUNTAR (){

        String Fln="", Flr="";


}
}
