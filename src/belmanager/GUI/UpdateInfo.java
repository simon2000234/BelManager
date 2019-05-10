/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.UpdatableInformation;
import belmanager.BLL.BMManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Melchertsen
 */
public class UpdateInfo implements Runnable
{

    private List<UpdatableInformation> info;
    private final int oneDayInEpochMilli = 86400000;
    private BelModel bm;
    private int currentOrderIndex;

    public UpdateInfo(List<UpdatableInformation> info) throws SQLException
    {
        this.info = info;
        bm = new BelModel();
    }

    @Override
    public void run()
    {
        try
        {

            while (true)
            {
                //Gets the new version of all the orders
                List<Order> orders = bm.getAllOrders();

                Platform.runLater(() ->
                {
                    for (UpdatableInformation updatableInformation : info)
                    {
                        for (int i = 0; i < orders.size(); i++)
                        {
                            //Gets the order from the new info that matches the old info
                            if (orders.get(i).getOrderNumber().equals
                                (updatableInformation.getOrder().getOrderNumber()))
                            {
                                currentOrderIndex = i;
                            }
                        }
                        {

                            //Runs through the old info and changes it so it matches the new info
                            Order order = orders.get(currentOrderIndex);
                            List<DepartmentTask> tasks = order.getDepartmentTasks();
                            List<Circle> circles = updatableInformation.getCircles();
                            for (int i = 0; i < tasks.size(); i++)
                            {
                                Paint color = circles.get(i).getFill();
                                if (tasks.get(i).isFinishedOrder() == true)
                                {
                                    //Only change color if there is a defferance
                                    if (color != Color.GREEN)
                                    {
                                        circles.get(i).setFill(Color.GREEN);
                                    }
                                }
                                else if (tasks.get(i).getEpochEndDate() <= Instant.now().toEpochMilli())
                                {
                                    //Only change color if there is a defferance
                                    if (color != Color.RED)
                                    {
                                        circles.get(i).setFill(Color.RED);
                                    }
                                }
                                else if (tasks.get(i).getEpochEndDate()
                                        <= Instant.now().toEpochMilli() + oneDayInEpochMilli)
                                {
                                    //Only change color if there is a defferance
                                    if (color != Color.ORANGE)
                                    {
                                        circles.get(i).setFill(Color.ORANGE);
                                    }
                                }
                                else if (tasks.get(i).getEpochEndDate()
                                        > Instant.now().toEpochMilli() + oneDayInEpochMilli
                                        && tasks.get(i).getEpochStartDate() <= Instant.now().toEpochMilli())
                                {
                                    //Only change color if there is a defferance
                                    if (color != Color.YELLOW)
                                    {
                                        circles.get(i).setFill(Color.YELLOW);
                                    }
                                }
                                else
                                {
                                    //Only change color if there is a defferance
                                    if (color != Color.GREY)
                                    {
                                        circles.get(i).setFill(Color.GREY);
                                    }
                                }

                            }
                        }
                    }
                });
                //Waits 5 seconds before updating again
                TimeUnit.SECONDS.sleep(5);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Thred Stopped");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UpdateInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
