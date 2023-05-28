package xyz.dwaslashe.survivalcore.database.db.api;


import xyz.dwaslashe.survivalcore.database.db.DatabaseConnector;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.DataObject;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.PrimaryKey;
import xyz.dwaslashe.survivalcore.database.db.api.stereotype.Value;
import xyz.dwaslashe.survivalcore.database.db.helpers.ListHelper;
import xyz.dwaslashe.survivalcore.database.helpers.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataObjectScanner<T> {

    private final Class<T> clazz;
    private final DatabaseConnector connector;

    private DataObject dataObject;

    private Constructor<?> constructor;

    public DataObjectScanner(Class<T> clazz, DatabaseConnector connector){
        this.clazz = clazz;
        this.connector = connector;
        dataObject = clazz.getDeclaredAnnotation(DataObject.class);
        if(dataObject == null) return;
        constructor = ReflectionHelper.getConstructor(clazz, ResultSet.class);
        if(constructor == null){
            System.out.println("Constructor: \"" + clazz.getName()+"(ResultSet.class)\" cannot be null");
            return;
        }
        createTable();
    }

    public Class<T> getClazz() {
        return clazz;
    }

    private void createTable() {
        PrimaryKey primaryKey = null;
        Map<String, String> objectMap = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            PrimaryKey key = declaredField.getDeclaredAnnotation(PrimaryKey.class);
            if(key != null) {
                primaryKey = key;
                objectMap.put(key.value().key(), key.value().type());
            } else {
                Value value = declaredField.getDeclaredAnnotation(Value.class);
                if (value != null) {
                    objectMap.put(value.key(), value.type());
                }
            }
        }
        Objects.requireNonNull(primaryKey, "Primary key cannot be null");
        connector.getGetter().createTable(dataObject.table(), "primary key(" + primaryKey.value().key() + ")", ListHelper.mapToList(objectMap, (s, s2) -> s + " " + s2));
    }

    public void update(T object){
        Objects.requireNonNull(dataObject.table(), "Primary key cannot be null");
        Map<String, Object> objectMap = new HashMap<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            PrimaryKey primaryKey = declaredField.getDeclaredAnnotation(PrimaryKey.class);
            Value value = declaredField.getDeclaredAnnotation(Value.class);
            if(primaryKey != null) value = primaryKey.value();
            if (value == null) continue;
            Object obj = null;
            try {
                obj = declaredField.get(object);
                if(obj == null) continue;
                ItemSerializer<Object> serializer = (ItemSerializer<Object>) connector.getSerializerMap().get(obj.getClass());
                if(serializer == null)
                    objectMap.put(value.key(), obj.toString());
                else objectMap.put(value.key(), serializer.deserialize(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        connector.getGetter().createOrUpdate(dataObject.table(), objectMap);
    }

    public void load(DatabaseCache<T> databaseCache){
        ResultSet resultSet = null;
        try {
            resultSet = connector.getConnection().prepareStatement("SELECT * FROM `" + dataObject.table() + "`").executeQuery();
            while (resultSet.next()){
                T t = (T) ReflectionHelper.newInstance(constructor, resultSet);
                databaseCache.add(t);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
