package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Bookstore;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import java.awt.event.MouseEvent;

public class PurchaseHistoryClientController
{
	@FXML
	private TableView<Book> table;

	@FXML
	private Pagination pagination;

	@FXML
	private TableView<Book> recommendedBooksTable;

	@FXML
	private Pagination recommendedPagination;

	private Bookstore bookstore;

	private PagedTable<Book> recommendedPagedTable;

	@FXML
	public void initialize()
	{
		Account account = Session.getInstance().getAccount();
		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		PagedTable<Book> pagedTable = new PagedTable<>(table, pagination);

		System.out.println("Num purchased books = " + account.getPurchaseHistory().size());
		pagedTable.initialize(account::getPurchaseHistory);

		this.recommendedPagedTable = new PagedTable<>(recommendedBooksTable, recommendedPagination);
		this.recommendedPagedTable.initialize(FXCollections::observableArrayList);
	}

	public void handleSimilarBooksClick(javafx.scene.input.MouseEvent mouseEvent)
	{
		this.recommendedBooksTable.getItems().clear();
		Book selectedBook = this.table.getSelectionModel().getSelectedItem();
		if (selectedBook != null)
			this.recommendedPagedTable.load(() -> this.bookstore.getSimilarBooks(selectedBook));
	}
}
