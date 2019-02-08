package org.litespring.beans;

import com.sun.corba.se.impl.io.TypeMismatchException;

/**
 * 类型转换器接口
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;


}