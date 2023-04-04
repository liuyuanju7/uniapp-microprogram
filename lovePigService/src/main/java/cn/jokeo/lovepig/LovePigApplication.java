package cn.jokeo.lovepig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 爱猪猪 启动
 *
 * @author joke
 */
@EnableScheduling
@SpringBootApplication
@ServletComponentScan
public class LovePigApplication {

    public static void main(String[] args) {
        SpringApplication.run(LovePigApplication.class, args);
    }

}
