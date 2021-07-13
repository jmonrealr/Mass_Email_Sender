package com.snorlax;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class used to Analyze the whole string, finds the keys and replace with his value
 */
public class Analyzer {
    private String str;
    private String end = ">";
    private String start = "<";
    /**
     * Empty constructor
     */
    Analyzer(){

    }

    /**
     * Default constructor, uses default start and end pattern
     * @param str to be analyzed
     */
    Analyzer(String str){
        this.str = str;
    }

    /**
     * Constructor of the class
     * @param str to be analyzed
     * @param start of the pattern
     * @param end of the pattern
     */
    Analyzer(String str, String start, String end){
        this.str = str;
        this.end = end;
        this.start = start;
    }

    /**
     * Replace all the keys with his values, uses the String.replaceAll method
     * @param Keys List with the column names
     * @param Values List with the values to be replaced
     * @return
     */
    private String replace(List<String> Keys, List<String> Values){
        String temp = this.str;
        for (int i = 0; i < Keys.size(); i++) {
            System.out.println("Key " + this.start + Keys.get(i) + this.end + " Valor " + Values.get(i));
            temp.replaceAll(this.start + Keys.get(i) + this.end, Values.get(i));
        }
        return temp;
    }

}
