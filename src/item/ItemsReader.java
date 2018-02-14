package item;

import entities.*;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * ComponentsReader reads in the Components data file and offers an interface to parse and
 * interact with the data.
 */
public class ItemsReader {
	private static HashMap<String, AbstractItem> items;
	
	public static void readItemData(String dir) {
		initializeReader();
		readAllItemFiles(dir);
	}
	
	public static void initializeReader() {
		items = new HashMap<String, AbstractItem>();
	}
	
	public static void readAllItemFiles(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(".item")) {
				readItemFile(file);
			}
		}
	}
	
	public static void readItemFile(File file) {
		
		try {			
			Gson gson = new Gson();
			AbstractItem item = gson.fromJson(new FileReader(file), AbstractItem.class);
			
			items.put(item.name, item);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static AbstractItem getAbstractItem(String name) {
		return items.get(name);
	}
	
	// I want a list containing the abstract components available.
	public static Set<String> getAbstractComponentNames() {
		return items.keySet();
	}
	
	// Creates an instance of a component.
	public static Item makeComponent(String name, int quantity) {
		Item comp = items.get(name).makeItem(quantity);
		return comp;
	}
	
	// Dumps the structure of ItemsReader.
	public static String debugDump() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Beginning debugDump for ItemsReader..." + "\n\n");
		
		for(String itemName : items.keySet()) {
			sb.append(itemName + "\n" + "_______________________" + "\n");
			sb.append(items.get(itemName).toString());
		}
		
		return sb.toString();
	}
}