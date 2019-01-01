package org.litespring.test.v1;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;
import org.junit.Assert;

public class BeanFactoryTest {

    @Test
    public void testGetBean() {

        //BeanFactory是一个接口
        //DefaultBeanFactory是一个实现类
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        //BeanDefinition是bean的定义，通过BeanFactory从petstore-v1.xml中获取
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        //获取后还需要判断是不是和xml中的class相等
        //assertEquals() 第一个参数是期待值，第二个是实际值
        Assert.assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());

        //测试实例能否从BeanFactory中取出
        //希望这个方法能返回 PetStoreService类的对象
        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        //判断是否为空
        Assert.assertNotNull(petStore);

    }

    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try{
            factory.getBean("invalidBean");
        }catch(BeanCreationException e){
            return;
        }
        Assert.fail("expect BeanCreationException ");
    }

    @Test
    public void testInvalidXML(){
        try{
            new DefaultBeanFactory("xxxx.xml");
        }catch(BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }

}
