package org.applesline.desensitize.annotation;

import org.applesline.desensitize.config.AutoDesensitizeConfiguration;
import org.applesline.desensitize.config.AutoDesensitizeRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liuyaping
 * @date 2022/1/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutoDesensitizeConfiguration.class, AutoDesensitizeRegistrar.class})
public @interface EnableDesensitize {

    FieldMapping[] fieldMapping() default {};

    /**
     * ignore by JsonPath expression language
     *
     * @return
     */
    String[] ignoreByJpe() default {};
}
