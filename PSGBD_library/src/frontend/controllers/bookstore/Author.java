package frontend.controllers.bookstore;

public class Author
{
	public int getId()
	{
		return id;
	}

	private int id;
	private String firstName;
	private String lastName;

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public Author(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Author(int id, String firstName, String lastName)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Author(String fullName)
	{
		String[] tokens = fullName.split(" ");
		this.firstName = tokens[0];
		this.lastName = tokens[1];
	}

	@Override
	public String toString()
	{
		return this.firstName + " " + this.lastName;
	}
}
