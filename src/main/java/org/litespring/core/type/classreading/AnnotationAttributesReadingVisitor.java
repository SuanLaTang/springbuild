package org.litespring.core.type.classreading;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

/**
 *  实现对一个注解属性的解析
 */
final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();


    public AnnotationAttributesReadingVisitor(
            String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);

        this.annotationType = annotationType;
        this.attributesMap = attributesMap;

    }

    @Override
    public final void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    /**
     *
     * @param attributeName 传递的是注解上的属性名称 @Component(value="petStore") 中的 value
     * @param attributeValue 传递的是注解上的value值 @Component(value="petStore") 中的 petStore
     */
    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }


}