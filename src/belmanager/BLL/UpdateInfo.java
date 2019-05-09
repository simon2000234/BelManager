/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BLL;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.UpdatableInformation;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Melchertsen
 */
public class UpdateInfo implements Runnable
{

    private List<UpdatableInformation> info;
    private final int oneDayInEpochMilli = 86400000;
    private BMManager bm;

    public UpdateInfo(List<UpdatableInformation> info)
    {
        this.info = info;
        bm = new BMManager();
    }

    @Override
    public void run()
    {
        try
        {

            while (true)
            {
                Platform.runLater(() ->
                {
                    for (UpdatableInformation updatableInformation : info)
                    {
                        try
                        {
                            Order order = bm.getOrder(updatableInformation.getOrder().getOrderNumber());
                            List<DepartmentTask> tasks = order.getDepartmentTasks();
                            List<Circle> circles = updatableInformation.getCircles();
                            for (int i = 0; i < tasks.size() - 1; i++)
                            {
                                if (tasks.get(i).isFinishedOrder() == true)
                                {
                                    circles.get(i).setFill(Color.GREEN);
                                }
                                else if (tasks.get(i).getEpochEndDate() <= Instant.now().toEpochMilli())
                                {
                                    circles.get(i).setFill(Color.RED);
                                }
                                else if (tasks.get(i).getEpochEndDate()
                                        <= Instant.now().toEpochMilli() + oneDayInEpochMilli)
                                {
                                    circles.get(i).setFill(Color.ORANGE);
                                }
                                else if (tasks.get(i).getEpochEndDate()
                                        > Instant.now().toEpochMilli() + oneDayInEpochMilli
                                        && tasks.get(i).getEpochStartDate() <= Instant.now().toEpochMilli())
                                {
                                    circles.get(i).setFill(Color.YELLOW);
                                }
                                else
                                {
                                    circles.get(i).setFill(Color.GREY);
                                }

                            }
                        }
                        catch (SQLException ex)
                        {
                            Logger.getLogger(UpdateInfo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                TimeUnit.SECONDS.sleep(5);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Thred Stopped");
        }
    }

}
