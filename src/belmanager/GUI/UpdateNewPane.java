/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BLL.BMManager;
import belmanager.GUI.OrderOverviewPageController;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 *
 * @author Melchertsen
 */
public class UpdateNewPane implements Runnable
{

    private long lastUpdateTime;
    private final long fiveSecInMili = 5000L;
    private Accordion acc;
    private BelModel bm;
    private String currentDepartment;
    private OrderOverviewPageController oopc;

    public UpdateNewPane(long currentTime, Accordion mainAccordion, String currentDepartment,
            OrderOverviewPageController oopc) throws SQLException
    {
        lastUpdateTime = currentTime;
        acc = mainAccordion;
        bm = new BelModel();
        this.currentDepartment = currentDepartment;
        this.oopc = oopc;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                //Gets the current up to fate information
                List<Order> Orderlist = bm.filterOrdersByDepartment(currentDepartment);
                Platform.runLater(() ->
                {
                    //Goes through each order in the list
                    for (Order order : Orderlist)
                    {
                        //Goes through each department task in each order 
                        List<DepartmentTask> tasks = order.getDepartmentTasks();
                        for (DepartmentTask task : tasks)
                        {
                            //Adds new order to the panes, on certain conditions
                            if (task.getEpochStartDate() >= lastUpdateTime
                                    && task.getEpochStartDate() <= Instant.now().toEpochMilli() + fiveSecInMili)
                            {
                                TitledPane temp = oopc.createTitledPane(order);
                                acc.getPanes().add(temp);
                            }
                        }
                    }
                });
                //waits 5 seconds before updateing again
                lastUpdateTime = Instant.now().toEpochMilli();
                TimeUnit.SECONDS.sleep(5);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Thred Stopped");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UpdateNewPane.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
