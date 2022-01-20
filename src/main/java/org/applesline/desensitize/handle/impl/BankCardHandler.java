package org.applesline.desensitize.handle.impl;

import org.applesline.desensitize.constants.DesensitizeType;
import org.applesline.desensitize.handle.DesensitizeAdapter;
import org.springframework.stereotype.Component;

/**
 * @author liuyaping
 * @date 2022/1/13
 */
@Component
public class BankCardHandler extends DesensitizeAdapter {

    @Override
    public String doMask(String bankCardNo) {
        if (isBlank(bankCardNo)) {
            return bankCardNo;
        } else {
            bankCardNo = trim(bankCardNo);
            if (bankCardNo.length() < 9) {
                return bankCardNo;
            } else {
                int length = bankCardNo.length();
                int midLength = length - 8;
                StringBuilder buf = new StringBuilder();
                buf.append(bankCardNo, 0, 4);

                for(int i = 0; i < midLength; ++i) {
                    if (i % 4 == 0) {
                        buf.append(" ");
                    }

                    buf.append('*');
                }

                buf.append(" ").append(bankCardNo, length - 4, length);
                return buf.toString();
            }
        }
    }

    @Override
    public DesensitizeType getFieldType() {
        return DesensitizeType.BANK_CARD;
    }
}
