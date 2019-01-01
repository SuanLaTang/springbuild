package org.litespring.beans.factory;

public interface BeanFactory {

    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
