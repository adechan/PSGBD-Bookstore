package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Bookstore;
import frontend.controllers.bookstore.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CategoriesClientController
{
	@FXML
	private TableView<Category> categoriesTable;

	@FXML
	private Pagination pagination;

	private Bookstore bookstore;

	@FXML
	private Button deleteButton;

	@FXML
	private TextField addField;

	@FXML
	private Button addButton;

	PagedTable<Category> pagedTable;

	@FXML
	public void initialize()
	{
		Account account = Session.getInstance().getAccount();

		if (account.isAdmin())
		{
			deleteButton.setVisible(true);
			addButton.setVisible(true);
			addField.setVisible(true);
		}
		else
		{
			deleteButton.setVisible(false);
			addButton.setVisible(false);
			addField.setVisible(false);
		}

		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		this.pagedTable = new PagedTable<>(categoriesTable, pagination);
		this.pagedTable.initialize(() -> this.bookstore.getCategories());
	}

	@FXML
	void handleAddButtonClick(MouseEvent event)
	{
		if (!Session.getInstance().getAccount().isAdmin())
			return;

		String name = this.addField.getText();

		if (name != null)
		{
			this.bookstore.clear();
			this.bookstore.addCategory(name);
			this.pagedTable.load(this.bookstore::getCategories);
		}
	}

	@FXML
	void handleDeleteButtonClick(MouseEvent event)
	{
		Category category = this.categoriesTable.getSelectionModel().getSelectedItem();
		if (category != null)
		{
			this.bookstore.deleteCategory(category);
			this.categoriesTable.getItems().remove(category);
		}
	}
}
