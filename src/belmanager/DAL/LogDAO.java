/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Melchertsen
 */
public class LogDAO
{

    private DBConnectionProvider DB;

    protected LogDAO()
    {
        DB = new DBConnectionProvider();
    }

    /**
     * Creates a log for the time a deparment login to the program
     *
     * @param loginTimeEpocMilli the time of completion in epoc milli
     * @param depLogin the department that login
     * @throws SQLException
     */
    protected void createLoginLog(long loginTimeEpocMilli, String depLogin)
    {
        String SQL = "INSERT INTO LogLogin(loginTime, depLogin) VALUES(?,?);";
        Timestamp loginTimeInDate = new Timestamp(loginTimeEpocMilli);

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setTimestamp(1, loginTimeInDate);
            st.setString(2, depLogin);
            st.executeUpdate();
            st.close();
        }
        catch (SQLException ex)
        {
            try
            {
                File logFile = new File("log.txt");
                if (!logFile.exists())
                {
                    logFile.createNewFile();
                }
                FileWriter writer = new FileWriter(logFile, true);

                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.newLine();
                bufferWriter.write(loginTimeInDate.toString() + " login: " + depLogin);
                bufferWriter.close();
                writer.close();
            }
            catch (IOException ex1)
            {
                System.out.println("failed to even write log");
            }
        }
    }

    /**
     * Creates a log of the time a task was completed
     *
     * @param compleTimeEpocMilli the time of completion in epoc milli
     * @param deparment the department the task belonged to
     * @param orderNumber the order the task was a part of
     * @throws SQLException
     */
    protected void createCompleteLog(long compleTimeEpocMilli, String deparment, String orderNumber)
    {
        String SQL = "INSERT INTO LogCompleteTask"
                + "(compleTime, deparment, orderNumberComplete) VALUES(?,?,?);";

        Timestamp compleTimeInDate = new Timestamp(compleTimeEpocMilli);

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setTimestamp(1, compleTimeInDate);
            st.setString(2, deparment);
            st.setString(3, orderNumber);
            st.executeUpdate();
            st.close();
        }
        catch (SQLException ex)
        {
            try
            {
                File logFile = new File("log.txt");
                if (!logFile.exists())
                {
                    logFile.createNewFile();
                }
                FileWriter writer = new FileWriter(logFile, true);

                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.newLine();
                bufferWriter.write(compleTimeInDate.toString() + " Complete: " +
                        deparment + " Order: " + orderNumber);
                bufferWriter.close();
                writer.close();
            }
            catch (IOException ex1)
            {
                System.out.println("failed to even write log");
            }
        }
    }

    /**
     * Creates a log for when an error occurs
     *
     * @param errorTimeEpochMilli the time of error
     * @param errorType the type error
     * @throws SQLException
     */
    protected void createErrorLog(long errorTimeEpochMilli, String errorType)
    {
        String SQL = "INSERT INTO ErrorLog(errorTime, errorType) VALUES(?,?);";

        Timestamp errorTimeDate = new Timestamp(errorTimeEpochMilli);

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setTimestamp(1, errorTimeDate);
            st.setString(2, errorType);
            st.executeUpdate();
            st.close();
        }
        catch (SQLException ex)
        {
            try
            {
                File logFile = new File("log.txt");
                if (!logFile.exists())
                {
                    logFile.createNewFile();
                }
                FileWriter writer = new FileWriter(logFile, true);

                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.newLine();
                bufferWriter.write(errorTimeDate.toString() + " " + errorType);
                bufferWriter.close();
                writer.close();
            }
            catch (IOException ex1)
            {
                System.out.println("failed to even write log");
            }
        }
    }
}
