package com.kotomi.sale.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Kotomi on 2017/3/12.
 */
public final class CollectionUtil {
//    判断collection 是否为空,collection是一个容器类，arraylist等的最上层类
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }
//    判断collection是否非空
    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

//    判断map是否为空
    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

//    判断map是否非空
    public static boolean isNotEmpty(Map<?,?> map){
         return !isEmpty(map);
    }
}
