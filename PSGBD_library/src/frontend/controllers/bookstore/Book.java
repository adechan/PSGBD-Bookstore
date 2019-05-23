package frontend.controllers.bookstore;

public class Book
{
    private int id = -1;
    private String name;
    private Author author;
    private String description;

    public Category getCategory()
    {
        return category;
    }

    private Category category;

    private float price;
    private int stock;
    private float rating;

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public float getRating() {
        return rating;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor()
    {
        return author;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Book(
        String name, String description, float rating, int stock, float price,
        Author author, Category category
    )
    {
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString()
    {
        return this.name + " by " + this.getAuthor();
    }
}
