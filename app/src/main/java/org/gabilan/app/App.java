/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.gabilan.app;

import static org.gabilan.app.MethodCallReplacer.replaceMethodCall;

import org.gabilan.samples.Foo;

import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        Foo.testMethod();

        // Replace method call Foo.print(i) with Bar.print(i)
        byte[] code = replaceMethodCall(Foo.class.getMethod("testMethod"));
        new ClassLoader() {
            Class<?> get() {
                return defineClass(null, code, 0, code.length);
            }
        }.get().getMethod("testMethod").invoke(null);
    }
}