package casbin;

import casbin.exceptions.File.CasbinModelConfFileNotFound;
import casbin.exceptions.File.CasbinPolicyCSVFileNotFound;
import casbin.exceptions.File.ModelFileIsEmptyException;
import casbin.exceptions.File.PolicyFileIsEmptyException;
import casbin.properties.CasbinExceptionProperties;
import casbin.properties.CasbinProperties;
import casbin.properties.JDBCConfigurationProperties;
import casbin.properties.options.CasbinDataSourceInitializationMode;
import casbin.properties.options.CasbinStoreType;
import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.file_adapter.FileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CasbinEnforcer extends Enforcer {

    private static final Logger logger = LoggerFactory.getLogger(CasbinEnforcer.class);
    private static final CasbinProperties properties = new CasbinProperties();


    @Inject
    public CasbinEnforcer(JDBCConfigurationProperties jdbcConfigurationProperties, CasbinExceptionProperties exceptionProperties) throws Exception {
        super(resolveModel(), resolvePolicy(jdbcConfigurationProperties, exceptionProperties));
    }

    private static Adapter resolvePolicy(JDBCConfigurationProperties jdbcConfigurationProperties, CasbinExceptionProperties exceptionProperties) throws Exception {
        if (properties.getStoreType() == CasbinStoreType.FILE) return resolveFilePolicy();
        return resolveJDBCAdapter(jdbcConfigurationProperties, exceptionProperties);
    }

    public static FileAdapter resolveFilePolicy() throws CasbinPolicyCSVFileNotFound, PolicyFileIsEmptyException {
        try {
            properties.couldResolvePolicyContext();
        } catch (CasbinPolicyCSVFileNotFound | PolicyFileIsEmptyException e) {
            throw e;
        }

        return new FileAdapter(properties.getPolicy());
    }


    public static Adapter resolveJDBCAdapter(JDBCConfigurationProperties jdbcConfigurationProperties, CasbinExceptionProperties exceptionProperties) throws Exception {


        CasbinDataSourceInitializationMode initializationMode = properties.getInitializeSchema();
        boolean autoCreateTable = initializationMode == CasbinDataSourceInitializationMode.CREATE;
        String tableName = properties.getTableName();

        return new JDBCAdapter(jdbcConfigurationProperties.getDriver(), jdbcConfigurationProperties.getUrl(), jdbcConfigurationProperties.getUsername(), jdbcConfigurationProperties.getPassword(), exceptionProperties.isRemovePolicyFailed(), tableName, autoCreateTable);

    }

    public static Model resolveModel() throws CasbinModelConfFileNotFound, ModelFileIsEmptyException {
        try {
            properties.couldResolveModelContext();
            return newModel(properties.getModel(), "");
        } catch (CasbinModelConfFileNotFound | ModelFileIsEmptyException e) {
            if (!properties.isUseDefaultModelIfModelNotSetting()) {
                logger.info("Default Model setting is set to false, create model.conf file");
                throw e;
            }
        }

        Model model = new Model();
        logger.info("Can't find model config file, using default model config");

        // request definition
        model.addDef("r", "r", "sub, obj, act");
        // policy definition
        model.addDef("p", "p", "sub, obj, act");
        // role definition
        model.addDef("g", "g", "_, _");
        // policy effect
        model.addDef("e", "e", "some(where (p.eft == allow))");
        // matchers
        model.addDef("m", "m", "g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act");


        return model;
    }


}

