package me.jobcollection.modules.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author Hongrry
 * @create 2021-10-05 18:02
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String baseAccessPath;
    private String baseUploadPath;
    private Set<String> supportSuffix;
    /**
     * 文件大小限制
     */
    private Long maxSize;


    private String linux;

    private String windows;

    public String getPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return windows;
        }
        return linux;
    }

}
