package org.casbin.properties;

public class CasbinExceptionProperties extends Properties{
    private boolean removePolicyFailed = (boolean) getValue("removePolicyFailed", false);

    public CasbinExceptionProperties() {
        super("casbin");
    }

    public boolean isRemovePolicyFailed() {
        return removePolicyFailed;
    }

    public void setRemovePolicyFailed(boolean removePolicyFailed) {
        this.removePolicyFailed = removePolicyFailed;
    }
}
