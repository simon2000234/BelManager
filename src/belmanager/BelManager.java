/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager;

import belmanager.GUI.BelModel;
import belmanager.GUI.MultiOrderViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author Melchertsen
 */
public class BelManager extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        BelModel model = new BelModel();

        if (model.readFromFile() == null)
        {
            Parent root = FXMLLoader.load(getClass().getResource("GUI/DepartmentOverview.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("GUI/BelStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            model.setCurrentDepartment(model.readFromFile());

            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BelManager.class.getResource("GUI/MultiOrderView.fxml"));
            root = loader.load();

            stage.setTitle(model.readFromFile());
            stage.setScene(new Scene(root, 1000, 750));
            stage.setMaximized(true);
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

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
