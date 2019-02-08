package org.litespring.beans;

/**
 * setter 注入的数据结构
 * 设计一个数据结构 PropertyValue
 * 解析XML，填充这个数据结构
 * 利用这个数据结构做事情
 */
public class PropertyValue {
    private final String name;

    //value对于这个简版的Spring来说，有两种可能：
    // RuntimeBeanReference 或者 TypedStringValue
    // RuntimeBeanReference ：把 BeanId 变成 Bean 实例
    // TypedStringValue 把value 变成一个实际的值
    private final Object value;

    //代表 value 是否已经转换成 convertedValue
    private boolean converted = false;

    //保存 RuntimeBeanReference 经过查找后获取到的值，会存放到 convertedValue 中
    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }


    public synchronized void setConvertedValue(Object value) {
        this.converted = true;
        this.convertedValue = value;
    }

    public synchronized Object getConvertedValue() {
        return this.convertedValue;
    }

}