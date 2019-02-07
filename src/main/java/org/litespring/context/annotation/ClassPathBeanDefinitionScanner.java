package org.litespring.context.annotation;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.util.StringUtils;

/**
 * 但是，有了 ScannedGenericBeanDefinition 后，我们还需要一个类
 * 对给定的package的名称列表进行扫描(scan)，找到标记为 @Component 的类，创建 ScannedGenericBeanDefinition，并且注册到 BeanFactory 中。
 * 如果没有这个类的话，我们xml解析的时候会很复杂
 */
public class ClassPathBeanDefinitionScanner {

	private final BeanDefinitionRegistry registry;
	
	private PackageResourceLoader resourceLoader = new PackageResourceLoader();
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {		
		this.registry = registry;		
	}
	
	public Set<BeanDefinition> doScan(String packagesToScan) {
		//逗号分隔字符串 "org.litespring.service.v4,org.litespring.dao.v4"
		String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan,",");
		
		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				beanDefinitions.add(candidate);
				registry.registerBeanDefinition(candidate.getID(),candidate);
				
			}
		}
		return beanDefinitions;
	}


	/**
	 * 找到可成为候选的 Components
	 * @param basePackage
	 * @return
	 */
	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		try {
			
			Resource[] resources = this.resourceLoader.getResources(basePackage);
			
			for (Resource resource : resources) {
				try {
					
					MetadataReader metadataReader = new SimpleMetadataReader(resource);
					//如果获取的注解是@Component
					if(metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
						ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
						String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
						sbd.setId(beanName);
						candidates.add(sbd);					
					}
				}
				catch (Throwable ex) {
					throw new BeanDefinitionStoreException(
							"Failed to read candidate component class: " + resource, ex);
				}
				
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}
	
}