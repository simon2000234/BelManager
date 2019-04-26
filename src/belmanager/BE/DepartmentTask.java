/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

/**
 *
 * @author Melchertsen
 */
public class DepartmentTask
{

    private String departmentName;
    private String endDate;
    private String startDate;
    private boolean finishedOrder;

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
}
