package org.applesline.desensitize.handle;

import org.applesline.desensitize.constants.DesensitizeType;

/**
 * 屏蔽敏感字段处理器。
 *
 * @author liuyaping
 * @date 2022/1/14
 */
public interface DesensitizeHandler {

    String doMask(String fieldValue);

    Long doMask();

    DesensitizeType getFieldType();
}
