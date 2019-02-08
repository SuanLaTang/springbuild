package org.litespring.core.type.classreading;

import org.litespring.core.type.ClassMetadata;
import org.litespring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;


/**
 * ClassVisitor 是 ASM 的一个接口，定义在读取Class字节码时会触发的事件，如类头解析完成、注解解析、字段解析、方法解析等
 * 实现 visitMethod()/visitAnnotation() 等方法，用以定义对类结构（如方法、字段、注解）的访问方法
 */
//public class ClassMetadataReadingVisitor extends ClassVisitor{
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;


    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }


    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        //  name 是ASM框架告诉我们的
        //	org/litespring/service/v4/PetStoreService → org.litespring.service.v4.PetStoreService
        this.className = ClassUtils.convertResourcePathToClassName(name);
        //  判断是接口？抽象类？Final类型？
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        //	父类名称
        if (supername != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }


    public String getClassName() {
        return this.className;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isConcrete() {
        return !(this.isInterface || this.isAbstract);
    }

    public boolean isFinal() {
        return this.isFinal;
    }


    public boolean hasSuperClass() {
        return (this.superClassName != null);
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String[] getInterfaceNames() {
        return this.interfaces;
    }


}