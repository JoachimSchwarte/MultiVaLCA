/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.533
 */

public class MCAObject {
	private static HashMap<Class<? extends MCAObject>, LinkedHashMap<String, MCAObject>> instanceMap = new HashMap<>();
	
	private String name;
	
	protected MCAObject(String name) {
		this.name = name;		
		Class<? extends MCAObject> clazz = this.getClass ();
		LinkedHashMap<String,MCAObject> result = instanceMap.get(clazz);
		if (result == null) {
			result = new LinkedHashMap<> ();		
			instanceMap.put(clazz, result);
		} 
		result.put(name, this);
	}

	public final String getName() {
		return name;
	}
		
	public final void setName(String name) {
		this.name = name;			
	}
	
	public static void clear(Class<?> clazz) {
		LinkedHashMap<String, MCAObject> c = instanceMap.get(clazz);
		if (c != null) 
			c.clear();
	}
	
	public static boolean containsName(String string) {
		Set<String> allkeys = new HashSet<>();	
		Collection<LinkedHashMap<String, MCAObject>> c = instanceMap.values();
		for(LinkedHashMap<String, MCAObject> e : c) {
			allkeys.addAll(e.keySet());
		}
		return allkeys.contains(string);
	}
	
	public static Set<String> getAllNames(Class<?> clazz) {
		LinkedHashMap<String, MCAObject> c = instanceMap.get(clazz);
		return c != null ? c.keySet() : Collections.emptySet();	
	}
	
	public static LinkedHashMap<String, MCAObject> getAllInstances(Class<?> clazz) {
		LinkedHashMap<String, MCAObject> c = instanceMap.get(clazz);
		return c != null ? c : new LinkedHashMap<>();	
	}
	
	public static Class<?> getClass(String name) {
		Class<? extends MCAObject> rClazz = null;
		for(Class<? extends MCAObject> clazz : instanceMap.keySet()) {
			if(instanceMap.get(clazz).containsKey(name)) {
				rClazz = clazz;						
			}
		}
		return rClazz;
	}

}
