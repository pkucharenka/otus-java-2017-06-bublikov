package ru.otus.bvd.jsonwriter;

import ru.otus.bvd.jsonwriter.example.entity.AeroVehicle;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicleUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by vadim on 06.08.17.
 */
public class JsonWriter {
    public String toJSON(Object object) {
        String res = "";

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            navigateField(f,"root");
        }


        return res;
    }

    private void navigateField(Field field, String key) {
        // System.out.println(key + ": " + aware);
        System.out.println( field.getType().getSimpleName() );
//        String awareClassName = aware.getClass().getSimpleName();
//        switch (awareClassName) {
//            case "JSONArray":
//                JSONArray array = (JSONArray) aware;
//                array.forEach(element -> navigateTree(element, "array_element"));
//                break;
//            case "JSONObject":
//                JSONObject jsonObject = (JSONObject) aware;
//                jsonObject.entrySet().forEach(element -> navigateTree(element, "set_element"));
//                break;
//            case "Node":
//                Map.Entry entry = (Map.Entry) aware;
//                navigateTree(entry.getValue(), entry.getKey().toString());
//                break;
//            default:
//                System.out.println(key + ": " + aware);
//
//        }
    }

    public static void main(String[] args) {
        AeroVehicle copter = AeroVehicleUtils.createCopter();
        JsonWriter jw = new JsonWriter();
        jw.toJSON(copter);
    }
}
