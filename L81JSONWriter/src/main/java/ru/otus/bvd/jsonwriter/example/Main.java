package ru.otus.bvd.jsonwriter.example;

import ru.otus.bvd.jsonwriter.JsonWriterOtus;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicle;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicleUtils;

/**
 * Created by vadim on 05.08.17.
 */
public class Main {
    public static void main(String[] args)
    {
    	AeroVehicle copter = AeroVehicleUtils.createCopter();
        JsonWriterOtus otusWriter = new JsonWriterOtus();
        String outputJWO = otusWriter.toJSON(copter);
        System.out.println( outputJWO );
    }
}
