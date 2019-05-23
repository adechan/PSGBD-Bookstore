package frontend.controllers;

import frontend.controllers.accounts.Account;
import frontend.controllers.bookstore.Bookstore;
import frontend.controllers.client.BooksClientController;
import frontend.controllers.client.HomeClientController;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Session {

    private Stage stage;
    private Scene scene;
    private Account account;

    private Bookstore bookstore;

    public BooksClientController getBooksController()
    {
        return booksController;
    }

    public void setBooksController(BooksClientController booksController)
    {
        this.booksController = booksController;
    }

    private BooksClientController booksController;

    private static Session singleton = null;

    private Session()
    {
        this.stage = null;
        this.scene = null;
        this.account = null;

        try
        {
            this.bookstore = new Bookstore();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static Session getInstance()
    {
        if (Session.singleton == null)
            Session.singleton = new Session();

        return Session.singleton;
    }

    public Bookstore getBookstore()
    {
        return this.bookstore;
    }

    Stage getStage()
    {
        return this.stage;
    }

    public Scene getScene()
    {
        return this.scene;
    }

    public Account getAccount() { return this.account; }

    // Must be called as soon as possible (in start preferably)
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    void setCurrentScene(Scene scene)
    {
        this.scene = scene;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }


}
