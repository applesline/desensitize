package org.applesline.desensitize.handle;

import org.applesline.desensitize.constants.DesensitizeType;

/**
 * 屏蔽敏感字段处理器。
 *
 * @author liuyaping
 * @date 2022/1/14
 */
public class DesensitizeAdapter implements DesensitizeHandler {

    @Override
    public String doMask(String fieldValue) {
        throw new RuntimeException();
    }

    @Override
    public Long doMask() {
        return 0L;
    }

    protected String maskCardNumber(String idCardNum,int front,int end) {
        if (!isBlank(idCardNum) && front + end > idCardNum.length()) {
            return front >= 0 && end >= 0 ? hide(idCardNum, front, idCardNum.length() - end) : "";
        }
        return "";
    }

    protected String hide(CharSequence str, int startInclude, int endExclude) {
        return replace(str, startInclude, endExclude, '*');
    }

    protected String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (isEmpty(str)) {
            return str(str);
        } else {
            int strLength = str.length();
            if (startInclude > strLength) {
                return str(str);
            } else {
                if (endExclude > strLength) {
                    endExclude = strLength;
                }

                if (startInclude > endExclude) {
                    return str(str);
                } else {
                    char[] chars = new char[strLength];

                    for(int i = 0; i < strLength; ++i) {
                        if (i >= startInclude && i < endExclude) {
                            chars[i] = replacedChar;
                        } else {
                            chars[i] = str.charAt(i);
                        }
                    }

                    return new String(chars);
                }
            }
        }
    }

    protected int indexOf(CharSequence seq, int searchChar) {
        return isEmpty(seq) ? -1 : indexOf(seq, searchChar, 0);
    }

    private int indexOf(final CharSequence cs, final int searchChar, int start) {
        if (cs instanceof String) {
            return ((String)cs).indexOf(searchChar, start);
        } else {
            int sz = cs.length();
            if (start < 0) {
                start = 0;
            }

            for(int i = start; i < sz; ++i) {
                if (cs.charAt(i) == searchChar) {
                    return i;
                }
            }

            return -1;
        }
    }

    private boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    protected String str(CharSequence cs) {
        return cs == null ? null : cs.toString();
    }

    protected String repeat(char c, int count) {
        if (count <= 0) {
            return "";
        } else {
            char[] result = new char[count];

            for(int i = 0; i < count; ++i) {
                result[i] = c;
            }
            return new String(result);
        }
    }

    protected boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    @Override
    public DesensitizeType getFieldType() {
        throw new RuntimeException();
    }
}
