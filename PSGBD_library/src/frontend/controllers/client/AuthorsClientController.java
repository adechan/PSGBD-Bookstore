package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Author;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Bookstore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class AuthorsClientController
{
	@FXML
	private TableView<Author> authorsTable;

	@FXML
	private Pagination pagination;

	@FXML
	private Button deleteButton;

	private Bookstore bookstore;

	@FXML
	public void initialize()
	{
		Account account = Session.getInstance().getAccount();

		if (account.isAdmin())
			deleteButton.setVisible(true);
		else
			deleteButton.setVisible(false);

		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		PagedTable<Author> pagedTable = new PagedTable<>(authorsTable, pagination);
		pagedTable.initialize(() -> this.bookstore.getAuthors());
	}

	@FXML
	void handleDeleteButtonClick(MouseEvent event)
	{
		if (!Session.getInstance().getAccount().isAdmin())
			return;

		Author author = this.authorsTable.getSelectionModel().getSelectedItem();
		if (author != null)
		{
			this.bookstore.deleteAuthor(author);
			this.authorsTable.getItems().remove(author);
		}

	}
}
