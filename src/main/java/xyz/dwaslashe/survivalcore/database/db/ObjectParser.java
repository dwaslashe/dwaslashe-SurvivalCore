package xyz.dwaslashe.survivalcore.database.db;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Optional;

public interface ObjectParser<T> extends Serializable {


    static <T> Optional<ObjectParser<T>> getParser(Object object){
        return Optional.ofNullable(ObjectParser.class.isAssignableFrom(object.getClass()) ? (ObjectParser<T>) object : null);
    }

    T getValue();
    static <T> T deserialize(String data, Class<T> value){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Object object = objectInputStream.readObject();
            if(!Serializable.class.isAssignableFrom(object.getClass()))
                throw new InvalidParameterException();
            byteArrayInputStream.close();
            objectInputStream.close();
            return (T) object;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    default String serialize(Object object){
        if(!Serializable.class.isAssignableFrom(object.getClass())) throw new InvalidParameterException();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(object);

            outputStream.close();
            objectOutputStream.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

}