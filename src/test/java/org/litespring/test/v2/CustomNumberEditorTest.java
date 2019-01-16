package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

/**
 * 正常的实际开发中，测试用例会包含三种情况：正常情况，编辑条件，异常情况
 */
public class CustomNumberEditorTest {

	@Test
	public void testConvertString() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class,true);
		
		editor.setAsText("3");
		Object value = editor.getValue();
		Assert.assertTrue(value instanceof Integer);		
		Assert.assertEquals(3, ((Integer)editor.getValue()).intValue());
		
		
		editor.setAsText("");
		Assert.assertTrue(editor.getValue() == null);
		
		
		try{
			//转换时期待抛出异常
			editor.setAsText("3.1");
		}catch(IllegalArgumentException e){
			return ;
		}
		Assert.fail();		
		
	}

}