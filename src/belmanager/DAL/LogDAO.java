/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
     * @param loginTimeEpocMilli the time of completion in epoc milli
     * @param depLogin the department that login
     * @throws SQLException
     */
    protected void createLoginLog(long loginTimeEpocMilli, String depLogin) throws SQLException
    {
        String SQL = "INSERT INTO LogLogin(loginTime, depLogin) VALUES(?,?);";
        Date loginTimeInDate = new Date(loginTimeEpocMilli);
        
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setDate(1, loginTimeInDate);
            st.setString(2, depLogin);
            st.executeUpdate();
            st.close();
        }
    }
    
    /**
     * Creates a log the the time a task was completed
     * @param compleTimeEpocMilli the time of completion in epoc milli
     * @param deparment the department the task belonged to
     * @param orderNumber the order the task was a part of
     * @throws SQLException
     */
    protected void createCompleteLog(long compleTimeEpocMilli, String deparment, String orderNumber) throws SQLException
    {
        String SQL = "INSERT INTO LogCompleteTask"
                + "(compleTime, deparment, orderNumberComplete) VALUES(?,?,?);";
        
        Date compleTimeInDate = new Date(compleTimeEpocMilli);
        
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setDate(1, compleTimeInDate);
            st.setString(2, deparment);
            st.setString(3, orderNumber);
            st.executeUpdate();
            st.close();
        }
    }
}
