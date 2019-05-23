/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.BE;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Circle;

/**
 *
 * @author Melchertsen
 */
public class UpdatableInformation
{

    private List<Circle> circles;
    private Order order;
    private ProgressBar estimatedBar;
    private Label progress;
    private Button btnComplete;

    public UpdatableInformation(List<Circle> circles, Order order, ProgressBar estimatedBar, Label progress, Button btn)
    {
        this.circles = circles;
        this.order = order;
        this.estimatedBar = estimatedBar;
        this.progress = progress;
        this.btnComplete = btn;
    }

    public Order getOrder()
    {
        return order;
    }

    public List<Circle> getCircles()
    {
        return circles;
    }

    public ProgressBar getEstimatedBar()
    {
        return estimatedBar;
    }

    public Label getProgressLabel()
    {
        return progress;
    }

    public Button getBtnComplete()
    {
        return btnComplete;
    }

}
