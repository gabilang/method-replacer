package org.gabilan.app;

import org.gabilan.replacer.MethodReplaceVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MethodCallReplacer {

    static byte[] replaceMethodCall(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        ClassReader cr;
        try { // use resource lookup to get the class bytes
            cr = new ClassReader(Objects.requireNonNull(clazz.getResourceAsStream(clazz.getSimpleName() + ".class")));
        } catch(IOException ex) {
            throw new IllegalStateException(ex);
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor cv = new MethodReplaceVisitor(Opcodes.ASM9, cw,
                "org/gabilan/samples/Foo", "print", "(I)V",
                Opcodes.INVOKESTATIC, "org/gabilan/samples/Bar", "print", "(I)V");

        // int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
        cr.accept(cv, 0);
        return cw.toByteArray();
    }


}
