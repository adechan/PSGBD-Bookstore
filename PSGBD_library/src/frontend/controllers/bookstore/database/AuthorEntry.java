package frontend.controllers.bookstore.database;

import frontend.controllers.bookstore.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorEntry extends DatabaseEntry
{
	// get author sql query
	private static String queryString = "{? = call get_book_by_id(%d)}";

	@Override
	String getQueryString()
	{
		return String.format(AuthorEntry.queryString, this.id);
	}

	AuthorEntry(int id)
	{
		super(id);
	}

	@Override
	Author parseResultSet(ResultSet results) throws SQLException
	{
		return AuthorEntry.parseResults(results);
	}

	static Author parseResults(ResultSet results) throws SQLException
	{
		int id = results.getInt(1);
		String firstName = results.getString(3);
		String lastName = results.getString(2);

		return new Author(id, firstName, lastName);
	}

	@Override
	public Author loadFromDb()
	{
		return (Author)super.loadFromDb();
	}
}
