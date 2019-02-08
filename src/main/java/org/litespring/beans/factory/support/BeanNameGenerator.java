package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 如果我们通过xml文件配置bean的时候，需要写上bean的ID，
 * <bean id="itemDao" class="org.litespring.dao.v3.ItemDao"></bean>
 * 但如果通过注解 @Component(value="petStore")，没办法写ID，于是我们创建了 BeanNameGenerator
 *
 * @author 酸辣辣辣汤
 */
public interface BeanNameGenerator {

    /**
     * Generate a bean name for the given bean definition.
     *
     * @param definition the bean definition to generate a name for
     * @param registry   the bean definition registry that the given definition
     *                   is supposed to be registered with
     * @return the generated bean name
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}