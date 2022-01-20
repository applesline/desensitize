package org.applesline.desensitize.aop;

import org.applesline.desensitize.annotation.Desensitize;
import org.applesline.desensitize.annotation.FieldMapping;
import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.executor.DesensitizeExecutor;
import org.applesline.desensitize.executor.JsonPathDesensitizeExecutor;
import org.applesline.desensitize.handle.DesensitizeHandlerSelector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyaping
 * @date 2022/1/14
 */
@Aspect
public class DesensitizeAdvice implements ApplicationContextAware {

    private DesensitizeExecutor desensitizeExecutor;
    private DesensitizeExecutor globalDesensitizeExecutor;

    public DesensitizeAdvice(DesensitizeExecutor desensitizeExecutor) {
        this.desensitizeExecutor = desensitizeExecutor;
    }

    @Around("@annotation(org.applesline.desensitize.annotation.Desensitize)")
    public Object desensitize(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        Object obj = proceedingJoinPoint.proceed();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature)signature;
            Desensitize desensitize = methodSignature.getMethod().getAnnotation(Desensitize.class);
            Map<String, DesensitizeType> fieldMappingMap = new HashMap<>();
            FieldMapping[] fieldMappings = desensitize.fieldMapping();
            for (FieldMapping fieldMapping : fieldMappings) {
                DesensitizeType desensitizeType = fieldMapping.type();
                String[] fields = fieldMapping.fields();
                for (String field : fields) {
                    fieldMappingMap.putIfAbsent(field,desensitizeType);
                }
            }
            if (fieldMappingMap.isEmpty()) {
                obj = globalDesensitizeExecutor.executeMask(obj);
            } else {
                desensitizeExecutor.configFields(fieldMappingMap, Arrays.stream(desensitize.ignoreByJpe()).collect(Collectors.toList()));
                obj = desensitizeExecutor.executeMask(obj);
            }
        }
        return obj;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DesensitizeHandlerSelector selector = applicationContext.getBean(DesensitizeHandlerSelector.class);
        this.globalDesensitizeExecutor = new JsonPathDesensitizeExecutor(selector);
        this.globalDesensitizeExecutor.configFields(this.desensitizeExecutor.getMaskWordsMap(),this.desensitizeExecutor.getIgnoreJsonPathExpression());
    }
}
