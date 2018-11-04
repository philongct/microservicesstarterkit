package l.nguyen.ms.transactionreporting.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "l.nguyen.ms.transactionreporting.repository")
public class JpaConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setConnectionTimeout(30000);
        ds.setMaximumPoolSize(2);

        ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        ds.setJdbcUrl("jdbc:derby:database/settlement;create=true");
        ds.setUsername("app");
        ds.setPassword("pass");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("l.nguyen.ms.transactionreporting.model");

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        props.put("hibernate.show_sql", "true");
        // since this is simulation, we need fresh environment on startup
        props.put("hibernate.hbm2ddl.auto", "create");
        emf.setJpaProperties(props);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(null).getObject());

        return transactionManager;
    }
}
