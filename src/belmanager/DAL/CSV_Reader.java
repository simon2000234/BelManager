/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class CSV_Reader
{
  public static final String CSV_File_Location= "result.csv";
    
     public void fileInit() throws IOException
    {
        File f = new File(CSV_File_Location);
        f.createNewFile();
    }
     
     
     
         // Read From File Utility
    public String readFromFile() throws IOException
    {
        fileInit();

        File file = new File(CSV_File_Location);
        String line = null;
        if (!file.exists())
        {
            System.out.println("File doesn't exist");
        }

        InputStreamReader isReader;
        try
        {

            // FileReader reads text files in the default encoding.
            FileReader fileReader
                    = new FileReader(CSV_File_Location);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

           
            while ((line = bufferedReader.readLine()) != null)
            {
                bufferedReader.close();
                return line;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(
                    "Unable to open file '"
                    + CSV_File_Location + "'");
        }
        return line;

    }
}
