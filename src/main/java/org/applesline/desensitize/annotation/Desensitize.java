package org.applesline.desensitize.annotation;

import java.lang.annotation.*;

/**
 * @author liuyaping
 * @date 2022/1/13
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Desensitize {

    FieldMapping[] fieldMapping() default {};

    String[] ignoreByJpe() default {};
}
