package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.support.ClassPathXmlApplicationContext;
import org.litespring.beans.factory.support.context.ApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        //ClassPathXmlApplicationContext 意思是从某个ClassPath下去搜寻xml文件形成ApplicationContext
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }



}
