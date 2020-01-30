package com.notary.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 列表工具
 *
 * @author xiejing
 * @version 2010-8-29 xiejing新增
 */
@SuppressWarnings("unchecked")
public class ListUtil {

	public static <T> Map<Long, T> dictionaryListToMap(List list) {
        return null;
    }

	public static <T> List<T> convert(List list) {
        List<T> result = new ArrayList<T>();
        for (Object aList : list) {
            T t = (T) aList;
            result.add(t);
        }
        return result;
    }

    /**
     * 获取某一列上的值集合
     *
     * @param list         列表
     * @param propertyName 属性名
     * @return
     */
    public static <T> Set<T> getValueSet(List list, String propertyName) {
        Set<T> result = new HashSet<T>();
        if (list == null) {
            return result;
        }
        for (Object o : list) {
            try {
                if (DateUtil.isBlank(propertyName)) {
                    result.add((T) o);
                } else {
                    result.add((T) PropertyUtils.getProperty(o, propertyName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取某一列上的值集合
     *
     * @param list      列表
     * @param keyName   Key属性名
     * @param valueName value属性名
     * @return
     */
    public static <T1, T2> Map<T1, T2> getValueMap(List list, String keyName, String valueName) {
        Map<T1, T2> result = new HashMap<T1, T2>();
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            try {
                result.put((T1) PropertyUtils.getProperty(o, keyName), (T2) PropertyUtils.getProperty(o, valueName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取某一列上的值集合
     *
     * @param list      列表
     * @param keyName   Key属性名
     * @param valueName value属性名
     * @return
     */
    public static <T1, T2> void setValueByMap(List list, String keyName, String valueName, Map<T1, T2> map, T2 defaultValue) {
        for (Object o : list) {
            try {
                T1 key = (T1) PropertyUtils.getProperty(o, keyName);
                T2 value = map.get(key);
                if (value != null) {
                    PropertyUtils.setProperty(o, valueName, value);
                } else {
                    PropertyUtils.setProperty(o, valueName, defaultValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 替换列表中空白的字段
     *
     * @param list         列表
     * @param propertyName 属性名
     * @param value        属性值
     * @param <T1>         列表类型
     * @param <T2>         属性类型
     */
    public static <T1, T2> void replaceBlank(List<T1> list, final String propertyName, final T2 value) {
        IListItemConvertor convertor = new IListItemConvertor<T1, T2>() {
            public T2 convert(T1 item, T2 value, int index) {
                return value;
            }
        };
        IListItemFilter filter = new IListItemFilter<T1, T2>() {
            public boolean isAccept(T1 item, T2 value, int index) {
                if (value instanceof String) {
                    return DateUtil.isBlank((String) value);
                }
                return value == null;
            }
        };
        replace(list, propertyName, convertor, filter);
    }

    /**
     * 替换列表属性值
     *
     * @param list         列表
     * @param propertyName 要替换的列表属性名
     * @param convertor    列表转换器
     * @param filter       列表过滤器
     * @param <T1>         列表类型
     * @param <T2>         属性类型
     */
    public static <T1, T2> void replace(List<T1> list, final String propertyName, final IListItemConvertor<T1, T2> convertor, final IListItemFilter<T1, T2> filter) {
        assert (list != null);
        visit(list, new IListVisitor<T1>() {
            public boolean visit(T1 item, int index) {
                if (item == null)
                    return false;
                try {
                    T2 property = (T2) PropertyUtils.getProperty(item, propertyName);
                    if (filter != null) {
                        if (!filter.isAccept(item, property, index)) {
                            return false;
                        }
                    }
                    property = convertor.convert(item, property, index);
                    PropertyUtils.setProperty(item, propertyName, property);
                    return false;
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    /**
     * 遍历列表
     *
     * @param list    列表
     * @param visitor 访问器
     * @param <T>     列表类型
     */
    public static <T> void visit(List<T> list, IListVisitor<T> visitor) {
        assert (list != null);
        assert (visitor != null);
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (visitor.visit(t, i))
                break;
        }
    }

    /**
     * 列表元素过滤器
     *
     * @param <T1> 列表类型
     * @param <T2> 属性类型
     */
    public static interface IListItemFilter<T1, T2> {
        /**
         * 这个元素是否可以接受
         *
         * @param item  元素
         * @param value 属性值
         * @param index 下标
         * @return 是否接受
         */
        boolean isAccept(T1 item, T2 value, int index);
    }

    /**
     * 列表元素转换器
     *
     * @param <T1> 列表类型
     * @param <T2> 属性类型
     */
    public static interface IListItemConvertor<T1, T2> {
        /**
         * 转换属性
         *
         * @param item  元素
         * @param value 属性值
         * @param index 下标
         * @return 结果属性
         */
        T2 convert(T1 item, T2 value, int index);
    }

    /**
     * 列表元素遍历器
     *
     * @param <T> 列表类型
     */
    public static interface IListVisitor<T> {
        /**
         * 访问列表元素
         *
         * @param item  条目
         * @param index 下标
         * @return 是否停止遍历，false场合继续下一条
         */
        boolean visit(T item, int index);
    }
}
