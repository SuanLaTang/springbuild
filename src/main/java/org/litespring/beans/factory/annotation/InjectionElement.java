package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * InjectionElement 表示哪个类要进行注入，有哪些元素可以注入
 */
public abstract class InjectionElement {

    //Member is an interface that reflects identifying information about a single member (a field or a method) or a constructor.
    //用 number 代表好几种情况  (a field or a method) or a constructor
    protected Member member;

    //不选用 BeanFactory 的原因是因为 BeanFactory 中没有 resolveDependency() 的方法
    //不选用 ConfigurableBeanFactory 也是不想暴露 ConfigurableBeanFactory 中的 setBeanClassLoader() 和 getBeanClassLoader() 方法
    protected AutowireCapableBeanFactory factory;

    /**
     *
     * @param member 可以是 field、method、constructor
     * @param factory
     */
    InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}