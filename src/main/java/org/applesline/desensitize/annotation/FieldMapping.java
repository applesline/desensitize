package org.applesline.desensitize.annotation;


import org.applesline.desensitize.constants.DesensitizeType;

/**
 * @author liuyaping
 * @date 2022/1/14
 */
public @interface FieldMapping {

    DesensitizeType type();

    String[] fields() default {};
}
