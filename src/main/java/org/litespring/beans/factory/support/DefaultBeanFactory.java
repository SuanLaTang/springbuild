package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.factory.support.GenericBeanDefinition;
import org.litespring.util.ClassUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//为什么要放到support包下，因为我们希望factory放的是接口，别的程序员使用我们框架的API
////同时这也是Spring的命名规范
public class DefaultBeanFactory implements BeanFactory {

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    //我们采用dom4j进行解析
    //在testGetBean中可以看到，接收了一个xml的参数
    //我们就是要解析这个xml文件
    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 解析传进来的xml文件
     * @param configFile
     */
    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try{
            //通过classloader获取inputStream
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            //有inputStream后，可以调用dom4j相关的reader
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            //变成document后，可以遍历标签
            Element root = doc.getRootElement(); //root就是<beans>
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = (Element)iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                //把获取到的BeanDefinition保留下来，存到map里
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


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
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        return null;
    }

}
