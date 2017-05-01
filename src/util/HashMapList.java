package util;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * A utility class that sets up a Key -> List of Values
 * structure. 
 */

public class HashMapList<K, V> {
	private HashMap<K, ArrayList<V>> hm;
	
	public HashMapList() {
		hm = new HashMap<K, ArrayList<V>>();
	}
	
	public void put(K key, V value) {
		if(hm.containsKey(key)) {
			hm.get(key).add(value);
		} else {
			ArrayList<V> list = new ArrayList<V>();
			list.add(value);
			hm.put(key, list);
		}
	}
	
	public boolean containsKey(K key) {
		return hm.containsKey(key);
	}
	
}
