package ecology;

import ecology.WildSpecies;
import util.KeyValueReader;
import data.Macronutrient;

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
		
		KeyValueReader reader = new KeyValueReader();
		Scanner scan;
		
		try {
			scan = new Scanner(ecologyFile);

			while(scan.hasNextLine()) {
				reader.readLine(scan.nextLine());
			}

			String name = reader.getSingle("NAME");

			// Set up the Harvest HashMap.
			List<String> harvestList = reader.getList("HARVESTABLE");
			HashMap<String, Integer> harvestResult = new HashMap<String, Integer>();
			for(int i = 0; i < harvestList.size(); i += 2) {
				harvestResult.put(harvestList.get(i), Integer.parseInt(harvestList.get(i + 1)));
			}

			String captureResult = "None";
			try{
				captureResult = reader.getSingle("CAPTURE_RESULT");
			} catch(NullPointerException e) { }
			
			String consumptionType = reader.getSingle("CONSUMPTION");
			double reproductionRate = reader.getDouble("REPRODUCTION_RATE");
			
			int power = reader.getInt("POWER");

			double waterNeed = reader.getDouble("WATER");
			double carbonNeed = reader.getDouble("CARBON");
			double nitrogenNeed = reader.getDouble("NITROGEN");
			double potassiumNeed = reader.getDouble("POTASSIUM");
			double calciumNeed = reader.getDouble("CALCIUM");
			double phosphorusNeed = reader.getDouble("PHOSPHORUS");
			double saltNeed = reader.getDouble("SALT");

			Macronutrient macronutrient = new Macronutrient(waterNeed, 
				carbonNeed, 
				nitrogenNeed,
				potassiumNeed,
				calciumNeed,
				phosphorusNeed,
				saltNeed);
			
			double totalMass = macronutrient.nutrientSum();

			WildSpecies wildCreature = new WildSpecies(name,
				captureResult,
				harvestResult,
				consumptionType,
				reproductionRate,
				new Macronutrient(macronutrient),
				totalMass,
				power
				);
			
			wildInfo.put(name, wildCreature);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static WildSpecies getEcologyObject(String name) {
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