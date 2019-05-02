/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BLL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.Worker;
import belmanager.DAL.DataAccessFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Caspe
 */
public class BMManager
{
    
    private DataAccessFacade daFacade = new DataAccessFacade();
    private Calendar c;
    private String currentDate;

    /**
     * Filters all orders, showing only the orders where their current
     * department is the department that we are trying to get orders for.
     *
     * @param currentDepartment
     * @return a list of orders where the current department equals the
     * department given in the parameter.
     */
    public List<Order> filterOrdersByDepartment(String currentDepartment) throws SQLException
    {
        List<Order> temp = new ArrayList<>();
        c = Calendar.getInstance();
        currentDate = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
        
        for (Order order : daFacade.getAllOrders())
        {
            if (currentDepartment.equals(order.getCurrentDepartment().getDepartmentName())
                    || order.getCurrentDepartment().getStartDate().compareTo(currentDate) >= 0
                    && order.getCurrentDepartment().isFinishedOrder() == false)
            {
                temp.add(order);
            }
        }
        
        Collections.sort(temp);
        return temp;
        
    }
    
    public List<Order> getAllOrders() throws SQLException
    {
        return daFacade.getAllOrders();
    }
    
    public void deleteOrder(int orderID) throws SQLException
    {
        daFacade.DeleteOrder(orderID);
        
    }
    
    public Order getOrder(String orderNumber) throws SQLException
    {
        return daFacade.getOrder(orderNumber);
    }
    
    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
    {
        return daFacade.getAllDepartmentTasks(orderNumber);
    }
    
    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        return daFacade.getAllDepartmentTasks();
    }
    
    public Worker getWorker(int salaryNumber) throws SQLException
    {
        return daFacade.getWorker(salaryNumber);
    }
    
    public void deleteWorker(int salaryNumber) throws SQLException
    {
        daFacade.deleteWorker(salaryNumber);
    }
    
    public ArrayList<Worker> getAllWorkers() throws SQLException
    {
        return daFacade.getAllWorkers();
    }
    
    public void moveJsonToDB(String fileLocation) throws IOException, ParseException
    {
        daFacade.moveJsonToDB(fileLocation);
    }
    
    public void updateTaskIsFinished(int taskID) throws SQLException
    {
        daFacade.updateTaskIsFinished(taskID);
    }
}
