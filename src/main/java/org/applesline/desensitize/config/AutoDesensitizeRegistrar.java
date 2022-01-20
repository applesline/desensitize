package org.applesline.desensitize.config;

import org.applesline.desensitize.annotation.EnableDesensitize;
import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.handle.impl.AddressHandler;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyaping
 * @date 2022/1/19
 */
public class AutoDesensitizeRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String DESENSITIZE_BEAN_POST_PROCESSOR = "desensitizeBeanPostProcessor";
    private static final String FIELD_MAPPING = "fieldMapping";
    private static final String IGNORE_BY_JSONPATH_EXPRESS = "ignoreByJpe";
    private static final String TYPE = "type";
    private static final String FIELDS = "fields";

    private static final String SCAN_DESENSITIZE_HANDLER_BASE_PACKAGE = AddressHandler.class.getPackage().getName();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> defaultAttrs = importingClassMetadata.getAnnotationAttributes(EnableDesensitize.class.getName());

        AnnotationAttributes[] annotationAttributes = (AnnotationAttributes[]) defaultAttrs.get(FIELD_MAPPING);
        Map<String, DesensitizeType> fieldMappingMap = new HashMap<>();
        for (AnnotationAttributes annotationAttribute : annotationAttributes) {
            String[] fields = (String[])annotationAttribute.get(FIELDS);
            DesensitizeType desensitizeType = (DesensitizeType)annotationAttribute.get(TYPE);
            for (String field : fields) {
                fieldMappingMap.putIfAbsent(field,desensitizeType);
            }
        }
        String[] ignoreByJsonPathExpress = (String[])defaultAttrs.get(IGNORE_BY_JSONPATH_EXPRESS);
        List<String> jpeList = new ArrayList<>();
        for (String str : ignoreByJsonPathExpress) {
            if (!StringUtils.isEmpty(str.trim())) {
                jpeList.add(str.trim());
            }
        }

        if (!registry.containsBeanDefinition(DESENSITIZE_BEAN_POST_PROCESSOR)) {
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                    .genericBeanDefinition(DesensitizeBeanPostProcessor.class)
                    .addConstructorArgValue(fieldMappingMap)
                    .addConstructorArgValue(jpeList)
                    .getBeanDefinition();
            registry.registerBeanDefinition(DESENSITIZE_BEAN_POST_PROCESSOR, beanDefinition);
        }

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.scan(SCAN_DESENSITIZE_HANDLER_BASE_PACKAGE);
    }

}
