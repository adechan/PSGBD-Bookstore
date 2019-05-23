package frontend.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {

    public static Scene navigateToScene(String location) throws IOException
    {
        Session session = Session.getInstance();
        Stage stage = session.getStage();

        System.out.println("Loading page at [" + location + "]");

        // Load FXML resource
        Scene scene = new Scene(FXMLLoader.load(session.getClass().getResource("../" + location)));

        // Update session
        session.setCurrentScene(scene);

        // Show the scene in the current stage
        stage.setScene(scene);
        stage.show();

        // Update the current scene
        return scene;
    }

    public static Pane navigateToScene(Pane pane, String location) throws IOException
    {
        Session session = Session.getInstance();

        System.out.println("Loading page at [" + location + "]");
        Pane newPane = FXMLLoader.load(session.getClass().getResource("../" + location));
        pane.getChildren().setAll(newPane);

        return newPane;
    }
}
