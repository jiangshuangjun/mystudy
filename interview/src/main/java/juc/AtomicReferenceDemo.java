package juc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@ToString
@AllArgsConstructor
class User {
    String userName;
    int age;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        atomicReference.set(z3);

        // 第一次输出：变更是否成功：true，atomicReference 是: User(userName=li4, age=25)
        System.out.printf("变更是否成功：%s，atomicReference 是: %s\n", atomicReference.compareAndSet(z3, li4), atomicReference.get().toString());
        // 第二次输出：变更是否成功：false，atomicReference 是: User(userName=li4, age=25)
        System.out.printf("变更是否成功：%s，atomicReference 是: %s\n", atomicReference.compareAndSet(z3, li4), atomicReference.get().toString());
    }
}
