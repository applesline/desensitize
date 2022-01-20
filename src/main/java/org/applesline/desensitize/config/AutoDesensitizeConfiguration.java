package org.applesline.desensitize.config;

import org.applesline.desensitize.aop.DesensitizeAdvice;
import org.applesline.desensitize.executor.DesensitizeExecutor;
import org.applesline.desensitize.executor.JsonPathDesensitizeExecutor;
import org.applesline.desensitize.handle.DesensitizeHandlerSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyaping
 * @date 2022/1/18
 */
@Configuration
public class AutoDesensitizeConfiguration {

    @Bean
    public DesensitizeHandlerSelector maskHandlerSelector() {
        return new DesensitizeHandlerSelector();
    }

    @Bean
    public DesensitizeExecutor maskSensitiveExecutor(DesensitizeHandlerSelector desensitizeHandlerSelector) {
        return new JsonPathDesensitizeExecutor(desensitizeHandlerSelector);
    }

    @Bean
    public DesensitizeAdvice desensitizeAdvice(DesensitizeExecutor desensitizeExecutor) {
        return new DesensitizeAdvice(desensitizeExecutor);
    }

}
