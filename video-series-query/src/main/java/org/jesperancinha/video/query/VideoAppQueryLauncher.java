package org.jesperancinha.video.query;

import org.jesperancinha.video.core.configuration.AxonConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@SpringBootApplication
@Import(AxonConfig.class)
public class VideoAppQueryLauncher implements ApplicationRunner {
    public VideoAppQueryLauncher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(VideoAppQueryLauncher.class);
    }

    @Value("classpath:schema-after-load-postgres.sql")
    private Resource script;

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        ScriptUtils
//                .executeSqlScript(jdbcTemplate.getDataSource().getConnection(), script);
    }
}
