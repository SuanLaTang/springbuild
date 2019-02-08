package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception {

        Resource r = new ClassPathResource("petstore-v1.xml");

        InputStream is = null;

        try {
            is = r.getInputStream();
            // 注意：这个测试其实并不充分！！
            // 真正充分的测试应该检查input流，把内容进行比对
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    /**
     * 真正路径，需要自行修改构造函数里的路径
     *
     * @throws Exception
     */
    @Test
    public void testFileSystemResource() throws Exception {

		/*Resource r = new FileSystemResource("C:\\Users\\suanlatang\\buildspring\\src\\test\\resources\\petstore-v1.xml");

		InputStream is = null;

		try {
			is = r.getInputStream();
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
*/
    }
}
