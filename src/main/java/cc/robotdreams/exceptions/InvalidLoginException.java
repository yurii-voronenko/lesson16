package cc.robotdreams.exceptions;

public class InvalidLoginException extends RuntimeException
{
    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException() {
    }
}
