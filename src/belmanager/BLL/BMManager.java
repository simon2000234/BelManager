/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BLL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.DAL.DataAccessFacade;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caspe
 */
public class BMManager
{
    DataAccessFacade daFacade = new DataAccessFacade();

    /**
     * Filters all orders, showing only the orders where their current department
     * is the department that we are trying to get orders for.
     *
     * @param currentDepartment
     * @return a list of orders where the current department equals the department
     * given in the parameter.
     */
    public List<Order> filterOrders(DepartmentTask currentDepartment) throws SQLException
    {
        List<Order> temp = new ArrayList<>();
        
        for (Order order : daFacade.getAllOrders())
        {
            if (currentDepartment.getDepartmentName().equals(order.getCurrentDepartment().getDepartmentName()))
            {
                temp.add(order);
            }
        }       
        return temp;

    }

}
