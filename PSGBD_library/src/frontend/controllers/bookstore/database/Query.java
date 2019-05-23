package frontend.controllers.bookstore.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query
{
	Statement statement;
	ResultSet results;

	public Query(Statement statement, ResultSet results)
	{
		this.statement = statement;
		this.results = results;
	}

	public ResultSet getResults()
	{
		return this.results;
	}

	public boolean next() throws SQLException
	{
		return this.results.next();
	}

	public void close() throws SQLException
	{
		this.statement.close();
		this.results.close();
	}
}
