package src.main.java;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class JsonParser {

    /**
     *
     * @param jsonString The JSON string to convert to POJO
     * @param targetClass The class to convert the JSON string into
     * @return T A new instance of type T with parameters from the string JSON.
     * @param <T> ANY POJO
     *  <p>
     *      * Examples
     *      * ------------
     *      * jsonString = { "name: "Tyrien"} | {"user_name": "Tyrien"}
     *      * targetClass = record Person(@JSONProperty(name = "user_name") String name){}
     *      * results = Both inputs return Person("Tyrien")
     *      * </p>
     */

    public static <T> T fromJSON(String jsonString, Class<T> targetClass) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        try{
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            return parseJSON(jsonObject, instance);
        }catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param instance the POJO to be converted to JSON string
     * @return JSON string representation of instance
     * @param <T> The generic type for POJO
     * <p>
     * Examples
     * ------------
     * record Person(@JSONProperty(name = "user_name") String name){}
     * instance = Person("Tyrien")
     * returns = {"user_name": "Tyrien"}
     * ------------
     * record Person(String name){}
     * instance = Person("Tyrien")
     * returns = {"name": "Tyrien"}
     * ------------
     * </p>
     */

    public static <T> String toJSON(T instance) throws JSONException {
        JSONObject json = new JSONObject();
        for(Field field: instance.getClass().getDeclaredFields()){
            var annotation = field.getAnnotation(JsonProperty.class);

            String name = field.getName();
            Object value = null;

            if(annotation != null){
                name = annotation.name();
            }

            if(name.isBlank()){
                name = field.getName();
            }


            field.setAccessible(true);
            try{
                value = field.get(instance);
            }catch(IllegalAccessException e){
                throw new RuntimeException(e);
            }

            json.put(name, value);

        }
        return json.toString();
    }

    public static <T> T parseJSON(JSONObject json, T instance) throws JSONException {
        for(Field field: instance.getClass().getDeclaredFields()){
            var annotation = field.getAnnotation(JsonProperty.class);

            String name = field.getName();
            Object value = null;

            if(annotation != null){
                name = annotation.name();
                if(json.has(name)){
                    value = json.get(name);
                }
            }

            if(name.isBlank() || value == null){
                name = field.getName();
                if(json.has(name)){
                    value = json.get(name);
                }
            }
            //If still empty after checks are made throw error
            if(name.isBlank() || value == null){
                throw new RuntimeException("Value for field " + field.getName() + " not found");
            }

            field.setAccessible(true);
            try{
                field.set(instance, value);//on this instance for this field set this value
            }catch(IllegalAccessException e){
                throw new RuntimeException(e);
            }

        }
        return instance;
    }
}
