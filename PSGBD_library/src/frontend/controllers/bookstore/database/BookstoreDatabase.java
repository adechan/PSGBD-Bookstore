package frontend.controllers.bookstore.database;

import frontend.controllers.Session;
import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Author;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Category;
import frontend.controllers.bookstore.Publisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.jdbc.OracleTypes;

import java.sql.*;

public class BookstoreDatabase
{
	private final Connection connection;

	Connection getConnection()
	{
		return connection;
	}

	public BookstoreDatabase() throws SQLException, ClassNotFoundException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","STUDENT","STUDENT");
	}

	private Query executeFunction(String query) throws SQLException
	{
		CallableStatement statement = connection.prepareCall(query);
		statement.registerOutParameter(1, OracleTypes.CURSOR);
		statement.executeQuery();

		return new Query(statement, (ResultSet)statement.getObject(1));
	}

	private Object executeFunction(String query, int type) throws SQLException
	{
		CallableStatement statement = connection.prepareCall(query);
		statement.registerOutParameter(1, type);
		statement.executeQuery();

		Object result = statement.getObject(1);
		statement.close();
		return result;
	}

	private void executeProcedure(String query) throws SQLException
	{
		CallableStatement statement = connection.prepareCall(query);
		statement.executeQuery();
		statement.close();
	}

	public void addBook(String name, String description, float price, int stock, Author author, Category category, Publisher publisher)
			throws SQLException
	{
		this.executeProcedure(String.format(
				"{call A_ADD_BOOK('%s', '%s', %f, %d, %d, %d, %d, %d)}",
				name, description, price, stock,
				author.getId(), category.getId(), publisher.getId(),
				Session.getInstance().getAccount().getId()
		));
	}

	public void addCategory(String name)
			throws SQLException
	{
		this.executeProcedure(String.format(
			"{call A_ADD_CATEGORY('%s', %d)}",
			name, Session.getInstance().getAccount().getId()
		));
	}

	public Account getUserByEmail(String email, String accountType) throws SQLException
	{
		String fn = "GET_USER_BY_EMAIL";
		if (accountType.equals("Administrator"))
			fn = "GET_ADMIN_BY_EMAIL";

		Query query = this.executeFunction(String.format("{? = call %s('%s')}", fn, email));

		query.next();
		Account account = AccountEntry.parseResults(query.getResults(), accountType);
		query.close();

		return account;
	}

	public Account login(String email, String password, String accountType) throws SQLException
	{
		String loginFn = "LOGHEAZA_USER";
		if (accountType.equals("Administrator"))
			loginFn = "LOGHEAZA_ADMIN";

		String result = (String)this.executeFunction(
			String.format(
				"{? = call %s('%s', '%s')}",
				loginFn, email, password
			), OracleTypes.VARCHAR
		);

		if (!result.equals("Succes"))
			return null;

		Account account = getUserByEmail(email, accountType);

		ObservableList<Book> books = this.getPurchaseHistory(account);

		System.out.println("Purchase history size = " + books.size());
		account.setPurchaseHistory(books);

		// General XML of user history
		if (!account.isAdmin())
			this.executeProcedure(String.format("{call GENERATE_XML('%s')}", email));
		return account;
	}

	public void buyBook(Book book) throws SQLException
	{
		Account account = Session.getInstance().getAccount();

		System.out.println("Account id = " + account.getId());
		System.out.println("Book id = " + book.getId());
		this.executeProcedure(String.format("{call BUY_BOOK(%d, %d)}", account.getId(), book.getId()));

		account.addPurchasedBook(book);
		System.out.println("Successfully bought " + book + ", " + account.getPurchaseHistory().size());
	}

	public ObservableList<Book> getPurchaseHistory(Account user) throws SQLException
	{
		if (user.isAdmin())
			return FXCollections.observableArrayList();

		Query query = this.executeFunction(String.format("{? = call GET_BOOKS_FROM_USER('%s')}", user.getEmail()));

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	public void register(String email, String password,
			String firstName, String lastName, String address, int phoneNumber
	) throws SQLException
	{
		this.executeProcedure(String.format("{call ADAUGA_USER('%s', '%s', '%s', '%s', '%s', %d)}",
			lastName, firstName, password, address, email, phoneNumber
		));
	}

	public ObservableList<Author> getAuthors() throws SQLException
	{
		Query query = this.executeFunction("{? = call GET_ALL_AUTHORS}");

		ObservableList<Author> authors = FXCollections.observableArrayList();

		while (query.next())
			authors.add(AuthorEntry.parseResults(query.getResults()));

		query.close();
		return authors;

	}

	public ObservableList<Book> getSimilarBooks(Book book) throws SQLException
	{
		Query query = this.executeFunction(String.format(
			"{? = call recommend_by_others_pref('%s', %d)}",
			Session.getInstance().getAccount().getEmail(),
			book.getId()
		));

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	public ObservableList<Book> getRecommendedBooks() throws SQLException
	{
		Query query = this.executeFunction(String.format(
			"{? = call RECOMMEND_BY_USER_PREF('%s')}",
			Session.getInstance().getAccount().getEmail()
		));

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();

		System.out.println("num books = " + books.size());
		return books;
	}

	public ObservableList<Book> getBooksInPriceRange(float highestPrice, boolean ascending) throws SQLException
	{
		String sortType = "ASC";
		if (!ascending)
			sortType = "DESC";

		String queryString = String.format("{? = call GET_BOOKS_PRICE_RANGE(0, %d, '%s')}", (int)highestPrice, sortType);
		Query query = this.executeFunction(queryString);

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	public ObservableList<Book> getAllBooks() throws SQLException
	{
		String queryString = "{? = call join_tables}";
		Query query = this.executeFunction(queryString);

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	//GET_BOOKS_BY_NAME

	public ObservableList<Book> getBooksByName(String name) throws SQLException
	{
		String queryString = String.format("{? = call GET_BOOKS_BY_NAME('%s')}", name);
		Query query = this.executeFunction(queryString);

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	public ObservableList<Category> getAllCategories()
	{
		AllCategoriesEntry all = new AllCategoriesEntry();
		return all.loadFromDb();
	}

	public void deletePublisher(Publisher publisher) throws SQLException
	{
		this.executeProcedure(String.format("{call A_DELETE_TABLE_ROW('publicatii', %d)}", publisher.getId()));
	}

	public void deleteBook(Book book) throws SQLException
	{
		this.executeProcedure(String.format("{call A_DELETE_TABLE_ROW('carti', %d)}", book.getId()));
	}

	public void deleteAuthor(Author author) throws SQLException
	{
		this.executeProcedure(String.format("{call A_DELETE_TABLE_ROW('autori', %d)}", author.getId()));
	}

	public void deleteCategory(Category category) throws SQLException
	{
		this.executeProcedure(String.format("{call A_DELETE_TABLE_ROW('categorii', %d)}", category.getId()));
	}

	public ObservableList<Publisher> getPublishers() throws SQLException
	{
		String queryString = "{? = call GET_ALL_PUBLICATIONS}";
		Query query = this.executeFunction(queryString);

		ObservableList<Publisher> publishers = FXCollections.observableArrayList();

		while (query.next())
			publishers.add(PublisherEntry.parseResults(query.getResults()));

		query.close();
		return publishers;
	}

	public ObservableList<Book> getBooksSortedByCategory(Category category, boolean ascending) throws SQLException
	{
		String sortType = "ASC";
		if (!ascending)
			sortType = "DESC";

		String queryString = String.format("{? = call SORT_BOOKS_SECOND('%s', '%s')}", category.getName(), sortType);
		Query query = this.executeFunction(queryString);

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	public ObservableList<Book> getBooksSortedByRating(Category category, boolean ascending) throws SQLException
	{
		String sortType = "ASC";
		if (!ascending)
			sortType = "DESC";

		String categoryType = "NULL";
		if (category != null)
			categoryType = "'" + category.getName() + "'";

		String queryString = String.format("{? = call SORT_BOOKS_SECOND(%s, '%s')}", categoryType, sortType);
		Query query = this.executeFunction(queryString);

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}

	public ObservableList<Book> getLatestBooks() throws SQLException
	{
		String queryString = String.format("{? = call MOST_RECENT_10_BOOKS}");
		Query query = this.executeFunction(queryString);

		ObservableList<Book> books = FXCollections.observableArrayList();

		while (query.next())
			books.add(FullBookEntry.parseResults(query.getResults()));

		query.close();
		return books;
	}
}
