/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christian Occhionero
 */
public class ConfigFileDao
{

    private static String file_location = "Config.txt";

    protected void fileInit() throws IOException
    {
        File f = new File(file_location);
        f.createNewFile();
    }

    // Save to file Utility
    protected void writeToFile(String myData, String myOffset) throws IOException
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

        // Convenience class for writing character files
        FileWriter writer;
        writer = new FileWriter(file.getAbsoluteFile(), true);

        // Writes text to a character-output stream
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(myData);
        bufferWriter.newLine();
        bufferWriter.write(myOffset);
        bufferWriter.close();
        writer.close();
    }

    // Read From File Utility
    protected List<String> readFromFile() throws IOException
    {
        fileInit();

        File file = new File(file_location);
        String line;
        List<String> configInfo = new ArrayList<>();
        if (!file.exists())
        {
            System.out.println("File doesn't exist");
        }

        InputStreamReader isReader;

        // FileReader reads text files in the default encoding.
        FileReader fileReader
                = new FileReader(file_location);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null)
        {
            configInfo.add(line);
        }
        return configInfo;
    }
}
