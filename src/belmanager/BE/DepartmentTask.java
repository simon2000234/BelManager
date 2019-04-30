/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Melchertsen
 */
public class DepartmentTask implements Comparable<String>
{

    private String departmentName;
    private String endDate;
    private String startDate;
    private boolean finishedOrder;
    private int taskID;

    public DepartmentTask(String departmentName, String endDate, String startDate, boolean finishedOrder, int taskID)
    {
        this.departmentName = departmentName;
        this.endDate = convertDate(endDate);
        this.startDate = convertDate(startDate);
        this.finishedOrder = finishedOrder;
        this.taskID = taskID;
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
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
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
    public int compareTo(String o)
    {
        int c;
        String[] thisDepartment = this.startDate.split("-");
        String[] givenDate = o.split("-");

        c = thisDepartment[2].compareTo(givenDate[2]);
        if (c >= 0)
        {
            c = thisDepartment[1].compareTo(givenDate[1]);
        }
        if (c >= 0)
        {
            c = thisDepartment[0].compareTo(givenDate[0]);
        }
        if (c >= 0)
        {
            return c;
        }
        return -1;
    }

}
