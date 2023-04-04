package cn.jokeo.lovepig.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 配置Validator校验快速失败(第一个不通过后边的就不校验了)
 *
 * @author joke
 */
@Configuration
public class ValidatorConfiguration {
    /**
     * 配置Validator校验快速失败(第一个不通过后边的就不校验了)
     *
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}