package casbin.properties;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.inject.Singleton;

@Singleton
public class JDBCConfigurationProperties extends Properties{

    private final Config config = ConfigFactory.load();

    private String username = (String) getValue("db.default.username", "");

    private String password = (String) getValue("db.default.password", "");

    private String driver = (String) getValue("db.default.driver", "");

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
