package org.litespring.beans.factory.config;

import java.util.List;

/**
 * 新建一个类继承 BeanFactory 的目的是 不想让 BeanFactory 这种使用频繁的接口 带有setter
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

    //把PostProcessor 加进 BeanFactory 中
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();

}
