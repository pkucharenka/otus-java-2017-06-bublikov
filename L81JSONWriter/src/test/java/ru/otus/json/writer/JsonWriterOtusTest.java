package ru.otus.json.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import ru.otus.bvd.jsonwriter.JsonWriterOtus;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicle;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicleUtils;

public class JsonWriterOtusTest {
	@Test
	public void toJsonTest() {
		//сериализуем нашей библиотекой
		AeroVehicle copter = AeroVehicleUtils.createCopter();
		JsonWriterOtus otusWriter = new JsonWriterOtus();
		String outputJWO = otusWriter.toJson(copter);
		System.out.println("output JsonWriterOtus = " + outputJWO );

		//десереализуем в новый объект с помощью gson
		Gson gson = new GsonBuilder().create();
		AeroVehicle copterUnserialize = gson.fromJson(outputJWO, AeroVehicle.class);

		//оба объекта сериализуем в строку с помощью gson и сравниваем их
		String outputGSON = gson.toJson(copter, AeroVehicle.class);
		String outputGSONUnserialize = gson.toJson(copterUnserialize, AeroVehicle.class);
		System.out.println("output GSON      =       " +  outputGSON  );
		System.out.println("output JWO-OBJECT-GSON = " +   outputGSONUnserialize  );

        assertEquals("JSON не совпадает с выводом GSON", outputGSON, outputGSONUnserialize);

	}
	
}
