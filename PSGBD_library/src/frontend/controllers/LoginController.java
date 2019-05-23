package frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController
{
    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private ComboBox<String> accountTypeCombo;

    @FXML
    void handleLoginButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        String email = emailInput.getText();
        String password = passwordInput.getText();

        String accountType = accountTypeCombo.getValue();
        if (accountType == null)
            accountType = "Client";

        try
        {
            Authentication.login(email, password, accountType);
            Navigation.navigateToScene("views/client/homeMenu.fxml");
        }
        catch (IOException | IllegalArgumentException | SQLException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSignupButtonClick(MouseEvent event)
    {
        try
        {
            Navigation.navigateToScene("views/register.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
