package org.litespring.beans.factory.config;

import org.litespring.util.Assert;

import java.lang.reflect.Field;

/**
 * 依赖描述符
 */
public class DependencyDescriptor {
    private Field field;
    private boolean required;

    /**
     *
     * @param field 字段名
     * @param required 是否为required
     */
    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;

    }

    /**
     * 如果字段不为空
     * @return 类型，如test包里的 AccountDao 和 ItemDao ，但目前只支持 AccountDao 和 ItemDao ，其他类型则抛出异常，后续等扩展
     */
    public Class<?> getDependencyType() {
        if (this.field != null) {
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    /**
     * getter方法
     * @return
     */
    public boolean isRequired() {
        return this.required;
    }
}