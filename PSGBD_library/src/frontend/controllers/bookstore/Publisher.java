package frontend.controllers.bookstore;

public class Publisher
{
	private final String name;

	public int getId()
	{
		return id;
	}

	private final int id;

	public Publisher(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public String getName() { return this.name; }

	@Override
	public String toString() { return this.name; }
}
