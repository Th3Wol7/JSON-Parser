package src.main.java;

import org.json.JSONException;

public class Domain {
    public static class User{
        @JsonProperty(name = "user_name")
        private String name;

        User(String name){
            this.name = name;
        }
        private User() {}

    }

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
