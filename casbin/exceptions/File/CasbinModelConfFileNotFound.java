package casbin.exceptions.File;

public class CasbinModelConfFileNotFound extends Exception{
    public CasbinModelConfFileNotFound() {
        super("Casbin Model configuration file not found. {HINT: Either provide the model path in application.conf or create the file as /conf/casbin/model.conf}");
    }
}
