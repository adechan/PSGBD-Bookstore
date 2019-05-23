package frontend.controllers.bookstore.database;

import frontend.controllers.bookstore.Author;
import frontend.controllers.bookstore.Book;
import frontend.controllers.bookstore.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FullBookEntry extends DatabaseEntry
{
    private static String queryString = "{? = call get_book_by_id(%d)}";

    @Override
    String getQueryString()
    {
        return String.format(FullBookEntry.queryString, this.id);
    }

    public FullBookEntry(int id)
    {
        super(id);
    }

    @Override
    public Book loadFromDb()
    {
        return (Book)super.loadFromDb();
    }

    @Override
    Book parseResultSet(ResultSet results) throws SQLException
    {
        return FullBookEntry.parseResults(results);
    }

    static Book parseResults(ResultSet results) throws SQLException
    {
        int id = results.getInt(1);
        String name = results.getString(2);
        String description = results.getString(3);

        float rating = results.getFloat(4);
        float price = results.getFloat(5);
        int stock = results.getInt(6);

        String authorFirstName = results.getString(7);
        String authorLastName = results.getString(8);
        Author author = new Author(authorFirstName, authorLastName);

        Category category = new Category(results.getString(9));
        Book book = new Book(name, description, rating, stock, price, author, category);
        book.setId(id);
        return book;
    }
}
