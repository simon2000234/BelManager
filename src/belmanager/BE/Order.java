/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author Melchertsen
 */
public class Order
{

    private String orderNumber;

    private String customerName;

    private String deliveryTime;

    private ArrayList<DepartmentTask> departmentTasks;

    public Order(String orderNumber, String customerName, String deliveryTime, ArrayList<DepartmentTask> departmentTasks) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.deliveryTime = deliveryTime;
        this.departmentTasks = departmentTasks;
    }

    
    public ArrayList<DepartmentTask> getDepartmentTasks()
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

    @Override
    public String toString() {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", deliveryTime=" + deliveryTime + ", departmentTasks=" + departmentTasks + '}';
    }


}
