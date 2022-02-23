package xyz.dwaslashe.survivalcore.helpers;

public interface BiReplaceHelper<K, V> {

    Object accept(K key, V value);

}
