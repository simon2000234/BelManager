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
    
    private String Initials;

    private String Name;

    private int SalaryNumber;

    /**
     * Get the value of SalaryNumber
     *
     * @return the value of SalaryNumber
     */
    public int getSalaryNumber()
    {
        return SalaryNumber;
    }

    /**
     * Set the value of SalaryNumber
     *
     * @param SalaryNumber new value of SalaryNumber
     */
    public void setSalaryNumber(int SalaryNumber)
    {
        this.SalaryNumber = SalaryNumber;
    }

    /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * Set the value of Name
     *
     * @param Name new value of Name
     */
    public void setName(String Name)
    {
        this.Name = Name;
    }

    /**
     * Get the value of Initials
     *
     * @return the value of Initials
     */
    public String getInitials()
    {
        return Initials;
    }

    /**
     * Set the value of Initials
     *
     * @param Initials new value of Initials
     */
    public void setInitials(String Initials)
    {
        this.Initials = Initials;
    }

}
