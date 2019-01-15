package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//为什么要放到support包下，因为我们希望factory放的是接口，别的程序员使用我们框架的API
////同时这也是Spring的命名规范
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory,BeanDefinitionRegistry {

    private ClassLoader beanClassLoader;

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    public DefaultBeanFactory() {

    }

    @Override
    public void registerBeanDefinition(String beanID, BeanDefinition bd){
        this.beanDefinitionMap.put(beanID, bd);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if(bd == null){
            return null;
        }
        if(bd.isSingleton()){
            Object bean = this.getSingleton(beanID);
            if(bean == null){
                bean = createBean(bd);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

//    private Object createBean(BeanDefinition bd) {
//        ClassLoader cl = this.getBeanClassLoader();
//        String beanClassName = bd.getBeanClassName();
//        try {
//            //通过反射new出一个类
//            //类必须要有无参的构造函数，才能newInstance出来
//            Class<?> clz = cl.loadClass(beanClassName);
//			return clz.newInstance();
//        } catch (Exception e) {
//            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
//        }
//    }

    /**
     * 把 createBean() 拆分成两个方法：instantiateBean() 和 populateBean()
     * @param bd
     * @return
     */
    private Object createBean(BeanDefinition bd) {
        //初始化bean，创建实例
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd, bean);

        return bean;

    }

    /**
     * 通过 BeanDefinition(Bean的定义)中的className，用反射的方式来初始化Bean
     * @param bd
     * @return
     */
    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            //通过反射new出一个类
            //类必须要有无参的构造函数，才能newInstance出来
            Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }
    }

    /**
     * 在本方法里调用setter方法
     * @param bd
     * @param bean
     */
    protected void populateBean(BeanDefinition bd, Object bean){
        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try{
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

                //获取bean中有哪些字段和方法体
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                //获取字段
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)){
                        //getWriteMethod() 就是 setter方法
                        pd.getWriteMethod().invoke(bean, resolvedValue);
                        break;
                    }
                }


            }
        }catch(Exception ex){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }


    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

}


