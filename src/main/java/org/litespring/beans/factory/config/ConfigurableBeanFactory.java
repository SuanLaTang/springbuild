package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * 新建一个类继承BeanFactory的目的是不想让BeanFactory这种使用频繁的接口，带有setter
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

}
