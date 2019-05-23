package frontend.controllers.accounts;

public class Administrator extends Account {

    public Administrator(int id, String firstName, String lastName, String address, String email, int phoneNumber)
    {
        super(id, firstName, lastName, address, email, phoneNumber);
    }

    @Override
    public boolean isAdmin()
    {
        return true;
    }
}
