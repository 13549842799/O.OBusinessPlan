package com.oo.businessplan.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {

    private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
    private static Pattern numericStringPattern = Pattern
            .compile("^[0-9\\-\\-]+$");
    private static Pattern floatNumericPattern = Pattern
            .compile("^[0-9\\-\\.]+$");
    private static Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
    public static final String splitStrPattern = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";
    private static Log logger = LogFactory.getLog(StringUtil.class);

    /**
     * 判断是否数字表示
     *
     * @param src
     *            源字符串
     * @return 是否数字的标志
     */
    public static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否数字表示
     *
     * @param src
     *            源字符串
     * @return 是否数字的标志
     */
    public static boolean isNumericString(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericStringPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否纯字母组合
     *
     * @param src
     *            源字符串
     * @return 是否纯字母组合的标志
     */
    public static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否浮点数字表示
     *
     * @param src
     *            源字符串
     * @return 是否数字的标志
     */
    public static boolean isFloatNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = floatNumericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }



    /***************************************************************************
     * getHideEmailPrefix - 隐藏邮件地址前缀。
     *
     * @param email
     *            - EMail邮箱地址 例如: linwenguo@koubei.com 等等...
     * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
     * @version 1.0 (2006.11.27) Wilson Lin
     **************************************************************************/
    public static String getHideEmailPrefix(String email) {
        if (null != email) {
            int index = email.lastIndexOf('@');
            if (index > 0) {
                email = repeat("*", index).concat(email.substring(index));
            }
        }
        return email;
    }

    /***************************************************************************
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     *
     * @param src
     *            - 源字符串 例如: 空格(" "), 星号("*"), "浙江" 等等...
     * @param num
     *            - 重复生成次数
     * @return 返回已生成的重复字符串
     * @version 1.0 (2006.10.10) Wilson Lin
     **************************************************************************/
    public static String repeat(String src, int num) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }

    /**
     * 根据指定的字符把源字符串分割成一个数组
     *
     * @param src
     * @return
     */
    public static List<String> parseString2ListByCustomerPattern(
            String pattern, String src) {

        if (src == null)
            return null;
        List<String> list = new ArrayList<String>();
        String[] result = src.split(pattern);
        for (int i = 0; i < result.length; i++) {
            list.add(result[i]);
        }
        return list;
    }

    /**
     * 根据指定的字符把源字符串分割成一个数组
     *
     * @param src
     * @return
     */
    public static List<String> parseString2ListByPattern(String src) {
        String pattern = "，|,|、|。";
        return parseString2ListByCustomerPattern(pattern, src);
    }

    /**
     * 判断是否是空字符串 null和"" 都返回 true
     *
     * @author Robin Chang
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {

        return s == null && s.trim().equals("");
    }
    
    public static boolean isNotEmpty(String s) {
    	return !isEmpty(s);
    }
    
    /**
     * 判断a和b是否相同，如果两者都为null也返回false
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsNotNull(String a, String b) {

    	return !isEmpty(a) && !isEmpty(b) || a.equals(b);
    }

    /**
     * 数字转字符串,如果num<=0 则输出"";
     *
     * @param num
     * @return
     */
    public static String numberToString(Object num) {
        if (num == null) {
            return null;
        } else if (num instanceof Integer && (Integer) num > 0) {
            return Integer.toString((Integer) num);
        } else if (num instanceof Long && (Long) num > 0) {
            return Long.toString((Long) num);
        } else if (num instanceof Float && (Float) num > 0) {
            return Float.toString((Float) num);
        } else if (num instanceof Double && (Double) num > 0) {
            return Double.toString((Double) num);
        } else {
            return "";
        }
    }

    /**
     * 货币转字符串
     *
     * @param money
     * @param style
     *            样式 [default]要格式化成的格式 such as #.00, #.#
     * @return
     */

    public static String moneyToString(Object money, String style) {
        if (money != null && style != null
                && (money instanceof Double || money instanceof Float)) {
            Double num = (Double) money;

            if (style.equalsIgnoreCase("default")) {
                // 缺省样式 0 不输出 ,如果没有输出小数位则不输出.0
                if (num == 0) {
                    // 不输出0
                    return "";
                } else if ((num * 10 % 10) == 0) {
                    // 没有小数
                    return Integer.toString((int) num.intValue());
                } else {
                    // 有小数
                    return num.toString();
                }

            } else {
                DecimalFormat df = new DecimalFormat(style);
                return df.format(num);
            }
        }
        return null;
    }

    public static String simpleEncrypt(String str) {
        if (str != null && str.length() > 0) {
            // str = str.replaceAll("0","a");
            str = str.replaceAll("1", "b");
            // str = str.replaceAll("2","c");
            str = str.replaceAll("3", "d");
            // str = str.replaceAll("4","e");
            str = str.replaceAll("5", "f");
            str = str.replaceAll("6", "g");
            str = str.replaceAll("7", "h");
            str = str.replaceAll("8", "i");
            str = str.replaceAll("9", "j");
        }
        return str;

    }

    /**
     * 过滤用户输入的URL地址（防治用户广告） 目前只针对以http或www开头的URL地址
     * 本方法调用的正则表达式，不建议用在对性能严格的地方例如:循环及list页面等
     *
     * @author fengliang
     * @param str
     *            需要处理的字符串
     * @return 返回处理后的字符串
     */
    public static String removeURL(String str) {
        if (str != null)
            str = str.toLowerCase()
                    .replaceAll("(http|www|com|cn|org|\\.)+", "");
        return str;
    }


    /**
     * 检查字符串是否属于手机号码
     *
     * @author Peltason
     * @date 2007-5-9
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        if (str == null || str.equals(""))
            return false;
        if (str.length() != 11 || !isNumeric(str))
            return false;
        if (!str.substring(0, 2).equals("13")
                && !str.substring(0, 2).equals("15")
                && !str.substring(0, 2).equals("18"))
            return false;
        return true;
    }

 

    /**
     *
     * 转换编码
     *
     * @param s
     *            源字符串
     * @param fencode
     *            源编码格式
     * @param bencode
     *            目标编码格式
     * @return 目标编码
     */
    public static String changCoding(String s, String fencode, String bencode) {
        try {
            String str = new String(s.getBytes(fencode), bencode);
            return str;
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }


    /**
     *
     * 根据正则表达式分割字符串
     *
     * @param str
     *            源字符串
     * @param ms
     *            正则表达式
     * @return 目标字符串组
     */
    public static String[] splitString(String str, String ms) {
        String regEx = ms;
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        String[] sp = p.split(str);
        return sp;
    }
  

    /**
     * 带有前一次替代序列的正则表达式替代
     *
     * @param s
     * @param pf
     * @param pb
     * @param start
     * @return
     */
    public static String stringReplace(String s, String pf, String pb, int start) {
        Pattern pattern_hand = Pattern.compile(pf);
        Matcher matcher_hand = pattern_hand.matcher(s);
        int gc = matcher_hand.groupCount();
        int pos = start;
        String sf1 = "";
        String sf2 = "";
        String sf3 = "";
        int if1 = 0;
        String strr = "";
        while (matcher_hand.find(pos)) {
            sf1 = matcher_hand.group();
            if1 = s.indexOf(sf1, pos);
            if (if1 >= pos) {
                strr += s.substring(pos, if1);
                pos = if1 + sf1.length();
                sf2 = pb;
                for (int i = 1; i <= gc; i++) {
                    sf3 = "\\" + i;
                    sf2 = replaceAll(sf2, sf3, matcher_hand.group(i));
                }
                strr += sf2;
            } else {
                return s;
            }
        }
        strr = s.substring(0, start) + strr;
        return strr;
    }

    /**
     * 存文本替换
     *
     * @param s
     *            源字符串
     * @param sf
     *            子字符串
     * @param sb
     *            替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String s, String sf, String sb) {
        int i = 0, j = 0;
        int l = sf.length();
        boolean b = true;
        boolean o = true;
        String str = "";
        do {
            j = i;
            i = s.indexOf(sf, j);
            if (i > j) {
                str += s.substring(j, i);
                str += sb;
                i += l;
                o = false;
            } else {
                str += s.substring(j);
                b = false;
            }
        } while (b);
        if (o) {
            str = s;
        }
        return str;
    }

    /**
     * 判断是否与给定字符串样式匹配
     *
     * @param str
     *            字符串
     * @param pattern
     *            正则表达式样式
     * @return 是否匹配是true,否false
     */
    public static boolean isMatch(String str, String pattern) {
        Pattern pattern_hand = Pattern.compile(pattern);
        Matcher matcher_hand = pattern_hand.matcher(str);
        boolean b = matcher_hand.matches();
        return b;
    }

    /**
     * 判断是否是邮箱格式
     * @param eamilStr
     * @return
     */
    public static boolean isEmail(String emailStr){
    	
    	String pattern ="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
	
    	return Pattern.matches(pattern, emailStr);
    }
    
    public static boolean isIdCard(String idCardStr){
    	String pattern_18 ="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        String pattern_15 ="^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";
        
        return Pattern.matches(pattern_18, idCardStr)||Pattern.matches(pattern_15,idCardStr);
    
    }
    
    public static boolean isPhone(String phone){
    	String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    	
    	return Pattern.matches(regex, phone);
    }
    

    public static String subYhooStringDot(String subject, int size) {
        subject = subject.substring(1, size) + "...";
        return subject;
    }

  

    /**
     * 判断是否是空字符串 null和"" null返回result,否则返回字符串
     *
     * @param s
     * @return
     */
    public static String isEmpty(String s, String result) {
        if (s != null && !s.equals("")) {
            return s;
        }
        return result;
    }


    /**
     * 把16进制转换成字节码
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }


    public static boolean isDate(String date) {
        String regEx = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        boolean result = m.find();
        return result;
    }

    public static boolean isFormatDate(String date, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        boolean result = m.find();
        return result;
    }


    /**
     * 分割字符，从开始到第一个split字符串为止
     *
     * @param src
     *            源字符串
     * @param split
     *            截止字符串
     * @return
     */
    public static String subStr(String src, String split) {
        if (!isEmpty(src)) {
            int index = src.indexOf(split);
            if (index >= 0) {
                return src.substring(0, index);
            }
        }
        return src;
    }

    /**
     * 取url里的keyword（可选择参数）参数，用于整站搜索整合
     *
     * @author huoshao
     * @param params
     * @param qString
     * @return
     */
    public static String getKeyWord(String params, String qString) {
        String keyWord = "";
        if (qString != null) {
            String param = params + "=";
            int i = qString.indexOf(param);
            if (i != -1) {
                int j = qString.indexOf("&", i + param.length());
                if (j > 0) {
                    keyWord = qString.substring(i + param.length(), j);
                }
            }
        }
        return keyWord;
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query
     *            源参数字符串
     * @param split1
     *            键值对之间的分隔符（例：&）
     * @param split2
     *            key与value之间的分隔符（例：=）
     * @param dupLink
     *            重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
     *            null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
     * @return map
     * @author sky
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseQuery(String query, char split1,
                                                 char split2, String dupLink) {
        if (!isEmpty(query) && query.indexOf(split2) > 0) {
            Map<String, String> result = new HashMap();

            String name = null;
            String value = null;
            String tempValue = "";
            int len = query.length();
            for (int i = 0; i < len; i++) {
                char c = query.charAt(i);
                if (c == split2) {
                    value = "";
                } else if (c == split1) {
                    if (!isEmpty(name) && value != null) {
                        if (dupLink != null) {
                            tempValue = result.get(name);
                            if (tempValue != null) {
                                value += dupLink + tempValue;
                            }
                        }
                        result.put(name, value);
                    }
                    name = null;
                    value = null;
                } else if (value != null) {
                    value += c;
                } else {
                    name = (name != null) ? (name + c) : "" + c;
                }
            }

            if (!isEmpty(name) && value != null) {
                if (dupLink != null) {
                    tempValue = result.get(name);
                    if (tempValue != null) {
                        value += dupLink + tempValue;
                    }
                }
                result.put(name, value);
            }

            return result;
        }
        return null;
    }

  
    /**
     * 字符串转java.sql.Time类型
     * @param str
     * @return
     */
    public static Time strToTime(String str){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        java.util.Date d = null;

        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Time time = new java.sql.Time(d.getTime());
        return time;
    }

    /**
     * 字符串转java.sql.Date类型
     * @param str
     * @return
     */
    public static Date strToDate(String str){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;

        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }

}
