/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager;

import belmanager.GUI.BelModel;
import belmanager.GUI.MultiOrderViewController;
import java.time.Instant;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

<<<<<<< HEAD
        if (model.readFromFile().isEmpty())
=======
        if (model.readFromFile() == null)
>>>>>>> parent of d25c5f0... Confeck Fix
        {
            Parent root = FXMLLoader.load(getClass().getResource("GUI/DepartmentOverview.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("GUI/BelStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        else
        {
<<<<<<< HEAD
            model.setCurrentDepartment(model.readFromFile().get(0));
            model.setTimeOffset(model.readFromFile().get(1));
            model.createLoginLog(Instant.now().toEpochMilli(), model.getCurrentDepartment());
=======
            model.setCurrentDepartment(model.readFromFile());
>>>>>>> parent of d25c5f0... Confeck Fix

            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BelManager.class.getResource("GUI/MultiOrderView.fxml"));
            root = loader.load();

<<<<<<< HEAD
            stage.setTitle(model.readFromFile().get(0));
=======
            stage.setTitle(model.readFromFile());
>>>>>>> parent of d25c5f0... Confeck Fix
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
