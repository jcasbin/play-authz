package org.casbin.properties;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.inject.Singleton;

@Singleton
public class JDBCConfigurationProperties extends Properties{

    private String username = (String) getValue("username", "");

    private String url = (String) getValue("url", "");

    private String password = (String) getValue("password", "");

    private String driver = (String) getValue("driver", "");

    public JDBCConfigurationProperties() {
        super("db.default");
    }

    public Config getConfig() {
        return config;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
         this.url = url;
    }

}
