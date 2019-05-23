package frontend.controllers.client;

import frontend.controllers.Authentication;
import frontend.controllers.Navigation;
import frontend.controllers.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class HomeClientController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    void initialize()
    {
        this.searchField.setDisable(true);
        Session.getInstance().setBooksController(null);
    }

    @FXML
    void handleBookSearchClick(MouseEvent event)
    {
        BooksClientController bookController = Session.getInstance().getBooksController();
        if (bookController == null)
            return;

        String name = this.searchField.getText();

        if (name != null)
            bookController.searchBookByName(name);
    }

    @FXML
    void handleBooksButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/books.fxml");

            this.searchField.setDisable(false);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAuthorsButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            System.out.println("Authors button clicked");
            Navigation.navigateToScene(mainPane, "views/client/authors.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleBestSellersButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/bestSellers.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void handleCategoriesButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/categories.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void handleLatestBooksButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/latestBooks.fxml");

            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLatestPurchasesButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/latestPurchases.fxml");
            Session.getInstance().setBooksController(null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void handleMyAccountButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/myAccount.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handlePublishingsButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/publishings.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRecommendationsButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/recommendations.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void handlePurchaseHistoryButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            Navigation.navigateToScene(mainPane, "views/client/purchaseHistory.fxml");

            Session.getInstance().setBooksController(null);
            this.searchField.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutButtonClick(MouseEvent event)
    {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        try
        {
            // Navigate to login page, but if this throws an exception,
            // then the account will be empty while being stuck on the logout page.
            // So we clear the account afterwards
            Navigation.navigateToScene("views/login.fxml");
            Authentication.logout();
        }
        catch (IllegalArgumentException | SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
