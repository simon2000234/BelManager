/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author Melchertsen
 */
public class Order
{

    private String orderNumber;

    private String customerName;

    private String deliveryTime;
    
    private List<DepartmentTask> departmentTasks = new ArrayList<>();

    public Order(String orderNumber, String customerName, String deliveryTime, List<DepartmentTask> departmentTasks)
    {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.deliveryTime = convertDate(deliveryTime);
        this.departmentTasks = departmentTasks;
    }

    private String convertDate(String dateToConvert)
    {
        String sringDate = dateToConvert.substring(6, 19);
        long DateEpoch = Long.parseLong(sringDate);
        Date date = new Date(DateEpoch);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formatted = format.format(date);
        return formatted;
    }

    public List<DepartmentTask> getDepartmentTasks()
    {
        return departmentTasks;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getDeliveryTime()
    {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }

    public DepartmentTask getCurrentDepartment()
    {
        
        DepartmentTask temp = null;
        for (DepartmentTask departmentTask : departmentTasks)
        {
            if (departmentTask.isFinishedOrder() == false)
            {
                return departmentTask;
            }
        }
        return temp;
    }

    @Override
    public String toString()
    {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", deliveryTime=" + deliveryTime + ", departmentTasks=" + departmentTasks + '}';
    }

}
