package duzce.bm.mf.telefonrehberi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@PropertySource(value = "classpath:hibernate.properties", encoding = "UTF-8")
@EnableTransactionManagement
@Configuration
@ComponentScan(basePackages = "duzce.bm.mf.telefonrehberi")
public class AppConfig {

    @Autowired
    Environment environment;

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource());

        Properties properties = new Properties();

        properties.put(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        properties.put(DIALECT, environment.getProperty("hibernate.dialect"));
        properties.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));

        properties.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        properties.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        properties.put(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        properties.put(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        properties.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));
        properties.put(C3P0_IDLE_TEST_PERIOD, environment.getProperty("hibernate.c3p0.idle_test_period"));
        properties.put(C3P0_CONFIG_PREFIX + ".initialPoolSize", environment.getProperty("hibernate.c3p0.initialPoolSize"));

        factoryBean.setHibernateProperties(properties);
        factoryBean.setAnnotatedClasses(Department.class, Person.class, SubDepartment.class, User.class);
        return factoryBean;
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("mysql.driver"));
        dataSource.setUrl(environment.getProperty("mysql.url"));
        dataSource.setUsername(environment.getProperty("mysql.username"));
        dataSource.setPassword(environment.getProperty("mysql.password"));
        return dataSource;
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
