package ru.otus.bvd.jsonwriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.otus.bvd.jsonwriter.example.entity.AeroVehicle;
import ru.otus.bvd.jsonwriter.example.entity.AeroVehicleUtils;

/**
 * Created by vadim on 06.08.17.
 */
public class JsonWriterOtus {
    public String toJSON(Object object)  {
    	JSONObject jsonObject = new JSONObject();
    	Map<Object, String> arrNodes = new HashMap<>();
    	
    	Field[] fields = object.getClass().getDeclaredFields();
    	try {
    		for (Field f : fields) {
	        	f.setAccessible(true);
	        	Class<?> type = f.getType();
	        	if (type.isArray()) {
	        		Object arrayField = f.get(object);
	        		jsonObject.put(arrayField , null );
	        		arrNodes.put(arrayField, f.getName());
	        	} else if (type.isEnum()) {
	        		jsonObject.put( f.getName(), f.get(object).toString() ) ;        		
	        	} else if ( Collection.class.isAssignableFrom(type) ) {
	        		Collection collection = (Collection) f.get(object);
	        		Object arrayField = collection.toArray(); 
	
	        		jsonObject.put(arrayField , null );
	        		arrNodes.put(arrayField, f.getName());
	        	} else {
	        		jsonObject.put( f.getName(), f.get(object) ) ;
	        	}        	
	        }
    	} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
        String str = jsonObject.toJSONString();
        for (Map.Entry<Object,String> entry : arrNodes.entrySet()) {
    		StringBuilder sb = new StringBuilder();
    		navigateArr(entry.getKey(), sb);
    		str = str.replace('"'+entry.getKey().toString()+'"'+":null", '"'+entry.getValue().toString()+'"'+':'+sb.toString());
        }
        return str;
    }
    

    private void navigateArr(Object arrayField, StringBuilder sb) {    	
    	int length = Array.getLength(arrayField);
	    if (length==0)
	    	return;
	    sb.append('[');
	    for (int i = 0; i < length; i ++) {
	        Object arrayElement = Array.get(arrayField, i);
        	if (Collection.class.isInstance(arrayElement))
        		arrayElement = ((Collection) arrayElement).toArray();

	        if (arrayElement.getClass().isArray()) { 
	        	navigateArr(arrayElement,  sb);
	        } else {	        	
	        	if (Number.class.isInstance(arrayElement)) {
		        	sb.append( arrayElement.toString() );	        		
	        	} else if (String.class.isInstance(arrayElement)) {
		        	sb.append('"').append( arrayElement.toString() ).append('"');	        		
	        	} else {
	        		JsonWriterOtus otusWriter = new JsonWriterOtus();
	                String outputJWO = otusWriter.toJSON(arrayElement);
	        		sb.append(outputJWO);
	        	}
	        }
	        sb.append(',');
	    }
	    sb.setLength( sb.length()-1 );
	    sb.append(']');
    }

    public static void main(String[] args) {
    	AeroVehicle copter = AeroVehicleUtils.createCopter();
        JsonWriterOtus otusWriter = new JsonWriterOtus();
        String outputJWO = otusWriter.toJSON(copter);
        System.out.println( outputJWO );
        
        Gson gson = new GsonBuilder().create();
        AeroVehicle copterUnserialize = gson.fromJson(outputJWO, AeroVehicle.class);
        
        
        String outputGSON = gson.toJson(copter, AeroVehicle.class);
        String outputGSONUnserialize = gson.toJson(copterUnserialize, AeroVehicle.class);
        System.out.println( outputGSON  );
        System.out.println( outputGSONUnserialize  );
    }
}
