package frontend.controllers.client;

import frontend.controllers.Session;
import frontend.controllers.bookstore.Author;
import frontend.controllers.bookstore.Bookstore;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;
import java.util.concurrent.Callable;

public class PagedTable<T>
{
	private TableView<T> table;

	private Pagination pagination;

	public static final int ITEMS_PER_PAGE = 10;

	private boolean loaded = false;

	private ObservableList<T> data;

	public PagedTable(TableView<T> table, Pagination pagination)
	{
		this.table = table;
		this.pagination = pagination;
	}

	private Node createPage(int index)
	{
		if (loaded)
		{
			int authorsStartIndex = index * ITEMS_PER_PAGE;
			int authorsEndIndex = Math.min(authorsStartIndex + ITEMS_PER_PAGE, this.data.size());

			this.table.setItems(FXCollections.observableArrayList(this.data.subList(authorsStartIndex, authorsEndIndex)));
		}

		return new AnchorPane(this.table);
	}

	public void load(Callable<ObservableList<T>> getData)
	{
		this.data = null;
		this.loaded = false;
		this.pagination.setPageCount(1);
		this.table.getItems().clear();

		new Thread(() -> {
			try
			{
				this.data = getData.call();
				this.loaded = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.data = FXCollections.observableArrayList();
			}

			Platform.runLater(() -> this.pagination.setPageCount(this.data.size() / ITEMS_PER_PAGE + 1));

		}).start();
	}

	public void initialize(Callable<ObservableList<T>> getData)
	{
		this.pagination.setPageFactory(this::createPage);
		this.load(getData);
	}
}
