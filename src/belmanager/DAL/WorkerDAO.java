/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import belmanager.BE.Worker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Melchertsen
 */
public class WorkerDAO
{

    private DBConnectionProvider DB;

    public WorkerDAO()
    {
        DB = new DBConnectionProvider();
    }
    
    public void createWorker(String initials, String name, int salaryNumber) throws SQLException
    {
        String SQL = "INSERT INTO Worker(initials, name, salaryNumber) VALUES(?,?,?);";
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, initials);
            st.setString(2, name);
            st.setInt(3, salaryNumber);
            st.executeUpdate();
            st.close();
        }
    }
    
    public Worker getWorker(int salaryNumber) throws SQLException
    {
        Worker worker = null;
        String SQL = "SELECT * FROM Worker WHERE salaryNumber = ?;";
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, salaryNumber);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                String initials = rs.getString("initials");
                String name = rs.getString("name");
                
                worker = new Worker(initials, name, salaryNumber);
                
            }
        }
        
        return worker;
    }

    
}
