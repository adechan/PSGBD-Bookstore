package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Bookstore;
import frontend.controllers.bookstore.Publisher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class PublishingsClientController
{
	@FXML
	private TableView<Publisher> publishersTable;

	@FXML
	private Pagination pagination;

	private Bookstore bookstore;

	@FXML
	private Button deleteButton;

	PagedTable<Publisher> pagedTable;

	Account account;

	@FXML
	public void initialize()
	{
		this.account = Session.getInstance().getAccount();

		if (account.isAdmin())
			deleteButton.setVisible(true);
		else
			deleteButton.setVisible(false);

		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		this.pagedTable = new PagedTable<>(publishersTable, pagination);
		pagedTable.initialize(this.bookstore::getPublishers);
	}

	@FXML
	public void handleDeleteButtonClick(MouseEvent mouseEvent)
	{
		if (!this.account.isAdmin())
			return;

		Publisher publisher = this.publishersTable.getSelectionModel().getSelectedItem();
		if (publisher != null)
		{
			this.bookstore.deletePublisher(publisher);
			this.publishersTable.getItems().remove(publisher);
		}
	}
}
