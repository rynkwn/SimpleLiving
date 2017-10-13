package entities;

import com.google.gson.Gson;

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
		
		KeyValueReader reader = new KeyValueReader();
		Scanner scan;
		
		try {
			scan = new Scanner(speciesFile);
			
			while(scan.hasNextLine()) {
				reader.readLine(scan.nextLine());
			}
			
			String name = reader.getSingle("NAME");
			int gestationPeriod = reader.getInt("GESTATION_PERIOD");
			int minOffspring = reader.getInt("MIN_OFFSPRING");
			int maxOffspring = reader.getInt("MAX_OFFSPRING");
			Range numberOffspring = new Range(minOffspring, maxOffspring);
			long initialSize = reader.getLong("INITIAL_SIZE");
			long finalSize = reader.getLong("FINAL_SIZE");
			int timeTillMaturation = reader.getInt("TIME_TILL_MATURATION");
			
			// Setting up offspring probabilities.
			TreeMap<Double, String> offspringProbabilities = new TreeMap<Double, String>();
			for(String offspring : reader.getList("OFFSPRING")) {
				int indexOfSpace = offspring.indexOf('=');
				String speciesName = offspring.substring(0, indexOfSpace);
				String probability = offspring.substring(indexOfSpace + 1);
				
				offspringProbabilities.put(Double.parseDouble(probability), speciesName);
			}
			
			ChanceOutcomes<String> offspring = new ChanceOutcomes<String>(offspringProbabilities);
			
			String behaviorFile = reader.getSingle("BEHAVIOR");
			
			// Building up the body.
			Body initialBodyStructure = new Body(reader.getSingle("NUTRITION_TYPE"), 
												 reader.getDouble("METABOLISM"));
			for(List<String> bodySection : reader.get("BODY")) {
				BodyPart bp = new BodyPart(bodySection.get(0));
				
				for(int i = 1; i < bodySection.size(); i++) {
					bp.addOrgan(new BodyPart(bodySection.get(i)));
				}
				
				initialBodyStructure.addPart(bp);
			}
			
			// Now get biological products.
			HashMap<String, BiologicalProduct> products = new HashMap<String, BiologicalProduct>();
			for(String productString : reader.getList("PRODUCTS")) {
				String[] product = productString.trim().split("=");
				
				BiologicalProduct bioprod = new BiologicalProduct(product[0], 
																  Double.parseDouble(product[1]),
																  Integer.parseInt(product[2]));
				products.put(product[0], bioprod);
			}
			
			// Now get tags
			HashSet<String> tags = new HashSet<String>();
			for(String tag : reader.getList("TAGS")) {
				tags.add(tag);
			}
			
			// Now make the Species object
			Species species = new Species(name,
										  gestationPeriod,
										  numberOffspring,
										  initialSize,
										  finalSize,
										  timeTillMaturation,
										  offspring,
										  initialBodyStructure,
										  behaviorFile,
										  products,
										  tags
										 );
			
			initialBodyStructure.species = name;
			Gson gson = new Gson();
			System.out.println(gson.toJson(species));
			speciesInfo.put(name, species);
			
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