package cn.jokeo.lovepig.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/24 23:26
 */
@Component
@ConfigurationProperties(prefix = "love")
@Data
public class LoveConfig {

    private String staticUrl;

    @Value("#{'${love.imageType}'.split(',')}")
    private List<String> imageTypes;

    @Value("#{'${love.videoType}'.split(',')}")
    private List<String> videoTypes;
}
