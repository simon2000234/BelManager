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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christian Occhionero
 */
public class ConfigFileDao {

    private static String file_location = "Config.txt";

    protected void fileInit() throws IOException {
        File f = new File(file_location);
        f.createNewFile();
    }

// Save to file Utility
<<<<<<< HEAD
    protected void writeToFile(String myData, String myOffset) throws IOException
=======
    protected void writeToFile(String myData) throws IOException
>>>>>>> parent of d25c5f0... Confeck Fix
    {
        fileInit();
        File file = new File(file_location);
        if (!file.exists()) {
            try {
                File directory = new File(file.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {

            }
        }

        // Convenience class for writing character files
        FileWriter writer;
        writer = new FileWriter(file.getAbsoluteFile(), true);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(myData);
<<<<<<< HEAD
            bufferWriter.newLine();
            bufferWriter.write(myOffset);
=======
>>>>>>> parent of d25c5f0... Confeck Fix
            bufferWriter.close();
            writer.close();        
    }

    // Read From File Utility
<<<<<<< HEAD
    protected List<String> readFromFile() throws IOException
=======
    protected String readFromFile() throws IOException
>>>>>>> parent of d25c5f0... Confeck Fix
    {
        fileInit();

        File file = new File(file_location);
<<<<<<< HEAD
        String line;
        List<String> configInfo = new ArrayList<>();
=======
        String line = null;
>>>>>>> parent of d25c5f0... Confeck Fix
        if (!file.exists())
        {
            System.out.println("File doesn't exist");
        }

        InputStreamReader isReader;
<<<<<<< HEAD
        try
        {
<<<<<<< HEAD

            // FileReader reads text files in the default encoding.
            FileReader fileReader
                    = new FileReader(file_location);
=======
>>>>>>> parent of 006c2fc... Revert "Merge branch 'master' of https://github.com/simon2000234/BelManager"

        // FileReader reads text files in the default encoding.
        FileReader fileReader
                = new FileReader(file_location);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null)
            {
                configInfo.add(line);
            }
<<<<<<< HEAD

=======

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

>>>>>>> parent of d25c5f0... Confeck Fix
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex)
        {
            System.out.println(
                    "Unable to open file '"
                    + file_location + "'");
        }
<<<<<<< HEAD
=======
>>>>>>> parent of 006c2fc... Revert "Merge branch 'master' of https://github.com/simon2000234/BelManager"
        return configInfo;
=======
        return line;
>>>>>>> parent of d25c5f0... Confeck Fix

    }

//    // reads second line in the config file containing the time
//    public String readTimeOfSet() throws IOException {
//        fileInit();
//
//        File file = new File(file_location);
//        String line = null;
//        if (!file.exists()) {
//            System.out.println("File doesn't exist");
//        }
//
//        InputStreamReader isReader;
//
//        FileReader fileReader
//                = new FileReader(file_location);
//
//        // Always wrap FileReader in BufferedReader.
//        BufferedReader bufferedReader
//                = new BufferedReader(fileReader);
//
//        bufferedReader.readLine();
//        line = bufferedReader.readLine();
//
//        bufferedReader.close();
//        return line;
//
//    }
}
