package org.gabilan.replacer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_NATIVE;

public class MethodReplaceVisitor extends ClassVisitor {

    private final String currentOwner;
    private final String currentMethodName;
    private final String currentMethodDesc;
    private final int newOpcode;
    private final String newOwner;
    private final String newMethodName;
    private final String newMethodDesc;

    public MethodReplaceVisitor(int api, ClassVisitor classVisitor,
                                String currentOwner, String currentMethodName, String currentMethodDesc,
                                int newOpcode, String newOwner, String newMethodName, String newMethodDesc) {
        super(api, classVisitor);
        this.currentOwner = currentOwner;
        this.currentMethodName = currentMethodName;
        this.currentMethodDesc = currentMethodDesc;
        this.newOpcode = newOpcode;
        this.newOwner = newOwner;
        this.newMethodName = newMethodName;
        this.newMethodDesc = newMethodDesc;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        // ignore instance and class initialization methods
        if (mv != null && !"<init>".equals(name) && !"<clinit>".equals(name)) {
            boolean isAbstractMethod = (access & ACC_ABSTRACT) != 0;
            boolean isNativeMethod = (access & ACC_NATIVE) != 0;
            // ignore abstract and native methods
            if (!isAbstractMethod && !isNativeMethod) {
                mv = new MethodReplaceAdapter(api, mv);
            }
        }
        return mv;
    }


    private class MethodReplaceAdapter extends MethodVisitor {

        public MethodReplaceAdapter(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (currentOwner.equals(owner) && currentMethodName.equals(name) &&
                    currentMethodDesc.equals(descriptor)) {
                super.visitMethodInsn(newOpcode, newOwner, newMethodName, newMethodDesc, false);
            }
            else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }
    }
}
