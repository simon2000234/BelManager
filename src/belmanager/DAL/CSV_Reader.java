/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class CSV_Reader
{

    private String csvFile = "result.csv";
    private BufferedReader br = null;
    private String line = "";
    private String cvsSplitBy = ",";
    private ArrayList<String> resultFromCsvFile = new ArrayList<>();

    public ArrayList<String> readFormCSVFile()
    {

        try
        {
            // we are making a bufferedReader with the file 
            br = new BufferedReader(new FileReader(csvFile));
            // reading all lines from the file
            while ((line = br.readLine()) != null)
            {

                    // use comma as separator 
                    String[] worker = line.split(cvsSplitBy);

                    // løber vores workers array igemmen
                    for (String workers : worker)
                    {
                        // add all result to your arrayList 
                        resultFromCsvFile.add(workers);
                    }
                
            }
            // handler error if they occurs
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        // retuner you'r ArrayList with all result in
        return resultFromCsvFile;
    }
}
