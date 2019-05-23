package frontend.controllers.bookstore.database;

import frontend.controllers.accounts.Account;
import frontend.controllers.accounts.Administrator;
import frontend.controllers.accounts.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountEntry
{
	static Account parseResults(ResultSet results, String accountType) throws SQLException
	{
		int id = results.getInt(1);
		String lastName = results.getString(2);
		String firstName = results.getString(3);

		int offset = 0;
		if (accountType.equals("Administrator"))
			offset = 1;

		String address = results.getString(4 + offset);
		String email = results.getString(5 + offset);
		int phoneNumber = results.getInt(6 + offset);

		if (accountType.equals("Administrator"))
			return new Administrator(id, firstName, lastName, address, email, phoneNumber);

		return new Client(id, firstName, lastName, address, email, phoneNumber);
	}
}
