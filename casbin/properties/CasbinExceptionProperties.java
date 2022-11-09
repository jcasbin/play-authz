package casbin.properties;

public class CasbinExceptionProperties extends Properties{
    private boolean removePolicyFailed = (boolean) getValue("removePolicyFailed", false);

    public boolean isRemovePolicyFailed() {
        return removePolicyFailed;
    }

    public void setRemovePolicyFailed(boolean removePolicyFailed) {
        this.removePolicyFailed = removePolicyFailed;
    }
}
