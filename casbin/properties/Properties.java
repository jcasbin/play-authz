package casbin.properties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

abstract class Properties {
    private final String root = "casbin";
    protected final Config config = ConfigFactory.load();


    private boolean hasKey(String attribute) {
        return config.hasPath(root + "." + attribute);
    }

    protected Object getValue(String attribute, Object defaultValue){
        return hasKey(attribute) ? config.getString(attribute) : defaultValue;
    }
}
