package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 成员变量分别对应petstore-v1.xml中的 id 和 class
 */
public class GenericBeanDefinition implements BeanDefinition{

    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {

        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

}
