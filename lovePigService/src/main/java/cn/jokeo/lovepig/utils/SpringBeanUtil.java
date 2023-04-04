package cn.jokeo.lovepig.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring容器中的事例
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringBeanUtil.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> objClass) {
        ApplicationContext context = getContext();
        if (null != context) {
            return (T) context.getBean(objClass);
        }
        return null;
    }

    public static Object getBean(String beanName) {
        ApplicationContext context = getContext();
        if (null != context) {
            return context.getBean(beanName);
        }
        return null;
    }
}
