package org.casbin.exceptions.File;

public class CasbinPolicyCSVFileNotFound extends Exception{
    public CasbinPolicyCSVFileNotFound() {
        super("Casbin Policy CSV file not found. {HINT: Either provide the policy path in application.conf or create the file as /conf/casbin/policy.csv}");
    }
}
