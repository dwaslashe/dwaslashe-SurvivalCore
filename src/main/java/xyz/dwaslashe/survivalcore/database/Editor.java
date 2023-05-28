package xyz.dwaslashe.survivalcore.database;

import java.util.function.Consumer;

public interface Editor<V> {

    static <V> Editor<V> start(V value){
        return () -> value;
    }

    V getValue();

    default Editor<V> edit(Consumer<V> consumer){
        consumer.accept(getValue());
        return this;
    }

}
