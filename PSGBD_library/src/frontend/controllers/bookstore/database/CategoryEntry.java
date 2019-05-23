package frontend.controllers.bookstore.database;

import frontend.controllers.bookstore.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryEntry extends DatabaseEntry
{
	private static String queryString = "{? = call GET_ALL_CATEGORIES}";

	@Override
	String getQueryString()
	{
		return String.format(CategoryEntry.queryString, id);
	}

	public CategoryEntry(int id)
	{
		super(id);
	}

	@Override
	public Category loadFromDb()
	{
		return (Category)super.loadFromDb();
	}

	@Override
	Category parseResultSet(ResultSet results) throws SQLException
	{
		return CategoryEntry.parseResults(results);
	}

	static Category parseResults(ResultSet results) throws SQLException
	{
		int id = results.getInt(1);
		String name = results.getString(2);
		return new Category(id, name);
	}
}
