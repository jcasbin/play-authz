package org.casbin;

import org.casbin.jcasbin.main.Enforcer;
import org.casbin.properties.CasbinExceptionProperties;
import org.casbin.properties.JDBCConfigurationProperties;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

/**
 * description:
 *
 * @author wht
 * @createDate 2022/12/8 15:15
 */
public class PlayTest {
    @Inject
    CasbinEnforcer enforcer;

    @Test
    public void testFile() {
        Enforcer e = new Enforcer("examples/rbac_model.conf", "examples/rbac_policy.csv");
        e.getModel();
        e.getPolicy();
        e.enforce("alice","data1","read");
    }

    @Test
    public void testMysql() throws Exception {
        JDBCConfigurationProperties jdbcConfigurationProperties = new JDBCConfigurationProperties();
        jdbcConfigurationProperties.setDriver("com.mysql.cj.jdbc.Driver");
        jdbcConfigurationProperties.setUrl("jdbc:mysql://127.0.0.1:3306/casbin?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true");
        jdbcConfigurationProperties.setUsername("casbin_test");
        jdbcConfigurationProperties.setPassword("TEST_casbin");
        CasbinEnforcer enforcer = new CasbinEnforcer(jdbcConfigurationProperties,new CasbinExceptionProperties());
        enforcer.getModel();
        enforcer.getPolicy();
        enforcer.addPolicy("admin", "data2_admin", "read");
        enforcer.addRoleForUser("alice","admin");
        assert enforcer.enforce("alice","data2_admin","read") == true;
    }

    @Test
    public void testPostgreSql () throws Exception {
        JDBCConfigurationProperties jdbcConfigurationProperties = new JDBCConfigurationProperties();
        jdbcConfigurationProperties.setDriver("org.postgresql.Driver");
        jdbcConfigurationProperties.setUrl("jdbc:postgresql://localhost:5432/casbin");
        jdbcConfigurationProperties.setUsername("casbin_test");
        jdbcConfigurationProperties.setPassword("TEST_casbin");
        CasbinEnforcer enforcer = new CasbinEnforcer(jdbcConfigurationProperties,new CasbinExceptionProperties());
        enforcer.getModel();
        enforcer.getPolicy();
        enforcer.addPolicy("admin", "data2_admin", "read");
        enforcer.addRoleForUser("alice","admin");
        assert enforcer.enforce("alice","data2_admin","read") == true;
    }


}
