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
import java.util.Objects;
import java.util.TimeZone;

/**
 *
 * @author Melchertsen
 */
public class Order implements Comparable<Order>
{

    private String orderNumber;

    private String customerName;

    private long deliveryTime;

    private List<DepartmentTask> departmentTasks = new ArrayList<>();

    private DepartmentTask selectedDepartmentTask;

    public Order(String orderNumber, String customerName, long deliveryTime, List<DepartmentTask> departmentTasks)
    {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.deliveryTime = deliveryTime;
        this.departmentTasks = departmentTasks;
    }

    private String convertDate(long dateToConvert)
    {
        long DateEpoch = dateToConvert;
        Date date = new Date(DateEpoch);
        DateFormat format = new SimpleDateFormat("ww:u");
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
        return convertDate(deliveryTime);
    }

    public long getEpochDeliveryTime()
    {
        return deliveryTime;
    }

    public DepartmentTask setSelectedDepartmentTask(String departmentname)
    {
        DepartmentTask temp = null;
        for (DepartmentTask departmentTask : departmentTasks)
        {
            if (departmentTask.isFinishedOrder() == false && departmentTask.getDepartmentName().equals(departmentname))
            {
                selectedDepartmentTask = departmentTask;
            }
        }
        return temp;
    }

    public DepartmentTask getSelectedDepartmentTask()
    {
        if (selectedDepartmentTask != null)
        {
            return selectedDepartmentTask;
        }
        return null;
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

    public DepartmentTask getDepartment(String department)
    {
        DepartmentTask temp = null;
        for (DepartmentTask departmentTask : departmentTasks)
        {
            if (departmentTask.getDepartmentName().equals(department))
            {
                return departmentTask;
            }
        }
        return temp;
    }

    public ArrayList<String> getAllDepartments()
    {
        ArrayList<String> allDeps = new ArrayList<>();
        List<DepartmentTask> allTasks =  departmentTasks;

        for (DepartmentTask task : allTasks)
        {
            allDeps.add(task.getDepartmentName());
        }
        return allDeps;
    }

    @Override
    public String toString()
    {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", deliveryTime=" + deliveryTime + ", departmentTasks=" + departmentTasks + '}';
    }

    @Override
    public int compareTo(Order o)
    {
        long thisDepartment = this.getSelectedDepartmentTask().getEpochEndDate();
        long givenDate = o.getSelectedDepartmentTask().getEpochEndDate();

        if (thisDepartment < givenDate)
        {
            return -1;
        }
        if (thisDepartment > givenDate)
        {
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.orderNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.orderNumber, other.orderNumber))
        {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName))
        {
            return false;
        }
        if (!Objects.equals(this.deliveryTime, other.deliveryTime))
        {
            return false;
        }
        if (!Objects.equals(this.departmentTasks, other.departmentTasks))
        {
            return false;
        }
        for (int i = 0; i < departmentTasks.size() - 1; i++)
        {
            if (!Objects.equals(this.departmentTasks.get(i), other.departmentTasks.get(i)))
            {
                return false;
            }
        }

        return true;
    }

}
