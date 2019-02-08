package org.litespring.beans.factory.config;

/**
 * 用以表达 <property name="age" ="3"/> 这种值
 */
public class TypedStringValue {
    private String value;

    public TypedStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}