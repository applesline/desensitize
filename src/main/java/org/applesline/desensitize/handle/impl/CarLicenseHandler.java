package org.applesline.desensitize.handle.impl;

import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.handle.DesensitizeAdapter;
import org.springframework.stereotype.Component;

/**
 * @author liuyaping
 * @date 2022/1/19
 */
@Component
public class CarLicenseHandler extends DesensitizeAdapter {
    @Override
    public String doMask(String carLicense) {
        if (isBlank(carLicense)) {
            return "";
        } else {
            if (carLicense.length() == 7) {
                carLicense = hide(carLicense, 3, 6);
            } else if (carLicense.length() == 8) {
                carLicense = hide(carLicense, 3, 7);
            }

            return carLicense;
        }
    }

    @Override
    public DesensitizeType getFieldType() {
        return DesensitizeType.CAR_LICENSE;
    }
}
