/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.GUI;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import belmanager.BelManager;
import java.io.IOException;

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

    /*
    denne methode giver en mulighed for at vægle en file
    copy past fra tidligere opgave er en dejlige ting at gøre
    .trim() fjerne whiteSpace " "
    */
    @FXML
    private void pickAFile(ActionEvent event)
    {
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".JSON", "*.JSON"));
        file = fileChooser.showOpenDialog(null).getAbsoluteFile().getPath();
        System.out.println(file.trim());
        
    }

    @FXML
    private void handleButtonActionDep5(ActionEvent event) throws IOException
    {
        openDepartments();
    }

    @FXML
    private void handleButtonActionDep4(ActionEvent event) throws IOException
    {
        openDepartments();
    }

    @FXML
    private void handleButtonActionDep3(ActionEvent event) throws IOException
    {
        openDepartments();
    }

    @FXML
    private void handleButtonActionDep2(ActionEvent event) throws IOException
    {
        openDepartments();
    }

    @FXML
    private void handleButtonActionDep1(ActionEvent event) throws Exception
    {
       openDepartments();
                
                
           
    }

    @FXML
    private void handleButtonActionDep6(ActionEvent event) throws IOException
    {
        openDepartments();
       
    }

    @FXML
    private void handleButtonActionDep7(ActionEvent event) throws IOException
    {
        openDepartments();
    }
    private void openDepartments() throws IOException{
       
        Parent root;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(BelManager.class.getResource("GUI/OrderOverviewPage.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Bertel");
                stage.setScene(new Scene(root, 600, 450));
                stage.show();
                 Stage current = (Stage)PickAFile.getScene().getWindow() ;
                 current.close();
       
    }

  
    
}
