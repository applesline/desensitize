package org.applesline.desensitize.handle.impl;

import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.handle.DesensitizeAdapter;
import org.springframework.stereotype.Component;

/**
 * @author liuyaping
 * @date 2022/1/19
 */
@Component
public class BirthdayHandler extends DesensitizeAdapter {
    @Override
    public String doMask(String birthday) {
        return isBlank(birthday) ? "" : "****-**-**";
    }

    @Override
    public DesensitizeType getFieldType() {
        return DesensitizeType.BIRTHDAY;
    }
}
