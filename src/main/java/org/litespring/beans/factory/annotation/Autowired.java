package org.litespring.beans.factory.annotation;

import java.lang.annotation.*;

//可以用在构造器、字段、方法、注解
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Documented 说明该注解将被包含在javadoc中，可被文档化
@Documented
public @interface Autowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
