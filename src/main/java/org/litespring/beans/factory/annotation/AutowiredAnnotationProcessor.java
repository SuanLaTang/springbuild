package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;


/**
 * 把一个Class变成 InjectionMetadata
 *
 * @author 酸辣辣辣汤
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;
    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<Class<? extends Annotation>>();

    public AutowiredAnnotationProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    /**
     * 把一个Class传进来，就能形成 InjectionMetadata
     *
     * @param clazz
     * @return
     */
    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {

        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
        Class<?> targetClass = clazz;

        do {
            LinkedList<InjectionElement> currElements = new LinkedList<InjectionElement>();
            //遍历Class的字段名
            for (Field field : targetClass.getDeclaredFields()) {
                Annotation ann = findAutowiredAnnotation(field);

                if (ann != null) {
                    //如果是静态，就跳过
                    if (Modifier.isStatic(field.getModifiers())) {

                        continue;
                    }
                    //不是静态，则判断是否required
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }
            for (Method method : targetClass.getDeclaredMethods()) {
                //TODO 处理方法注入
            }
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    /**
     * 判断是否 required
     *
     * @param ann
     * @return
     */
    protected boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        } catch (Exception ex) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 在实例化之前
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        //do nothing
        return bean;
    }

    /**
     * 在实例化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        // do nothing
        return bean;
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        // do nothing
        return true;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            //把bean中的所有东西都注入进去
            metadata.inject(bean);
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }
}