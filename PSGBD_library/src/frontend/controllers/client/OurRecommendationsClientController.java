package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Bookstore;
import frontend.controllers.bookstore.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

public class OurRecommendationsClientController
{
	@FXML
	private TableView<Book> recommendationsTable;

	@FXML
	private Pagination pagination;

	private Bookstore bookstore;

	@FXML
	public void initialize()
	{
		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		PagedTable<Book> pagedTable = new PagedTable<>(recommendationsTable, pagination);
		pagedTable.initialize(() -> this.bookstore.getRecommendedBooks());
	}

}
