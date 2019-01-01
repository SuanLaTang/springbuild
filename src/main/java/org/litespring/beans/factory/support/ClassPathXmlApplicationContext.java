package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.context.ApplicationContext;
import org.litespring.beans.factory.support.factory.xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements ApplicationContext{

    private DefaultBeanFactory factory = null;

    public ClassPathXmlApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(configFile);
    }

    public BeanDefinition getBeanDefinition(String beanID) {
        return null;
    }

    @Override
    public Object getBean(String beanID) {

        return factory.getBean(beanID);
    }

}
