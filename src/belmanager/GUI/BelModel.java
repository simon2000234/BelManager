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
    private Long timeOffset;

    public BelModel() throws SQLException
    {
        this.currentOrders = new ArrayList<>();
        currentOrders.addAll(bmm.filterOrdersByDepartment(currentDepartment));
        this.shownOrders = new HashMap();
    }

    /**
     * Filters all orders, showing only the orders where their current
     * department is the department that we are trying to get orders for.
     *
     * @param currentDepartment
     * @return a list of orders where the current department equals the
     * department given in the parameter.
     */
    public List<Order> filterOrdersByDepartment(String selectedDepartment) throws SQLException
    {
        return bmm.filterOrdersByDepartment(selectedDepartment);
    }

    /**
     * Get all order in the database that match with the current department
     *
     * @param currentDepartment the department that you want orders from
     * @return an arrayList of all orders in the database that match with the
     * current deparment
     * @throws SQLException
     */
    public List<Order> getAllOrders() throws SQLException
    {
        return bmm.getAllOrders(currentDepartment);
    }

    /**
     * Gets an order from the database
     *
     * @param orderNumber
     * @return the order that matches with the ordernumber
     * @throws SQLException
     */
    public Order getOrder(String orderNumber) throws SQLException
    {
        return bmm.getOrder(orderNumber);
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
    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
    {
        return bmm.getAllDepartmentTasks(orderNumber);
    }

    
    /**
     * Gets all deparment tasks in the database
     *
     * @return an arrayList of all department task in the database
     * @throws SQLException
     */
    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        return bmm.getAllDepartmentTasks();
    }

    
    /**
     * Gets a worker from the database
     *
     * @param salaryNumber
     * @return the wokrer who matches the salary number
     * @throws SQLException
     */
    public Worker getWorker(int salaryNumber) throws SQLException
    {
        return bmm.getWorker(salaryNumber);
    }

    /**
     * Deletes a worker from the database
     *
     * @param salaryNumber
     * @throws SQLException
     */
    public void deleteWorker(int salaryNumber) throws SQLException
    {
        bmm.deleteWorker(salaryNumber);
    }

    
    /**
     * Gets all the workers from the database
     *
     * @return an arrayList of all the wokrers in the database
     * @throws SQLException
     */
    public ArrayList<Worker> getAllWorkers() throws SQLException
    {
        return bmm.getAllWorkers();
    }

    public List<Order> getCurrentOrders()
    {
        return currentOrders;
    }

    public List<String> readFromFile() throws IOException
    {
        return bmm.readFromFile();
    }

    public void writeToFile(String myData, String myOffset) throws IOException
    {
        bmm.writeToFile(myData, myOffset);
    }

    /**
     * Deletes an order in the database
     *
     * @param orderID
     * @throws SQLException
     */
    public void deleteOrder(int OrderID)
    {
        try
        {
            bmm.deleteOrder(OrderID);
        }
        catch (SQLException ex)
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

    /**
     * This methord takes a JSON file made by Belman, converts it and sendt it
     * to our database
     *
     * @param fileLocation the location of the file
     * @throws IOException
     * @throws ParseException
     */
    public void moveJsonToDB(String fileLocation) throws IOException, ParseException
    {
        bmm.moveJsonToDB(fileLocation);
    }

    /**
     * Sets an deparment task to be fininshed by changeing its boolean value in
     * the database, from false to true
     *
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
     * Gets shown orders
     * @return a HashMap containing the currently shown orders
     */
    public HashMap<String, Order> getShownOrders()
    {
        return shownOrders;
    }

    /**
     * Sets the HashMap of the orders to the parameter.
     *
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
     *
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
     *
     * @param compleTimeEpocMilli the time of completion in epoc milli
     * @param deparment the department the task belonged to
     * @param orderNumber the order the task was a part of
     * @throws SQLException
     */
    public void createCompleteLog(long compleTimeEpocMilli, String deparment, String orderNumber) throws SQLException
    {
        bmm.createCompleteLog(compleTimeEpocMilli, deparment, orderNumber);
    }

    public Long getTimeOffset()
    {
        return timeOffset;
    }

    public void setTimeOffset(String timeOffset)
    {
        int oneDayInEpochMilli = 86400000;
        int days = Integer.parseInt(timeOffset);
        Long Offset = (long)(days*oneDayInEpochMilli);
        this.timeOffset = Offset;
    }
    
    
}
