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
 * @author andre
 */
public class CSV_Reader
{

    private String csvFile = "result.csv";
    private BufferedReader br = null;
    private String line = "";
    private String cvsSplitBy = ",";
    private ArrayList<String> resultFromCsvFile = new ArrayList<>();

    protected ArrayList<String> readFormCSVFile(){
    
        try
        {
            // laver en bufferedReader med den file vi skal read
            br = new BufferedReader(new FileReader(csvFile));
            // Læser alle liner i file
            while ((line = br.readLine()) != null)
            {

                // bruger komma som separator 
                // 
                String[] worker = line.split(cvsSplitBy);
                
                // løber vores workers array igemmen
                for (String workers : worker)
                {
                    // tilføjer alle resulter til vores arrayList 
                    resultFromCsvFile.add(workers);
                    
                }

            }
            
            // håntere fejl hvis der er nogle
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        // retuner vores ArrayList med alle resulter i
        return resultFromCsvFile;
    }
}
