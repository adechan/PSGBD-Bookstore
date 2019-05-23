package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MyAccountClientController
{
	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField addressField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField phoneNumberField;

	@FXML
	public void initialize()
	{
		Account account = Session.getInstance().getAccount();

		firstNameField.setText(account.getFirstName());
		lastNameField.setText(account.getLastName());
		addressField.setText(account.getAddress());
		emailField.setText(account.getEmail());
		phoneNumberField.setText(Integer.toString(account.getPhoneNumber()));
	}
}
