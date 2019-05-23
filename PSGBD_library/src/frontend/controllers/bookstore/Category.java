package frontend.controllers.bookstore;

public class Category
{
	public int getId()
	{
		return id;
	}

	private int id;
	private String name;
	private String description;

	public Category(int id, String name)
	{
		this.id = id;
		this.name = name;
		this.description = null;
	}

	public Category(String name)
	{
		this.id = -1;
		this.name = name;
		this.description = null;
	}

	public String getName()
	{
		return this.name;
	}

	@Override
	public String toString()
	{
		return this.getName();
	}
}
