package ru.regorov.rrvs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
public class AppConfig {

    //store in memory
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .generateUniqueName(true)
                .addScript("db/schema.sql")
                .addScript("db/population.sql")
                .build();
    }

    /*//store in file
    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setUrl("jdbc:hsqldb:file:D:/temp/rrsv");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        // schema init
        Resource initSchema = new ClassPathResource("db/schema.sql");
        Resource initData = new ClassPathResource("db/population.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }*/
}
