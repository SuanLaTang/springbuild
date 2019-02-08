package org.litespring.stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)//可以用在类、接口、枚举、注解
@Retention(RetentionPolicy.RUNTIME)//保留到运行时
@Documented
public @interface Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

}
