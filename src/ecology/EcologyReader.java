package ecology;

import com.google.gson.Gson;

import ecology.WildSpecies;

import java.util.*;
import java.io.*;

public class EcologyReader {

	public static HashMap<String, WildSpecies> wildInfo;
	
	public static void readEcologyData(String dirPath) {
		initializeReader();
		readAllEcologyFiles(dirPath);
	}
	
	public static void initializeReader() {
		wildInfo = new HashMap<String, WildSpecies>();
	}
	
	public static void readAllEcologyFiles(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(".wild")) {
				readEcologyFile(file);
			}
		}
	}
	
	public static void readEcologyFile(File ecologyFile) {
		
		try {
			Gson gson = new Gson();
			
			WildSpecies wildspec = gson.fromJson(new FileReader(ecologyFile), WildSpecies.class);
			wildspec.processJSON();
			wildInfo.put(wildspec.name, wildspec);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static WildSpecies getWildSpecies(String name) {
		return wildInfo.get(name);
	}
	
	public static ArrayList<String> getAllSpeciesNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		for(String name : wildInfo.keySet())
			names.add(name);
		
		return names;
	}
	
	// Dumps the structure of SpeciesReader.
	public static String debugDump() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Beginning debugDump for EcologyReader..." + "\n\n");
		
		for(String ecologyObjectName : wildInfo.keySet()) {
			sb.append(ecologyObjectName + "\n" + "_______________________" + "\n");
			sb.append(wildInfo.get(ecologyObjectName).toString());
		}
		
		return sb.toString();
	}

}
