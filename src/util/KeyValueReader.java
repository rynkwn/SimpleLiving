package util;

import java.util.*;

/*
 * A HashMap with additional helper functions, to be used in the various readers. 
 */
public class KeyValueReader extends HashMap<String, ArrayList<List<String>>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6516344122747106163L;

	public KeyValueReader() {
		super();
	}
	
	/*
	 * Reads a line of data, semi-colons split up elements in a list.
	 * If elements are separated by a comma, then they're added together in a sublist.
	 * 
	 * LIST: a; b; c;
	 * -> [[a], [b], [c]]
	 * SUBLIST: a, b; 1, 2
	 * -> [[a, b], [1, 2]]
	 */
	public void readLine(String line) {
		// Remove all whitespace.
		String[] property = line.replaceAll("\\s+", "").split(":");
		
		String key = property[0];
		
		String[] valueList = property[1].split(";");
		
		ArrayList<List<String>> values = new ArrayList<List<String>>();
		
		for(String sublist : valueList) {
			String[] groupedProperties = sublist.split(",");
			values.add(Arrays.asList(groupedProperties));
		}
		
		this.put(key, values);
	}
	
	// Assumes that a single value lives at the key address.
	public String getSingle(String key) {
		return this.get(key).get(0).get(0);
	}
	
	public double getDouble(String key) {
		return Double.parseDouble(this.getSingle(key));
	}
	
	public int getInt(String key) {
		return Integer.parseInt(this.getSingle(key));
	}
	
	public long getLong(String key) {
		return Long.parseLong(this.getSingle(key));
	}
	
	public List<String> getList(String key) {
		try {
			return get(key).get(0);
		} catch(NullPointerException e) {
			return new ArrayList<String>(); 
		}
	}
}
