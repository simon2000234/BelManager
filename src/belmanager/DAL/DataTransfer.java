package belmanager.DAL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Melchertsen
 */
public class DataTransfer
{

    public void moveJsonToDB(String fileName) throws IOException, ParseException 
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject JsonFile = (JSONObject) obj;
            JSONArray workerList = (JSONArray) JsonFile.get("AvailableWorkers");
            workerList.forEach( worker -> transferWorkersToDB( (JSONObject) worker ) );

            JSONArray orderList = (JSONArray) JsonFile.get("ProductionOrders");
            orderList.forEach(order -> transferOrdersToDB((JSONObject) order));

        }

    }

    public void transferWorkersToDB(JSONObject worker)
    {
        try
        {
            String initials = (String) worker.get("Initials");
            String name = (String) worker.get("Name");
            long stringSalaryNumber = (long) worker.get("SalaryNumber");
            int salaryNumber = (int) stringSalaryNumber;
            WorkerDAO wd = new WorkerDAO();
            wd.createWorker(initials, name, salaryNumber);
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Fail, are you connected to the school internet?");
        }
    }

    public void transferOrdersToDB(JSONObject order)
    {
        try
        {
            DepartmentTaskDAO dt = new DepartmentTaskDAO();
            
            JSONObject customer = (JSONObject) order.get("Customer");
            String customerName = (String) customer.get("Name");
            
            JSONObject delivery = (JSONObject) order.get("Delivery");
            String deliveryTime = (String) delivery.get("DeliveryTime");
            
            JSONObject realOrder = (JSONObject) order.get("Order");
            String orderNumber = (String) realOrder.get("OrderNumber");
            
            dt.createOrder(orderNumber, customerName, deliveryTime);
            JSONArray taskList = (JSONArray) order.get("DepartmentTasks");
            
            taskList.forEach(task -> transferTasksToDB((JSONObject) task, orderNumber));
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Fail, are you connected to the school internet?");
        }
    }

    public void transferTasksToDB(JSONObject task, String currentOrderNumber)
    {
        try
        {
            DepartmentTaskDAO dt = new DepartmentTaskDAO();
            
            JSONObject department = (JSONObject) task.get("Department");
            String departmentName = (String) department.get("Name");
            
            String endDate = (String) task.get("EndDate");
            
            String startDate = (String) task.get("StartDate");
            
            boolean finishedOrder = (boolean) task.get("FinishedOrder");
            
            dt.createDeparmentTask(departmentName, endDate, startDate, finishedOrder, currentOrderNumber);
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Fail, are you connected to the school internet?");
        }
    }

}
