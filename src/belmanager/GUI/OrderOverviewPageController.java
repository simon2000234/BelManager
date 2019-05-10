/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.UpdatableInformation;
import belmanager.BLL.UpdateInfo;
import belmanager.BLL.UpdateNewPane;
import java.net.URL;
import java.time.Instant;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private BelModel bm;

    private List<TitledPane> listPanes;
    private Accordion mainAccordion;
    private final int oneDayInEpochMilli = 86400000;
    private double initialHeight;
    private double initialWidth;
    private ExecutorService newPanesUpdater;
    private Runnable newPanestask;
    private ExecutorService infoUpdater;
    private Runnable infoTask;
    private List<UpdatableInformation> updateList;
    @FXML
    private ImageView imageLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Image logo = new Image("belman_logo.jpg");
        imageLogo.setImage(logo);
        initialHeight = scrollPane.getHeight();
        initialWidth = scrollPane.getWidth();
        updateList = new ArrayList<>();
    }

    public TitledPane createTitledPane(Order order)
    {
        double X = 15.00;
        double Y = 20.00;

        List<DepartmentTask> tempDTlist;
        tempDTlist = order.getDepartmentTasks();
        AnchorPane tempAnch = new AnchorPane();
        

        // Creates the title of each TitlePane based on the Order's variables
        String titleString = "Order: " + order.getOrderNumber() + " " + "\t\t\t\t\t\t"
                + " Start Date: " + order.getDepartment(bm.getCurrentDepartment()).getStartDate() + "\t\t"
                + "End Date: " + order.getDepartment(bm.getCurrentDepartment()).getEndDate();
        TitledPane temp = new TitledPane(titleString, tempAnch);
        temp.setOnMouseClicked((MouseEvent event) ->
        {
            TitledPane tempPane = (TitledPane) event.getSource();
            String[] thisTpane = tempPane.getText().split(" ");
            String theOrderNumber = thisTpane[1];
            bm.setSelectedOrder(bm.getShownOrders().get(theOrderNumber));
        });

        //Creates labels for all the Order's variables and required information
        List<Label> labels = new ArrayList<>();
        Label OrderNumberLBL = new Label("Order Number: " + order.getOrderNumber());
        labels.add(OrderNumberLBL);
        Label DeliveryDateLBL = new Label("Delivery Date: " + order.getDeliveryTime());
        labels.add(DeliveryDateLBL);
        Label DepartmentName = new Label("Department: " + order.getDepartment(bm.getCurrentDepartment()).getDepartmentName());
        labels.add(DepartmentName);
        Label CustomerLBL = new Label(order.getCustomerName());
        labels.add(CustomerLBL);
        Label StartDateLBL = new Label(order.getDepartment(bm.getCurrentDepartment()).getStartDate());
        labels.add(StartDateLBL);
        Label EndDateLBL = new Label(order.getDepartment(bm.getCurrentDepartment()).getEndDate());
        labels.add(EndDateLBL);

        //Creates Button for marking an order as complete
        Button btnFinishOrder = new Button("Complete");
        btnFinishOrder.setMinHeight(25.00);
        AnchorPane.setTopAnchor(btnFinishOrder, Y * (labels.size() + 1));
        AnchorPane.setLeftAnchor(btnFinishOrder, X);
        btnFinishOrder.setOnAction((ActionEvent event) ->
        {

            try
            {
                bm.updateTaskIsFinished(bm.getSelectedOrder().getCurrentDepartment().getTaskID());
                removeTPane(bm.getSelectedOrder().getOrderNumber());
            }
            catch (SQLException ex)
            {
                Logger.getLogger(OrderOverviewPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        //Creates ProgressBar's in order to show realized vs planned progress.
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

        //Creates a label for each department an order has to go through
        //Creates a corresponding circle with a color RED/YELLOW/GREEN showing
        // whether an order is delayed/close to delayed/on time
        List<Circle> departmentStatus = new ArrayList<>();
        List<Label> labelsRightSide = new ArrayList<>();
        for (DepartmentTask departmentTask : tempDTlist)
        {
            Label tempDTname = new Label(departmentTask.getDepartmentName());
            labelsRightSide.add(tempDTname);
            if (departmentTask.isFinishedOrder() == true)
            {
                Circle tempCircle = new Circle(X / 2, Color.GREEN);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
            else if (departmentTask.getEpochEndDate() <= Instant.now().toEpochMilli())
            {
                Circle tempCircle = new Circle(X / 2, Color.RED);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
            else if (departmentTask.getEpochEndDate() <= Instant.now().toEpochMilli() + oneDayInEpochMilli)
            {
                Circle tempCircle = new Circle(X / 2, Color.ORANGE);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
            else if (departmentTask.getEpochEndDate() > Instant.now().toEpochMilli() + oneDayInEpochMilli
                    && departmentTask.getEpochStartDate() <= Instant.now().toEpochMilli())
            {
                Circle tempCircle = new Circle(X / 2, Color.YELLOW);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
            else
            {
                Circle tempCircle = new Circle(X / 2, Color.GREY);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
        }
        updateList.add(new UpdatableInformation(departmentStatus, order));

        //Fixes the labels constraints for the AnchorPane in the TitledPane. 
        fixLabels(labelsRightSide, X * 3, Y, true);
        fixLabels(labels, X, Y, false);

        
        //Scales all labels and text as the height of the application increases/decreases
        scrollPane.heightProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                double heightOld = (double) oldValue;
                double heightNew = (double) newValue;
                double fontsize = 11;
                double heightChanger = heightNew / 100;
                double fontChanger = (heightNew - initialHeight) / 100;
                btnFinishOrder.setPrefHeight(btnFinishOrder.getMinHeight() + heightChanger);
                imageLogo.setLayoutX((scrollPane.getWidth()/2)-(imageLogo.getFitWidth()/2));
                if ((fontsize + fontChanger) >= fontsize)
                {
                    btnFinishOrder.styleProperty().bind(Bindings.concat("-fx-font-size: ", Double.toString(fontsize + fontChanger)));
                }
                for (Label label : labels)
                {
                    label.setMinHeight(25.00);
                    double newfontsize = fontsize + fontChanger;
                    label.setPrefHeight(label.getMinHeight() + heightChanger);
                    if (newfontsize >= fontsize)
                    {
                        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", Double.toString(newfontsize)));
                    }
                }
                for (Label label : labelsRightSide)
                {
                    label.setMinHeight(25.00);
                    double newfontsize = fontsize + fontChanger;
                    label.setPrefHeight(label.getMinHeight() + heightChanger);
                    if (newfontsize >= fontsize)
                    {
                        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", Double.toString(newfontsize)));
                    }
                }
                for (Circle departmentStatu : departmentStatus)
                {
                    double defaultRadius = (X / 2);
                    double defaultAnchor = Y * departmentStatus.indexOf(departmentStatu);
                    if (labelsRightSide.get(departmentStatus.indexOf(departmentStatu)).getHeight() / 2
                            >= departmentStatu.getRadius())
                    {
                        departmentStatu.setRadius(defaultRadius + (fontChanger / 4));
                    }
                    AnchorPane.setTopAnchor(departmentStatu, (defaultAnchor + (fontChanger)));

                }

            }
        });

        //adds everything to the pane
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
        }
        else if (leftORright == true)
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
        for (TitledPane pane : mainAccordion.getPanes())
        {
            if (getOrderNumberPaneTitle(pane).equals(orderNumber))
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

    public void setModel(BelModel model)
    {
        this.bm = model;

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        listPanes = new ArrayList<>();
        mainAccordion = new Accordion();

        // Creates TitledPanes each containing the details of 1 order
        try
        {
            bm.setShownOrders(bm.createTheHashmap(bm.filterOrdersByDepartment(bm.getCurrentDepartment())));
            for (Order order : bm.filterOrdersByDepartment(bm.getCurrentDepartment()))
            {
                if (order.getDepartment(bm.getCurrentDepartment()).getEpochStartDate() < Instant.now().toEpochMilli())
                {
                    TitledPane temp = createTitledPane(order);
                    mainAccordion.getPanes().add(temp);
                    listPanes.add(temp);
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(OrderOverviewPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        vboxScroll.getChildren().add(mainAccordion);

        newPanestask = new UpdateNewPane(Instant.now().toEpochMilli(),
                mainAccordion, model.getCurrentDepartment(), this);
        //Husk skal tilføje nye panes til liste

        newPanesUpdater = Executors.newSingleThreadExecutor();
        newPanesUpdater.submit(newPanestask);

        infoTask = new UpdateInfo(updateList);

        infoUpdater = Executors.newSingleThreadExecutor();
        infoUpdater.submit(infoTask);

    }
}
