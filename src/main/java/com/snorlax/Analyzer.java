package com.snorlax;

import java.util.Collection;
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
     * @param keys List with the column names
     * @param values List with the values to be replaced
     * @return
     */
    public String replace(List<String> keys, List<String> values){
        String temp = this.str;
        for (int i = 0; i < keys.size(); i++) {
            //System.out.println("Key " + this.start + keys.get(i) + this.end + " Valor " + values.get(i));
            //System.out.println("valores remplazados " + temp.replaceAll(this.start + keys.get(i) + this.end, values.get(i)));
            temp = temp.replaceAll(this.start + keys.get(i) + this.end, values.get(i));
        }
        return temp;
    }

}
