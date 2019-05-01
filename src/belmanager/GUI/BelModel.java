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
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Melchertsen
 */
public class BelModel
{
    private BMManager bmm = new BMManager();
            
            
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
    
     public void moveJsonToDB(String fileLocation) throws IOException, ParseException
    {
        bmm.moveJsonToDB(fileLocation);
    }
}