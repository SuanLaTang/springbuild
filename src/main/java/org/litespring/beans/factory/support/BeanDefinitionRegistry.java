package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 为了符合单一职责原则(SRP)，而把注册的职责交到这个接口里
 */
public interface BeanDefinitionRegistry {

    //从BeanFactory中抽取
    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String beanID, BeanDefinition bd);

}
