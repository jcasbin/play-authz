package casbin.properties;

import casbin.exceptions.File.*;
import casbin.properties.options.CasbinDataSourceInitializationMode;
import casbin.properties.options.CasbinStoreType;
import casbin.properties.options.CasbinWatcherType;
import casbin.properties.utils.FileUtil;
import com.typesafe.config.Config;

import javax.inject.Singleton;
import java.io.FileNotFoundException;


@Singleton
public class CasbinProperties extends Properties {

    private boolean enableCasbin = (boolean) getValue("enableCasbin", true);

    private boolean useSyncedEnforcer = (boolean) getValue("useSyncedEnforcer", false);

    private String model = (String) getValue("model", "conf/casbin/model.conf");

    private String policy = (String) getValue("policy","conf/casbin/policy.csv");

    private String policyTopic = (String) getValue("policyTopic", "CASBIN_POLICY_TOPIC");


    private boolean enableWatcher = (boolean) getValue("enableWatcher", false);

    private boolean autoSave = (boolean) getValue("autoSave", true);

    private boolean useDefaultModelIfModelNotSetting = (boolean) getValue("useDefaultModelIfModelNotSetting", true);

    private String tableName = (String) getValue("tableName", "casbin_rule");

    private CasbinStoreType storeType = (CasbinStoreType) getValue("storeType", CasbinStoreType.JDBC);

    private CasbinWatcherType watcherType = (CasbinWatcherType) getValue("watcherType", CasbinWatcherType.REDIS);

    private CasbinDataSourceInitializationMode initializeSchema = (CasbinDataSourceInitializationMode) getValue("initializeSchema", CasbinDataSourceInitializationMode.CREATE);


    public boolean couldResolveModelContext() throws CasbinModelConfFileNotFound, ModelFileIsEmptyException {
        try {
            return FileUtil.isFileEmpty(model);
        } catch (FileNotFoundException e) {
            throw new CasbinModelConfFileNotFound();
        } catch (FileIsEmptyException e) {
            throw new ModelFileIsEmptyException();
        }

    }


    public boolean couldResolvePolicyContext() throws CasbinPolicyCSVFileNotFound, PolicyFileIsEmptyException {
        try {
            return FileUtil.isFileEmpty(policy);
        } catch (FileNotFoundException e) {
            throw new CasbinPolicyCSVFileNotFound();
        } catch (FileIsEmptyException e) {
            throw new PolicyFileIsEmptyException();
        }


    }

    public Config getConfig() {
        return config;
    }

    public boolean isEnableCasbin() {
        return enableCasbin;
    }

    public boolean isUseSyncedEnforcer() {
        return useSyncedEnforcer;
    }

    public String getModel() {
        return model;
    }

    public String getPolicy() {
        return policy;
    }

    public String getPolicyTopic() {
        return policyTopic;
    }

    public boolean isEnableWatcher() {
        return enableWatcher;
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public boolean isUseDefaultModelIfModelNotSetting() {
        return useDefaultModelIfModelNotSetting;
    }

    public String getTableName() {
        return tableName;
    }

    public CasbinStoreType getStoreType() {
        return storeType;
    }

    public CasbinWatcherType getWatcherType() {
        return watcherType;
    }

    public CasbinDataSourceInitializationMode getInitializeSchema() {
        return initializeSchema;
    }

    public void setEnableCasbin(boolean enableCasbin) {
        this.enableCasbin = enableCasbin;
    }

    public void setUseSyncedEnforcer(boolean useSyncedEnforcer) {
        this.useSyncedEnforcer = useSyncedEnforcer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public void setPolicyTopic(String policyTopic) {
        this.policyTopic = policyTopic;
    }

    public void setEnableWatcher(boolean enableWatcher) {
        this.enableWatcher = enableWatcher;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public void setUseDefaultModelIfModelNotSetting(boolean useDefaultModelIfModelNotSetting) {
        this.useDefaultModelIfModelNotSetting = useDefaultModelIfModelNotSetting;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setStoreType(CasbinStoreType storeType) {
        this.storeType = storeType;
    }

    public void setWatcherType(CasbinWatcherType watcherType) {
        this.watcherType = watcherType;
    }

    public void setInitializeSchema(CasbinDataSourceInitializationMode initializeSchema) {
        this.initializeSchema = initializeSchema;
    }


}
