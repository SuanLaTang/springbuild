package org.litespring.beans.factory.config;

/**
 * 用以表达 <property name="accountDao" ref="accountDao"/> 这种值
 * 成员变量 beanName 就是 ref所表达的东西
 */
public class RuntimeBeanReference {
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}