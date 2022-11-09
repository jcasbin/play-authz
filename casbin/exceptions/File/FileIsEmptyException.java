package casbin.exceptions.File;

public class FileIsEmptyException extends Exception{
    public FileIsEmptyException() {
        super("File is empty");
    }
}
