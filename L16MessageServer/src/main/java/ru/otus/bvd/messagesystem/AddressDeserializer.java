package ru.otus.bvd.messagesystem;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import ru.otus.bvd.example.MainMessageSystem;

public class AddressDeserializer implements JsonDeserializer<Address> {

    @Override
    public Address deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String id = jObject.get("id").getAsString() + "Address";                
        return (Address) MainMessageSystem.appContext.getBean(id);
    }

}
