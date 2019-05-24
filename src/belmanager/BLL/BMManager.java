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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Caspe
 */
public class BMManager {

    private DataAccessFacade daFacade = new DataAccessFacade();

    /**
     * Filters all orders, showing only the orders where their current
     * department is the department that we are trying to get orders for.
     *
     * @param currentDepartment
     * @return a list of orders where the current department equals the
     * department given in the parameter.
     */
    public List<Order> filterOrdersByDepartment(String currentDepartment) throws SQLException {
        List<Order> temp = new ArrayList<>();
        for (Order order : daFacade.getAllOrders()) {
            List<DepartmentTask> allDepsInOrder = order.getDepartmentTasks();
            for (DepartmentTask dep : allDepsInOrder) {
                if (dep.getDepartmentName().equals(currentDepartment) && dep.isFinishedOrder() == false) {
                    order.setSelectedDepartmentTask(currentDepartment);
                    temp.add(order);
                }
            }
        }
        Collections.sort(temp);
        return temp;

    }

    /**
     * Get all order in the database that match with the current department
     *
     * @param currentDepartment the department that you want orders from
     * @return an arrayList of all orders in the database that match with the 
     * current deparment
     * @throws SQLException
     */
    public List<Order> getAllOrders(String currentDepartment) throws SQLException {
        List<Order> temp = new ArrayList<>();
        for (Order allOrder : daFacade.getAllOrders())
        {
            allOrder.setSelectedDepartmentTask(currentDepartment);
            temp.add(allOrder);
        }
        return temp;
    }

    /**
     * Deletes an order in the database
     *
     * @param orderID
     * @throws SQLException
     */
    public void deleteOrder(int orderID) throws SQLException {
        daFacade.deleteOrder(orderID);

    }

    /**
     * Gets an order from the database
     *
     * @param orderNumber
     * @return the order that matches with the ordernumber
     * @throws SQLException
     */
    public Order getOrder(String orderNumber) throws SQLException {
        return daFacade.getOrder(orderNumber);
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
    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException {
        return daFacade.getAllDepartmentTasks(orderNumber);
    }

     /**
     * Gets all deparment tasks in the database
     *
     * @return an arrayList of all department task in the database
     * @throws SQLException
     */
    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException {
        return daFacade.getAllDepartmentTasks();
    }

    
    /**
     * Gets a worker from the database
     *
     * @param salaryNumber
     * @return the wokrer who matches the salary number
     * @throws SQLException
     */
    public Worker getWorker(int salaryNumber) throws SQLException {
        return daFacade.getWorker(salaryNumber);
    }

    /**
     * Deletes a worker from the database
     *
     * @param salaryNumber
     * @throws SQLException
     */
    public void deleteWorker(int salaryNumber) throws SQLException {
        daFacade.deleteWorker(salaryNumber);
    }

    
    /**
     * Gets all the workers from the database
     *
     * @return an arrayList of all the wokrers in the database
     * @throws SQLException
     */
    public ArrayList<Worker> getAllWorkers() throws SQLException {
        return daFacade.getAllWorkers();
    }

    /**
     * This methord takes a JSON file made by Belman, converts it and sendt it
     * to our database
     *
     * @param fileLocation the location of the file
     * @throws IOException
     * @throws ParseException
     */
    public void moveJsonToDB(String fileLocation) throws IOException, ParseException {
        daFacade.moveJsonToDB(fileLocation);
    }

    
    /**
     * Sets an deparment task to be fininshed by changeing its boolean value in
     * the database, from false to true
     *
     * @param taskID
     * @throws SQLException
     */
    public void updateTaskIsFinished(int taskID) throws SQLException {
        daFacade.updateTaskIsFinished(taskID);
    }

<<<<<<< HEAD
    public void writeToFile(String myData, String myOffset) throws IOException {
        daFacade.writeToFile(myData,myOffset);

    }

    public List<String> readFromFile() throws IOException {
        return daFacade.readFromFile();
    }
   
    /**
     * Creates a log of the time a task was completed
     * @param compleTimeEpocMilli the time of completion in epoc milli
     * @param deparment the department the task belonged to
     * @param orderNumber the order the task was a part of
     * @throws SQLException
     */
    
    public void createCompleteLog(long compleTimeEpocMilli, String deparment, String orderNumber)
    {
        daFacade.createCompleteLog(compleTimeEpocMilli, deparment, orderNumber);
    }
    
    /**
     * Creates a log for the time a deparment login to the program
     * @param loginTimeEpocMilli the time of completion in epoc milli
     * @param depLogin the department that login
     * @throws SQLException
     */
    public void createLoginLog(long loginTimeEpocMilli, String depLogin)
    {
        daFacade.createLoginLog(loginTimeEpocMilli, depLogin);
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
    public void writeToFile(String myData) throws IOException {
        daFacade.writeToFile(myData);

    }

    public String readFromFile() throws IOException {
        return daFacade.readFromFile();
    }
>>>>>>> parent of d25c5f0... Confeck Fix
=======
=======
>>>>>>> parent of 006c2fc... Revert "Merge branch 'master' of https://github.com/simon2000234/BelManager"
    
    /**
     * Creates a log for when an error occurs
     * @param errorTimeEpochMilli the time of error
     * @param errorType the type error
     * @throws SQLException
     */
    public void createErrorLog(long errorTimeEpochMilli, String errorType)
    {
        daFacade.createErrorLog(errorTimeEpochMilli, errorType);
    }

<<<<<<< HEAD
>>>>>>> parent of 006c2fc... Revert "Merge branch 'master' of https://github.com/simon2000234/BelManager"
=======
>>>>>>> parent of 006c2fc... Revert "Merge branch 'master' of https://github.com/simon2000234/BelManager"
}
