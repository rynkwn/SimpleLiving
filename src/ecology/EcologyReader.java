package ecology;

import ecology.WildSpecies;
import util.KeyValueReader;
import util.Range;
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
			String type = reader.getSingle("TYPE");

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
			
			int minTemp = reader.getInt("MIN_TEMPERATURE");
			int maxTemp = reader.getInt("MAX_TEMPERATURE");
			Range temperatureTolerance = new Range(minTemp, maxTemp);
			
			int power = reader.getInt("POWER");

			double waterNeed = reader.getDouble("WATER");
			double carbonNeed = reader.getDouble("CARBON");
			double nitrogenNeed = reader.getDouble("NITROGEN");
			double potassiumNeed = reader.getDouble("POTASSIUM");
			double calciumNeed = reader.getDouble("CALCIUM");
			double phosphorusNeed = reader.getDouble("PHOSPHORUS");
			double saltNeed = reader.getDouble("SALT");
			
			Macronutrient macronutrient = new Macronutrient(0);
			macronutrient.set("water", waterNeed);
			macronutrient.set("carbon", carbonNeed);
			macronutrient.set("nitrogen", nitrogenNeed);
			macronutrient.set("potassium", potassiumNeed);
			macronutrient.set("calcium", calciumNeed);
			macronutrient.set("phosphorus", phosphorusNeed);
			macronutrient.set("salt", saltNeed);
			
			double turnWater = reader.getDouble("PER_TURN_WATER");
			double turnCarbon = reader.getDouble("PER_TURN_CARBON");
			double turnNitrogen = reader.getDouble("PER_TURN_NITROGEN");
			double turnPotassium = reader.getDouble("PER_TURN_POTASSIUM");
			double turnCalcium = reader.getDouble("PER_TURN_CALCIUM");
			double turnPhosphorus = reader.getDouble("PER_TURN_PHOSPHORUS");
			double turnSalt = reader.getDouble("PER_TURN_SALT");
			
			Macronutrient turnNutrient = new Macronutrient(0);
			turnNutrient.set("water", turnWater);
			turnNutrient.set("carbon", turnCarbon);
			turnNutrient.set("nitrogen", turnNitrogen);
			turnNutrient.set("potassium", turnPotassium);
			turnNutrient.set("calcium", turnCalcium);
			turnNutrient.set("phosphorus", turnPhosphorus);
			turnNutrient.set("salt", turnSalt);
			
			double deathWater = reader.getDouble("ON_DEATH_WATER");
			double deathCarbon = reader.getDouble("ON_DEATH_CARBON");
			double deathNitrogen = reader.getDouble("ON_DEATH_NITROGEN");
			double deathPotassium = reader.getDouble("ON_DEATH_POTASSIUM");
			double deathCalcium = reader.getDouble("ON_DEATH_CALCIUM");
			double deathPhosphorus = reader.getDouble("ON_DEATH_PHOSPHORUS");
			double deathSalt = reader.getDouble("ON_DEATH_SALT");
			
			Macronutrient deathNutrient = new Macronutrient(0);
			deathNutrient.set("water", deathWater);
			deathNutrient.set("carbon", deathCarbon);
			deathNutrient.set("nitrogen", deathNitrogen);
			deathNutrient.set("potassium", deathPotassium);
			deathNutrient.set("calcium", deathCalcium);
			deathNutrient.set("phosphorus", deathPhosphorus);
			deathNutrient.set("salt", deathSalt);
			
			double totalMass = macronutrient.nutrientSum();

			WildSpecies wildCreature = new WildSpecies(name,
					type,
					captureResult,
					harvestResult,
					consumptionType,
					reproductionRate,
					temperatureTolerance,
					new Macronutrient(macronutrient),
					new Macronutrient(turnNutrient),
					new Macronutrient(deathNutrient),
					totalMass,
					power
					);
			
			wildInfo.put(name, wildCreature);

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
