package ru.otus.bvd.jsonwriter.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicle;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicleUtils;

/**
 * Created by vadim on 05.08.17.
 */
public class Main {
    public static void main(String[] args)
    {
        AeroVehicle plane = AeroVehicleUtils.createPlane();
        AeroVehicle copter = AeroVehicleUtils.createCopter();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String planejson = gson.toJson(plane);
        String copterjson = gson.toJson(copter);

        System.out.println(planejson);
    }
}
