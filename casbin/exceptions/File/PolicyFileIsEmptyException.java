package casbin.exceptions.File;

public class PolicyFileIsEmptyException extends Exception{

    public PolicyFileIsEmptyException() {
        super("Policy file is empty");
    }
}
