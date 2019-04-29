/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

import java.util.ArrayList;
import java.util.List;

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
        this.deliveryTime = deliveryTime;
        this.departmentTasks = departmentTasks;
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
                temp = new DepartmentTask(departmentTask.getDepartmentName(), departmentTask.getEndDate(), departmentTask.getStartDate(), false, departmentTask.getTaskID());
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
