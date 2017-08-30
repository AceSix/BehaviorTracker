package com.kotomi.sale.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Kotomi on 2017/3/12.
 */
public final class StringUtil {
    public static boolean isEmpty(String str){
        if(str!=null){
            str=str.trim();
        }
       return  StringUtils.isEmpty(str);
    }


    public static  boolean isNotEmpty(String str){
        return  !isEmpty(str);
    }
}
