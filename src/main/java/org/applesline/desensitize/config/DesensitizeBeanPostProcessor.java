package org.applesline.desensitize.config;

import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.executor.JsonPathDesensitizeExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;
import java.util.Map;

/**
 * @author liuyaping
 * @date 2022/1/20
 */
public class DesensitizeBeanPostProcessor implements BeanPostProcessor {

    Map<String, DesensitizeType> maskFieldMapping;
    List<String> ignoreByJsonPathExpress;

    public DesensitizeBeanPostProcessor(Map<String, DesensitizeType> maskFieldMapping, List<String> ignoreByJsonPathExpress) {
        this.maskFieldMapping = maskFieldMapping;
        this.ignoreByJsonPathExpress = ignoreByJsonPathExpress;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == JsonPathDesensitizeExecutor.class) {
            JsonPathDesensitizeExecutor executor = (JsonPathDesensitizeExecutor)bean;
            executor.configFields(maskFieldMapping,ignoreByJsonPathExpress);
        }
        return bean;
    }
}
