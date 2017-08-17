package ru.otus.bvd.jsonwriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by vadim on 06.08.17.
 * 
 */
public class JsonWriterOtus {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toJson(Object object)  {
    	if (isPrimitiveWrapperEnumString(object.getClass())) {
    		return "null";
    	}
    	
    	JSONObject jsonObject = objectToJsonObject(object);
    	return jsonObject.toJSONString();
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONObject objectToJsonObject(Object object) {
    	JSONObject jsonObject = new JSONObject();
		
    	Field[] fields = object.getClass().getDeclaredFields();
    	try {
    		for (Field f : fields) {
	        	if ( java.lang.reflect.Modifier.isStatic(f.getModifiers()) )
	        		continue;
    			f.setAccessible(true);
	        	Class<?> type = f.getType();
	        	if (isPrimitiveWrapperEnumString(type)) {
	        		jsonObject.put(f.getName(), f.get(object).toString());
	        	} else if (type.isArray()) {
	        		jsonObject.put(f.getName(), arrToJsonArray(f.get(object)));
	        	} else if ( Collection.class.isAssignableFrom(type) ) {
	        		Collection fieldCollection = (Collection) f.get(object);
	        		jsonObject.put( f.getName(), arrToJsonArray(fieldCollection.toArray()) );
	        	} else {
	        		jsonObject.put( f.getName(), objectToJsonObject(f.get(object)) ) ;
	        	}        	
	        }
    	} catch (Exception  e) {
			e.printStackTrace();
		}	
    	return jsonObject;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private JSONArray arrToJsonArray (Object array) {
		JSONArray jsonArray = new JSONArray();
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++) {
			Object arrayElement = Array.get(array, i);
			if (isPrimitiveWrapperEnumString(arrayElement.getClass())) {
				jsonArray.add(arrayElement);
			} else if (arrayElement.getClass().isArray()) {
				jsonArray.add( arrToJsonArray(arrayElement) );
			} else {
				jsonArray.add( objectToJsonObject(arrayElement) );
			}
		}
		return jsonArray;
	}
	
	
    private boolean isPrimitiveWrapperEnumString(Class<?> c) {
    	if (
    			c==Boolean.class ||
    			c==Character.class ||
    			c==Byte.class ||
    			c==Short.class ||
    			c==Integer.class ||
    			c==Float.class ||
    			c==Double.class ||
    			c==boolean.class ||
    			c==char.class ||
    			c== byte.class ||
    			c==short.class ||
    			c==int.class ||
    			c==float.class ||
    			c==double.class ||
    			c.isEnum() ||
    			c == String.class
    		) {
    		return true;
    	} else {
    		return false;
    	}
    }    
}

