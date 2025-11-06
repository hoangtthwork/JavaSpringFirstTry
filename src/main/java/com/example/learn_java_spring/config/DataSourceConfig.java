package com.example.learn_java_spring.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig {

    /* ---------- Properties binding ---------- */

    @Bean(name = "writeDataSourceProperties")
    @ConfigurationProperties("spring.datasource.write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "readDataSourceProperties")
    @ConfigurationProperties("spring.datasource.read")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    /* ---------- Real DataSources (Hikari) ---------- */

    @Bean(name = "writeDataSource")
    public DataSource writeDataSource(
            @Qualifier("writeDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder()
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();
    }

    @Bean(name = "readDataSource")
    public DataSource readDataSource(
            @Qualifier("readDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder()
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();
    }

    /* ---------- Routing DataSource ---------- */

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
            @Qualifier("writeDataSource") DataSource write,
            @Qualifier("readDataSource")  DataSource read) {

        Map<Object, Object> targets = new HashMap<>();
        targets.put("WRITE", write);
        targets.put("READ",  read);

        AbstractRoutingDataSource routing = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                // Kiểm tra xem có transaction đang active không
                if (!TransactionSynchronizationManager.isActualTransactionActive()) {
                    // Không có transaction -> mặc định WRITE
                    return "WRITE";
                }
                
                // Kiểm tra readOnly flag
                boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
                
                // Debug log để kiểm tra
                System.out.println("[DataSourceConfig] determineCurrentLookupKey: " +
                    "txActive=" + TransactionSynchronizationManager.isActualTransactionActive() +
                    ", readOnly=" + isReadOnly +
                    ", result=" + (isReadOnly ? "READ" : "WRITE"));
                
                return isReadOnly ? "READ" : "WRITE";
            }
        };
        routing.setDefaultTargetDataSource(write);
        routing.setTargetDataSources(targets);
        routing.afterPropertiesSet();
        return routing;
    }

    /* ---------- Lazy Connection Proxy (quan trọng để đảm bảo connection chỉ được lấy sau khi transaction đã khởi tạo) ---------- */

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        // LazyConnectionDataSourceProxy đảm bảo connection chỉ được lấy khi thực sự cần,
        // sau khi transaction đã được khởi tạo đầy đủ với readOnly flag
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    /* ---------- JPA ---------- */

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource ds) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setPackagesToScan("com.example.learn_java_spring.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpa = new HashMap<>();
        jpa.put("hibernate.hbm2ddl.auto", "validate");
        jpa.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        em.setJpaPropertyMap(jpa);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    /* ---------- (Tùy chọn) Debug URL đúng chuẩn: dùng CommandLineRunner thay vì @PostConstruct ---------- */
    // @Bean
    // public org.springframework.boot.CommandLineRunner debugUrls(
    //         @Qualifier("writeDataSource") DataSource w,
    //         @Qualifier("readDataSource")  DataSource r) {
    //     return args -> {
    //         try (var cw = w.getConnection(); var cr = r.getConnection()) {
    //             System.out.println("WRITE URL = " + cw.getMetaData().getURL());
    //             System.out.println("READ  URL = " + cr.getMetaData().getURL());
    //         }
    //     };
    // }
}
