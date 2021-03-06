package org.litespring.test.v1;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;
import org.junit.Assert;

public class BeanFactoryTest {

    @Test
    public void testGetBean() {

        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");

        Assert.assertNotNull(petStore);
    }

}
