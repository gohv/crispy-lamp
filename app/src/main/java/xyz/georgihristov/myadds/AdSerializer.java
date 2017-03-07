package xyz.georgihristov.myadds;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by gohv on 06.03.17.
 */

public class AdSerializer implements JsonSerializer<Ad> {

    @Override
    public JsonElement serialize(Ad src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("images", src.getProductPhoto());
        jsonObject.addProperty("title", src.getProductName());
        jsonObject.addProperty("category", src.getCategory());
        jsonObject.addProperty("subcategory", src.getSubCategory());
        jsonObject.addProperty("description", src.getProductDescription());
        jsonObject.addProperty("price", src.getProductPrice());
        jsonObject.addProperty("phone", src.getContactNumber());
        return jsonObject;
    }
}
