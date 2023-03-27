package com.mysql.sharding.proxy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linzf
 * @since 2021/6/17
 * 类描述：
 */
public class ComplexUtils {


    /**
     * 将obj 转换为String
     *
     * @param obj
     * @return
     */
    public static String getObjStr(Object obj) {

        return obj == null ? "" : obj.toString().trim();
    }

    /**
     * 将obj转换为Long
     *
     * @param obj
     * @return
     */
    public static Long getObjLong(Object obj) {

        return Long.parseLong(obj == null ? "0" : obj + "");
    }
    /**
     * 示例算法文件
     * @param s
     * @return
     */
    public static int getpingjun(String s){
        int out=0;
        int i=0;
        char[] ss=s.toCharArray();

        for (i=0;i< ss.length;i++) {
            out += ss[i]-'0';
        }
        return out/i;
    }
    /**
     * 时间戳转换算法
     *输入时间戳转换为年份和月份，然后计算表明后缀
     */
    public static int TimetoYear(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        int year=0;
        Date date=new Date(time*1000L);
        String Alldate=sdf.format(date);
        char[] ss=Alldate.toCharArray();
        year=ss[0]*1000+ss[1]*100+ss[2]*10+ss[3];
        return  year;
    }
    /**
     * 时间戳转换算法
     *输入时间戳转换为年份和月份，然后计算表明后缀
     */
    public static int TimetoMouth(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        int mouth=0;
        Date date=new Date(time*1000L);
        String Alldate=sdf.format(date);
        char[] ss=Alldate.toCharArray();
        mouth=ss[5]*10+ss[6];
        return  mouth;
    }
}