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
public class Worker
{
    
    private String initials;

    private String name;

    private int salaryNumber;

    public Worker(String initials, String name, int salaryNumber)
    {
        this.initials = initials;
        this.name = name;
        this.salaryNumber = salaryNumber;
    }
    
    

    /**
     * Get the value of SalaryNumber
     *
     * @return the value of SalaryNumber
     */
    public int getSalaryNumber()
    {
        return salaryNumber;
    }

    /**
     * Set the value of SalaryNumber
     *
     * @param SalaryNumber new value of SalaryNumber
     */
    public void setSalaryNumber(int SalaryNumber)
    {
        this.salaryNumber = SalaryNumber;
    }

    /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the value of Name
     *
     * @param Name new value of Name
     */
    public void setName(String Name)
    {
        this.name = Name;
    }

    /**
     * Get the value of Initials
     *
     * @return the value of Initials
     */
    public String getInitials()
    {
        return initials;
    }

    /**
     * Set the value of Initials
     *
     * @param Initials new value of Initials
     */
    public void setInitials(String Initials)
    {
        this.initials = Initials;
    }

    @Override
    public String toString() {
        return "Worker{" + "initials=" + initials + ", name=" + name + ", salaryNumber=" + salaryNumber + '}';
    }


}
