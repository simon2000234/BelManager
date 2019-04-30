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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caspe
 */
public class BMManager
{
    DataAccessFacade daFacade = new DataAccessFacade();

    /**
     * Filters all orders, showing only the orders where their current department
     * is the department that we are trying to get orders for.
     *
     * @param currentDepartment
     * @return a list of orders where the current department equals the department
     * given in the parameter.
     */
    public List<Order> filterOrders(DepartmentTask currentDepartment) throws SQLException
    {
        List<Order> temp = new ArrayList<>();
        
        for (Order order : daFacade.getAllOrders())
        {
            if (currentDepartment.getDepartmentName().equals(order.getCurrentDepartment().getDepartmentName()))
            {
                temp.add(order);
            }
        }       
        return temp;

    }

    
    public List<Order> getAllOrders() throws SQLException
    {
        return daFacade.getAllOrders();
    }

    public Order getOrder(String orderNumber) throws SQLException
    {
        return daFacade.getOrder(orderNumber);
    }

    public void createDeparmentTask(String departmentName, String endDate, String startDate, boolean finishedOrder, String orderID) throws SQLException
    {
        daFacade.createDeparmentTask(departmentName, endDate, startDate, finishedOrder, orderID);
    }

    public void createOrder(String orderNumber, String customerName, String deliveryTime) throws SQLException
    {
        daFacade.createOrder(orderNumber, customerName, deliveryTime);
    }

    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
    {
        return daFacade.getAllDepartmentTasks(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        return daFacade.getAllDepartmentTasks();
    }

    public void createWorker(String initials, String name, int salaryNumber) throws SQLException
    {
        daFacade.createWorker(initials, name, salaryNumber);
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
}
