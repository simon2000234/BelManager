/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

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
        TitledPane test1 = createTitledPane("123-4567-89");
        TitledPane test2 = createTitledPane("987-6543-21");
        TitledPane test3 = createTitledPane("678-9543-21");
        TitledPane test4 = createTitledPane("123-4567-89");
        TitledPane test5 = createTitledPane("987-6543-21");
        TitledPane test6 = createTitledPane("678-9543-21");
        TitledPane test7 = createTitledPane("123-4567-89");
        TitledPane test8 = createTitledPane("987-6543-21");
        TitledPane test9 = createTitledPane("678-9543-21");
        TitledPane test10 = createTitledPane("123-4567-89");
        TitledPane test11 = createTitledPane("987-6543-21");
        TitledPane test12 = createTitledPane("678-9543-21");
        testacc.getPanes().addAll(test1, test2, test3, test4, test5, test6, test7, test8, test9, test10, test11, test12);
//        vboxScroll.setPrefSize(scrollPane.getWidth(), scrollPane.getHeight());
        vboxScroll.getChildren().add(testacc);
        System.out.println(vboxScroll.getPrefWidth());
    }

    public TitledPane createTitledPane(String OrderNumber)
    {
        double X = 15.00;
        double Y = 20.00;
        AnchorPane tempAnch = new AnchorPane();
        String titleString = "Order: " + OrderNumber + " "+"\t\t\t\t\t\t"+ " Start Date: "+"25-04-2019"+"\t\t"+"End Date: "+"30-04-2019";
        TitledPane temp = new TitledPane(titleString, tempAnch);
        temp.setOnMouseClicked((MouseEvent event) ->
        {
            TitledPane tempPane = (TitledPane) event.getSource();
            String[] thisTpane = tempPane.getText().split(" ");
            String theOrderNumber = thisTpane[1];
            System.out.println(theOrderNumber);
        });
        List<Label> labels = new ArrayList<>();
        Label OrderNumberLBL = new Label("Order Number: " + OrderNumber);
        labels.add(OrderNumberLBL);
        Label CustomerLBL = new Label("CustomerName");
        labels.add(CustomerLBL);
        Label StartDateLBL = new Label("24-04-2019");
        labels.add(StartDateLBL);
        Label EndDateLBL = new Label("25-04-2019");
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
