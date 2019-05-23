package frontend.controllers.bookstore.database;

import frontend.controllers.bookstore.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherEntry extends DatabaseEntry
{
	private static String queryString = "{? = call get_book_by_id(%d)}";

	@Override
	String getQueryString()
	{
		return String.format(PublisherEntry.queryString, this.id);
	}

	public PublisherEntry(int id)
	{
		super(id);
	}

	@Override
	Publisher parseResultSet(ResultSet results) throws SQLException
	{
		return PublisherEntry.parseResults(results);
	}

	static Publisher parseResults(ResultSet results) throws SQLException
	{
		int id = results.getInt(1);
		String name = results.getString(2);
		return new Publisher(id, name);
	}

	@Override
	public Publisher loadFromDb()
	{
		return (Publisher)super.loadFromDb();
	}
}
