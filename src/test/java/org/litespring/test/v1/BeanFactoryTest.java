package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    /**
     * 预先注册，每次测试的factory会重新初始化，保持测试用例的独立性
     */
    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertTrue(bd.isSingleton());

        Assert.assertFalse(bd.isPrototype());

        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        //获取后还需要判断是不是和xml中的class相等
        //assertEquals() 第一个参数是期待值，第二个是实际值
        Assert.assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        //测试实例能否从BeanFactory中取出
        //希望这个方法能返回 PetStoreService类的对象
        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        //判断是否为空
        Assert.assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");

        Assert.assertTrue(petStore.equals(petStore1));

    }

    /**
     * 测试 xml 文件中不包含对应BeanID时，抛出 BeanCreationException
     */
    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("expect BeanCreationException ");
    }

    /**
     * 测试文件名称不对时，捕获BeanDefinitionStoreException
     */
    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }

}
