package com.snorlax;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * This class is used to converts an html input to PDF as attached file
 */
public class ConvertToPDF {
    /**
     *
     */
    public void Convertir(){
        File archivo = new File("Descargas/index.html");//remplazar por ubicacion de la plantilla html
        try {
            HtmlConverter.convertToPdf(new FileInputStream(archivo.getAbsolutePath()), new FileOutputStream("src/main/resources/Salida.pdf")); //reemplazar por el nombre del archivo de salida
        }catch(Exception ex){
                ex.printStackTrace();
        }
    }
}
