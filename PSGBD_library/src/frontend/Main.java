package frontend;

import frontend.controllers.Navigation;
import frontend.controllers.Session;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        Session session = Session.getInstance();
        session.setStage(stage);

        // Navigate to the login page
        Navigation.navigateToScene("views/login.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
