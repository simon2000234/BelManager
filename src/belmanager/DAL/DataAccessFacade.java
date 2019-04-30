/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.Worker;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caspe
 */
public class DataAccessFacade
{

    WorkerDAO wdao = new WorkerDAO();
    DepartmentTaskDAO dtdao = new DepartmentTaskDAO();

    public List<Order> getAllOrders() throws SQLException
    {
        return dtdao.getAllOrders();
    }

    public Order getOrder(String orderNumber) throws SQLException
    {
        return dtdao.getOrder(orderNumber);
    }

    public void createDeparmentTask(String departmentName, String endDate, String startDate, boolean finishedOrder, String orderID) throws SQLException
    {
        dtdao.createDeparmentTask(departmentName, endDate, startDate, finishedOrder, orderID);
    }

    public void createOrder(String orderNumber, String customerName, String deliveryTime) throws SQLException
    {
        dtdao.createOrder(orderNumber, customerName, deliveryTime);
    }

    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException
    {
        return dtdao.getAllDepartmentTasks(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        return dtdao.getAllDepartmentTasks();
    }

    public void createWorker(String initials, String name, int salaryNumber) throws SQLException
    {
        wdao.createWorker(initials, name, salaryNumber);
    }

    public Worker getWorker(int salaryNumber) throws SQLException
    {
        return wdao.getWorker(salaryNumber);
    }

    public void deleteWorker(int salaryNumber) throws SQLException
    {
        wdao.deleteWorker(salaryNumber);
    }

    public ArrayList<Worker> getAllWorkers() throws SQLException
    {
        return wdao.getAllWorkers();
    }
}
