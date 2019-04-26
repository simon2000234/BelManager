/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Caspe
 */
public class OrderOverviewPageController implements Initializable
{

    @FXML
    private AnchorPane mainPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxScroll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        Accordion testacc = new Accordion();
        
        // Temporary code for testing GUI and View stuff
        DepartmentTask dt1 = new DepartmentTask("Department_One", "30-04-2019", "26-04-2019", false);
        DepartmentTask dt2 = new DepartmentTask("Department_Two", "01-05-2019", "25-04-2019", false);
        DepartmentTask dt3 = new DepartmentTask("Department_Three", "29-04-2019", "23-04-2019", false);
        List<DepartmentTask> dtQueue = new ArrayList<>();
        dtQueue.add(dt1);
        dtQueue.add(dt2);
        dtQueue.add(dt3);
        
        
        Order o1 = new Order("123-456-78", "Customer_One", "Delivery_Time", dtQueue);
        Order o2 = new Order("234-567-89", "Customer_Two", "Delivery_Time", dtQueue);
        Order o3 = new Order("098-765-43", "Customer_Three", "Delivery_Time", dtQueue);
        Order o4 = new Order("987-654-32", "Customer_Four", "Delivery_Time", dtQueue);
        List<Order> orderList = new ArrayList<>();
        orderList.add(o1);
        orderList.add(o2);
        orderList.add(o3);
        orderList.add(o4);
        
        for (Order order : orderList)
        {
            testacc.getPanes().add(createTitledPane(order));
        }
        
        vboxScroll.getChildren().add(testacc);
        System.out.println(vboxScroll.getPrefWidth());
    }

    public TitledPane createTitledPane(Order order)
    {
        double X = 15.00;
        double Y = 20.00;
        AnchorPane tempAnch = new AnchorPane();
        String titleString = "Order: " + order.getOrderNumber() + " "+"\t\t\t\t\t\t"+
                " Start Date: "+order.getCurrentDepartment().getStartDate()+"\t\t"+
                "End Date: "+order.getCurrentDepartment().getEndDate();
        TitledPane temp = new TitledPane(titleString, tempAnch);
        temp.setOnMouseClicked((MouseEvent event) ->
        {
            TitledPane tempPane = (TitledPane) event.getSource();
            String[] thisTpane = tempPane.getText().split(" ");
            String theOrderNumber = thisTpane[1];
            System.out.println(theOrderNumber);
        });
        List<Label> labels = new ArrayList<>();
        Label OrderNumberLBL = new Label("Order Number: " + order.getOrderNumber());
        labels.add(OrderNumberLBL);
        Label CustomerLBL = new Label(order.getCustomerName());
        labels.add(CustomerLBL);
        Label StartDateLBL = new Label(order.getCurrentDepartment().getStartDate());
        labels.add(StartDateLBL);
        Label EndDateLBL = new Label(order.getCurrentDepartment().getEndDate());
        labels.add(EndDateLBL);
        Button btnFinishOrder = new Button("Complete");
        btnFinishOrder.setLayoutX(X);
        btnFinishOrder.setLayoutY(Y * (labels.size()+1));
        ProgressBar estimated = new ProgressBar(0.65F);
        estimated.setLayoutX(X);
        estimated.setLayoutY(Y*(labels.size()+3));
        estimated.setPrefSize((300.00), 25.00);
        ProgressBar realized = new ProgressBar(0.35F);
        realized.setLayoutX(X);
        realized.setLayoutY(Y*(labels.size()+5));
        realized.setPrefSize(300.00, 25.00);
        fixLabels(labels);
        tempAnch.getChildren().addAll(OrderNumberLBL, CustomerLBL, StartDateLBL, EndDateLBL, btnFinishOrder, estimated, realized);

        return temp;

    }
    
    public void fixLabels(List<Label> labels)
    {
        double X = 15.00;
        double Y = 20.00;
        double space = 1.00;
        for (Label label1 : labels)
        {
            label1.setLayoutX(X);
            label1.setLayoutY(Y*space);
            space++;
        }
    }
    
    
}
