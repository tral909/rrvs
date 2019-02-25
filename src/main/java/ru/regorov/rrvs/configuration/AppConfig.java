package ru.regorov.rrvs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import ru.regorov.rrvs.web.json.JacksonObjectMapper;

@Configuration
public class AppConfig {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        //////start standalone server for multiple connections to database
        //cd C:\Users\Roman\.m2\repository\org\hsqldb\hsqldb\2.4.1
        //mkdir db; cd db
        //java -cp .\hsqldb-2.4.1.jar org.hsqldb.Server -database.0 mem:rrvs
        //java -cp ..\hsqldb-2.4.1.jar org.hsqldb.Server --database.0 file:mydb --dbname.0 rrvs
        //dataSource.setUrl("jdbc:hsqldb:hsql://localhost:9001/rrvs");
        //////start database in memory or in file
        dataSource.setUrl("jdbc:hsqldb:mem:rrvs");
        //dataSource.setUrl("jdbc:hsqldb:file:D:/temp/rrsv");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        // schema init
        Resource initSchema = new ClassPathResource("db/schema.sql");
        Resource initData = new ClassPathResource("db/population.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return dataSource;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonObjectMapper.getMapper();
    }
}