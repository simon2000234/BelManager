/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Melchertsen
 * 
 * 
 * en departmentTask er at de forskellig afdellinger skal lavet noget på den samme order 
 * f.esk: bælg->male->montage2
 * bælg ville være en departmentTask osv..

 */
public class DepartmentTaskDAO
{

    private DBConnectionProvider DB;

    protected DepartmentTaskDAO()
    {
        DB = new DBConnectionProvider();
    }

    /*
    
    create a DepartTask to the database
    
    */
    protected void createDeparmentTask(String departmentName, String endDate, String startDate, boolean finishedOrder, String orderID) throws SQLException
    {   
        String SQL = "INSERT INTO DepartmentTask(departmentName, endDate, startDate, finishedOrder, orderID)"
                + "VALUES(?,?,?,?,?);";
        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setString(1, departmentName);
            st.setString(2, endDate);
            st.setString(3, startDate);
            st.setBoolean(4, finishedOrder);
            st.setString(5, orderID);
            st.executeUpdate();
            st.close();
        }
    }

    /*
    creat a order on to the database
    
    */
    protected void createOrder(String orderNumber, String customerName, String deliveryTime) throws SQLException
    {
        String SQL = "INSERT INTO [Order](orderNumber, customerName, deliveryTime) VALUES(?,?,?);";

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, orderNumber);
            st.setString(2, customerName);
            st.setString(3, deliveryTime);
            st.executeUpdate();
            st.close();
        }
    }
/**
 * select en bestemt order med et bestemt orderNumber 
 * det bestemmet ordernumber er  @param orderNumber
 * 
 * @param orderNumber
 * @return Order
 * @throws SQLException 
 */
    protected Order getOrder(String orderNumber) throws SQLException
    {
        String SQL = "SELECT * FROM [Order] WHERE orderNumber = ?";
        Order order = null;
        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, orderNumber);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                String customerName = rs.getString("customerName");
                String deliveryTime = rs.getString("deliveryTime");
                order = new Order(orderNumber, customerName, deliveryTime,
                        getAllDepartmentTasks(orderNumber));
            }
        }
        return order;
    }

    
    /**
     * returner alle orders
     * 
     * @return ArrayList<Order> 
     * @throws SQLException 
     */
    protected ArrayList<Order> getAllOrders() throws SQLException
    {
        String SQL = "SELECT * FROM [Order];";
        ArrayList<Order> allOrders = new ArrayList<>();

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                String orderNumber = rs.getString("orderNumber");
                String customerName = rs.getString("customerName");
                String deliveryTime = rs.getString("deliveryTime");

                Order order = new Order(orderNumber, customerName, deliveryTime,
                        getAllDepartmentTasks(orderNumber));
                allOrders.add(order);
            }
            st.close();
            return allOrders;
        }
    }

    /**
     *  returner alle departmentTask
     * @param orderNumber
     * @return ArrayList<DepartmentTask>
     * @throws SQLException 
     */
    protected ArrayList<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
    {
        String SQL = "SELECT * FROM DepartmentTask WHERE orderID = ?;";
        ArrayList<DepartmentTask> allTasks = new ArrayList<>();

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, orderNumber);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                String departmentName = rs.getString("departmentName");
                String endDate = rs.getString("endDate");
                String startDate = rs.getString("startDate");
                boolean finishedOrder = rs.getBoolean("finishedOrder");
                int taskID = rs.getInt("taskID");

                DepartmentTask dt = new DepartmentTask(departmentName, endDate,
                        startDate, finishedOrder, taskID);
                allTasks.add(dt);
            }
            st.close();
        }
        return allTasks;
    }

    /**
     * henter alle departmentTask fra DataBasen
     * 
     * @return ArrayList<DepartmentTask> 
     * @throws SQLException 
     */
    protected ArrayList<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        String SQL = "SELECT * FROM DepartmentTask;";
        ArrayList<DepartmentTask> allTasks = new ArrayList<>();

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                String departmentName = rs.getString("departmentName");
                String endDate = rs.getString("endDate");
                String startDate = rs.getString("startDate");
                boolean finishedOrder = rs.getBoolean("finishedOrder");
                int taskID = rs.getInt("taskID");

                DepartmentTask dt = new DepartmentTask(departmentName, endDate,
                        startDate, finishedOrder, taskID);
                allTasks.add(dt);
            }
            st.close();
        }
        return allTasks;
    }
    
    
    /**
     * update en task til at være færdig i databasen
     * @param taskID
     * @throws SQLException 
     */
    protected void updateTaskIsFinished(int taskID) throws SQLException
    {
        String SQL = "UPDATE DepartmentTask SET finishedOrder = ? WHERE taskID = ?;";
        
        try(Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setBoolean(1, true);
            st.setInt(2, taskID);
            st.execute();
            st.close();
        }
    }
    
    /**
     * sletter en order med det givet @parameter orderID
     * @param orderID
     * @throws SQLException 
     */
     protected void DeleteOrder (int orderID) throws SQLException
    {
        String SQL = "delete * from [Order] where orderNumber= ? ";
       

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setInt(1, orderID);
            st.executeUpdate();
                     
        }
        
    }
}
