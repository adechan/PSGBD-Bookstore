package frontend.controllers.bookstore;

import frontend.controllers.Session;
import frontend.controllers.bookstore.database.BookstoreDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Bookstore
{
    private static Bookstore singleton;

    private ObservableList<Book> books;

    private ObservableList<Category> categories;

    private ObservableList<Publisher> publishers;

    private ObservableList<Author> authors;

    public BookstoreDatabase getDatabase()
    {
        return database;
    }

    private BookstoreDatabase database;

    public Bookstore getInstance() throws SQLException, ClassNotFoundException
    {
        if (singleton == null)
            singleton = new Bookstore();

        return singleton;
    }

    public Bookstore() throws SQLException, ClassNotFoundException
    {
        this.database = new BookstoreDatabase();
    }

    public void clear()
    {
        if (this.books != null)
            this.books.clear();

        if (this.publishers != null)
            this.publishers.clear();

        if (this.authors != null)
            this.authors.clear();
    }

    public void addBook(String name, String description, float price, int stock, Author author, Category category, Publisher publisher)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;

        try
        {
            this.database.addBook(name, description, price, stock, author, category, publisher);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addAuthor(String fullName)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;
    }

    public void addCategory(String name)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;

        try
        {
            this.database.addCategory(name);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addPublisher(String name)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;
    }

    public void deleteBook(Book book)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;

        try
        {
            this.database.deleteBook(book);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteAuthor(Author author)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;

        try
        {
            this.database.deleteAuthor(author);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteCategory(Category category)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;

        try
        {
            this.database.deleteCategory(category);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deletePublisher(Publisher publisher)
    {
        if (!Session.getInstance().getAccount().isAdmin())
            return;

        try
        {
            this.database.deletePublisher(publisher);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void buyBook(Book book)
    {
        try
        {
            this.database.buyBook(book);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<Author> getAuthors()
    {
        if (this.authors == null || this.authors.isEmpty())
        {
            try
            {
                this.authors = this.database.getAuthors();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                this.authors = FXCollections.observableArrayList();
            }
        }

        return this.authors;
    }

    public ObservableList<Book> getBooks()
    {
        if (this.isEmpty())
        {
            try
            {
                this.books = this.database.getAllBooks();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                this.books = FXCollections.observableArrayList();
            }
        }

        return this.books;
    }

    public boolean isEmpty()
    {
        return this.books == null || this.books.isEmpty();
    }

    public ObservableList<Category> getCategories()
    {
        if (this.categories == null)
            this.categories = this.database.getAllCategories();

        return this.categories;
    }

    public ObservableList<Book> getRecommendedBooks()
    {
        if (this.books == null || this.books.isEmpty())
        {
            try
            {
                this.books = this.database.getRecommendedBooks();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                this.books = FXCollections.observableArrayList();
            }
        }

        return this.books;
    }

    public ObservableList<Book> getBooksByName(String name)
    {
        if (this.books == null || this.books.isEmpty())
        {
            try
            {
                this.books = this.database.getBooksByName(name);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                this.books = FXCollections.observableArrayList();
            }
        }

        return this.books;
    }

    public ObservableList<Book> getSimilarBooks(Book book)
    {
        if (this.books == null || this.books.isEmpty())
        {
            try
            {
                this.books = this.database.getSimilarBooks(book);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                this.books = FXCollections.observableArrayList();
            }
        }

        return this.books;
    }

    public ObservableList<Publisher> getPublishers()
    {
        if (this.publishers == null || this.publishers.isEmpty())
        {
            try
            {
                this.publishers = this.database.getPublishers();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                this.publishers = FXCollections.observableArrayList();
            }
        }

        return this.publishers;
    }

    public void restrictInPriceRange(float highestPrice, boolean ascending)
    {
        try
        {
            this.books = this.database.getBooksInPriceRange(highestPrice, ascending);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            this.books = FXCollections.observableArrayList();
        }
    }



    public void searchByCategory(Category category, boolean ascending)
    {
        try
        {
            this.books = this.database.getBooksSortedByCategory(category, ascending);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            this.books = FXCollections.observableArrayList();
        }
    }

    public void sortByRating(Category category, boolean ascending)
    {
        try
        {
            this.books = this.database.getBooksSortedByRating(category, ascending);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            this.books = FXCollections.observableArrayList();
        }
    }

    public ObservableList<Book> getLatestBooks()
    {
        try
        {
            this.books = this.database.getLatestBooks();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            this.books.clear();
        }

        return this.books;
    }
}
