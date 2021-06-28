package com.snorlax;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

public class DestinatariosExcel {
    ArrayList<String>Contactos= new ArrayList<>();


    public ArrayList<String> abrirContactos(String arch)throws IOException{
        File f = new File(System.getProperty("user.home")+"/"+arch+".xls");
        InputStream inp = new FileInputStream(f);
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sh = wb.getSheetAt(0);

        Row contar = sh.getRow(0);
        int columnasSize=0, FilasSize=0;
        while(contar.getCell(columnasSize) != null){
            columnasSize++;
        }
        while (contar != null){
            FilasSize++;
            contar = sh.getRow(FilasSize);
        }

        int firstrow = 0;
        Row row = sh.getRow(firstrow);
        Cell cell = row.getCell(0);
        for (int i =0; i<columnasSize; i++) {
            for (int j = 0; j < FilasSize; j++) {
                row = sh.getRow(j);
                cell = row.getCell(i);
                String value = cell.getStringCellValue();
                Contactos.add(value);
                if (value=="CORREO"){
                    Contactos.remove(0);
                }
            }
        }
        System.out.println(Contactos);

        return Contactos;
    }

}
