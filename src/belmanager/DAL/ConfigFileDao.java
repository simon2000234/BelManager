/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Christian Occhionero
 */
public class ConfigFileDao
{

    // filen vi skriver til
    private static String file_location = "Config.txt";

    // vi laver filen
    protected void fileInit() throws IOException
    {
        File f = new File(file_location);
        f.createNewFile();
    }

// Save to file Utility
    protected void writeToFile(String myData) throws IOException
    {
        fileInit();
        File file = new File(file_location);
        if (!file.exists())
        {
            try
            {
                File directory = new File(file.getParent());
                if (!directory.exists())
                {
                    directory.mkdirs();
                }
                file.createNewFile();
            }
            catch (IOException e)
            {

            }
        }

        try
        {
            // Convenience class for writing character files
            FileWriter writer;
            writer = new FileWriter(file.getAbsoluteFile(), true);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(myData);
            bufferWriter.close();
            writer.close();
        }
        catch (IOException e)
        {

        }
    }

    // Read From File Utility
    protected String readFromFile() throws IOException
    {
        fileInit();

        File file = new File(file_location);
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
                    = new FileReader(file_location);

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
                    + file_location + "'");
        }
        return line;

    }

}
