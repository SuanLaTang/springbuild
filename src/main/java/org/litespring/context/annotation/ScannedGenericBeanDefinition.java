package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

/**
 * 为了不污染 GenericBeanDefinition
 * 我们新建了 AnnotatedBeanDefinition(继承 BeanDefinition 接口)，内有 getMetadata()
 * 同时新建了 ScannedGenericBeanDefinition 继承 GenericBeanDefinition 和实现 AnnotatedBeanDefinition
 *
 * ScannedGenericBeanDefinition 是为了表达从 Class 文件中解析出来的 BeanDefinition
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

	private final AnnotationMetadata metadata;


	public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
		super();
		
		this.metadata = metadata;
		
		setBeanClassName(this.metadata.getClassName());
	}


	@Override
	public final AnnotationMetadata getMetadata() {
		return this.metadata;
	}

}