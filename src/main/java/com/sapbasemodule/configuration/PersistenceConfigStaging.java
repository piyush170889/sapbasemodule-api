package com.sapbasemodule.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Log4jConfigurer;

@Profile("staging")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"com.sapbasemodule.persitence"})
@PropertySources({
	@PropertySource("classpath:config-staging.properties")
})
public class PersistenceConfigStaging {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] {"com.sapbasemodule.domain"});

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}


	@Bean
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    
//	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//	    
//  		dataSource.setUrl("jdbc:mysql://localhost:3306/jagtap_solutions_db");
//  		dataSource.setUsername("root");
//  		dataSource.setPassword("Replete@789");

		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://192.168.0.114:1433;databaseName=New Demo");
		dataSource.setUsername("sa");
		dataSource.setPassword("jbsadmin@123");

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public PersistenceAnnotationBeanPostProcessor paPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}
	
	@Bean(name="configProperties")
	public Properties getConfigPropertiesFile() throws IOException {
		Log4jConfigurer.initLogging("classpath:log4j-staging.properties");
		Resource resource = new ClassPathResource("config-staging.properties");
		return PropertiesLoaderUtils.loadProperties(resource);
	}
	

	Properties additionalProperties() {
		Properties properties = new Properties();
		
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		properties.put("hibernate.show_sql", false);
	    properties.put("hibernate.format_sql", false);
	    
		return properties;
	}
}
