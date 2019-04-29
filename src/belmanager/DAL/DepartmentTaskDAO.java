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

    public DepartmentTaskDAO()
    {
        DB = new DBConnectionProvider();
    }

    public void createDeparmentTask(String departmentName, String endDate, String startDate, boolean finishedOrder, String orderID) throws SQLException
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

    public void createOrder(String orderNumber, String customerName, String deliveryTime) throws SQLException
    {
        String SQL = "INSERT INTO Order(orderNumber, customerName, deliveryTime) VALUES(?,?,?);";

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

    public Order getOrder(String orderNumber) throws SQLException
    {
        String SQL = "SELECT * FROM Order WHERE orderNumber = ?";
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

    public ArrayList<Order> getAllOrders() throws SQLException
    {
        String SQL = "SELECT * FROM Order;";
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

    public ArrayList<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
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

    public ArrayList<DepartmentTask> getAllDepartmentTasks() throws SQLException
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
}
