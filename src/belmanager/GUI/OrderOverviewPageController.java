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
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

    List<TitledPane> listPanes;
    Accordion mainAccordion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        listPanes = new ArrayList<>();
        mainAccordion = new Accordion();

        // Temporary code for testing GUI and View stuff
        DepartmentTask dt1 = new DepartmentTask("Department_One", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", true,0);
        DepartmentTask dt2 = new DepartmentTask("Department_Two", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", false,1);
        DepartmentTask dt3 = new DepartmentTask("Department_Three", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", false,2);
        DepartmentTask dt4 = new DepartmentTask("Department_Four", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", false,3);
        DepartmentTask dt5 = new DepartmentTask("Department_Five", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", false,4);
        DepartmentTask dt6 = new DepartmentTask("Department_Six", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", false,5);
        DepartmentTask dt7 = new DepartmentTask("Department_Seven", "/Date(1559955098813+0200)/", "/Date(1559955098813+0200)/", false,6);
        List<DepartmentTask> dtQueue = new ArrayList<>();
        dtQueue.add(dt1);
        dtQueue.add(dt2);
        dtQueue.add(dt3);
        dtQueue.add(dt4);
        dtQueue.add(dt5);
        dtQueue.add(dt6);
        dtQueue.add(dt7);

        Order o1 = new Order("123-456-78", "Customer_One", "/Date(1559955098813+0200)/", dtQueue);
        Order o2 = new Order("234-567-89", "Customer_Two", "/Date(1559955098813+0200)/", dtQueue);
        Order o3 = new Order("098-765-43", "Customer_Three", "/Date(1559955098813+0200)/", dtQueue);
        Order o4 = new Order("987-654-32", "Customer_Four", "/Date(1559955098813+0200)/", dtQueue);
        List<Order> orderList = new ArrayList<>();
        orderList.add(o1);
        orderList.add(o2);
        orderList.add(o3);
        orderList.add(o4);

        for (Order order : orderList)
        {
            TitledPane temp = createTitledPane(order);
            mainAccordion.getPanes().add(temp);
            listPanes.add(temp);
        }

        AnchorPane sometest = (AnchorPane) listPanes.get(0).getContent();

        Label oktest = (Label) sometest.getChildren().get(2);
        oktest.setText("Ikke mere department");

        //---------Test code ends----------
        vboxScroll.getChildren().add(mainAccordion);

    }

    public TitledPane createTitledPane(Order order)
    {
        double X = 15.00;
        double Y = 20.00;

        List<DepartmentTask> tempDTlist = new ArrayList<>();
        tempDTlist = order.getDepartmentTasks();

        AnchorPane tempAnch = new AnchorPane();
        String titleString = "Order: " + order.getOrderNumber() + " " + "\t\t\t\t\t\t"
                + " Start Date: " + order.getCurrentDepartment().getStartDate() + "\t\t"
                + "End Date: " + order.getCurrentDepartment().getEndDate();
        TitledPane temp = new TitledPane(titleString, tempAnch);
        temp.setOnMouseClicked((MouseEvent event) ->
        {
            TitledPane tempPane = (TitledPane) event.getSource();
            String[] thisTpane = tempPane.getText().split(" ");
            String theOrderNumber = thisTpane[1];
        });
        List<Label> labels = new ArrayList<>();
        Label OrderNumberLBL = new Label("Order Number: " + order.getOrderNumber());
        labels.add(OrderNumberLBL);
        Label DeliveryDateLBL = new Label("Delivery Date: " + order.getDeliveryTime());
        labels.add(DeliveryDateLBL);
        Label DepartmentName = new Label("Department: " + order.getCurrentDepartment().getDepartmentName());
        labels.add(DepartmentName);
        Label CustomerLBL = new Label(order.getCustomerName());
        labels.add(CustomerLBL);
        Label StartDateLBL = new Label(order.getCurrentDepartment().getStartDate());
        labels.add(StartDateLBL);
        Label EndDateLBL = new Label(order.getCurrentDepartment().getEndDate());
        labels.add(EndDateLBL);
        Button btnFinishOrder = new Button("Complete");
        AnchorPane.setTopAnchor(btnFinishOrder, Y * (labels.size() + 1));
        AnchorPane.setLeftAnchor(btnFinishOrder, X);
        btnFinishOrder.setOnAction((ActionEvent event) ->
        {
            Button completeButton = (Button) event.getSource();
            AnchorPane currentApane = (AnchorPane) completeButton.getParent();
            Label orderLabel = (Label) currentApane.getChildren().get(0);
            String currentOrder = orderLabel.getText();
            removeTPane(currentOrder);

        });
        ProgressBar estimated = new ProgressBar(0.65F);
        AnchorPane.setTopAnchor(estimated, Y * (labels.size() + 3));
        AnchorPane.setLeftAnchor(estimated, X);
        AnchorPane.setRightAnchor(estimated, X);
        estimated.setPrefSize((300.00), 25.00);
        ProgressBar realized = new ProgressBar(0.35F);
        AnchorPane.setTopAnchor(realized, Y * (labels.size() + 5));
        AnchorPane.setLeftAnchor(realized, X);
        AnchorPane.setRightAnchor(realized, X);
        realized.setPrefSize(300.00, 25.00);
        
        List<Circle> departmentStatus = new ArrayList<>();
        List<Label> labelsRightSide = new ArrayList<>();
        for (DepartmentTask departmentTask : tempDTlist)
        {
            Label tempDTname = new Label(departmentTask.getDepartmentName());
            labelsRightSide.add(tempDTname);
            if (departmentTask.getDepartmentName().equals(tempDTlist.get(1).getDepartmentName()))
            {
                Circle tempCircle = new Circle(X/2, Color.YELLOW);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            } else if (departmentTask.isFinishedOrder() == true)
            {
                Circle tempCircle = new Circle(X/2, Color.GREEN);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            } else
            {
                Circle tempCircle = new Circle(X/2, Color.RED);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
        }
        fixLabels(labelsRightSide, X*3, Y, true);
        fixLabels(labels, X, Y, false);
        
        tempAnch.getChildren().addAll(labels);
        tempAnch.getChildren().addAll(labelsRightSide);
        tempAnch.getChildren().addAll(btnFinishOrder, estimated, realized);
        tempAnch.getChildren().addAll(departmentStatus);

        return temp;

    }

    /**
     * Makes sure that all labels added are separated to avoid overlapping nodes
     * by setting AnchorPane constraints
     *
     * @param labels
     * @param x
     * @param y
     * @param leftORright - decided by boolean, false = left and true =
     * right(refers to left/right AnchorPane constraints).
     */
    public void fixLabels(List<Label> labels, double x, double y, boolean leftORright)
    {
        double X = x;
        double Y = y;
        double space = 0.00;
        if (leftORright == false)
        {
            for (Label label1 : labels)
            {
                AnchorPane.setTopAnchor(label1, Y * space);
                AnchorPane.setLeftAnchor(label1, X);
                space++;
            }
        } else if (leftORright == true)
        {
            for (Label label1 : labels)
            {
                AnchorPane.setTopAnchor(label1, Y * space);
                AnchorPane.setRightAnchor(label1, X);
                space++;
            }
        }
    }

    /**
     * Removes a TitledPane from the Accordion, so it no longer shows up on the
     * view. The TitledPane is only removed if the Order Number of the panes
     * title, is equal to the Order Number given as a parameter.
     *
     * @param orderNumber
     */
    public void removeTPane(String orderNumber)
    {
        String[] tempStringArray = orderNumber.split(" ");
        String tempString = tempStringArray[2];

        for (TitledPane pane : mainAccordion.getPanes())
        {
            if (getOrderNumberPaneTitle(pane).equals(tempString))
            {
                mainAccordion.getPanes().remove(pane);
                break;
            }
        }
    }

    /**
     * Creates a substring from a TitledPane's title containing only the Order
     * number, then returns it as a string.
     *
     * @param tpane
     * @return a String containing an order number
     */
    public String getOrderNumberPaneTitle(TitledPane tpane)
    {
        TitledPane tempPane = tpane;
        String[] thisTpane = tempPane.getText().split(" ");
        String theOrderNumber = thisTpane[1];
        return theOrderNumber;
    }

}
