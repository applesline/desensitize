package org.applesline.desensitize.executor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.handle.DesensitizeHandlerSelector;

import java.util.Collection;
import java.util.Map;

/**
 * @author liuyaping
 * @date 2022/1/17
 */
public class DesensitizeExecutorAdapter implements DesensitizeExecutor {

    protected DesensitizeHandlerSelector desensitizeHandlerSelector;

    protected Map<String, DesensitizeType> maskWordsMap;
    protected Collection<String> ignoreJsonPathExpression;

    public DesensitizeExecutorAdapter(DesensitizeHandlerSelector desensitizeHandlerSelector) {
        this.desensitizeHandlerSelector = desensitizeHandlerSelector;
    }

    protected Gson gson = new GsonBuilder().create();

    @Override
    public void configFields(Map<String, DesensitizeType> maskWordsMap, Collection<String> ignoreJsonPathExpression) {
        this.maskWordsMap = maskWordsMap;
        this.ignoreJsonPathExpression = ignoreJsonPathExpression;
    }

    @Override
    public Map<String, DesensitizeType> getMaskWordsMap() {
        return this.maskWordsMap;
    }

    @Override
    public Collection<String> getIgnoreJsonPathExpression() {
        return this.ignoreJsonPathExpression;
    }

    @Override
    public Object executeMask(Object obj) {
        throw new RuntimeException();
    }

    public Long doMask(DesensitizeType desensitizeType) {
        return desensitizeHandlerSelector.getService(desensitizeType).doMask();
    }

    public String doMask(DesensitizeType desensitizeType, String origin) {
        return desensitizeHandlerSelector.getService(desensitizeType).doMask(origin);
    }

}
