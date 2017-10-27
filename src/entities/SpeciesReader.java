package entities;

import com.google.gson.Gson;

import ecology.WildSpecies;
import util.KeyValueReader;
import util.ChanceOutcomes;
import util.Range;

import java.util.*;
import java.io.*;

/*
 * Reads in the characteristics and special abilities of
 * species from the species data file.
 */

public class SpeciesReader {
	public static HashMap<String, Species> speciesInfo;
	
	public static void readSpeciesData(String dirPath) {
		initializeReader();
		readAllSpeciesFiles(dirPath);
	}
	
	public static void initializeReader() {
		speciesInfo = new HashMap<String, Species>();
	}
	
	public static void readAllSpeciesFiles(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(".species")) {
				readSpeciesFile(file);
			}
		}
	}
	
	public static void readSpeciesFile(File speciesFile) {
		
		try {			
			Gson gson = new Gson();
			Species species = gson.fromJson(new FileReader(speciesFile), Species.class);
			
			species.processJSON();
			
			speciesInfo.put(species.name, species);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Species getSpecies(String name) {
		return speciesInfo.get(name);
	}
	
	// Dumps the structure of SpeciesReader.
	public static String debugDump() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Beginning debugDump for SpeciesReader..." + "\n\n");
		
		for(String speciesName : speciesInfo.keySet()) {
			sb.append(speciesName + "\n" + "_______________________" + "\n");
			sb.append(speciesInfo.get(speciesName).toString());
		}
		
		return sb.toString();
	}
}