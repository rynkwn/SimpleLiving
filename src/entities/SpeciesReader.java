package entities;

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
		Scanner scan;
		
		try {
			scan = new Scanner(speciesFile);
			
			String name = scan.nextLine();
			int gestationPeriod = Integer.parseInt(scan.nextLine());
			int minOffspringProduced = Integer.parseInt(scan.nextLine());
			int maxOffspringProduced = Integer.parseInt(scan.nextLine());
			Range numberOffspringProduced = new Range(minOffspringProduced, maxOffspringProduced);
			long initialSize = Long.parseLong(scan.nextLine());
			long finalSize = Long.parseLong(scan.nextLine());
			
			// Get offspring species probabilities.
			TreeMap<Double, String> offspringProbabilities = new TreeMap<Double, String>();
			String nextLine = scan.nextLine();
			while(! nextLine.contains(".behavior")) {
				int indexOfSpace = nextLine.indexOf(' ');
				String speciesName = nextLine.substring(0, indexOfSpace);
				String probability = nextLine.substring(indexOfSpace + 1);
				
				offspringProbabilities.put(Double.parseDouble(probability), speciesName);
				nextLine = scan.nextLine();
			}
			
			ChanceOutcomes<String> offspring = new ChanceOutcomes<String>(offspringProbabilities);
			
			// nextLine is now the behavior file.
			String behaviorFile = nextLine;
			
			nextLine = scan.nextLine();
			Body initialBodyStructure = new Body();
			
			if(nextLine.contains("body")) {
				String partsString = scan.nextLine();
				
				while(!partsString.contains("End")) {
					String[] bodypartNames = partsString.split("\\s+");
					
					BodyPart bp = new BodyPart(bodypartNames[0]);
					
					for(int i = 1; i < bodypartNames.length; i++) {
						bp.addOrgan(new BodyPart(bodypartNames[i]));
					}
					
					initialBodyStructure.addPart(bp);
					partsString = scan.nextLine();
				}
			}
			
			// Now get tags.
			String[] tagsLine = scan.nextLine().split(";");
			ArrayList<String> tags = new ArrayList<String>();
			
			for(String tag : tagsLine) {
				tags.add(tag);
			}
			
			// Now make the Species object
			Species species = new Species(name,
										  gestationPeriod,
										  numberOffspringProduced,
										  initialSize,
										  finalSize,
										  offspring,
										  initialBodyStructure,
										  behaviorFile,
										  tags
										 );
			
			speciesInfo.put(name, species);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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