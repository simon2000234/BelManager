/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import belmanager.BE.DepartmentTask;
import belmanager.BE.Order;
import belmanager.BE.UpdatableInformation;
import belmanager.BE.Worker;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Caspe
 */
public class MultiOrderViewController implements Initializable
{

    @FXML
    private ScrollPane mainScrollPane;
    private List<Worker> workerList;
    private double initialHeight;
    private double initialWidth;
    private BelModel bm;
    private final int oneDayInEpochMilli = 86400000;
    private VBox vboxOne;
    private VBox vboxTwo;
    private List<UpdatableInformation> updateList;
    @FXML
    private HBox mainHBox;
    private Collection<TitledPane> boxOneList = new ArrayList<>();
    private Collection<TitledPane> boxTwoList = new ArrayList<>();
    private ExecutorService newPanesUpdater;
    private Runnable newPanestask;
    private ExecutorService infoUpdater;
    private Runnable infoTask;
    @FXML
    private ImageView imgView;
    @FXML
    private AnchorPane mainPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initialHeight = mainScrollPane.getHeight();
        initialWidth = mainScrollPane.getWidth();
        updateList = new ArrayList<>();

        try
        {
            bm = new BelModel();
        } catch (SQLException ex)
        {
            Logger.getLogger(MultiOrderViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image bellLogo = new Image("belman_logo.png");
        imgView.setImage(bellLogo);

    }

    public TitledPane createTitledPane(Order order)
    {
        double X = 15.00;
        double Y = 20.00;

        List<DepartmentTask> tempDTlist;
        tempDTlist = order.getDepartmentTasks();
        AnchorPane tempAnch = new AnchorPane();

        // Creates a TitledPane using the order number and end date, as the title.
        String titleString = "Order: " + order.getOrderNumber()
                + " " + "\t\t" + "End Date: " + order.getDepartment(bm.getCurrentDepartment()).getEndDate();
        TitledPane temp = new TitledPane(titleString, tempAnch);

        //Creates labels for all the Order's variables and required information
        List<Label> labels = new ArrayList<>();
        Label OrderNumberLBL = new Label("Order Number: " + order.getOrderNumber());
        labels.add(OrderNumberLBL);
        Label DeliveryDateLBL = new Label("Delivery Date: " + order.getDeliveryTime());
        labels.add(DeliveryDateLBL);
        Label DepartmentName = new Label("Department: " + order.getDepartment(bm.getCurrentDepartment()).getDepartmentName());
        labels.add(DepartmentName);
        Label CustomerLBL = new Label("Customer: " + order.getCustomerName());
        labels.add(CustomerLBL);
        Label StartDateLBL = new Label("Start Date: " + order.getDepartment(bm.getCurrentDepartment()).getStartDate());
        labels.add(StartDateLBL);
        Label EndDateLBL = new Label("End Date: " + order.getDepartment(bm.getCurrentDepartment()).getEndDate());
        labels.add(EndDateLBL);

        //Adds a label for workers to be shown on the order.
        Random r = new Random();
        Label WorkerLBL = new Label("Worker: " + workerList.get(r.nextInt(workerList.size() - 1)).getName());
        labels.add(WorkerLBL);

        //Creates Button for marking an order as complete
        Button btnFinishOrder = new Button("Complete");
        btnFinishOrder.setMinHeight(25.00);
        AnchorPane.setTopAnchor(btnFinishOrder, Y * (labels.size() + 1));
        AnchorPane.setLeftAnchor(btnFinishOrder, X);
        btnFinishOrder.setOnAction((ActionEvent event) ->
        {
            try
            {
                Label tempLabel = (Label) tempAnch.getChildren().get(0);
                String[] tempOrderNumber = tempLabel.getText().split(" ");
                bm.updateTaskIsFinished(bm.getShownOrders().get(tempOrderNumber[2]).getSelectedDepartmentTask().getTaskID(), order);
                removeTPane(tempOrderNumber[2]);

                bm.createCompleteLog(Instant.now().toEpochMilli(), bm.getCurrentDepartment(), order.getOrderNumber());
            } catch (SQLException ex)
            {
                System.out.println("Something went wrong at the complete button,"
                        + " are you connected to the internet?");
            }
        });
        if (!order.getCurrentDepartment().getDepartmentName().equals(order.getSelectedDepartmentTask().getDepartmentName()))
        {
            btnFinishOrder.setDisable(true);
        }
        //Creates a ProgressBar to show the estimated progress of an order
        ProgressBar estimated = new ProgressBar(0.00);
        AnchorPane.setTopAnchor(estimated, Y * (labels.size() + 3));
        AnchorPane.setLeftAnchor(estimated, X);
        AnchorPane.setRightAnchor(estimated, X);
        estimated.setPrefSize((initialWidth / 4), 25.00);
        Label progressLabel = new Label();
        AnchorPane.setTopAnchor(progressLabel, Y * (labels.size() + 2));
        AnchorPane.setRightAnchor(progressLabel, X * 2);

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
            } else if (departmentTask.getEpochEndDate() <= Instant.now().toEpochMilli())
            {
                Circle tempCircle = new Circle(X / 2, Color.RED);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            } else if (departmentTask.getEpochEndDate() <= Instant.now().toEpochMilli() + oneDayInEpochMilli)
            {
                Circle tempCircle = new Circle(X / 2, Color.ORANGE);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            } else if (departmentTask.getEpochEndDate() > Instant.now().toEpochMilli() + oneDayInEpochMilli
                    && departmentTask.getEpochStartDate() <= Instant.now().toEpochMilli())
            {
                Circle tempCircle = new Circle(X / 2, Color.YELLOW);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            } else
            {
                Circle tempCircle = new Circle(X / 2, Color.GREY);
                AnchorPane.setRightAnchor(tempCircle, X);
                AnchorPane.setTopAnchor(tempCircle, Y * tempDTlist.indexOf(departmentTask));
                departmentStatus.add(tempCircle);
            }
        }
        updateList.add(new UpdatableInformation(departmentStatus, order, estimated, progressLabel, btnFinishOrder));
        bm.getShownOrders().put(order.getOrderNumber(), order);

        //Fixes the labels constraints for the AnchorPane in the TitledPane. 
        fixLabels(labelsRightSide, X * 3, Y, true);
        fixLabels(labels, X, Y, false);

        //Scales all labels and text as the height of the application increases/decreases
        mainScrollPane.heightProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                double heightNew = (double) newValue;
                double fontsize = 11;
                double heightChanger = heightNew / 100;
                double fontChanger = (heightNew - initialHeight) / 100;
                btnFinishOrder.setPrefHeight(btnFinishOrder.getMinHeight() + heightChanger);
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
        tempAnch.getChildren().addAll(btnFinishOrder, estimated, progressLabel);
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
        for (Node node : vboxOne.getChildren())
        {
            TitledPane temp = (TitledPane) node;
            if (getOrderNumberPaneTitle(temp).equals(orderNumber))
            {
                vboxOne.getChildren().remove(temp);
                break;
            }
        }

        for (Node node : vboxTwo.getChildren())
        {
            TitledPane temp = (TitledPane) node;
            if (getOrderNumberPaneTitle(temp).equals(orderNumber))
            {
                vboxTwo.getChildren().remove(temp);
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

        vboxOne = new VBox();
        vboxTwo = new VBox();

        mainScrollPane.setFitToHeight(true);
        mainScrollPane.setFitToWidth(true);

        // Creates TitledPanes each containing the details of 1 order
        try
        {
            workerList = bm.getAllWorkers();
            int col = 0;
//            bm.createTheHashmap(bm.filterOrdersByDepartment(bm.getCurrentDepartment()));
            for (Order order : bm.filterOrdersByDepartment(bm.getCurrentDepartment()))
            {
                if (order.getDepartment(bm.getCurrentDepartment()).getEpochStartDate() < Instant.now().toEpochMilli() && col % 2 == 0)
                {
                    TitledPane temp = createTitledPane(order);
                    if (col > 5)
                    {
                        temp.setExpanded(false);
                    }
                    boxOneList.add(temp);
                    col++;
                } else if (order.getDepartment(bm.getCurrentDepartment()).getEpochStartDate() < Instant.now().toEpochMilli() && col % 2 == 1)
                {
                    TitledPane temp = createTitledPane(order);
                    if (col > 5)
                    {
                        temp.setExpanded(false);
                    }
                    boxTwoList.add(temp);
                    col++;
                }
            }
        } catch (SQLException ex)
        {
//            Logger.getLogger(OrderOverviewPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vboxOne.getChildren().addAll(boxOneList);
        vboxTwo.getChildren().addAll(boxTwoList);
        vboxOne.setMinWidth(450.00);
        vboxTwo.setMinWidth(450.00);
        vboxOne.setFillWidth(true);
        vboxTwo.setFillWidth(true);
        HBox.setHgrow(vboxOne, Priority.ALWAYS);
        HBox.setHgrow(vboxTwo, Priority.ALWAYS);
        mainHBox.setFillHeight(true);
        mainHBox.getChildren().addAll(vboxOne, vboxTwo);

        try
        {
            newPanestask = new UpdateNewPane(Instant.now().toEpochMilli(),
                    vboxOne, vboxTwo, model.getCurrentDepartment(), this);
            newPanesUpdater = Executors.newSingleThreadExecutor();
            newPanesUpdater.submit(newPanestask);

            infoTask = new UpdateInfo(updateList, bm);

            infoUpdater = Executors.newSingleThreadExecutor();
            infoUpdater.submit(infoTask);
        } catch (SQLException ex)
        {
//            Logger.getLogger(OrderOverviewPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
