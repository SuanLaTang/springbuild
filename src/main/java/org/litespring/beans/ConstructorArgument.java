package org.litespring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 和Setter注入类似
 * 设计一个数据结构 ConstuctorArgument( setter 使用 PropertyValue )
 * 解析XML，填充这个数据结构
 * 利用这个数据结构做事情
 */
public class ConstructorArgument {

	
	private final List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();


	/**
	 * Create a new empty ConstructorArgumentValues object.
	 */
	public ConstructorArgument() {
	}


	

	public void addArgumentValue(ValueHolder valueHolder) {
		this.argumentValues.add(valueHolder);
	}
	
	public List<ValueHolder> getArgumentValues() {
		return Collections.unmodifiableList(this.argumentValues);
	}


	public int getArgumentCount() {
		return this.argumentValues.size();
	}

	public boolean isEmpty() {
		return this.argumentValues.isEmpty();
	}

	/**
	 * Clear this holder, removing all argument values.
	 */
	public void clear() {
		this.argumentValues.clear();
	}





	/**
	 * ValueHolder 是一个静态内部类
	 * ValueHolder 用于描述构造函数的参数，ValueHolder 与 ConstructorArgument 是高内聚的关系
	 * 但在这个demo里，type 和 name 其实没实际作用，因为只实现 value 方面的构造器注入
	 */
	public static class ValueHolder{

		private Object value;

		private String type;

		private String name;

		public ValueHolder(Object value) {
			this.value = value;
		}

		public ValueHolder(Object value, String type) {
			this.value = value;
			this.type = type;
		}

		
		public ValueHolder(Object value, String type, String name) {
			this.value = value;
			this.type = type;
			this.name = name;
		}

		
		public void setValue(Object value) {
			this.value = value;
		}

		
		public Object getValue() {
			return this.value;
		}

		
		public void setType(String type) {
			this.type = type;
		}

		
		public String getType() {
			return this.type;
		}

		public void setName(String name) {
			this.name = name;
		}

	
		public String getName() {
			return this.name;
		}
	}

}