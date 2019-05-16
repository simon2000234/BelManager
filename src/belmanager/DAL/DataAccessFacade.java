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
public class DataAccessFacade {

    WorkerDAO wdao = new WorkerDAO();
    DepartmentTaskDAO dtdao = new DepartmentTaskDAO();
    DataTransfer dt = new DataTransfer();
    ConfigFileDao cfdao = new ConfigFileDao();

    public List<Order> getAllOrders() throws SQLException {
        return dtdao.getAllOrders();
    }

    public Order getOrder(String orderNumber) throws SQLException {
        return dtdao.getOrder(orderNumber);
    }

    public void deleteOrder(int OrderID) throws SQLException {
        dtdao.deleteOrder(OrderID);

    }

    public List<DepartmentTask> getAllDepartmentTasks(String orderNumber) throws SQLException {
        return dtdao.getAllDepartmentTasks(orderNumber);
    }

    public List<DepartmentTask> getAllDepartmentTasks() throws SQLException {
        return dtdao.getAllDepartmentTasks();
    }

    public Worker getWorker(int salaryNumber) throws SQLException {
        return wdao.getWorker(salaryNumber);
    }

    public void deleteWorker(int salaryNumber) throws SQLException {
        wdao.deleteWorker(salaryNumber);
    }

    public ArrayList<Worker> getAllWorkers() throws SQLException {
        return wdao.getAllWorkers();
    }

    public void moveJsonToDB(String fileLocation) throws IOException, ParseException {
        dt.moveJsonToDB(fileLocation);
    }

    public void updateTaskIsFinished(int taskID) throws SQLException {
        dtdao.updateTaskIsFinished(taskID);
    }

    public void writeToFile(String myData) throws IOException {
        cfdao.writeToFile(myData);
    }

    public String readFromFile() throws IOException {
       return cfdao.readFromFile();
    }
}
