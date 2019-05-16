package belmanager.DAL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
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

    /**
     * This methord takes a JSON file given to us by Belman, converts it 
     * and sendt it to our database
     * @param fileLocation the location of the file
     * @throws IOException
     * @throws ParseException
     */
    protected void moveJsonToDB(String fileLocation) throws IOException, ParseException 
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileLocation))
        {
            //Gets a hold of the entire file
            Object obj = jsonParser.parse(reader);
            JSONObject JsonFile = (JSONObject) obj;
            
            //Gets a hold of the array that holds all the workers in the file
            JSONArray workerList = (JSONArray) JsonFile.get("AvailableWorkers");
            //Goes through each worker and send them to the database
            workerList.forEach( worker -> transferWorkersToDB( (JSONObject) worker ) );

            //Gets a hold of the array that holds all the orders in the file
            JSONArray orderList = (JSONArray) JsonFile.get("ProductionOrders");
            //Goes through each order and sendt it to the database
            orderList.forEach(order -> transferOrdersToDB((JSONObject) order));

        }

    }

    /**
     * Goes through a workers data in the JSON file and sends it to our database
     * @param worker the worker object in the JSON file
     */
    private void transferWorkersToDB(JSONObject worker)
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

    /**
     * Goes through an orders data in the JSON file and sends it to our database
     * @param order the order object in the JSON file
     */
    private void transferOrdersToDB(JSONObject order)
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
            
            //Each order can have multiple tasks in it, 
            //therefore we go through each and send it to our database
            taskList.forEach(task -> transferTasksToDB((JSONObject) task, orderNumber));
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Fail, are you connected to the school internet?");
        }
    }

    /**
     * Goes through a taskin the JSON file and sends it to our databse
     * @param task the task object in the JSON file
     * @param currentOrderNumber the orderNumber of the order 
     * this task belongs to
     */
    private void transferTasksToDB(JSONObject task, String currentOrderNumber)
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
