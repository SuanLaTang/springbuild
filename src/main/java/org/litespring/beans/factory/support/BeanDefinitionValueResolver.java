package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

//用于将bean定义对象中包含的值解析为应用于目标bean实例的实际值
public class BeanDefinitionValueResolver {
//	private final DefaultBeanFactory beanFactory;

    private final BeanFactory beanFactory;

    //	public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
    public BeanDefinitionValueResolver(BeanFactory beanFactory) {

        this.beanFactory = beanFactory;
    }

    /**
     * 判断值是否真的要 resolve(分解)
     *
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value) {

        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            //refName 就是<property name="xxx" ref="xxx"/> 中的 ref 的值
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;

        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            throw new RuntimeException("the value " + value + " has not implemented");
        }
    }
}