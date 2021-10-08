package me.jobcollection.config;

import com.upyun.RestManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HONGRRY
 */
@Configuration
public class UpyunConfig {
    private final static String BUCKET_NAME = "hongrry";
    private final static String USER_NAME = "847";
    private final static String PASSWORD = "m7jyaGwnVVrJFmxvF644Y6SwimBZPEWR";

    @Bean
    public RestManager restManager() {
        RestManager restManager = new RestManager(BUCKET_NAME, USER_NAME, PASSWORD);
        restManager.setApiDomain(RestManager.ED_AUTO);
        restManager.setTimeout(60);
        return restManager;
    }
}