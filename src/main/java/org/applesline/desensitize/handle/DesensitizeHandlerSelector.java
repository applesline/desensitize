package org.applesline.desensitize.handle;

import org.applesline.desensitize.constants.DesensitizeType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyaping
 * @date 2022/1/14
 */
public class DesensitizeHandlerSelector implements ApplicationContextAware {

    private Map<DesensitizeType, DesensitizeHandler> serviceMap = new HashMap<>();

    public DesensitizeHandler getService(DesensitizeType desensitizeType) {
        return serviceMap.get(desensitizeType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,DesensitizeHandler> handlers = applicationContext.getBeansOfType(DesensitizeHandler.class);
        handlers.values().forEach(handler->{
            serviceMap.putIfAbsent(handler.getFieldType(),handler);
        });
    }
}
