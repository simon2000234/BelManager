/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 *
 * @author Melchertsen
 */
public class DepartmentTask implements Comparable<Long>
{

    private String departmentName;
    private long endDate;
    private long startDate;
    private boolean finishedOrder;
    private int taskID;

    public DepartmentTask(String departmentName, long endDate, long startDate, boolean finishedOrder, int taskID)
    {
        this.departmentName = departmentName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.finishedOrder = finishedOrder;
        this.taskID = taskID;
    }

    private String convertDate(long dateToConvert)
    {

        long dateEpoch = dateToConvert;
        Date date = new Date(dateEpoch);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy (ww:u)");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formatted = format.format(date);
        return formatted;
    }

    public int getTaskID()
    {
        return taskID;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getEndDate()
    {
        return convertDate(endDate);
    }

    public long getEpochEndDate()
    {
        return endDate;
    }

    public String getStartDate()
    {
        return convertDate(startDate);
    }

    public long getEpochStartDate()
    {
        return startDate;
    }

    public boolean isFinishedOrder()
    {
        return finishedOrder;
    }

    public void setFinishedOrder(boolean finishedOrder)
    {
        this.finishedOrder = finishedOrder;
    }

    @Override
    public String toString()
    {
        return "DepartmentTask{" + "departmentName=" + departmentName + ", endDate=" + endDate + ", startDate=" + startDate + ", finishedOrder=" + finishedOrder + '}';
    }

    @Override
    public int compareTo(Long o)
    {
        long thisDepartment = getEpochStartDate();
        long givenDate = o;

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
        final DepartmentTask other = (DepartmentTask) obj;
        if (this.finishedOrder != other.finishedOrder)
        {
            return false;
        }
        if (this.taskID != other.taskID)
        {
            return false;
        }
        if (!Objects.equals(this.departmentName, other.departmentName))
        {
            return false;
        }
        return true;
    }

}
