package ru.otus.bvd.jsonwriter.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        String outputJWO = otusWriter.toJson(copter);
        System.out.println( outputJWO );
        
		Gson gson = new GsonBuilder().create();
		String outputGSON = gson.toJson(copter, AeroVehicle.class);
		System.out.println(outputGSON);        
    }
}
