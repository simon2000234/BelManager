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
 */
public class DepartmentTaskDAO
{

    private DBConnectionProvider DB;

    protected DepartmentTaskDAO()
    {
        DB = new DBConnectionProvider();
    }

    /**
     * Creates a worker in the database, with the specified parameters
     *
     * @param departmentName
     * @param endDate
     * @param startDate
     * @param finishedOrder
     * @param orderID
     * @throws SQLException
     */
    protected void createDeparmentTask(String departmentName, long endDate, long startDate, boolean finishedOrder, String orderID) throws SQLException
    {
        String SQL = "INSERT INTO DepartmentTask(departmentName, endDate, startDate, finishedOrder, orderID)"
                + "VALUES(?,?,?,?,?);";
        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setString(1, departmentName);
            st.setLong(2, endDate);
            st.setLong(3, startDate);
            st.setBoolean(4, finishedOrder);
            st.setString(5, orderID);
            st.executeUpdate();
            st.close();
        }
    }

    /**
     * Creates an order in the database with the specified parameters
     *
     * @param orderNumber
     * @param customerName
     * @param deliveryTime
     * @throws SQLException
     */
    protected void createOrder(String orderNumber, String customerName, long deliveryTime) throws SQLException
    {
        String SQL = "INSERT INTO [Order](orderNumber, customerName, deliveryTime) VALUES(?,?,?);";

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, orderNumber);
            st.setString(2, customerName);
            st.setLong(3, deliveryTime);
            st.executeUpdate();
            st.close();
        }
    }

    /**
     * Gets an order from the database
     *
     * @param orderNumber
     * @return the order that matches with the ordernumber
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
                long deliveryTime = rs.getLong("deliveryTime");
                order = new Order(orderNumber, customerName, deliveryTime,
                        getAllDepartmentTasks(orderNumber));
            }
            st.close();
            rs.close();
        }
        return order;
    }

    /**
     * Get all order in the database
     *
     * @return an arrayList of all orders in the database
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
                long deliveryTime = rs.getLong("deliveryTime");

                Order order = new Order(orderNumber, customerName, deliveryTime,
                        getAllDepartmentTasks(orderNumber));
                allOrders.add(order);
            }
            st.close();
            return allOrders;
        }
    }

    /**
     * Gets all depmartment tasks in the database that match with a specific
     * order;
     *
     * @param orderNumber the ordernumber of the order that you wish to get the
     * tasks for
     * @return an arrayList of department tasks that are part of the specified
     * order
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
                long endDate = rs.getLong("endDate");
                long startDate = rs.getLong("startDate");
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
     * Gets all deparment tasks in the database
     *
     * @return an arrayList of all department task in the database
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
                long endDate = rs.getLong("endDate");
                long startDate = rs.getLong("startDate");
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
     * Sets an deparment task to be fininshed by changeing its boolean value in
     * the database, from false to true
     *
     * @param taskID
     * @throws SQLException
     */
    protected void updateTaskIsFinished(int taskID) throws SQLException
    {
        String SQL = "UPDATE DepartmentTask SET finishedOrder = ? WHERE taskID = ?;";

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareStatement(SQL);
            st.setBoolean(1, true);
            st.setInt(2, taskID);
            st.execute();
            st.close();
        }
    }

    /**
     * Deletes an order in the database
     *
     * @param orderID
     * @throws SQLException
     */
    protected void deleteOrder(int orderID) throws SQLException
    {
        String SQL = "delete * from [Order] where orderNumber= ? ";

        try (Connection con = DB.getConnection())
        {
            PreparedStatement st = con.prepareCall(SQL);
            st.setInt(1, orderID);
            st.executeUpdate();
            st.close();
        }

    }
}
