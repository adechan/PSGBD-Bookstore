package frontend.controllers.bookstore.database;

import frontend.controllers.bookstore.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllCategoriesEntry extends DatabaseEntry
{
	private static String queryString = "{? = call GET_ALL_CATEGORIES}";

	@Override
	String getQueryString()
	{
		return String.format(AllCategoriesEntry.queryString, id);
	}

	public AllCategoriesEntry()
	{
		super(-1);
	}

	@Override
	public ObservableList<Category> loadFromDb()
	{
		return (ObservableList<Category>) super.loadFromDb();
	}

	@Override
	ObservableList<Category> parseResultSet(ResultSet results) throws SQLException
	{
		ObservableList<Category> categories = FXCollections.observableArrayList();
		do
		{
			categories.add(CategoryEntry.parseResults(results));
		} while (results.next());
		return categories;
	}
}
