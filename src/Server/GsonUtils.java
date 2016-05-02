package Server;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class GsonUtils {
    static Gson getGson(){
        Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new JsonDeserializer<Map<String, Object>>() {
            public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                HashMap<String, Object> map = new HashMap<String, Object>();
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    map.put(entry.getKey(), entry.getValue());
                }
                return map;
            }
        }).create();
        return gson;
    }
}