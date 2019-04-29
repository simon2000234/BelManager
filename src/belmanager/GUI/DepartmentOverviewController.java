/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

/**
 *
 * @author Melchertsen
 */
public class DepartmentOverviewController implements Initializable
{

    @FXML
    private Button PickAFile;
    private String file;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleButtonActionDep5(ActionEvent event)
    {
    }

    @FXML
    private void handleButtonActionDep4(ActionEvent event)
    {
    }

    @FXML
    private void handleButtonActionDep3(ActionEvent event)
    {
    }

    @FXML
    private void handleButtonActionDep2(ActionEvent event)
    {
    }

    @FXML
    private void handleButtonActionDep1(ActionEvent event)
    {
    }

    @FXML
    private void handleButtonActionDepMore(ActionEvent event)
    {
    }

    /*
    denne methode giver en mulighed for at vægle en file
    copy past fra tidligere opgave er en dejlige ting at gøre
    */
    @FXML
    private void pickAFile(ActionEvent event)
    {
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".txt","*.txt"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".JSON", "*.JSON"));
        file = fileChooser.showOpenDialog(null).getAbsoluteFile().getPath();
        System.out.println("du har valgt: "+file);
        
    }
    
}
