/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.Worker;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Caspe
 */
public class DataAccessFacade
{

    WorkerDAO wdao = new WorkerDAO();
    DepartmentTaskDAO dtdao = new DepartmentTaskDAO();
    DataTransfer dt = new DataTransfer();
    ConfigFileDao cfdao = new ConfigFileDao();


    /**
     * Get all order in the database
     *
     * @return an arrayList of all orders in the database
     * @throws SQLException
     */

    public List<Order> getAllOrders() throws SQLException
    {
        return dtdao.getAllOrders();
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
        return dtdao.getOrder(orderNumber);
    }


    /**
     * Deletes an order in the database
     *
     * @param orderID
     * @throws SQLException
     */

    public void deleteOrder(int OrderID) throws SQLException
    {
        dtdao.deleteOrder(OrderID);

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
        return dtdao.getAllDepartmentTasks(orderNumber);
    }


    /**
     * Gets all deparment tasks in the database
     *
     * @return an arrayList of all department task in the database
     * @throws SQLException
     */

    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException
    {
        return dtdao.getAllDepartmentTasks();
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
        return wdao.getWorker(salaryNumber);
    }


    /**
     * Deletes a worker from the database
     *
     * @param salaryNumber
     * @throws SQLException
     */
    public void deleteWorker(int salaryNumber) throws SQLException
    {
        wdao.deleteWorker(salaryNumber);
    }


    /**
     * Gets all the workers from the database
     *
     * @return an arrayList of all the wokrers in the database
     * @throws SQLException
     */

    public ArrayList<Worker> getAllWorkers() throws SQLException
    {
        return wdao.getAllWorkers();
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
        dt.moveJsonToDB(fileLocation);
    }


    /**
     * Sets an deparment task to be fininshed by changeing its boolean value in
     * // * the database, from false to true
     *
     * @param taskID
     * @throws SQLException
     */

    public void updateTaskIsFinished(int taskID) throws SQLException
    {
        dtdao.updateTaskIsFinished(taskID);
    }

    public void writeToFile(String department, String myOfSet) throws IOException
    {
        cfdao.writeToFile(department, myOfSet);
    }

    public String readDepartmentFromFile() throws IOException
    {
        return cfdao.readDepartmentFromFile();
    }

    public String readTimeOfSet() throws IOException 
    {
        return cfdao.readTimeOfSet(); 
    }



}
