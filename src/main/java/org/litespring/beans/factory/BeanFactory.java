package org.litespring.beans.factory;

/**
 * 可以把 beanID 变成 bean的实例
 */
public interface BeanFactory {


    Object getBean(String beanID);

}
