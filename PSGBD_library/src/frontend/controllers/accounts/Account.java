package frontend.controllers.accounts;

import frontend.controllers.bookstore.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Account
{
    public int getId()
    {
        return id;
    }

    private int id;
    private String firstName;
    private String lastName;
    private String address;

    private String email = "";

    private int phoneNumber;

    private ObservableList<Book> purchasedBooks;

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public int getPhoneNumber()
    {
        return phoneNumber;
    }

    public Account(int id, String firstName, String lastName, String address, String email, int phoneNumber)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchasedBooks = FXCollections.observableArrayList();
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setPurchaseHistory(ObservableList<Book> books)
    {
        this.purchasedBooks = books;
    }

    public void addPurchasedBook(Book book)
    {
        this.purchasedBooks.add(book);
    }

    public ObservableList<Book> getPurchaseHistory()
    {
        return this.purchasedBooks;
    }

    public boolean isAdmin()
    {
        return false;
    }
}
