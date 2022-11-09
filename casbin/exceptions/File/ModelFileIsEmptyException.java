package casbin.exceptions.File;

public class ModelFileIsEmptyException extends Exception {
    public ModelFileIsEmptyException() {
        super("Model file is empty");
    }
}
