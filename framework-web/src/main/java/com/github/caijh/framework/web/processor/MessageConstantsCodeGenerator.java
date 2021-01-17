package com.github.caijh.framework.web.processor;

import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class MessageConstantsCodeGenerator implements Opcodes {

    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    public byte[] genCode(String fullName, Set<String> propertyKeys) {
        cw.visit(V1_8, ACC_PUBLIC, fullName, null, "java/lang/Object", null);

        //生成默认的构造方法
        MethodVisitor mw = cw.visitMethod(ACC_PRIVATE,
                "<init>",
                "()V",
                null,
                null);

        //生成构造方法的字节码指令
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mw.visitInsn(RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();

        for (String propertyKey : propertyKeys) {
            cw.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, propertyKey, "Ljava/lang/String;", null, propertyKey)
              .visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

}
