package me.jobcollection.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Hongrry
 * @create 2021-10-05 21:00
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class EmailConfigProperties implements Serializable {
    private String host;
    private String port;
    private String user;
    private String pass;
    private String fromUser;
}
