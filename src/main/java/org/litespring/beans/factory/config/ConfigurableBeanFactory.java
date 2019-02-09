package org.litespring.beans.factory.config;

/**
 * 新建一个类继承BeanFactory的目的是 不想让BeanFactory这种使用频繁的接口 带有setter
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

}
