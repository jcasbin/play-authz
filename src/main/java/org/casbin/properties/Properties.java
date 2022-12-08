package org.casbin.properties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

abstract class Properties {
    protected final Config config = ConfigFactory.load();

    protected final String root;
    public Properties(String root) {
        this.root = root;
    }

    private boolean hasKey(String path) {
        return config.hasPath(root+"."+path);
    }

    protected Object getValue(String path, Object defaultValue) {
        return hasKey(path) ? config.getString(root+"."+path) : defaultValue;
    }
}
