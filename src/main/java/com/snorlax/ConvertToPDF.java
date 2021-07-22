package com.snorlax;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.*;

/**
 * This class is used to converts an html input to PDF as attached file
 */
public class ConvertToPDF {

    /**
     * Convert a String html code to pdf file
     * @param html String code to be transformed
     * @throws IOException if failed to parse code to PDF
     * @return path of the file saved
     */
    public String convert(String html) throws IOException {
        HtmlConverter.convertToPdf(html, new FileOutputStream("template.pdf"));
        String path = new File("template.pdf").getPath();
        return path;
    }

    /**
     * Convert a html file to pdf file
     * @param file to be converted
     */
    public void convert(File file){
        //File archivo = new File("Descargas/index.html");//remplazar por ubicacion de la plantilla html
        File archivo = file;
        try {
            HtmlConverter.convertToPdf(new FileInputStream(archivo.getAbsolutePath()), new FileOutputStream("src/main/resources/Salida.pdf")); //reemplazar por el nombre del archivo de salida
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
