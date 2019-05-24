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
import java.sql.SQLException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Melchertsen
 */
public class DepartmentOverviewController implements Initializable
{

    @FXML
    private Button PickAFile;
    private String file;
    @FXML
    private Button btnDep5;
    @FXML
    private Button btnDep4;
    @FXML
    private Button btnDep3;
    @FXML
    private Button btnDep2;
    @FXML
    private Button btnDep1;
    @FXML
    private Button btnDep6;
    @FXML
    private Button btnDep7;
    private BelModel model;
    @FXML
    private TextField txtOffset;

    @Override

    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            model = new BelModel();

        } catch (SQLException ex)
        {

            Logger.getLogger(DepartmentOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    denne methode giver en mulighed for at vægle en file
    copy past fra tidligere opgave er en dejlige ting at gøre
    .trim() fjerne whiteSpace " "
     */
    @FXML
    private void pickAFile(ActionEvent event)
    {
        try
        {
            FileChooser fileChooser;
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".JSON", "*.JSON"));
            file = fileChooser.showOpenDialog(null).getAbsoluteFile().getPath();
            model.moveJsonToDB(file.trim());
        } catch (IOException ex)
        {
            Logger.getLogger(DepartmentOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(DepartmentOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleButtonActionDep5(ActionEvent event) throws IOException
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep5.getText(), txtOffset.getText());
            openDepartments(btnDep5.getText());

        } else
        {
            AlertNoOffset(btnDep5.getText());
        }
    }

    @FXML
    private void handleButtonActionDep4(ActionEvent event) throws IOException
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep4.getText(), txtOffset.getText());
            openDepartments(btnDep4.getText());

        } else
        {
            AlertNoOffset(btnDep4.getText());
        }
    }

    @FXML
    private void handleButtonActionDep3(ActionEvent event) throws IOException
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep3.getText(), txtOffset.getText());
            openDepartments(btnDep3.getText());

        } else
        {
            AlertNoOffset(btnDep3.getText());
        }
    }

    @FXML
    private void handleButtonActionDep2(ActionEvent event) throws IOException
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep2.getText(), txtOffset.getText());
            openDepartments(btnDep2.getText());

        } else
        {
            AlertNoOffset(btnDep2.getText());
        }
    }

    @FXML
    private void handleButtonActionDep1(ActionEvent event) throws Exception
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep1.getText(), txtOffset.getText());
            openDepartments(btnDep1.getText());

        } else
        {
            AlertNoOffset(btnDep1.getText());
        }

    }

    @FXML
    private void handleButtonActionDep6(ActionEvent event) throws IOException
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep6.getText(), txtOffset.getText());
            openDepartments(btnDep6.getText());

        } else
        {
            AlertNoOffset(btnDep6.getText());
        }

    }

    @FXML
    private void handleButtonActionDep7(ActionEvent event) throws IOException
    {
        if (isInputValid(txtOffset.getText()) == true)
        {
            model.writeToFile(btnDep7.getText(), txtOffset.getText());
            openDepartments(btnDep7.getText());
        } else
        {
            AlertNoOffset(btnDep7.getText());
        }
    }

    private void openDepartments(String departmentName) throws IOException
    {

        try
        {
            model.setCurrentDepartment(departmentName);
            if (isInputValid(txtOffset.getText()) == true)
            {
                model.setTimeOffset(txtOffset.getText());
            } else
            {
                model.setTimeOffset("0");
            }

            model.createLoginLog(Instant.now().toEpochMilli(), model.getCurrentDepartment());

            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BelManager.class.getResource("GUI/MultiOrderView.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(departmentName);
            stage.setScene(new Scene(root, 1000, 750));
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent we)
                {
                    Platform.exit();
                    System.exit(0);
                }
            });
            MultiOrderViewController mopController = loader.getController();
            mopController.setModel(model);

            Stage current = (Stage) PickAFile.getScene().getWindow();
            current.close();
        } catch (SQLException ex)
        {
            System.out.println("Something went wrong with SQL, are you connected to the right wifi?");
        }

    }

    public boolean isInputValid(String s)
    {
        int valid;
        try
        {
            valid = Integer.parseInt(s);
            if (valid >= 0)
            {
                return true;
            } else
            {
                return false;
            }
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

    public void AlertNoOffset(String department)
    {
        Alert offsetAlert = new Alert(Alert.AlertType.CONFIRMATION);
        offsetAlert.setContentText("No Time-Offset has been set, if you continue the offset will be defaulted to 0");
        offsetAlert.showAndWait();
        if (offsetAlert.getResult() == ButtonType.OK)
        {
            try
            {
                openDepartments(department);
                model.writeToFile(department, "0");
            } catch (IOException ex)
            {
                Logger.getLogger(DepartmentOverviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (offsetAlert.getResult() == ButtonType.CANCEL)
        {
            offsetAlert.close();
        }

    }

}
