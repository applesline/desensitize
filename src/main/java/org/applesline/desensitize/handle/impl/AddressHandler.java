package org.applesline.desensitize.handle.impl;

import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.handle.DesensitizeAdapter;
import org.springframework.stereotype.Component;

/**
 * @author liuyaping
 * @date 2022/1/13
 */
@Component
public class AddressHandler extends DesensitizeAdapter {

    @Override
    public String doMask(String address) {
        if (isBlank(address)) {
            return "";
        } else {
            int length = address.length();
            return hide(address, length - 8, length);
        }
    }

    @Override
    public DesensitizeType getFieldType() {
        return DesensitizeType.ADDRESS;
    }
}
