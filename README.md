# method-replacer
This program contains a basic logic to replace java method calls at runtime using ASM bytecode manipulation library.

## Samples
In the following example, the method call `foo` inside the method `testMethod` can be replaced by the method `bar` dynamically at runtime.

```java
public static void testMethod() {
    foo(5);
}
```

```java
public static void foo(int i) {
    System.out.println(i);
}

public static void bar(int i) {
    System.out.print("Input value: ");
    System.out.println(i);
}
```
