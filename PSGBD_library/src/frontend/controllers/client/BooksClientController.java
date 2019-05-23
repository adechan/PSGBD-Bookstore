package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Bookstore;
import frontend.controllers.bookstore.Category;
import frontend.controllers.bookstore.Publisher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class BooksClientController
{
	@FXML
	private TableView<Book> booksTable;

	@FXML
	private Pagination pagination;

	private PagedTable<Book> pagedTable;

	@FXML
	private ComboBox<Category> categoryCombo;

	@FXML
	private Slider priceSlider;

	@FXML
	private Button deleteButton;

	private Bookstore bookstore;

	private boolean ascendingSort = true;

	@FXML
	public void initialize()
	{
		Account account = Session.getInstance().getAccount();

		if (account.isAdmin())
			deleteButton.setVisible(true);
		else
			deleteButton.setVisible(false);

		Session.getInstance().setBooksController(this);
		this.bookstore = Session.getInstance().getBookstore();
		this.bookstore.clear();

		this.categoryCombo.setItems(this.bookstore.getCategories());
		this.pagedTable = new PagedTable<>(booksTable, pagination);
		this.pagedTable.initialize(() -> this.bookstore.getBooks());
	}

	private void resetTable()
	{
		pagination.setPageCount(1);
		this.booksTable.getItems().clear();
		this.ascendingSort = !this.ascendingSort;
	}

	void searchBookByName(String name)
	{
		System.out.println("name = " + name);
		this.resetTable();
		this.bookstore.clear();
		this.pagedTable.load(() -> this.bookstore.getBooksByName(name));
	}

	@FXML
	void handleDeleteButtonClick(MouseEvent event)
	{
		if (!Session.getInstance().getAccount().isAdmin())
			return;

		Book book = this.booksTable.getSelectionModel().getSelectedItem();
		if (book != null)
		{
			this.bookstore.deleteBook(book);
			this.booksTable.getItems().remove(book);
		}
	}

	@FXML
	void handleSortByPriceRelease(MouseEvent event)
	{
		this.resetTable();
		float sliderValue = (float)priceSlider.getValue();

		this.pagedTable.load(() -> {
			this.bookstore.restrictInPriceRange(sliderValue, this.ascendingSort);
			return this.bookstore.getBooks();
		});
	}

	@FXML
	void handleAddtoCartButtonClick(MouseEvent event)
	{
		Book selectedBook = this.booksTable.getSelectionModel().getSelectedItem();
		this.bookstore.buyBook(selectedBook);
	}

	@FXML
	void handleSortByRatingButtonClick(MouseEvent event)
	{
		this.resetTable();

		Category category = categoryCombo.getValue();
		System.out.println("category value = " + category);

		this.pagedTable.load(() -> {
			this.bookstore.sortByRating(category, this.ascendingSort);
			return this.bookstore.getBooks();
		});
	}
}
