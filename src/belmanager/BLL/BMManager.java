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

    public List<Order> getAllOrders(String currentDepartment) throws SQLException {
        List<Order> temp = new ArrayList<>();
        for (Order allOrder : daFacade.getAllOrders())
        {
            allOrder.setSelectedDepartmentTask(currentDepartment);
            temp.add(allOrder);
        }
        return temp;
    }

    public void deleteOrder(int orderID) throws SQLException {
        daFacade.deleteOrder(orderID);

    }

    public Order getOrder(String orderNumber) throws SQLException {
        return daFacade.getOrder(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException {
        return daFacade.getAllDepartmentTasks(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException {
        return daFacade.getAllDepartmentTasks();
    }

    public Worker getWorker(int salaryNumber) throws SQLException {
        return daFacade.getWorker(salaryNumber);
    }

    public void deleteWorker(int salaryNumber) throws SQLException {
        daFacade.deleteWorker(salaryNumber);
    }

    public ArrayList<Worker> getAllWorkers() throws SQLException {
        return daFacade.getAllWorkers();
    }

    public void moveJsonToDB(String fileLocation) throws IOException, ParseException {
        daFacade.moveJsonToDB(fileLocation);
    }

    public void updateTaskIsFinished(int taskID) throws SQLException {
        daFacade.updateTaskIsFinished(taskID);
    }

    public void writeToFile(String myData) throws IOException {
        daFacade.writeToFile(myData);

    }

    public String readFromFile() throws IOException {
        return daFacade.readFromFile();
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
        daFacade.createCompleteLog(compleTimeEpocMilli, deparment, orderNumber);
    }
    
    /**
     * Creates a log for the time a deparment login to the program
     * @param loginTimeEpocMilli the time of completion in epoc milli
     * @param depLogin the department that login
     * @throws SQLException
     */
    public void createLoginLog(long loginTimeEpocMilli, String depLogin) throws SQLException
    {
        daFacade.createLoginLog(loginTimeEpocMilli, depLogin);
    }
}
