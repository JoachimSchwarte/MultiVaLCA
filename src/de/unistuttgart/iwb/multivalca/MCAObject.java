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
 * @version 0.558
 */

public class MCAObject {
	
	/**
	 * Data collection containing a tree that maps at the top level
	 * all existing subclasses to elements of a second level at which
	 * names are mapped to single members of the respective subclass
	 */
	private static HashMap<Class<? extends MCAObject>, LinkedHashMap<String, MCAObject>> instanceMap = new HashMap<>();
	
	/**
	 * the Name of a single member of the respective subclass
	 */
	private String name;
	
	/**
	 * during construction every new member is stored in instanceMap
	 * @param name
	 * the Name of the treated member of the respective subclass
	 */
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

	/**
	 * gets the name of a specific member
	 * @return
	 * the name of the member
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * overwrites the given object name
	 * @param name
	 * the name of the respective member
	 */
	public final void setName(String name) {
		this.name = name;			
	}
	
	/**
	 * clears a branch of the data collection instanceMap
	 * @param clazz
	 * the subclass to be cleared
	 */
	public static void clear(Class<?> clazz) {
		LinkedHashMap<String, MCAObject> c = instanceMap.get(clazz);
		if (c != null) 
			c.clear();
	}
	
	/**
	 * clears the entire data collection instanceMap
	 */
	public static void clear() {
		instanceMap.clear();
	}
	
	/**
	 * checks whether a name is already in use or not
	 * @param string
	 * the name to be checked
	 * @return
	 * true or false
	 */
	public static boolean containsName(String string) {
		Set<String> allkeys = new HashSet<>();	
		Collection<LinkedHashMap<String, MCAObject>> c = instanceMap.values();
		for(LinkedHashMap<String, MCAObject> e : c) {
			allkeys.addAll(e.keySet());
		}
		return allkeys.contains(string);
	}
	
	/**
	 * gets all existing names of objects of a subclass
	 * @param clazz
	 * the respective subclass
	 * @return
	 * the Set of all existing names
	 */
	public static Set<String> getAllNames(Class<?> clazz) {
		LinkedHashMap<String, MCAObject> c = instanceMap.get(clazz);
		return c != null ? c.keySet() : Collections.emptySet();	
	}
	
	/**
	 * returns a branch of the data collection instanceMap
	 * @param clazz
	 * the respective subclass
	 * @return
	 * a LinkedHashMap that contains all names of objects in the
	 * subclass and the mapped objects
	 */
	public static LinkedHashMap<String, MCAObject> getAllInstances(Class<?> clazz) {
		LinkedHashMap<String, MCAObject> c = instanceMap.get(clazz);
		return c != null ? c : new LinkedHashMap<>();	
	}
	
	/**
	 * returns the subclass that an object that is identified by a given
	 * name belongs to
	 * @param name
	 * the name of the respective object
	 * @return
	 * the subclass
	 */
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
