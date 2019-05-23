package frontend.controllers.bookstore.database;

import frontend.controllers.Session;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseEntry
{
	public int getId()
	{
		return id;
	}

	protected int id;

	abstract String getQueryString();

	DatabaseEntry(int id)
	{
		this.id = id;
	}

	abstract Object parseResultSet(ResultSet results) throws SQLException;

	public Object loadFromDb()
	{
		BookstoreDatabase database = Session.getInstance().getBookstore().getDatabase();
		try
		{
			CallableStatement statement = database.getConnection().prepareCall(this.getQueryString());
			statement.registerOutParameter(1, OracleTypes.CURSOR);
			statement.executeQuery();

			ResultSet results = (ResultSet)statement.getObject(1);
			results.next();

			Object result = this.parseResultSet(results);

			results.close();
			statement.close();
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
