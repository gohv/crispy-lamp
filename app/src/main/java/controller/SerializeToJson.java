package controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmObject;
import model.Ad;

/**
 * Created by gohv on 06.03.17.
 */

public class SerializeToJson {

    public static String serialize(Realm realm) throws ClassNotFoundException {

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(Class.forName("io.realm.AdRealmProxy"), new AdSerializer())
            .create();
        String json = gson.toJson(realm.where(Ad.class).findFirst());
        return json;
}
    // Serialize a Realm object to a JSON string


    public SerializeToJson() throws ClassNotFoundException {
    }
}
