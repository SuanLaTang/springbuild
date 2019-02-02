package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 构造器注入的难点：一个类有多个构造函数，如何判断使用哪个构造函数；XML文件中的值，如何判断是否符合构造器中的入参参数类型
 */
public class ConstructorResolver {

    protected final Log logger = LogFactory.getLog(getClass());


    private final ConfigurableBeanFactory beanFactory;


    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    public Object autowireConstructor(final BeanDefinition bd) {
        // constructorToUse 是想找到一个可用的构造器
        Constructor<?> constructorToUse = null;
        // 记录传递给构造器的参数
        Object[] argsToUse = null;

        Class<?> beanClass = null;
        try {
            //实际的Spring框架里，不会采用这种低效的加载方式
            //高效的读取方式：如果这个类已经加载过一次，会存到缓存中(加载是一个比较耗费资源的功能)
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());

        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getID(), "Instantiation of bean failed, can't resolve class", e);
        }

        //反射获取构造器
        Constructor<?>[] candidates = beanClass.getConstructors();


        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument cargs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        //对可供候选的构造器做一次循环
        for (int i = 0; i < candidates.length; i++) {

            //取出构造函数中的参数数量
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            //如果构造器中的参数数量和xml文件中的参数数量不相等，就跳过这个构造函数
            if (parameterTypes.length != cargs.getArgumentCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];

            //判断值是否和tpye相匹配
            boolean result = this.valuesMatchTypes(parameterTypes, cargs.getArgumentValues(), argsToUse, valueResolver, typeConverter);

            if (result) {
                constructorToUse = candidates[i];
                break;
            }

        }


        //循环走完，还找不到一个合适的构造函数
        if (constructorToUse == null) {
            throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
        }


        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getID(), "can't find a create instance using " + constructorToUse);
        }


    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter) {


        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder
                    = valueHolders.get(i);
            //获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try {
                //获得真正的值
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                //如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
                //如果转型失败，则抛出异常。说明这个构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                //转型成功，记录下来
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }


}