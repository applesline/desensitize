package org.applesline.desensitize.executor;


import org.applesline.desensitize.constants.DesensitizeType;

import java.util.Collection;
import java.util.Map;

/**
 * @author liuyaping
 * @date 2022/1/13
 */
public interface DesensitizeExecutor {

    void configFields(Map<String, DesensitizeType> maskWordsMap, Collection<String> ignoreJsonPathExpression);

    Map<String, DesensitizeType> getMaskWordsMap();

    Collection<String> getIgnoreJsonPathExpression();

    Object executeMask(Object obj);

}
