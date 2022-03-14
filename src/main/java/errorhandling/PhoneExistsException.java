package errorhandling;

public class PhoneExistsException extends Exception
{
    public PhoneExistsException(String message)
    {
        super(message);
    }
}
