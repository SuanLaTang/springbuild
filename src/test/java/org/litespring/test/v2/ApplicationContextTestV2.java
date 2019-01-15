package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;

/**
 * 针对接口写 "high level" 的测试用例，暂时不用pass，可以保持fail状态
 * 对一些子任务写测试用例：BeanDefinitionTestV2 and BeanDefinitionValueResolverTest
 * 把子任务生成的代码，类组合起来，让 high level 的测试用例pass
 */
public class ApplicationContextTestV2 {

	@Test
	public void testGetBeanProperty() {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());

		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		assertTrue(petStore.getItemDao() instanceof ItemDao);

		assertEquals("liuxin",petStore.getOwner());
	}

}