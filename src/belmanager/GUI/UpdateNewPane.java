/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Melchertsen
 */
public class UpdateNewPane implements Runnable
{

    private long lastUpdateTime;
    private final long fiveSecInMili = 5000L;
    private VBox vboxOne;
    private VBox vboxTwo;
    private BelModel bm;
    private String currentDepartment;
    private MultiOrderViewController oopc;

    public UpdateNewPane(long lastUpdateTime, VBox vboxOne, VBox vboxTwo, String currentDepartment, MultiOrderViewController oopc) throws SQLException
    {
        this.lastUpdateTime = lastUpdateTime;
        this.vboxOne = vboxOne;
        this.vboxTwo = vboxTwo;
        this.bm = new BelModel();
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
                //Gets the current up to date information
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
                                if (vboxOne.getChildren().size() < vboxTwo.getChildren().size())
                                {
                                    vboxOne.getChildren().add(temp);
                                } else
                                {
                                    vboxTwo.getChildren().add(temp);
                                }

                            }
                        }
                    }
                });
                //waits 5 seconds before updateing again
                lastUpdateTime = Instant.now().toEpochMilli();
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException ex)
        {
            System.out.println("Thred Stopped");
        } catch (SQLException ex)
        {
            Logger.getLogger(UpdateNewPane.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}