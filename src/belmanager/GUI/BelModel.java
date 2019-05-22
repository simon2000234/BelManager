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
    private HashMap<String, Order> shownOrders;

    public BelModel() throws SQLException
    {
        this.currentOrders = new ArrayList<>();
        currentOrders.addAll(bmm.filterOrdersByDepartment(currentDepartment));
        this.shownOrders = new HashMap();
    }

    public List<Order> filterOrdersByDepartment(String selectedDepartment) throws SQLException
    {
        return bmm.filterOrdersByDepartment(selectedDepartment);
    }

    public List<Order> getAllOrders() throws SQLException
    {
        return bmm.getAllOrders(currentDepartment);
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
     public String readFromFile() throws IOException {
            return bmm.readFromFile();
        
     }
    
     public void writeToFile(String myData) throws IOException {
        bmm.writeToFile(currentDepartment);
     }
    
    public void deleteOrder (int OrderID)
    {
        try
        {
            bmm.deleteOrder(OrderID);
        } catch (SQLException ex)
        {
            System.out.println("the order could not be deleted");
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

    /**
     * Updates the given task to being completed
     * @param taskID
     * @throws SQLException 
     */
    public void updateTaskIsFinished(int taskID, Order order) throws SQLException
    {
        shownOrders.remove(order.getOrderNumber());
        currentOrders.remove(order);
        bmm.updateTaskIsFinished(taskID);

    }

    /**
     * 
     * @return a HashMap containing the currently shown orders 
     */
    public HashMap<String, Order> getShownOrders()
    {
        return shownOrders;
    }

    /**
     * Sets the HashMap of the orders to the parameter.
     * @param ShownOrders 
     */
    public void setShownOrders(HashMap<String, Order> ShownOrders)
    {
        this.shownOrders = ShownOrders;
    }

    /**
     * Creates a HashMap containing a string(the order number) as the key, and
     * the corresponding order as the value.
     * 
     * @param orderlist
     * @return a HashMap for easy access to all orders
     */
    public HashMap<String, Order> createTheHashmap(List<Order> orderlist)
    {
        HashMap<String, Order> temp = new HashMap();
        for (Order order : orderlist)
        {
            temp.put(order.getOrderNumber(), order);
        }
        return temp;
    }
    
    /**
     * Creates a log for the time a deparment login to the program
     * @param loginTimeEpocMilli the time of completion in epoc milli
     * @param depLogin the department that login
     * @throws SQLException
     */
    public void createLoginLog(long loginTimeEpocMilli, String depLogin) throws SQLException
    {
        bmm.createLoginLog(loginTimeEpocMilli, depLogin);
    }
    
    /**
     * Creates a log the the time a task was completed
     * @param compleTimeEpocMilli the time of completion in epoc milli
     * @param deparment the department the task belonged to
     * @param orderNumber the order the task was a part of
     * @throws SQLException
     */
    public void createCompleteLog(long compleTimeEpocMilli, String deparment, String orderNumber) throws SQLException
    {
        bmm.createCompleteLog(compleTimeEpocMilli, deparment, orderNumber);
    }
}
