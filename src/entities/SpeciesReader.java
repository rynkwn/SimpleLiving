package entities;

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
		Scanner scan;
		
		try {
			scan = new Scanner(speciesFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class Species {
	String name;
	Range numberOffspringProduced;
	
}