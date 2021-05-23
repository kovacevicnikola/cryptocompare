package com.magus.cryptocompare.pojo;

public class StringUtils {

    public static String setString(String string) {
        return string.isEmpty() ? "" : string.trim();
    }

    public static String setString(Integer number) {
        return number != null ? String.valueOf(number) : "";
    }

    public static String setCustomerCode(String customerCode, String customerBusinessUnitCode, String potentialCustomerCode) {

        if (StringUtils.isNotBlank(customerBusinessUnitCode)) {

            if (customerBusinessUnitCode.length() > 2) {
                return customerCode + customerBusinessUnitCode.substring(customerBusinessUnitCode.length() - 2);
            } else {
                return customerCode;
            }
        } else if (StringUtils.isEmpty(customerCode)) {
            return potentialCustomerCode;
        }
        return customerCode == null ? "" : customerCode;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return cs != null && cs.length() > 0;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int len = searchStr.length();
        final int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (regionMatches(str, true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                 final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

}
