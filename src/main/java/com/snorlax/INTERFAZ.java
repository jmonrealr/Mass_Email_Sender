package com.AlcoM0312;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class INTERFAZ {
    private String remitente="", clave="", destino="", asunto = "", contenido = "";

            ArrayList<String>fileRoute = new ArrayList<>();
            ArrayList<String> fileName = new ArrayList<>();

    public void formulario(){
        ArrayList<String> destinatarios = new ArrayList<>();
        Scanner leer = new Scanner(System.in);

        System.out.println("Sistema de envio de correos UPV");
        System.out.println("Ingrese su correo");
        remitente = leer.nextLine();
        System.out.println("Ingrese la contraseña de su cuenta");
        clave=leer.nextLine();

        int x=0;
        do {
            System.out.println("Ingrese el correo de destino");
            destino = leer.nextLine();
            destinatarios.add(destino);
            System.out.println("Si desea  dejar de ingresar destinatarios ingrese un -1");
            x= leer.nextInt();

        }while(x!=-1);

        asunto=leer.nextLine();
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

        MENSAJERO a = new MENSAJERO(remitente,clave,destinatarios,asunto,contenido,fileName,fileRoute);
        a.Enviar();

    }

    public void ADJUNTAR (){

        String Fln="", Flr="";


}
}
