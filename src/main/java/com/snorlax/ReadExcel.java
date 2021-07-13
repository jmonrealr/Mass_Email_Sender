package com.snorlax;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Class used to read a Excel File
 */
public class ReadExcel {
    private String path;
    private File file;
    /**
     * Constructor of the class which takes the
     * @param file to be read
     */
    ReadExcel(File file){
        this.file = file;
    }

    /**
     * Constructor of the class which takes the
     * @param path of the file to be read
     */
    ReadExcel(String path){
        this.path = path;
    }

    /**
     * Reads the Excel File, First read the Column and after the Cell
     * @return An LinkedHashMap with Column Name as Key and Values as the List of all the cell
     * @throws IOException When reading the file, something goes wrong
     */
    public LinkedHashMap<String, List<String>> getDataAsLHM() throws IOException {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        try {
            InputStream inputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            boolean flag = true;
            ArrayList<String> names = new ArrayList<>();
            for (Row row: sheet) {
                List<String> temp = new ArrayList<>();
                for (Cell cell:row) {
                    if (flag){
                        map.put(cell.toString(), new ArrayList<String>());
                        names.add(cell.toString());
                    }else {
                        temp.add(cell.toString());
                    }
                }
                if (flag) flag = false;
                else{
                    for (int i = 0; i < names.size(); i++){
                        map.get((String) names.get(i)).add(temp.get(i));
                    }
                }
            }

        } catch (EncryptedDocumentException e) {
            System.err.println("File encrypted " + e.getMessage());
        }catch (IOException e){
            throw new IOException("File damaged" + e.getMessage());
        }
        return map;
    }

    public List<List<String>> getDataAsList(){
        List<List<String>> excelContent = new ArrayList();
        int columnCounter = 0;
        int rowCounter = 0;

        try (InputStream inputStream = new FileInputStream(this.path)) {
            DataFormatter formatter = new DataFormatter();

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> tempList = new ArrayList();

                for (Cell cell : row) {
                    String text = formatter.formatCellValue(cell);
                    System.out.print(++columnCounter + ": " + text + " - ");
                    System.out.println(text.length());
                    tempList.add(text.length() == 0 ? "" : text);
                }
                columnCounter = 0;
                excelContent.add(tempList);

                ++rowCounter;
            }
        }
        catch (IOException | EncryptedDocumentException ex) {
            System.out.println(ex.toString());
        }

        return excelContent;
    }
}
