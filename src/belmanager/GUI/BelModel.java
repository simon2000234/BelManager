/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.Worker;
import belmanager.BLL.BMManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Melchertsen
 */
public class BelModel
{

    private BMManager bmm = new BMManager();
    private List<Order> currentOrders;
    private String currentDepartment;
    private Order selectedOrder;
    private HashMap<String, Order> ShownOrders;

    public BelModel() throws SQLException
    {
        //this.currentDepartment = "Bælg"; //Need to get department from a config file on login
        this.currentOrders = new ArrayList<>();
        currentOrders.addAll(bmm.filterOrdersByDepartment(currentDepartment));
        this.ShownOrders = new HashMap();
    }

    public List<Order> filterOrdersByDepartment(String selectedDepartment) throws SQLException
    {
        return bmm.filterOrdersByDepartment(selectedDepartment);
    }

    public List<Order> getAllOrders() throws SQLException
    {
        return bmm.getAllOrders();
    }

    public Order getOrder(String orderNumber) throws SQLException
    {
        return bmm.getOrder(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
    {
        return bmm.getAllDepartmentTasks(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        return bmm.getAllDepartmentTasks();
    }

    public Worker getWorker(int salaryNumber) throws SQLException
    {
        return bmm.getWorker(salaryNumber);
    }

    public void deleteWorker(int salaryNumber) throws SQLException
    {
        bmm.deleteWorker(salaryNumber);
    }

    public ArrayList<Worker> getAllWorkers() throws SQLException
    {
        return bmm.getAllWorkers();
    }

    public List<Order> getCurrentOrders()
    {
        return currentOrders;
    }
    
    public void DeleteOrder (int OrderID)
    {
        try
        {
            bmm.deleteOrder(OrderID);
        } catch (SQLException ex)
        {
            System.out.println("order er ikke blivet slette fordi der er sket en fejl");
        }
    }

    public void setCurrentOrders(List<Order> currentOrders)
    {
        this.currentOrders = currentOrders;
    }

    public String getCurrentDepartment()
    {
        return currentDepartment;
    }

    public void setCurrentDepartment(String currentDepartment)
    {
        this.currentDepartment = currentDepartment;
    }

    public Order getSelectedOrder()
    {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder)
    {
        this.selectedOrder = selectedOrder;
    }

    public void moveJsonToDB(String fileLocation) throws IOException, ParseException
    {
        bmm.moveJsonToDB(fileLocation);
    }

    public void updateTaskIsFinished(int taskID) throws SQLException
    {
        ShownOrders.remove(selectedOrder.getOrderNumber());
        currentOrders.remove(selectedOrder);
        bmm.updateTaskIsFinished(taskID);

    }

    public HashMap<String, Order> getShownOrders()
    {
        return ShownOrders;
    }

    public void setShownOrders(HashMap<String, Order> ShownOrders)
    {
        this.ShownOrders = ShownOrders;
    }

    public HashMap<String, Order> createTheHashmap(List<Order> orderlist)
    {
        HashMap<String, Order> temp = new HashMap();
        for (Order order : orderlist)
        {
            temp.put(order.getOrderNumber(), order);
        }
        return temp;
    }
}
