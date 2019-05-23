package frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController
{
    @FXML
    private TextField nameInput;

    @FXML
    private TextField surnameInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField passwordInput;

    @FXML
    void handleSignupButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        String email = emailInput.getText();
        String password = passwordInput.getText();
        String name = nameInput.getText();
        String surname = surnameInput.getText();
        String address = addressInput.getText();
        String phoneNumber = phoneNumberInput.getText();

        try
        {
            Authentication.register(email, password, name, surname, address, phoneNumber);

            try
            {
                Navigation.navigateToScene("views/client/homeMenu.fxml");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (IllegalArgumentException | SQLException e)
        {
            e.printStackTrace();
        }
    }

}
