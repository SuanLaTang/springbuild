package org.litespring.test.v1;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
//一健测试
@Suite.SuiteClasses({ApplicationContextTest.class, BeanFactoryTest.class, ResourceTest.class})
public class V1AllTests {


}
