/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BLL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
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
    private Accordion acc;
    private BMManager bm;
    private String currentDepartment;
    private OrderOverviewPageController oopc;

    public UpdateNewPane(long currentTime, Accordion mainAccordion, String currentDepartment,
            OrderOverviewPageController oopc)
    {
        lastUpdateTime = currentTime;
        acc = mainAccordion;
        bm = new BMManager();
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
                List<Order> Orderlist = bm.filterOrdersByDepartment(currentDepartment);
                Platform.runLater(() ->
                {

                    for (Order order : Orderlist)
                    {
                        List<DepartmentTask> tasks = order.getDepartmentTasks();
                        for (DepartmentTask task : tasks)
                        {
                            if (task.getEpochStartDate() >= lastUpdateTime
                                    && task.getEpochStartDate() <= Instant.now().toEpochMilli())
                            {
                                TitledPane temp = oopc.createTitledPane(order);
                                acc.getPanes().add(temp);
                            }
                        }

                    }
                });
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
