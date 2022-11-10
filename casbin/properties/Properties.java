package casbin.properties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

abstract class Properties {
    protected final Config config = ConfigFactory.load();


    private boolean hasKey(String path) {
        return config.hasPath(path);
    }

    protected Object getValue(String path, Object defaultValue) {
        return hasKey(path) ? config.getString(path) : defaultValue;
    }
}
