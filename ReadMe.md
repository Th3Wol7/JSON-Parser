# JSON Parser - Java

This project provides a simple and flexible way to convert JSON strings to POJOs (Plain Old Java Objects) and vice versa. It uses reflection to dynamically create objects and populate their fields from JSON, and to generate JSON from existing objects.

## Features

- **Flexible Mapping**: The parser can handle different naming conventions between the JSON and the Java object. This is done using the `@JsonProperty` annotation, which can be applied to fields in the target class to specify the corresponding property name in the JSON.

- **Easy to Use**: The parser provides two static methods, `fromJSON` and `toJSON`, for converting between JSON strings and Java objects. These methods handle all the details of the conversion process, providing a simple and straightforward interface for the user.

## Usage

### Converting JSON to POJO
To convert a JSON string to a POJO, use the fromJSON method provided by the JsonParser class.

```Java
String jsonString = "{\"user_name\" : \"TH3WOL7\"}";
User user = JsonParser.fromJSON(jsonString, User.class);
```

### Converting POJO to JSON
To convert a POJO to a JSON string, use the toJSON method provided by the JsonParser class.
```java
User user = new User("Tyrien");
String json = JsonParser.toJSON(user);
```

## Examples
### Customizing Field Names
You can customize field names in the JSON representation using the @JsonProperty annotation. For example:
```java
public static class User {
    @JsonProperty(name = "user_name")
    private String name;

    // Constructor and other fields omitted for brevity
}

```
### Example Usage
```java
public class Main{
    public static void main(String[] args) throws JSONException {
        fromJSONExample();
        toJSONExample();
    }
    
    private static void fromJSONExample() throws JSONException {
        String json = "{\"user_name\" : \"TH3WOL7\"}";
        User user = JsonParser.fromJSON(json, User.class);
        System.out.println(user.name);
    }
    
    private static void toJSONExample() throws JSONException {
        User user = new User("Tyrien");
        System.out.println(JsonParser.toJSON(user));
    }
}

```

In this example, the `User` class has a `name` field, which is mapped to the `user_name` property in the JSON. 
The `fromJSONExample` method shows how to convert a JSON string to a `User` object, and the `toJSONExample`
method shows how to convert a `User` object back to a JSON string.

## Requirements
This project requires Java 8 or later and org.json jar file to be added to your classpath.
This project was compiled and functional using Java 19.

## License
This project is licensed under the terms of the MIT license
