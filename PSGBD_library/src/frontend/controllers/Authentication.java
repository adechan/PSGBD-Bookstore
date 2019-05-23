package frontend.controllers;

import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.database.BookstoreDatabase;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class Authentication
{
    public enum AccountType
    {
        User, Admin
    }

    private static boolean isValidEmail(String email)
    {
        Pattern emailPattern = Pattern.compile(".+@.+\\..+");
        return  emailPattern.matcher(email).matches();
    }

    private static boolean isValidPassword(String password)
    {
        return password.length() > 5;
    }

    private static void validateEmailPassword(String email, String password) throws IllegalArgumentException
    {
        if (!Authentication.isValidEmail(email))
            throw new IllegalArgumentException("Invalid email address");

        if (!Authentication.isValidPassword(password))
            throw new IllegalArgumentException("Password length must be more than 5 characters long");
    }

    private static void validateNameAddressPhoneNumber(
            String name, String lastName,
            String address, String phoneNumber
    ) throws IllegalArgumentException
    {
        if (name.length() < 2)
            throw new IllegalArgumentException("Invalid name");

        if (address.length() < 2)
            throw new IllegalArgumentException("Invalid address");

        if (phoneNumber.length() < 4)
            throw new IllegalArgumentException("Invalid phone number");
    }

    static Account login(String email, String password, String accountType)
            throws IllegalArgumentException, SQLException
    {
        Session session = Session.getInstance();
	    BookstoreDatabase database = session.getBookstore().getDatabase();

        System.out.println("Email: " + email);
        Authentication.validateEmailPassword(email, password);

	    Account account = database.login(email, password, accountType);

	    if (account == null)
	        throw new IllegalArgumentException("Invalid email or password");

	    System.out.println("Logged into " + accountType + " account");
        session.setAccount(account);
        return account;
    }

    static Account register(
            String email, String password,
            String name, String surname,
            String address, String phoneNumber
    ) throws IllegalArgumentException, SQLException
    {
	    BookstoreDatabase database = Session.getInstance().getBookstore().getDatabase();

        System.out.println("Authentication.register");
        System.out.println("Email: " + email);
        Authentication.validateEmailPassword(email, password);
        Authentication.validateNameAddressPhoneNumber(name, surname, address, phoneNumber);

        // Create account here
	    database.register(email, password, name, surname, address, Integer.parseInt(phoneNumber));

        // if database_stuff() was successful then login
        return Authentication.login(email, password, "Client");
    }

    public static void logout() throws IllegalArgumentException, SQLException, ClassNotFoundException
    {
        System.out.println("Authentication.logout");
        System.out.println("Email: " + Session.getInstance().getAccount().getEmail());
        Session session = Session.getInstance();
        if (session.getAccount() == null)
            throw new IllegalArgumentException("You must be logged in to logout");

        session.setAccount(null);
    }
}
