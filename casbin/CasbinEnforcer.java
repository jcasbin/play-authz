package casbin;

import casbin.exceptions.File.CasbinModelConfFileNotFound;
import casbin.exceptions.File.PolicyFileIsEmptyException;
import casbin.properties.CasbinExceptionProperties;
import casbin.properties.JDBCConfigurationProperties;
import casbin.properties.options.CasbinDataSourceInitializationMode;
import casbin.properties.CasbinProperties;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.file_adapter.FileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import casbin.exceptions.File.CasbinPolicyCSVFileNotFound;
import casbin.exceptions.File.ModelFileIsEmptyException;
import play.db.Database;
import org.casbin.adapter.JDBCAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CasbinEnforcer extends Enforcer {

    private static final Logger logger = LoggerFactory.getLogger(CasbinEnforcer.class);
    public static final CasbinProperties properties = new CasbinProperties();


    public CasbinEnforcer() throws CasbinModelConfFileNotFound, ModelFileIsEmptyException, PolicyFileIsEmptyException, CasbinPolicyCSVFileNotFound {
        super(resolveModel(), resolveFilePolicy());
    }
    
//    @Inject
    public CasbinEnforcer(Database db, JDBCConfigurationProperties jdbcConfigurationProperties, CasbinExceptionProperties exceptionProperties) throws Exception {
        super(resolveModel(), resolveJDBCAdapter(db, jdbcConfigurationProperties, exceptionProperties));

    }

    public static FileAdapter resolveFilePolicy() throws CasbinPolicyCSVFileNotFound, PolicyFileIsEmptyException {
        try {
            properties.couldResolvePolicyContext();
        } catch (CasbinPolicyCSVFileNotFound | PolicyFileIsEmptyException e) {
            throw e;
        }

        return new FileAdapter(properties.getPolicy());
    }

    @Inject
    public static Adapter resolveJDBCAdapter(Database db, JDBCConfigurationProperties jdbcConfigurationProperties, CasbinExceptionProperties exceptionProperties) throws Exception {
        String dbName = db.getName();
        CasbinDataSourceInitializationMode initializationMode = properties.getInitializeSchema();
        boolean autoCreateTable = initializationMode == CasbinDataSourceInitializationMode.CREATE;
        String tableName = properties.getTableName();
        logger.info("Casbin current use database product: {}", dbName);
        return new JDBCAdapter(jdbcConfigurationProperties.getDriver(), db.getUrl(), jdbcConfigurationProperties.getUsername(), jdbcConfigurationProperties.getPassword(), exceptionProperties.isRemovePolicyFailed(), tableName, autoCreateTable);

    }

    public static Model resolveModel() throws CasbinModelConfFileNotFound, ModelFileIsEmptyException {
        try {
            properties.couldResolveModelContext();
            return newModel(properties.getModel(), "");
        } catch (CasbinModelConfFileNotFound | ModelFileIsEmptyException e) {
            if (!properties.isUseDefaultModelIfModelNotSetting()) {
                logger.error("Default Model setting is set to false, create model.conf file");
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

