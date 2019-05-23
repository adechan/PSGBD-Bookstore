package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Bookstore;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

public class LatestBooksClientController {

	private Bookstore bookstore;
	@FXML
	private TableView<Book> latestTable;

	@FXML
	private Pagination pagination;

	@FXML
	public void initialize()
	{
		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		PagedTable<Book> pagedTable = new PagedTable<>(latestTable, pagination);
		pagedTable.initialize(() -> this.bookstore.getLatestBooks());
	}
}
