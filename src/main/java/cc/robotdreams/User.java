package cc.robotdreams;

import cc.robotdreams.exceptions.InvalidLoginException;
import cc.robotdreams.exceptions.InvalidPasswordException;
import cc.robotdreams.exceptions.InvalidUsernameException;

public class User
{
    static public void checkUsername(String username)
            throws InvalidUsernameException
    {
        if (username == null || username.isEmpty())
            throw new InvalidUsernameException("Username is empty");
    }

    static public void checkPassword(String password)
            throws InvalidPasswordException
    {
        if (password == null || password.isEmpty())
            throw new InvalidPasswordException("Password is empty");

        if (password.length() < 5 || password.length() > 10)
            throw new InvalidPasswordException("Password length must be from 5 to 10 characters");
    }


    final public String username;
    final private String password;

    public User(String username, String password)
            throws InvalidUsernameException, InvalidPasswordException
    {
        checkUsername(username);
        checkPassword(password);

        this.username = username;
        this.password = password;
    }

    public boolean login(String password) {
        if (this.password.equals(password))
            return true;
        else
            throw new InvalidLoginException();
    }
}
