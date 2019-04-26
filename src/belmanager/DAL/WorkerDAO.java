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
import java.util.ArrayList;

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
            st.close();
        }
        
        if(worker == null)
        {
            System.out.println("The Worker does not exist or problems with internet");
        }
        
        return worker;
    }
    
    public void deleteWorker(int salaryNumber) throws SQLException
    {
        String SQL = "DELETE FROM Worker WHERE salaryNumber = ?;";
        
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setInt(1, salaryNumber);
            st.executeUpdate();
            st.close();
        }
    }
    
    public ArrayList<Worker> getAlllWorkers() throws SQLException
    {
        String SQL = "SELECT * FROM Worker;";
        ArrayList<Worker> allWorkers = new ArrayList<>();
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
                String initials = rs.getString("initials");
                String name = rs.getString("name");
                int salaryNumber = rs.getInt("salaryNumber");
                Worker wk = new Worker(initials, name, salaryNumber);
                allWorkers.add(wk);
            }
        }
        
        if(allWorkers.isEmpty())
        {
            System.out.println("There are no Workers, or someting wrong, maybe with internet");
        }
        
        return allWorkers;
    }

    
}
