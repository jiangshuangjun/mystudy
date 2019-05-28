package study.jdk8.lambda;

@FunctionalInterface
public interface LongFunc<T, R> {

    R getValue(T t1, T t2);

}
